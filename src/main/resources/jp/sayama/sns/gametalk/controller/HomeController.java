package jp.sayama.sns.gametalk.controller;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.sayama.sns.gametalk.controller.form.CreateLoginUserForm;
import jp.sayama.sns.gametalk.model.LoginUser;
import jp.sayama.sns.gametalk.repository.LoginUserRepository;


/* ホーム画面を管理するコントローラー*/
@Controller
public class HomeController {
	
	/**
	* ログインユーザーを管理するリポジトリ
	*/
	private final LoginUserRepository loginUserRepository;
	private final PasswordEncoder passwordEncoder;

	public HomeController(LoginUserRepository loginUserRepository, PasswordEncoder passwordEncoder) {
	// ログインユーザーを管理するリポジトリを初期化する。
	this.loginUserRepository = loginUserRepository;
	// パスワードのエンコーダーを初期化する。
	this.passwordEncoder = passwordEncoder;
	}

	/**ホーム画面を扱う。@return テンプレート*/
	@GetMapping("/")
	public String index() {
		// レンダリングに利用するテンプレート名を返す。
		return "home/index";
	}
	
	@GetMapping("/signin")
	public String create(Model model) {
	// フォームを表す変数createLoginUserFormを定義する。
	CreateLoginUserForm createLoginUserForm = new CreateLoginUserForm();
	// 変数createLoginUserFormをテンプレート側からcreateLoginUserFormという名前の変数として利用するための設定を追加する。
	model.addAttribute("createLoginUserForm", createLoginUserForm);
	// レンダリングに利用するテンプレート名を返す。
	return "home/signin";
	}
	
	@PostMapping("/signin")
	public String register(@ModelAttribute @Valid CreateLoginUserForm createLoginUserForm, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        return "home/signin";
	    }

	    String userName = createLoginUserForm.getUserName();
	    String rawPassword = createLoginUserForm.getPassword();
	    String encodedPassword = passwordEncoder.encode(rawPassword);

	    // LoginUser の enabled は Integer型なので 1 を設定
	    LoginUser loginUser = new LoginUser();
	    loginUser.setUserId(0L);
	    loginUser.setUserName(userName);
	    loginUser.setPassword(encodedPassword);
	    loginUser.setEnabled(true); // 有効を 1 に

	    loginUserRepository.register(loginUser);

	    return "redirect:/home/login";
	}

	/** ログイン画面を扱う。* @return テンプレート*/
	@GetMapping("/home/login")
	public String login() {
	// レンダリングに利用するテンプレート名を返す。
		return "home/login";
	}
	@GetMapping("/login")
	public String loginRedirect() {
	    return "redirect:/home/login";
	}
	
}