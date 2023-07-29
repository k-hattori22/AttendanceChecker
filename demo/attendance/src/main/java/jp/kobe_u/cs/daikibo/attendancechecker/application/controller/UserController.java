package jp.kobe_u.cs.daikibo.attendancechecker.application.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserSession;
import jp.kobe_u.cs.daikibo.attendancechecker.application.utils.AppUtils;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService us;

    @GetMapping("/home")
	public String redirectToMainPage(Model model) {
        UserSession user = AppUtils.getUserSession();
        model.addAttribute("user", user);
		List<User> users = us.getAllUsers();
		//座席順に並べ変える
		//Collections.sort(users, (u1, u2) -> u1.getSeat().compareTo(u2.getSeat()));
		model.addAttribute("users", users);
		return "attendance/main";
	}

    //学生情報画面を表示
    @GetMapping("/home/{userId}")
    String showStudentInfo(@PathVariable String userId, Model model) {
        User user = us.getUser(userId);
        model.addAttribute("user", user);
        return "student_info";
    }
}
