package jp.kobe_u.cs.daikibo.attendancechecker.application.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserSession;
import jp.kobe_u.cs.daikibo.attendancechecker.application.utils.AppUtils;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Attendance;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.AttendanceStatus;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.AttendanceService;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.UserService;

@Controller
public class CardReaderController {
    @Autowired
    private UserService us;
    @Autowired
    private AttendanceService as;

    @GetMapping("/read")
	public String showCardReaderPage(Model model) {
        UserSession user = AppUtils.getUserSession();
        model.addAttribute("user", user);
		return "cardreader";
	}

    @PostMapping("/read")
    public String postAttendance(@RequestParam String userId, Model model) {
        Attendance att = new Attendance();
        User user = us.getUser(userId);
        att.setUserId(userId);
        AttendanceStatus status = user.getStatus();
        switch (status) {
            case PRESENT:
                us.changeToAbsent(userId);
                break;

            case ABSENT:
                us.changeToPresent(userId);
                break;
        }
        att.setStatus(user.getStatus());
        att.setCreatedAt(new Date());
        as.createAttendance(att);
        return "redirect:/read";
    }
}
