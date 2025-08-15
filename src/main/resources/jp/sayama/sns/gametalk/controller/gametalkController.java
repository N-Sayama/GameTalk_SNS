package jp.sayama.sns.gametalk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* コントローラー
* {@code @RestController}を付与しているため、HTTPレスポンスとしてJSONを返却する。
*/

@RestController
public class gametalkController {
	
	/**
	* indexメソッド
	* {@code @GetMapping}を付与しているため、パス "/" に対するHTTPのGETメソッドを利用したリクエストをマッピングする。
	* @return JSON
	*/
	@GetMapping("/gametalk")
	public String index() {
	return "profile";
	}

}
