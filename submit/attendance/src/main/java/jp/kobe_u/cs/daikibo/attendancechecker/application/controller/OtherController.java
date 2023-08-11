package jp.kobe_u.cs.daikibo.attendancechecker.application.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserSession;
import jp.kobe_u.cs.daikibo.attendancechecker.application.utils.AppUtils;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Attendance;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Seat;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.AttendanceService;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.SeatService;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.UserService;

@Controller
public class OtherController {
    @Autowired
    private UserService us;
    @Autowired
    private AttendanceService as;
    @Autowired
    private SeatService ss;

    /*---------- UC1: 出退勤を登録する ----------*/

    @GetMapping("/read")
	public String showCardReaderPage(Model model) {
        UserSession user = AppUtils.getUserSession();
        model.addAttribute("user", user);
		return "cardreader";
	}

    @PostMapping("/read")
    public String postAttendance(@RequestParam String userId) {
        Attendance att = new Attendance();
        User user = us.getUser(userId);
        att.setUserId(userId);
        att.setStatus(us.changeAttendanceStatus(user));
        as.createAttendance(att);
        return "redirect:/read";
    }

    @GetMapping("/")
	public String redirectToMainPage(Model model) {
        UserSession user = AppUtils.getUserSession();
        model.addAttribute("user", user);

        List<Seat> vacants = ss.getVacants();
        model.addAttribute("vacants", vacants);
        List<Seat> presents = ss.getPresents();
        model.addAttribute("presents", presents);
        List<Seat> absents = ss.getAbsents();
        model.addAttribute("absents", absents);

        // Map(seatId, User)の生成
		List<User> users = us.getGeneralUsers();
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getSeatId(), u -> u));
		model.addAttribute("userMap", userMap);

        List<User> favorites = us.getAllFavorites(user);
        model.addAttribute("favorites", favorites);
		return "index";
	}

    @GetMapping("/create-user")
    public String showVacants(Model model) {
        UserSession user = AppUtils.getUserSession();
        model.addAttribute("user", user);

        List<Seat> vacants = ss.getVacants();
        model.addAttribute("vacants", vacants);

		return "seat/vacants";
    }
}
