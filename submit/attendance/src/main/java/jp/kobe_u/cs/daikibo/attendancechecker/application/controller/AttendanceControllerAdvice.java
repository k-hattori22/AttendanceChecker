package jp.kobe_u.cs.daikibo.attendancechecker.application.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserSession;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.exception.AttendanceCheckerException;

@ControllerAdvice
public class AttendanceControllerAdvice {
    /**
     * 予約アプリのビジネス例外のハンドリング
     * @return カスタム・エラーページを表示
     */
    @ExceptionHandler(AttendanceCheckerException.class)
    public String handleYoyakuException(AttendanceCheckerException ex, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserSession user = (UserSession)auth.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("error", ex);
        return "error";
    }
    /**
     * Spring バリデーション例外のハンドリング．ビジネス例外にラップしなおしてハンドラを呼び出す
     */
    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex, Model model) {
        //ビジネス例外でラップして，上のハンドラに投げる
        AttendanceCheckerException e = new AttendanceCheckerException(AttendanceCheckerException.INVALID_ATTENDANCE_FORM, ex.getMessage(), ex);
        return handleYoyakuException(e, model);
    }
    /**
     * その他，一般例外のハンドリング．ビジネス例外にラップしなおしてハンドラを呼び出す
     */
    @ExceptionHandler(Exception.class)
    public String handleBindException(Exception ex, Model model) {
        //ビジネス例外でラップして，上のハンドラに投げる
        AttendanceCheckerException e = new AttendanceCheckerException(AttendanceCheckerException.ERROR, ex.getMessage(), ex);
        ex.printStackTrace();
        return handleYoyakuException(e, model);
    }
}
