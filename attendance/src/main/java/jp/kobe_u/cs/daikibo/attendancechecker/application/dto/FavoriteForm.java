package jp.kobe_u.cs.daikibo.attendancechecker.application.dto;

<<<<<<< HEAD:attendance/src/main/java/jp/kobe_u/cs/daikibo/attendancechecker/application/dto/FavoriteForm.java
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Favorite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FavoriteForm {
    @NotBlank
    //@Size(max = 32)
    private String favoritingUserId;

    @NotBlank
    //@Size(max = 32)
    private String favoritedUserId;

    public Favorite toEntity() {
        return new Favorite(null, favoritingUserId, favoritedUserId, null, null);
    }
=======
import lombok.Data;

@Data
public class FavoriteForm {
    String favoritedUserId;
    String favoritingUserId;
>>>>>>> 5a16226b5a0a8d4430cdf56d540e62c392bcdd3f:demo/attendance/src/main/java/jp/kobe_u/cs/daikibo/attendancechecker/application/dto/FavoriteForm.java
}
