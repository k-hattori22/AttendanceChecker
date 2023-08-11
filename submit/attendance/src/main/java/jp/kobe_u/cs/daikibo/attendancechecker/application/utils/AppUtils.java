package jp.kobe_u.cs.daikibo.attendancechecker.application.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserSession;

/**
 * アプリののユーティリティ
 */
public class AppUtils {
    /**
     * ユーティリティ関数．ユーザセッションを取得する
     */
    public static UserSession getUserSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserSession userSession = (UserSession) auth.getPrincipal();
        return userSession;
    }
}
