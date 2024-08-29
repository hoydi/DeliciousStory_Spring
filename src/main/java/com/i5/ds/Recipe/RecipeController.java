package com.i5.ds.Recipe;

import com.i5.ds.Recipe.Recipe;
import com.i5.ds.Recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    @GetMapping("/siteRecipetest")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/site_recipe")
    public String getRecipes(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "20") int size) {
    	System.out.println("siterecipe~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Page<Recipe> recipePage = recipeService.getRecipes(page, size);
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recipePage.getTotalPages());
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(recipePage.getTotalPages());
        return "pages/siteRecipe/siteRecipe_list";
    }

    @GetMapping("/recipe/{id}")
    public String getRecipeById(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "pages/siteRecipe/recipeDetail";
    }
}
