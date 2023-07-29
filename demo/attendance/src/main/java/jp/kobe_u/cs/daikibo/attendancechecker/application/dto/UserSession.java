package jp.kobe_u.cs.daikibo.attendancechecker.application.dto;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;

/**
 * ユーザのセッション情報．ログインが成功するとSpring Securityが生成する
 * 予約システムのユーザをアダプタパターンでラップしている
 */
public class UserSession implements UserDetails {
    private static final long serialVersionUID = 5481546178797722247L;
    // 予約システムのユーザ（ラップされている）
    private User user;
    // このユーザの権限．ユーザのroleによって決まる
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * コンストラクタ
     * @param user
     * @param authorities
     */
    public UserSession(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    /**
     * 権限を取得
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * パスワードを取得する．Spring Securityが呼び出す
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    /**
     * ユーザID（userId）を取得する．Spring Securityが呼び出す
     */
    @Override
    public String getUsername() {
        return user.getId();
    }
    /**
     * アカウントが期限切れになっていないか？Spring Securityが呼び出す
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * アカウントがロックされていないか？Spring Securityが呼び出す
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * 認証が期限切れしていないか？Spring Securityが呼び出す
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * アカウントが有効か？Spring Securityが呼び出す
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /* ------------------- オリジナルのユーザ情報のゲッター -------------------*/
    public String getName() {
        return user.getName();
    }

    public String getBossName() {
        return user.getBossName();
    }

    public String getGrade() {
        return user.getGrade();
    }

    public String getSeat() {
        return user.getSeat();
    }

    public User.Role getRole() {
        return user.getRole();
    }
}
