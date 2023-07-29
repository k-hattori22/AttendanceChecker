package jp.kobe_u.cs.daikibo.attendancechecker.domain.entity;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //アテンダンスエンティティの識別子

    private String userId; //学生のID

    private AttendanceStatus status; //出欠状況

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; //作成日時
}
