package jp.kobe_u.cs.daikibo.attendancechecker.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Seat;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Long> {
    //
}
