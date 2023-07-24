package jp.kobe_u.cs.daikibo.attendancechecker.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
  
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; //アテンダンスエンティティの識別子

    String userId; //学生のID

    boolean isAttendance; //出席してるか否か

    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt; //作成日時
}
