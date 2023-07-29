package jp.kobe_u.cs.daikibo.attendancechecker.application.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserSession;
import jp.kobe_u.cs.daikibo.attendancechecker.application.utils.AppUtils;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.exception.AttendanceCheckerException;


@Controller
public class LoginController {
	/**
	 * ログイン画面を表示する．（具体的なログイン処理は，Spring Securityに任せる）
	 */
	@GetMapping("/login")
	public String showLoginForm(@RequestParam Map<String, String> params, Model model) {
		//パラメータ処理．ログアウト時は?logout, 認証失敗時は?errorが帰ってくる（WebSecurityConfig.java参照） 
		if (params.containsKey("logout")) {
			model.addAttribute("message", "ログアウトしました");
		} else if (params.containsKey("error")) {
			model.addAttribute("message", "ログインに失敗しました");
		} 

		//ログイン画面
		return "login";
	}

	@GetMapping("/")
	public String redirectToMainPage() {
		UserSession user = AppUtils.getUserSession();
		User.Role role = user.getRole(); // ユーザの権限
        switch (role) {
			case CARDREADER:
                return "redirect:/read";

            case ADMIN:

            case STUDENT:
                return "redirect:/home";
                
            // それ以外はエラー
            default:
                throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_USER_ROLE, role + ": Invalid user role");
        }
	}
}