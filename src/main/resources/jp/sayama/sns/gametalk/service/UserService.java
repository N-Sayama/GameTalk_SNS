package jp.sayama.sns.gametalk.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.sayama.sns.gametalk.model.LoginUser;
import jp.sayama.sns.gametalk.model.User;
import jp.sayama.sns.gametalk.repository.mybatis.LoginUserMapper;
import jp.sayama.sns.gametalk.repository.mybatis.UserMapper;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper; // users テーブル

    @Autowired private LoginUserMapper loginUserMapper;

    /** users → login_users の順に検索して、最初に見つかった方を返す */
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        if (u != null) return u;

        LoginUser lu = loginUserMapper.findByUserName(username);
        return toUser(lu);
    }

    /** 両テーブルをマージして自分を除外・重複(username)排除 */
    public List<User> findAllExcept(String currentUserName) {
        List<User> merged = new ArrayList<>();
        merged.addAll(userMapper.findAllExcept(currentUserName));

        // login_users 側（LoginUser -> User に変換してから加える）
        for (LoginUser lu : loginUserMapper.findAllExcept(currentUserName)) {
            User u = toUser(lu);
            if (u != null) merged.add(u);
        }

        // username で重複排除 & 自分を除外
        LinkedHashMap<String, User> dedup = new LinkedHashMap<>();
        for (User u : merged) {
            if (u == null || u.getUsername() == null) continue;
            if (Objects.equals(u.getUsername(), currentUserName)) continue;
            dedup.putIfAbsent(u.getUsername(), u);
        }
        return new ArrayList<>(dedup.values());
    }
    
    private User toUser(LoginUser lu) {
        if (lu == null) return null;
        User u = new User();
        u.setId(lu.getUserId());
        u.setUsername(lu.getUserName());
        // 必要なら displayName 等もここでマッピング
        return u;
    }
}
