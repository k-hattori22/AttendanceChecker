package jp.kobe_u.cs.daikibo.attendancechecker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.service.UserService;

@Configuration
@EnableWebSecurity //(1) Spring Securityを使うための設定
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;         //アプリのユーザサービス
    @Autowired
    private PasswordEncoder passwordEncoder; //アプリ共通のパスワードエンコーダ
    @Autowired
    private AdminConfiguration adminConfig;   //管理者の設定
    @Autowired
    private CardReaderConfiguration cardreaderConfig;   //カードリーダーの設定

    @Override
    public void configure(WebSecurity web) throws Exception {
        //  (2) 全体に対するセキュリティ設定を行う
        //  特定のパスへのアクセスを認証の対象から外す
        web.ignoring().antMatchers("/img/**", "/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  (3) URLごとに異なるセキュリティ設定を行う
        //認可の設定
        http.authorizeRequests()
        .antMatchers("/login").permitAll()             //ログインページは誰でも許可
        .antMatchers("/create-user/**").hasRole("ADMIN")           //作成は管理者のみ許可
        .antMatchers("/users/*/update").hasRole("ADMIN")           //ユーザ管理は管理者のみ許可
        .antMatchers("/users/*/delete").hasRole("ADMIN")           //ユーザ管理は管理者のみ許可
        .antMatchers("/seats/**").hasRole("ADMIN")           //座席管理は管理者のみ許可
        .antMatchers("/users/*/favorite").hasRole("GENERAL")     //お気に入り管理は一般ユーザのみ許可
        .antMatchers("/me/**").hasRole("GENERAL")     //自分の情報があるのは一般ユーザのみ
        .antMatchers("/read").hasRole("CARDREADER")     //カード読み取りはカードリーダーのみ許可
        .anyRequest().authenticated();                 //それ以外は全て認証必要

        //ログインの設定
        http.formLogin()
            .loginPage("/login")                       // ログインページ
            .loginProcessingUrl("/authenticate")       // フォームのPOST先URL．認証処理を実行する
            .usernameParameter("userId")                  // ユーザ名に該当するリクエストパラメタ
            .passwordParameter("password")             // パスワードに該当するリクエストパラメタ
            .defaultSuccessUrl("/role-check", true)  // 成功時のページ (trueは以前どこにアクセスしてもここに遷移する設定)
            .failureUrl("/login?error");               // 失敗時のページ
        
        //ログアウトの設定
        http.logout()
            .logoutUrl("/logout")                      //ログアウトのURL
            .logoutSuccessUrl("/login?logout")         //ログアウト完了したらこのページへ
            .deleteCookies("JSESSIONID")               //クッキー削除
            .invalidateHttpSession(true)               //セッション情報消去
            .permitAll();                              //ログアウトはいつでもアクセスできる
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  (4) 認証方法の実装の設定を行う
        auth.userDetailsService(userService)   //認証はアプリのユーザサービスを使う
            .passwordEncoder(passwordEncoder); //パスワードはアプリ共通のものを使う
        // ついでにここで管理者ユーザを登録しておく
        userService.registerAdmin(adminConfig.getUsername(), adminConfig.getPassword());
        // ついでにここでカードリーダーを登録しておく
        userService.registerCardReader(cardreaderConfig.getUsername(), cardreaderConfig.getPassword());
    }
}
