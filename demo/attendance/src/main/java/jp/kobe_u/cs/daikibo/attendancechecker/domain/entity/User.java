package jp.kobe_u.cs.daikibo.attendancechecker.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザエンティティ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    //@Size(max=32)
    private String id; //ユーザID

    //@NotBlank
    //@Size(max=128)
    private String password; //パスワード

    //@NotBlank
    //@Size(max=32)
    private String name; //名前

    //@NotBlank
    //@Size(max=32)
    private String bossName; //担当教員名

    //@NotBlank
    //@Size(max=32)
    private String grade; //学年
    
    //@NotBlank
    //@Size(max=32)
    private String seat; //席

    private AttendanceStatus status; //出欠状況
    
    private Role role; //ロール

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; //作成日時

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt; //更新日時

    public enum Role {
        STUDENT,
        CARDREADER,
        ADMIN
    }
}
