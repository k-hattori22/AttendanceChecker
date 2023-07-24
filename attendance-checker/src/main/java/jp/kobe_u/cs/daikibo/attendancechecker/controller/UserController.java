package jp.kobe_u.cs.daikibo.attendancechecker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobe_u.cs.daikibo.attendancechecker.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.service.UserService;

public class UserController {
    @Autowired
    private UserService us;

    //学生情報画面を表示
    @GetMapping("/student/home/{id}")
    String showStudentInfo(@PathVariable String id, Model model) {
        User user = us.findById(id);
        model.addAttribute("user", user);
        return "student_info";
    }
}
