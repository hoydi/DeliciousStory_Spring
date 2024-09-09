package com.i5.ds.Recipe.SiteRecipe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, LikeId> {
	boolean existsByRecipeIdAndUserId(Long recipeId, String userId);

	Like findByRecipeIdAndUserId(Long recipeId, String userId);
}
