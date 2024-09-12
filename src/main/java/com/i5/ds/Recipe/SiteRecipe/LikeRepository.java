package com.i5.ds.Recipe.SiteRecipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
	boolean existsByRecipeIdAndUserId(Long recipeId, String userId);

	Like findByRecipeIdAndUserId(Long recipeId, String userId);

	// 사용자 ID로 좋아요를 누른 레시피 ID 목록을 찾는 메서드 추가
	@Query("SELECT l.recipeId FROM Like l WHERE l.userId = :userId")
	List<Long> findRecipeIdsByUserId(@Param("userId") String userId);

}
