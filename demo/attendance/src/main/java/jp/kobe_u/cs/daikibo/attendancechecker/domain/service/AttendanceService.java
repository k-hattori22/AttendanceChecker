package jp.kobe_u.cs.daikibo.attendancechecker.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Attendance;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.exception.AttendanceCheckerException;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.AttendanceRepository;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.repository.UserRepository;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository aRepo;
    @Autowired
    UserRepository uRepo;

    /**
     * 出退勤を新規作成
     * 
     * @param att
     * @return
     */
    public Attendance createAttendance(Attendance att) {
        Long attId = att.getId();
        if (attId!=null && aRepo.existsById(attId)) {
            throw new AttendanceCheckerException(AttendanceCheckerException.ATTENDANCE_ALREADY_EXISTS, attId + ": Attendance already exists");
        }
        att.setCreatedAt(new Date());
        return aRepo.save(att);
    }

    /**
     * 全出退勤取得
     * 
     * @return
     */
    public List<Attendance> getAllAttendances() {
        ArrayList<Attendance> list = new ArrayList<>();
        Iterable<Attendance> all = aRepo.findAll();
        all.forEach(list::add);
        return list;
    }

    /**
     * 出退勤をIDで取得
     * 
     * @param attId
     * @return
     */
    public Attendance getAttendance(Long attId) {
        Attendance att = aRepo.findById(attId)
                .orElseThrow(() -> new AttendanceCheckerException(AttendanceCheckerException.USER_NOT_FOUND, attId + ": No such attendance"));
        return att;
    }

    /**
     * ユーザを更新
     * 
     * @param attId
     * @param att
     * @return
     */
    public Attendance updateAttendance(Long attId, Attendance att) {
        Attendance orig = getAttendance(attId);
        if (!attId.equals(att.getId())) {
            throw new AttendanceCheckerException(AttendanceCheckerException.INVALID_USER_UPDATE, attId + ": attendanceId cannot be updated");
        }
        att.setCreatedAt(orig.getCreatedAt());
        return aRepo.save(att);
    }

    /**
     * 出退勤を消去する
     * 
     * @param attId
     */
    public void deleteAttendance(Long attId) {
        Attendance att = getAttendance(attId);
        aRepo.delete(att);
    }
}
