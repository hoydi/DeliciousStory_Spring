package com.i5.ds.Recipe;

import com.i5.ds.Recipe.SiteRecipe.Recipe;
import com.i5.ds.Recipe.SiteRecipe.RecipeService;
import com.i5.ds.Recipe.UserRecipe.UserRecipe;
import com.i5.ds.Recipe.UserRecipe.UserRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserRecipeService userRecipeService;


    @GetMapping("/siteRecipetest")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/siteRecipe")
    public String getRecipes(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int size) {

        Page<Recipe> recipePage = recipeService.getRecipes(page, size);
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recipePage.getTotalPages());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(recipePage.getTotalPages());
        return "pages/siteRecipe/siteRecipe_list";
    }

    @GetMapping("/siteRecipe/{id}")
    public String getRecipeById(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "pages/siteRecipe/recipeDetail";
    }


    @GetMapping("/userRecipe")
    public String getUserRecipes(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size) {

        Page<UserRecipe> userRecipePage = userRecipeService.getUserRecipes(page, size);
        model.addAttribute("userRecipes", userRecipePage.getContent());
        model.addAttribute("userCurrentPage", page);
        model.addAttribute("userTotalPages", userRecipePage.getTotalPages());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        // 전체 데이터의 갯수 출력
        System.out.println("Total recipes: " + userRecipePage.getContent().size());

        // 3번째 데이터가 존재하면 해당 데이터의 이름 출력
        if (userRecipePage.getContent().size() >= 3) {
            System.out.println("Name of the 3rd recipe: " + userRecipePage.getContent().get(2).getName());
        } else {
            System.out.println("Less than 3 recipes available.");
        }
        return "pages/userRecipe/userRecipe_list";
    }

    @GetMapping("/userRecipe/{id}")
    public String getUserRecipeById(@PathVariable("id") Long id, Model model) {
        UserRecipe userRecipe = userRecipeService.getRecipeById(id);
        model.addAttribute("userRecipe", userRecipe);
        return "pages/userRecipe/userRecipe_detail";
    }

    @GetMapping("/userRecipe_write")
    public String getUserRecipeWrite() {

        return "pages/userRecipe/userRecipe_write";
    }

    @PostMapping("/userRecipe_save")
    public String saveUserRecipe(@ModelAttribute UserRecipe userRecipe) {
        userRecipeService.saveUserRecipe(userRecipe);
        return "redirect:/user_recipe";

    }
}