package jp.kobe_u.cs.daikibo.attendancechecker.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs.daikibo.attendancechecker.domain.entity.Favorite;

@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
    public boolean existsByFavoritingUserIdAndFavoritedUserId(String favoritingUserId, String favoritedUserId);
}