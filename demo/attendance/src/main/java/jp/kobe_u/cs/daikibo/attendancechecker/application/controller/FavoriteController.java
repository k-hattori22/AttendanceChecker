package jp.kobe_u.cs.daikibo.attendancechecker.application.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.engine.AttributeName;

import aj.org.objectweb.asm.Attribute;
import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.FavoriteForm;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Favorite;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.FavoriteStatus;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.AttendanceService;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.FavoriteService;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.UserService;

@Controller
public class FavoriteController {
    @Autowired
    private FavoriteService fs;
    @Autowired
    private AttendanceService as;
    @Autowired
    private UserService us;

    //学生情報画面を表示
    @GetMapping("/home/{userId}")
    String showStudentInfo(@PathVariable String userId, Model model) {

        List<Favorite> list = fs.getAllFavorites(); //全お気に入りを取得
        model.addAttribute("favoriteList", list);
        model.addAttribute("favoriteForm", new FavoriteForm());
        return "favorite_list";
    }

    @PostMapping("/read")
    public String postFavorite(@ModelAttribute("favoriteForm") FavoriteForm form, Model model) {
        Favorite fvt = new Favorite();
        fvt.setFavoritedUserId(form.getFavoritedUserId());
        fvt.setFavoritingUserId(form.getFavoritingUserId());
        fs.createFavorite(fvt);
        return "redirect:/read";
    }

    // @PostMapping("/read")
    // public String postFavorite(@PathVariable String userId, Model model) {
    //     Favorite fvt = new Favorite();
    //     User user = us.getUser(userId);
    //     fvt.setUserId(userId);
    //     FavoriteStatus status = fvt.getStatus();
    //     switch (status) {
    //         case YellowStar:
    //             fs.changeToBlack(userId);
    //             break;

    //         case BlackStar:
    //             fs.changeToYellow(userId);
    //             break;
    //     }
    //     fvt.setStatus(user.getStatus());
    //     fvt.setCreatedAt(new Date());
    //     fs.createFavorite(fvt);
    //     return "redirect:/read";
    // }
}
