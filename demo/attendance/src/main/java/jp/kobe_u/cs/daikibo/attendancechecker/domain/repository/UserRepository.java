package jp.kobe_u.cs.daikibo.attendancechecker.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    //
}
