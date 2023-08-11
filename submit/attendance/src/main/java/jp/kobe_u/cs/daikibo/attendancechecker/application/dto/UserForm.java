package jp.kobe_u.cs.daikibo.attendancechecker.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.AttendanceStatus;
import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserForm {
    @NotBlank
    @Size(max = 16)
    @Pattern(regexp="[a-z0-9_\\-]+")
    private String id;

    @NotBlank
    @Size(min = 8, message = "パスワードは8文字以上")
    private String password;

    @NotBlank
    @Size(max = 32)
    private String name;

    @NotBlank
    @Size(max = 32)
    private String bossName;

    @NotBlank
    @Pattern(regexp = "(D[1-3])|(M[1-2])|(B[3-4])")
    private String grade;

    @NotNull
    private Long seatId;

    public User toEntity() {
        return new User(id, password, name, bossName, grade, seatId,
                        AttendanceStatus.ABSENT, User.Role.GENERAL, null, null);
    }

    public static UserForm toForm(User user) {
        return new UserForm(user.getId(), user.getPassword(), user.getName(), user.getBossName(), user.getGrade(), user.getSeatId());
    } 

}
