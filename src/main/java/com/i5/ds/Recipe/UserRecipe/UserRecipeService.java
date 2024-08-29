package com.i5.ds.Recipe.UserRecipe;


import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecipeService {

    private final UserRecipeRepository userRecipeRepository;

    public UserRecipeService(UserRecipeRepository userRecipeRepository) {
        this.userRecipeRepository = userRecipeRepository;
    }

    public Page<UserRecipe> getUserRecipes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRecipeRepository.findAll(pageable);
    }

    public List<UserRecipe> getAllRecipes() {
        return userRecipeRepository.findAll();
    }

    public UserRecipe getRecipeById(Long id) {
        return userRecipeRepository.findById(id).orElse(null);
    }


    public void saveUserRecipe(UserRecipe userRecipe) {
        userRecipeRepository.save(userRecipe);
    }
}
