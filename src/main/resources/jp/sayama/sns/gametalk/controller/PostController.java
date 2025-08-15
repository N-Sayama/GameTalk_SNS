package jp.sayama.sns.gametalk.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.sayama.sns.gametalk.model.Post;
import jp.sayama.sns.gametalk.model.Profile;
import jp.sayama.sns.gametalk.model.User;
import jp.sayama.sns.gametalk.repository.ProfileRepository;
import jp.sayama.sns.gametalk.repository.mybatis.PostMapper;
import jp.sayama.sns.gametalk.service.PostService;
import jp.sayama.sns.gametalk.service.UserService;// import文も修正

@Controller
@RequestMapping("/gametalk")
public class PostController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ProfileRepository profileRepository;

    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("post", new Post()); 
        return "home/index";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, 
    						@RequestParam(value = "mediaFile", required = false) MultipartFile mediaFile, Principal principal) {
        post.setAuthor(principal.getName());
        
        if (mediaFile != null && !mediaFile.isEmpty()) {
            String uploadDir = "src/main/resources/static/uploads/";
            String fileName = UUID.randomUUID() + "_" + mediaFile.getOriginalFilename();

            try {
                Path filePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(filePath.getParent());
                Files.copy(mediaFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                post.setMediaPath("/uploads/" + fileName);

                String contentType = mediaFile.getContentType();
                if (contentType != null) {
                    if (contentType.startsWith("image")) {
                        post.setMediaType("image");
                    } else if (contentType.startsWith("video")) {
                        post.setMediaType("video");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("保存された画像パス: " + post.getMediaPath());


        postService.createPost(post);
        return "redirect:/gametalk/posts";
    }
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new Post()); // 空の投稿オブジェクトを渡す
        return "gametalk/post_form";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // 例：ログイン中のユーザー情報を取得
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);  // ←これがないとThymeleafでuserはnullになる
        return "gametalk/profile";
    }
    
    @GetMapping("/posts")
    public String showPosts(Model model) {
        List<Post> posts = postMapper.findAll();

        // 投稿にプロフィール情報を紐付け
        for (Post post : posts) {
            Optional<Profile> optional = profileRepository.findByUserName(post.getAuthor());
            optional.ifPresent(post::setProfile); // Post に Profile をセット
        }

        model.addAttribute("posts", posts);
        return "gametalk/post-list";
    }



}