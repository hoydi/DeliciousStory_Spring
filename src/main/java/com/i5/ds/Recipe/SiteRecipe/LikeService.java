package com.i5.ds.Recipe.SiteRecipe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

	private final LikeRepository likeRepository;
	@Autowired
	private RecipeRepository recipeRepository; // 레시피를 조회할 수 있는 Repository

	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}

	// 좋아요 처리
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

	// 좋아요 취소
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

	// 사용자가 좋아요를 한 레시피 목록을 가져오는 메서드 추가
	public List<Long> getLikedRecipeIdsByUserId(String userId) {
		return likeRepository.findRecipeIdsByUserId(userId);
	}

	/**
	 * 사용자가 좋아요한 레시피의 ID 목록을 기반으로 레시피 정보를 가져옵니다.
	 * 
	 * @param recipeIds 레시피 ID 목록
	 * @return 레시피 목록
	 */
	public List<Recipe> getRecipesByIds(List<Long> recipeIds) {
		return recipeRepository.findAllById(recipeIds); // ID 목록에 해당하는 레시피를 모두 조회
	}
}
