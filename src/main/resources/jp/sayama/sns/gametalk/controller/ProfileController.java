package jp.sayama.sns.gametalk.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.sayama.sns.gametalk.model.Post;
import jp.sayama.sns.gametalk.model.Profile;
import jp.sayama.sns.gametalk.repository.ProfileRepository;
import jp.sayama.sns.gametalk.repository.mybatis.FollowMapper;

@Controller
@RequestMapping("/gametalk/profile")
public class ProfileController {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
    private FollowMapper followMapper;
	
	@GetMapping("/")
	public String index(Model model, Principal principal) {
		String loginName = principal.getName();
		Optional<Profile> optionalProfile = profileRepository.findByUserName(loginName);
		if (optionalProfile.isEmpty()) {
			return "redirect:/gametalk/profile/create";
		}
	    Profile profile = optionalProfile.get();
		
	    /*List<Profile> profiles = profileRepository.findAll();
	    Profile profile;
	    if (profiles.isEmpty()) {
	    	profile = new Profile();
	    }else {
	    	profile = profiles.get(profiles.size() - 1);
	    }

	    // 最後に登録されたプロフィールを表示
	    System.out.println("表示するプロフィール: " + profile.getName() + ", アイコン: " + profile.getIconPath());
	    System.out.println(profile.getIconPath());
	    */
	    model.addAttribute("profile", profile);
	    model.addAttribute("username", loginName);
	    return "gametalk/profile";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("post", new Post());

	// レンダリングに利用するテンプレート名を返す。
	return "gametalk/create";
	}
	
	@PostMapping("/register")
	public String register(
			@RequestParam("name") String name,
		    @RequestParam("games") String games,
		    @RequestParam("comment") String comment,
		    @RequestParam("extra") String extra,
		    @RequestParam(value = "icon", required = false) MultipartFile iconFile,
			Principal principal) {
				String loginName = principal.getName();
			    String iconPath = "/uploads/nukota02.png";
			
		
		//String iconPath = null;
	
		  if (iconFile != null && !iconFile.isEmpty()) {
		        String fileName = iconFile.getOriginalFilename();
		        Path uploadPath = Paths.get("src/main/resources/static/uploads");
		        try {
		            Files.createDirectories(uploadPath);
		            Path filePath = uploadPath.resolve(fileName);
		            Files.copy(iconFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		            iconPath = "/uploads/" + fileName;
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		// ✅ 既にプロフィールがあるかチェック
		    Optional<Profile> existingProfile = profileRepository.findByUserName(loginName);

		    if (existingProfile.isPresent()) {
		        // ✅ すでにある → update する
		        Profile profile = existingProfile.get();
		        profile.setName(name);
		        profile.setGames(games);
		        profile.setComment(comment);
		        profile.setExtra(extra);
		        profile.setIconPath(iconPath); // 上書きあり
		        profileRepository.update(profile);

		    } else {
		        // ✅ 初めての登録 → insert
		        Profile profile = new Profile(0L, name, games, comment, extra, iconPath);
		        profile.setUserName(loginName);
		        profileRepository.register(profile);
		    }
		    /*Profile profile = new Profile(0L, name, games, comment, extra, iconPath);
		    profile.setUserName(loginName);
		    profileRepository.register(profile);*/
		    
		    return "redirect:/gametalk/profile/";	
	}
			
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model, Principal principal) {
		String loginName = principal.getName();
		/*Optional<Profile> optional = profileRepository.findById(id);
	    List<Profile> profiles = profileRepository.findAll();
	    for (Profile p : profiles) {
	    	if (p.getId().equals(id) && loginName.equals(p.getUserName())) {
	            model.addAttribute("profile", p);
	            return "gametalk/edit"; 
	        }
	    }*/
		
		Optional<Profile> optional = profileRepository.findById(id);
		System.out.println("アクセスされたID：" + id);
		if (optional.isPresent()) {
	        Profile profile = optional.get();
	        	System.out.println("ログイン中ユーザー：" + loginName);
	            System.out.println("編集対象ユーザー：" + profile.getUserName());
	            model.addAttribute("profile", profile);
	            return "gametalk/edit";
	       // if (loginName.equals(profile.getUserName())) {
	          //  model.addAttribute("profile", profile);
	       // }
	            
	    }else {
	        System.out.println("プロフィールが見つかりませんでした");
	    }
	    return "redirect:/gametalk/profile/";
	}

	@PostMapping("/update")
	public String update(@RequestParam("id") Long id,
	                     @RequestParam("name") String name,
	                     @RequestParam("games") String games,
	                     @RequestParam("comment") String comment,
	                     @RequestParam("extra") String extra,
	                     @RequestParam(value = "icon", required = false) MultipartFile iconFile,
	                     Principal principal) {
		
		String loginName = principal.getName();

	    // ✅ 修正ポイント①：既存プロフィールを取得して iconPath を引き継ぐ
	    String currentIconPath = null;
	    List<Profile> profiles = profileRepository.findAll();
	    for (Profile p : profiles) {
	        if (p.getId().equals(id)) {
	            currentIconPath = p.getIconPath();
	            break;
	        }
	    }

	    String iconPath = currentIconPath;

	    // ✅ 修正ポイント②：新しいアイコンがアップロードされた場合のみ上書き
	    if (iconFile != null && !iconFile.isEmpty()) {
	        String fileName = iconFile.getOriginalFilename();
	        Path uploadPath = Paths.get("src/main/resources/static/uploads");

	        try {
	            Files.createDirectories(uploadPath);
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(iconFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	            iconPath = "/uploads/" + fileName; // 新しいアイコンパスに更新
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    // iconPath を保持したまま更新
	    Profile profile = new Profile(id, name, games, comment, extra, iconPath);
	    profile.setUserName(loginName);
	    profileRepository.update(profile);
	    return "redirect:/gametalk/profile/";
	}
	
	@GetMapping("/view/{username}")//プロフィール閲覧機能
	public String viewOtherProfile(@PathVariable("username") String username, Model model, Principal principal) {
	    Optional<Profile> optionalProfile = profileRepository.findByUserName(username);
	    if (optionalProfile.isPresent()) {
	        Profile profile = optionalProfile.get();
	        model.addAttribute("profile", profile);
	        model.addAttribute("username", username);
	        
	     // ✅ フォロー済かどうかを判定
	        String currentUser = principal.getName();
	        boolean isFollowing = followMapper.isFollowing(currentUser, username);
	        model.addAttribute("isFollowing", isFollowing);
	        
	        int followerCount = followMapper.countFollowers(username);
	        int followingCount = followMapper.countFollowing(username);
	        model.addAttribute("followerCount", followerCount);
	        model.addAttribute("followingCount", followingCount);
	        
	        return "gametalk/profile-view";  // 次で作るテンプレート
	    } else {
	        return "redirect:/gametalk/profile/";  // 該当ユーザーがいない場合
	    }
	}
	
	@GetMapping("/search-page")//検索
	public String searchPage() {
	    return "gametalk/search";
	}
	@GetMapping("/search")
	public String searchProfiles(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
	    if (keyword == null || keyword.isBlank()) {
	        return "redirect:/gametalk/profile/search-page"; // もしくはエラーメッセージを表示
	    }
	    List<Profile> results = profileRepository.findByNameContaining(keyword);
	    model.addAttribute("results", results);
	    model.addAttribute("keyword", keyword);
	    return "gametalk/search-result";
	}
}