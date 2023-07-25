package jp.kobe_u.cs.daikibo.attendancechecker.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.cs.daikibo.attendancechecker.entity.User;
import jp.kobe_u.cs.daikibo.attendancechecker.repository.UserRepository;

@Service

public class UserService {
    @Autowired
    UserRepository repo;
    //学生台帳から学生情報を取得する

    // userid:見たい席の学生情報（何で検索するかは相談）
    public User serchUserID(String userid) {
        User found = repo.findByUseID(userid);
        return User
    }
}
