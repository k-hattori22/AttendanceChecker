package jp.kobe_u.cs.daikibo.attendancechecker.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserForm;
import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserSession;
import jp.kobe_u.cs.daikibo.attendancechecker.application.utils.AppUtils;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService us;

    @GetMapping("/users/{userId}")
    String showUserInfo(@PathVariable String userId, Model model) {
        UserSession user = AppUtils.getUserSession();
        model.addAttribute("user", user);
        User target = us.getUser(userId);
        model.addAttribute("userForm", target);
        String favoriteMessage = "お気に入り";
        model.addAttribute("favoriteMessage", favoriteMessage);
        favoriteMessage += us.existsFavorite(user, target) ? "追加" : "削除";
        return "user/info";
    }

    @GetMapping("/users")
    public String showList(Model model) {
        List<User> list = us.getGeneralUsers();
        model.addAttribute("userList", list);
        model.addAttribute("user", AppUtils.getUserSession());
        return "user/list";
    }

    @GetMapping("/create-user/{seatId}")
    public String showForm(@PathVariable Long seatId, Model model) {
        UserForm uf = new UserForm();
        uf.setSeatId(seatId);
        model.addAttribute("userForm", uf);
        model.addAttribute("user", AppUtils.getUserSession());
        return "user/form";
    }
    @PostMapping("/create-user/{seatId}")
    public String create(@ModelAttribute("userForm") @Validated UserForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", AppUtils.getUserSession());
            return "user/form";
        }
        us.createUser(form.toEntity());
        return "redirect:/";
    }

    @GetMapping("/users/{userId}/update")
    public String showInfo(@PathVariable String userId, Model model) {
        User user = us.getUser(userId);
        model.addAttribute("userForm", user);
        model.addAttribute("user", AppUtils.getUserSession());
        return "user/update";
    }
    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable String userId, @ModelAttribute("userForm") @Validated User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", AppUtils.getUserSession());
            return "user/update";
        }
        us.updateUser(userId, user);
        return "redirect:/";
    }

    @GetMapping("/users/{userId}/delete")
    String checkDelete(@PathVariable String userId, Model model) {
        User user = us.getUser(userId);
        model.addAttribute("userForm", user);
        model.addAttribute("user", AppUtils.getUserSession());
        return "user/delete";        
    }
    @PostMapping("/users/{userId}/delete")
    String delete(@PathVariable String userId) {
        us.deleteUser(userId);
        return "redirect:/";        
    }
}
