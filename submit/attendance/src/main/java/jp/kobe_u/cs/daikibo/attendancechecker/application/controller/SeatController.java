package jp.kobe_u.cs.daikibo.attendancechecker.application.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.SeatForm;
import jp.kobe_u.cs.daikibo.attendancechecker.application.utils.AppUtils;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Seat;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.SeatService;

@Controller
@RequestMapping("/seats")
public class SeatController {
    @Autowired
    private SeatService ss;

    @GetMapping("")
    public String showList(Model model) {
        List<Seat> list = ss.getAllSeats();
        model.addAttribute("seats", list);
        model.addAttribute("user", AppUtils.getUserSession());
        return "seat/list";
    }

    @GetMapping("/{seatId}")
    String showSeatInfo(@PathVariable Long seatId, Model model) {
        Seat seat = ss.getSeat(seatId);
        model.addAttribute("seatForm", seat);
        model.addAttribute("user", AppUtils.getUserSession());
        return "seat/info";
    }

    @GetMapping("/create")
    public String showForm(Model model) {
        SeatForm sf = new SeatForm();
        sf.setPosFromLeft(new BigDecimal(0.0));
        sf.setPosFromTop(new BigDecimal(0.0));
        model.addAttribute("seatForm", sf);
        model.addAttribute("user", AppUtils.getUserSession());
        return "seat/form";
    }
    @PostMapping("/create")  
    public String create(@ModelAttribute("seatForm") @Validated SeatForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", AppUtils.getUserSession());
            return "seat/form";
        }
        ss.createSeat(form.toEntity());
        return "redirect:/";
    }

    @GetMapping("/{seatId}/update")
    public String showInfo(@PathVariable Long seatId, Model model) {
        Seat seat = ss.getSeat(seatId);
        model.addAttribute("seatForm", seat);
        model.addAttribute("user", AppUtils.getUserSession());
        return "seat/update";
    }
    @PostMapping("/{seatId}/update")
    public String update(@PathVariable Long seatId, @ModelAttribute("seatForm") @Validated Seat seat, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", AppUtils.getUserSession());
            return "seat/update";
        }
        ss.updateSeat(seatId, seat);
        return "redirect:/";
    }

    @GetMapping("/{seatId}/delete")
    String checkDelete(@PathVariable Long seatId, Model model) {
        Seat seat = ss.getSeat(seatId);
        model.addAttribute("seatForm", seat);
        model.addAttribute("user", AppUtils.getUserSession());
        return "seat/delete";        
    }
    @PostMapping("/{seatId}/delete")
    String delete(@PathVariable Long seatId) {
        ss.deleteSeat(seatId);
        return "redirect:/";        
    }
}
