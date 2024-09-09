package com.i5.ds.Recipe.SiteRecipe;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

	private final LikeRepository likeRepository;

	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}

	@Transactional
	public boolean likeRecipe(Long recipeId, String userId) {
		if (likeRepository.existsByRecipeIdAndUserId(recipeId, userId)) {
			// 이미 좋아요를 누른 상태
			return false;
		}
		Like like = new Like();
		like.setRecipeId(recipeId);
		like.setUserId(userId);
		likeRepository.save(like);
		return true;
	}

	@Transactional
	public boolean cancelLikeRecipe(Long recipeId, String userId) {
		Like like = likeRepository.findByRecipeIdAndUserId(recipeId, userId);
		if (like != null) {
			likeRepository.delete(like);
			return true;
		}
		return false;
	}

	// 사용자가 이미 좋아요를 눌렀는지 여부 확인
	public boolean hasLikedRecipe(Long recipeId, String userId) {
		return likeRepository.existsByRecipeIdAndUserId(recipeId, userId);
	}
}
