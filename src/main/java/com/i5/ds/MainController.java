package com.i5.ds;

import com.i5.ds.Recipe.SiteRecipe.Recipe;
import com.i5.ds.Recipe.SiteRecipe.RecipeService;
import com.i5.ds.Recipe.UserRecipe.UserRecipe;
import com.i5.ds.Recipe.UserRecipe.UserRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserRecipeService userRecipeService;


    @GetMapping("/")
    public String index(Model model) {
        // 첫 번째 페이지에서 4개의 레시피를 가져옴 ( 추가해도됨 )
        Page<Recipe> recipePage = recipeService.getRecipes(0, 4);
        // 첫 번째 페이지에서 4개의 유저 레시피를 가져옴
        Page<UserRecipe> userRecipePage = userRecipeService.getUserRecipes(0, 4);

        // 모델에 레시피와 유저 레시피 리스트를 추가
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("userRecipes", userRecipePage.getContent());
        return "index"; // index.html 페이지로 리턴
    }
}
