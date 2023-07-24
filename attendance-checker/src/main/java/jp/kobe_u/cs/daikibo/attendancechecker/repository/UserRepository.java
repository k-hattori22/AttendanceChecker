package jp.kobe_u.cs.daikibo.attendancechecker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import antlr.collections.List;
import jp.kobe_u.cs.daikibo.attendancechecker.entity.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    List<User> findByID(String UserID);

    Iterable<User> findByID(String string);
}
