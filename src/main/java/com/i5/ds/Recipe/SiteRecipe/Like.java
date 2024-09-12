package com.i5.ds.Recipe.SiteRecipe;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ds_likes")
@IdClass(LikeId.class)
public class Like {

	@Id
	@Column(name = "recipe_id")
	private Long recipeId;

	@Id
	@Column(name = "user_id")
	private String userId;

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

// LikeId 클래스 (복합키 정의)
class LikeId implements Serializable {
	private Long recipeId;
	private String userId;

	// 기본 생성자, equals(), hashCode() 오버라이드 필요
	public LikeId() {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		LikeId likeId = (LikeId) o;
		return Objects.equals(recipeId, likeId.recipeId) && Objects.equals(userId, likeId.userId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(recipeId, userId);
	}
}
