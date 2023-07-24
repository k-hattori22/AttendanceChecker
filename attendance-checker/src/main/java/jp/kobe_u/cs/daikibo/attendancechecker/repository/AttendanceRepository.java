package jp.kobe_u.cs.daikibo.attendancechecker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs.daikibo.attendancechecker.entity.Attendance;

@Repository
public interface AttendanceRepository extends CrudRepository<Attendance, Long> {
    //
}