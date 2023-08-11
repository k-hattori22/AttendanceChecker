package jp.kobe_u.cs.daikibo.attendancechecker.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 座席エンティティ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //座席ID

    private String name; //座席名

    private BigDecimal posFromLeft; //左からの位置（%指定）

    private BigDecimal posFromTop; //上からの位置（%指定）

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; //作成日時

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt; //更新日時
}
