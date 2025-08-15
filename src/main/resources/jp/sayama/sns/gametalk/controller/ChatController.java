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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.sayama.sns.gametalk.model.Message;
import jp.sayama.sns.gametalk.model.User;
import jp.sayama.sns.gametalk.repository.mybatis.FollowMapper;
import jp.sayama.sns.gametalk.service.MessageService;
import jp.sayama.sns.gametalk.service.UserService;

@Controller
@RequestMapping("/gametalk/chat")
public class ChatController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private FollowMapper followMapper;

    // 一覧：まずは従来通り。※推奨は findFollowingsForChat(me) に切替
    @GetMapping
    public String showUserList(Model model, Principal principal,
                               @RequestParam(value = "needFollow", required = false) String needFollow) {
        String me = principal.getName();
     // ★ 自分がフォローしているユーザー名を followers から取得（login_users / users の差違を無視できる）
        List<User> users = userService.findAllExcept(me); // ← 両テーブル統合済み
        
        model.addAttribute("users", users); 
        model.addAttribute("needFollow", needFollow);
        return "chat/user-list";
    }

    // DM画面：片側フォロー（自分→相手）必須＋NPE対策
    @GetMapping("/{username}")
    public String chatWithUser(@PathVariable String username,
                               Principal principal,
                               Model model,
                               RedirectAttributes ra) {
        String me = principal.getName();

        User otherUser   = userService.findByUsername(username);

        // ★ 存在チェック（NPE防止）
        if (otherUser == null) {
            ra.addFlashAttribute("error", "ユーザー '" + username + "' が見つかりません。");
            return "redirect:/gametalk/chat";
        }

        boolean isFollowing    = followMapper.isFollowing(me, username);        // 自分→相手
        boolean isFollowedBy   = followMapper.isFollowing(username, me);        // 相手→自分
        boolean isMutualFollow = isFollowing && isFollowedBy;

        // ★ 片側フォロー入室ガード（自分→相手）
        if (!isFollowing) {
            ra.addFlashAttribute("info", "この相手にDMするには、まずフォローしてください。");
            return "redirect:/gametalk/chat?needFollow=" + username;
        }

        // メッセージ取得
        List<Message> messages = messageService.getMessagesBetweenUsers(me, username);

        // View用
        model.addAttribute("currentUserName", me);
        model.addAttribute("otherUser", otherUser);
        model.addAttribute("messages", messages);
        model.addAttribute("isFollowing", isFollowing);
        model.addAttribute("isFollowedBy", isFollowedBy);
        model.addAttribute("isMutualFollow", isMutualFollow);

        return "chat/chat-room";
    }

    // 送信：宛先存在チェック＋例外をフラッシュして適切リダイレクト
    @PostMapping("/send")
    public String sendMessage(@RequestParam("toUsername") String toUsername,
                              @RequestParam("content") String content,
                              Principal principal,
                              RedirectAttributes ra) {
        String me = principal.getName();

        // ★ 宛先存在チェック
        User recipient = userService.findByUsername(toUsername);
        if (recipient == null) {
            ra.addFlashAttribute("error", "宛先ユーザー '" + toUsername + "' が見つかりません。");
            return "redirect:/gametalk/chat";
        }

        try {
            messageService.sendMessage(me, toUsername, content);
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());     // 空文字/長すぎ
            return "redirect:/gametalk/chat/" + toUsername;
        } catch (SecurityException e) {
            ra.addFlashAttribute("info", e.getMessage());      // 未フォロー
            return "redirect:/gametalk/chat?needFollow=" + toUsername;
        }

        return "redirect:/gametalk/chat/" + toUsername;
    }
}
