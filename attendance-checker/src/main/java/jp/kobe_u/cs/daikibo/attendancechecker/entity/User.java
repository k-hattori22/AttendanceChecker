package jp.kobe_u.cs.daikibo.attendancechecker.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
    @Id
    String id;

    String password;

    String name;

    String boss;

    String grade;
    
    String seat;
}
