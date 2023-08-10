package jp.kobe_u.cs.daikibo.attendancechecker.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.AttendanceStatus;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Favorite;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.FavoriteStatus;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.exception.AttendanceCheckerException;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.AttendanceRepository;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.FavoriteRepository;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.UserRepository;

@Service
public class FavoriteService {
    @Autowired
    FavoriteRepository fRepo;
    @Autowired
    AttendanceRepository aRepo;
    @Autowired
    UserRepository uRepo;

    /**
     *  お気に入りを新規作成
     * 
     * @param fvt
     * @return
     */
    public Favorite createFavorite(Favorite fvt) {
        Long fvtId = fvt.getId();
        if (fvtId!=null && fRepo.existsById(fvtId)) {
            throw new AttendanceCheckerException(AttendanceCheckerException.FAVORITE_ALREADY_EXISTS, fvtId + ": Favorite already exists");
        }
        fvt.setCreatedAt(new Date());
        return fRepo.save(fvt);
    }

    /**
     * 全お気に入り取得
     * 
     * @return
     */
    public List<Favorite> getAllFavorites() {
        ArrayList<Favorite> list = new ArrayList<>();
        Iterable<Favorite> all = fRepo.findAll();
        all.forEach(list::add);
        return list;
    }

    /**
     * お気に入りをIDで取得
     * 
     * @param fvtId
     * @return
     */
    public Favorite getFavorite(Long fvtId) {
        Favorite fvt = fRepo.findById(fvtId)
                .orElseThrow(() -> new AttendanceCheckerException(AttendanceCheckerException.FAVORITE_NOT_FOUND, fvtId + ": No such favorite"));
        return fvt;
    }

    // /**
    //  * お気に入りを更新
    //  * 
    //  * @param fvtId
    //  * @param fvt
    //  * @return
    //  */
    // public Favorite updateFavorite(Long fvtId, Favorite fvt) {
    //     Favorite orig = getFavorite(fvtId);
    //     fvt.setCreatedAt(orig.getCreatedAt());
    //     return fRepo.save(fvt);
    // }

    /**
     * お気に入りを消去する
     * 
     * @param fvtId
     */
    public void deleteFavorite(Long fvtId) {
        Favorite fvt = getFavorite(fvtId);
        fRepo.delete(fvt);
    }

    /**
     * お気に入りのステータスを登録状態に変える
     * @param fvtId
     */
    public void changeToYellowStar(Long fvtId) {
        Favorite fvt = getFavorite(fvtId);
        FavoriteStatus status = fvt.getStatus();
        //ステータスが出席の場合はエラー
        if (status == FavoriteStatus.YellowStar) {
            throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_FAVORITE_STATUS, fvtId + ": FavoriteStatus is already YellowStar");
        }
        fvt.setStatus(FavoriteStatus.YellowStar);
    }

    /**
     * お気に入りのステータスを解除状態に変える
     * @param fvtId
     */
    public void changeToBlackStar(Long fvtId) {
        Favorite fvt = getFavorite(fvtId);
        FavoriteStatus status = fvt.getStatus();
        //ステータスが欠席の場合はエラー
        if (status == FavoriteStatus.BlackStar) {
            throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_FAVORITE_STATUS, fvtId + ": FavoriteStatus is still BlackStar");
        }
        fvt.setStatus(FavoriteStatus.BlackStar);
    }
}