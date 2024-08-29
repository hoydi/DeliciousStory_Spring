package com.i5.ds.Recipe.UserRecipe;

import com.i5.ds.Recipe.UserRecipe.UserRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecipeRepository extends JpaRepository<UserRecipe, Long> {
    Page<UserRecipe> findAll(Pageable pageable);
}
