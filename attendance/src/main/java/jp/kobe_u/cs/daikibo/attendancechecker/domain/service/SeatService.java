package jp.kobe_u.cs.daikibo.attendancechecker.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.AttendanceStatus;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Seat;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.exception.AttendanceCheckerException;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.SeatRepository;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.UserRepository;

@Service
public class SeatService {
    @Autowired
    SeatRepository sRepo;
    @Autowired
    UserRepository uRepo;

    /**
     * 座席を新規作成
     * 
     * @param seat
     * @return
     */
    public Seat createSeat(Seat seat) {
        Long seatId = seat.getId();
        if (seatId!=null && sRepo.existsById(seatId)) {
            throw new AttendanceCheckerException(AttendanceCheckerException.ATTENDANCE_ALREADY_EXISTS, seatId + ": Seat already exists");
        }
        seat.setCreatedAt(new Date());
        return sRepo.save(seat);
    }

    /**
     * 全座席取得
     * 
     * @return
     */
    public List<Seat> getAllSeats() {
        ArrayList<Seat> list = new ArrayList<>();
        Iterable<Seat> all = sRepo.findAll();
        all.forEach(list::add);
        return list;
    }

    /**
     * 空席をすべて取得
     * 
     * @return
     */
    public List<Seat> getVacants() {
        return getAllSeats().stream()
                            .filter(seat -> isVacant(seat))
                            .toList();
    }

    /**
     * 出席の座席をすべて取得
     * 
     * @return
     */
    public List<Seat> getPresents() {
        ArrayList<Seat> list = new ArrayList<>();
        Iterable<User> presents = uRepo.findByStatus(AttendanceStatus.PRESENT);
        presents.forEach(user -> list.add(getSeat(user.getSeatId())));
        return list;
    }

    /**
     * 欠席の座席をすべて取得
     * 
     * @return
     */
    public List<Seat> getAbsents() {
        ArrayList<Seat> list = new ArrayList<>();
        Iterable<User> absents = uRepo.findByStatus(AttendanceStatus.ABSENT);
        absents.forEach(user -> list.add(getSeat(user.getSeatId())));
        return list;
    }

    /**
     * 座席をIDで取得
     * 
     * @param seatId
     * @return
     */
    public Seat getSeat(Long seatId) {
        Seat seat = sRepo.findById(seatId)
                .orElseThrow(() -> new AttendanceCheckerException(AttendanceCheckerException.USER_NOT_FOUND, seatId + ": No such seat"));
        return seat;
    }

    /**
     * 座席を更新
     * 
     * @param seatId
     * @param seat
     * @return
     */
    public Seat updateSeat(Long seatId, Seat seat) {
        Seat orig = getSeat(seatId);
        if (!seatId.equals(seat.getId())) {
            throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_USER_UPDATE, seatId + ": seatId cannot be updated");
        }
        seat.setCreatedAt(orig.getCreatedAt());
        seat.setUpdatedAt(new Date());
        return sRepo.save(seat);
    }

    /**
     * 座席を消去する
     * 
     * @param seatId
     */
    public void deleteSeat(Long seatId) {
        Seat seat = getSeat(seatId);
        // 空席でなければ消去できない
        if (!isVacant(seat)) {
            throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_USER_UPDATE, seatId + ": user sits on the seat");
        }
        sRepo.delete(seat);
    }

    /**
     * 座席が空席か判定
     * @param seat
     * @return
     */
    private boolean isVacant(Seat seat) {
        return !uRepo.existsBySeatId(seat.getId());
    }
}
