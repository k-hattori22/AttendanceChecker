package jp.kobe_u.cs.daikibo.attendancechecker.application.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SeatForm {
    @NotBlank
    //@Size(max = 5)
    //@Pattern(regexp="[A-Z]+[0-9]+")
    private String name;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private BigDecimal posFromLeft; //左からの位置（%指定）

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private BigDecimal posFromTop; //上からの位置（%指定）

    public Seat toEntity() {
        return new Seat(null, name, posFromLeft, posFromTop, null, null);
    }
}
