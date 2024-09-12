package com.i5.ds.Recipe;

import com.i5.ds.Recipe.SiteRecipe.LikeService;
import com.i5.ds.Recipe.SiteRecipe.Recipe;
import com.i5.ds.Recipe.SiteRecipe.RecipeService;
import com.i5.ds.Recipe.UserRecipe.UserRecipe;
import com.i5.ds.Recipe.UserRecipe.UserRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserRecipeService userRecipeService;
    @Autowired
    private LikeService likeService;

    @GetMapping("/siteRecipetest")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        System.out.println("dbTest");
        List<Recipe> recipes = recipeService.getAllRecipes();
        System.out.println(recipes.get(0));
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/siteRecipe")
    public String getRecipes(Model model, @RequestParam(defaultValue = "0") int page,
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
        return "pages/siteRecipe/siteRecipe_detail";
    }

    @GetMapping("/userRecipe")
    public String getUserRecipes(Model model, @RequestParam(defaultValue = "0") int page,
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

    // 모든 레시피 이름과 ID를 불러오는 API
    @GetMapping("/search")
    @ResponseBody
    public List<Map<String, Object>> getAllSiteRecipesSearch() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return recipes.stream().map(recipe -> {
            Map<String, Object> recipeInfo = new HashMap<>();
            recipeInfo.put("id", recipe.getId());
            recipeInfo.put("name", recipe.getName());
            return recipeInfo;
        }).collect(Collectors.toList());
    }

    // 좋아요 처리 API
    @PostMapping("/siteRecipe/{id}/like")
    @ResponseBody
    public ResponseEntity<Map<String, String>> likeRecipe(@PathVariable("id") Long recipeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> response = new HashMap<>();

        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("status", "notLoggedIn");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String userId = authentication.getName();
        boolean liked = likeService.likeRecipe(recipeId, userId);

        response.put("status", liked ? "liked" : "alreadyLiked");
        return ResponseEntity.ok(response);
    }

    // 좋아요 취소 API
    @PostMapping("/siteRecipe/{id}/like/cancel")
    @ResponseBody
    public ResponseEntity<Map<String, String>> cancelLike(@PathVariable("id") Long recipeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> response = new HashMap<>();

        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("status", "notLoggedIn");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String userId = authentication.getName();
        boolean cancelled = likeService.cancelLikeRecipe(recipeId, userId);

        response.put("status", cancelled ? "cancelled" : "notLiked");
        return ResponseEntity.ok(response);
    }

    // 좋아요 상태 확인 API
    @GetMapping("/siteRecipe/{id}/like/status")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getLikeStatus(@PathVariable("id") Long recipeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, String> response = new HashMap<>();

        // 인증되지 않은 사용자는 "notLoggedIn" 상태 반환
        if (authentication == null || !authentication.isAuthenticated()) {
            response.put("status", "notLoggedIn");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String userId = authentication.getName();
        boolean hasLiked = likeService.hasLikedRecipe(recipeId, userId);

        // 좋아요 여부에 따라 상태 반환
        response.put("status", hasLiked ? "alreadyLiked" : "notLiked");
        return ResponseEntity.ok(response);
    }

}