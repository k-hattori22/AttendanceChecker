package jp.kobe_u.cs.daikibo.attendancechecker.application.dto;

import lombok.Data;

@Data
public class FavoriteForm {
    String favoritedUserId;
    String favoritingUserId;
}
