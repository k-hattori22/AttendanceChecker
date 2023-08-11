package jp.kobe_u.cs.daikibo.attendancechecker.domain.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.kobe_u.cs.daikibo.attendancechecker.application.dto.UserSession;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.AttendanceStatus;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.exception.AttendanceCheckerException;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.FavoriteRepository;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

/**
 * ユーザサービス．予約システムのユーザのCRUD処理と，認証・認可に必要なサービスを提供する
 * Spring securityの UserDetailsServiceを実装している
 */
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository uRepo;
    @Autowired
    FavoriteRepository fRepo;
    @Autowired
    private PasswordEncoder passwordEncoder; //システム共通のパスワードエンコーダ（ユーザ作成・更新時に利用）

    /**
     * ユーザを新規作成
     * 
     * @param user
     * @return
     */
    public User createUser(User user) {
        String userId = user.getId();
        if (uRepo.existsById(userId)) {
            throw new AttendanceCheckerException(AttendanceCheckerException.USER_ALREADY_EXISTS, userId + ": User already exists");
        }
        // パスワードを暗号化
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        user.setCreatedAt(new Date());
        return uRepo.save(user);
    }

    /**
     * 全ユーザ取得
     * 
     * @return
     */
    public List<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        Iterable<User> all = uRepo.findAll();
        all.forEach(list::add);
        return list;
    }

    /**
     * 全GENERALユーザ取得
     * 
     * @return
     */
    public List<User> getGeneralUsers() {
        ArrayList<User> list = new ArrayList<>();
        Iterable<User> all = uRepo.findBySeatIdNotNull();
        all.forEach(list::add);
        return list;
    }

    /**
     * ユーザをIDで取得
     * 
     * @param userId
     * @return
     */
    public User getUser(String userId) {
        User user = uRepo.findById(userId)
                .orElseThrow(() -> new AttendanceCheckerException(AttendanceCheckerException.USER_NOT_FOUND, userId + ": No such user"));
        return user;
    }

    /**
     * ユーザを更新
     * 
     * @param userId
     * @param user
     * @return
     */
    public User updateUser(String userId, User user) {
        User orig = getUser(userId);
        if (!userId.equals(user.getId())) {
            System.err.println(userId + " " + user.getId());
            throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_USER_UPDATE, userId + ": userId cannot be updated");
        }
        // パスワードを暗号化
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        user.setCreatedAt(orig.getCreatedAt());
        user.setUpdatedAt(new Date());
        return uRepo.save(user);
    }

    /**
     * ユーザを消去する
     * 
     * @param userId
     */
    public void deleteUser(String userId) {
        User user = getUser(userId);
        uRepo.delete(user);
    }

    /**
     * お気に入りが存在するか
     * @param session
     * @param user
     * @return
     */
    public boolean existsFavorite(UserSession session, User user) {
        return fRepo.existsByFavoritingUserIdAndFavoritedUserId(session.getUsername(), user.getId());
    }

    /**
     * お気に入りしている全ユーザを取得
     * @param session
     * @return
     */
    public List<User> getAllFavorites(UserSession session) {
        return getGeneralUsers().stream()
                                .filter(user -> existsFavorite(session, user))
                                .toList();
    }

    /**
     * ユーザのステータスを出席に変える
     * @param user
     */
    public void changeToPresent(User user) {
        AttendanceStatus status = user.getStatus();
        //ステータスが出席の場合はエラー
        if (status == AttendanceStatus.PRESENT) {
            throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_USER_STATUS, user.getId() + ": User is already present");
        }
        user.setStatus(AttendanceStatus.PRESENT);
    }

    /**
     * ユーザのステータスを欠席に変える
     * @param user
     */
    public void changeToAbsent(User user) {
        AttendanceStatus status = user.getStatus();
        //ステータスが欠席の場合はエラー
        if (status == AttendanceStatus.ABSENT) {
            throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_USER_STATUS, user.getId() + ": User is still absent");
        }
        user.setStatus(AttendanceStatus.ABSENT);
    }

    /**
     * ユーザのステータスを変更し，変更後のステータスを返す
     * @param user
     */
    public AttendanceStatus changeAttendanceStatus(User user) {
        AttendanceStatus status = user.getStatus();
        switch (status) {
            case PRESENT:
                changeToAbsent(user);
                break;

            case ABSENT:
                changeToPresent(user);
                break;
        }
        uRepo.save(user);
        return user.getStatus();
    }

    /**
     * ユーザIDで検索して，ユーザ詳細を返すサービス．Spring Securityから呼ばれる
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // レポジトリからユーザを検索する
        User user = getUser(userId);

        // ユーザのロールに応じて，権限を追加していく
        List<GrantedAuthority> authorities = new ArrayList<>(); // 権限リスト
        User.Role role = user.getRole(); // ユーザの権限
        switch (role) {
            // 学生の時は，学生権限を追加
            case GENERAL:
                // 権限文字列には，ROLE_を付けないといけない．
                authorities.add(new SimpleGrantedAuthority("ROLE_GENERAL"));
                break;

            // カードリーダーの時は，カードリーダー権限を追加
            case CARDREADER:
                // 権限文字列には，ROLE_を付けないといけない．
                authorities.add(new SimpleGrantedAuthority("ROLE_CARDREADER"));
                break;

            // 管理者の時は，管理者権限を追加
            case ADMIN:
                // 権限文字列には，ROLE_を付けないといけない．
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
                
            // それ以外はエラー
            default:
                throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_USER_ROLE, role + ": Invalid user role");
        }

        // userからユーザセッション(UserDetailsの実装)を作成して返す
        UserSession userSession = new UserSession(user, authorities);

        return userSession;
    }

    /**
     * 管理者を登録する
     * @param userId
     * @param password
     */
    @Transactional
    public void registerAdmin(String userId, String password) {
        User user = new User(userId, passwordEncoder.encode(password), "システム管理者", null, null,
                null, null, User.Role.ADMIN, new Date(), null);
        uRepo.save(user);
    }

    /**
     * カードリーダーを登録する
     * @param userId
     * @param password
     */
    @Transactional
    public void registerCardReader(String userId, String password) {
        User user = new User(userId, passwordEncoder.encode(password), "カードリーダ", null, null,
                null, null, User.Role.CARDREADER, new Date(), null);
        uRepo.save(user);
    }
}
