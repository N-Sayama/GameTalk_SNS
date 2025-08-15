package jp.sayama.sns.gametalk.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.sayama.sns.gametalk.repository.mybatis.FollowMapper;

@Controller
@RequestMapping("/gametalk/follow")
public class FollowController {

    @Autowired
    private FollowMapper followMapper;

    @PostMapping("/follow/{userName}")
    public String follow(@PathVariable String userName, Principal principal) {
        followMapper.follow(principal.getName(), userName);
        return "redirect:/gametalk/profile/view/" + userName;
    }

    @PostMapping("/unfollow/{userName}")
    public String unfollow(@PathVariable String userName, Principal principal) {
        followMapper.unfollow(principal.getName(), userName);
        return "redirect:/gametalk/profile/view/" + userName;
    }
    @GetMapping("/followers/{userName}")
    public String showFollowers(@PathVariable String userName, Model model) {
        List<String> followers = followMapper.findFollowers(userName);
        model.addAttribute("title", "フォロワー一覧");
        model.addAttribute("userList", followers);
        return "gametalk/follow-list";
    }

    @GetMapping("/following/{userName}")
    public String showFollowing(@PathVariable String userName, Model model) {
        List<String> following = followMapper.findFollowing(userName);
        model.addAttribute("title", "フォロー中一覧");
        model.addAttribute("userList", following);
        return "gametalk/follow-list";
    }

}