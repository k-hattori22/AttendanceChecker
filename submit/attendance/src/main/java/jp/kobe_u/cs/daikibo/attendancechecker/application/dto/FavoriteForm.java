package jp.kobe_u.cs.daikibo.attendancechecker.application.dto;

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
    @Size(max = 32)
    private String favoritingUserId;

    @NotBlank
    @Size(max = 32)
    private String favoritedUserId;

    public Favorite toEntity() {
        return new Favorite(null, favoritingUserId, favoritedUserId, null, null);
    }
}
