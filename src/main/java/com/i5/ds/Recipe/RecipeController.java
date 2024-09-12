package com.i5.ds.Recipe;

import com.i5.ds.Recipe.SiteRecipe.LikeService;
import com.i5.ds.Recipe.SiteRecipe.Recipe;
import com.i5.ds.Recipe.SiteRecipe.RecipeService;
import com.i5.ds.Recipe.UserRecipe.UserRecipe;
import com.i5.ds.Recipe.UserRecipe.UserRecipeService;
import com.i5.ds.Upload.FileTransferService;
import com.i5.ds.User.User;
import com.i5.ds.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserRecipeService userRecipeService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FileTransferService fileTransferService;
    @Autowired
    private UserService userService;

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
        List<UserRecipe> userRecipes = userRecipePage.getContent();

        // mainImageUrl을 수정
        for (UserRecipe recipe : userRecipes) {
            String mainImageUrl = recipe.getMainImageUrl();
            if (mainImageUrl != null && !mainImageUrl.isEmpty()) {
                mainImageUrl = "https://axpt92hqzxmy.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/axpt92hqzxmy/b/bucket_ds/o/image%2F" + mainImageUrl;
                recipe.setMainImageUrl(mainImageUrl); // 수정된 URL 설정
            }
        }

        // 수정된 userRecipes를 모델에 추가
        model.addAttribute("userRecipes", userRecipes);
        model.addAttribute("userCurrentPage", page);
        model.addAttribute("userTotalPages", userRecipePage.getTotalPages());

        // 디버그용 출력
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Total recipes: " + userRecipes.size());

        if (userRecipes.size() >= 3) {
            System.out.println("Name of the 3rd recipe: " + userRecipes.get(2).getName());
        } else {
            System.out.println("Less than 3 recipes available.");
        }

        return "pages/userRecipe/userRecipe_list";
    }

    @GetMapping("/userRecipe/{id}")
    public String getUserRecipeById(@PathVariable("id") Long id, Model model) {
        UserRecipe userRecipe = userRecipeService.getRecipeById(id);

        // mainImageUrl 수정
        String mainImageUrl = userRecipe.getMainImageUrl();
        if (mainImageUrl != null && !mainImageUrl.isEmpty()) {
            mainImageUrl = "https://axpt92hqzxmy.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/axpt92hqzxmy/b/bucket_ds/o/image%2F" + mainImageUrl;
            userRecipe.setMainImageUrl(mainImageUrl);
        }

        // manual, ingredients, manualImage를 |,|로 분리
        List<String> manualList = Arrays.asList(userRecipe.getManual().split("\\|,\\|"));
        List<String> ingredientsList = Arrays.asList(userRecipe.getIngredients().split("\\|,\\|"));
        List<String> manualImageList = Arrays.asList(userRecipe.getManualImage().split("\\|,\\|"));
        for (int i = 0; i < manualImageList.size(); i++) {
            if (manualImageList.get(i) != null && !manualImageList.get(i).isEmpty()) {
                // 리스트의 i번째 값을 수정할 때 set() 사용
                manualImageList.set(i, "https://axpt92hqzxmy.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/axpt92hqzxmy/b/bucket_ds/o/image%2F" + manualImageList.get(i));
            }
        }

        // 모델에 추가
        model.addAttribute("userRecipe", userRecipe);
        model.addAttribute("manualList", manualList);
        model.addAttribute("ingredientsList", ingredientsList);
        model.addAttribute("manualImageList", manualImageList);

        return "pages/userRecipe/userRecipe_detail";
    }


    @GetMapping("/userRecipe_write")
    public String getUserRecipeWrite(Model model) {
        // 현재 사용자 정보를 가져오는 로직
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName(); // 현재 로그인한 사용자 ID를 가져옴

        // 사용자 정보를 DB에서 가져오기
        Optional<User> userOpt = userService.findByUserId(currentUserId);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        } else {
            // 사용자 정보가 없을 경우 처리 (예: 에러 메시지 추가)
            model.addAttribute("error", "User not found.");
        }
        return "pages/userRecipe/userRecipe_write";
    }

    @PostMapping("/userRecipe_write")
    public String saveUserRecipe(@RequestParam("rcp_nm") String recipeName,
                                 @RequestParam("rcp_way2") String cookingMethod,
                                 @RequestParam("rcp_pat2") String dishType,
                                 @RequestParam("att_file_no_main") MultipartFile mainImageFile,
                                 @RequestParam("rcp_parts_dtls") List<String> ingredients,
                                 @RequestParam("manual") List<String> manuals,
                                 @RequestParam("manual_img") List<MultipartFile> manualImages,
                                 @RequestParam("rcp_na_tip") String tips,
                                 @RequestParam("hash_tag") String hashTag,
                                 @RequestParam("user_id") String userId) throws IOException {

        // 메인 이미지 업로드 (파일이 있는지 확인)
        String mainImageUrl = null;
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            mainImageUrl = fileTransferService.uploadFileToExternalApi(mainImageFile, recipeName).getBody().get("fileName");
        }

        // 요리 순서와 이미지를 |,|로 구분하여 연결
        StringBuilder manualBuilder = new StringBuilder();
        StringBuilder manualImageBuilder = new StringBuilder();

        for (int i = 0; i < manuals.size(); i++) {
            // 요리 순서 추가
            manualBuilder.append(manuals.get(i));

            // 이미지 업로드 (파일이 있는지 확인)
            String manualImageUrl = null;
            if (manualImages.get(i) != null && !manualImages.get(i).isEmpty()) {
                manualImageUrl = fileTransferService.uploadFileToExternalApi(manualImages.get(i), recipeName).getBody().get("fileName");
            }

            // 이미지 URL 추가
            manualImageBuilder.append(manualImageUrl != null ? manualImageUrl : "");

            if (i < manuals.size() - 1) {
                manualBuilder.append("|,|");
                manualImageBuilder.append("|,|");
            }
        }

        // 재료를 |,| 구분자로 연결
        String ingredientsString = String.join("|,|", ingredients);

        // UserRecipe 객체 생성
        UserRecipe userRecipe = new UserRecipe(recipeName, cookingMethod, dishType, hashTag, mainImageUrl, ingredientsString, tips, manualBuilder.toString(), manualImageBuilder.toString(), new Date(), userId);

        // 저장
        userRecipeService.saveUserRecipe(userRecipe);

        return "redirect:/userRecipe";
    }

    // GET method for the update page
    @GetMapping("/userRecipe_update/{id}")
    public String getUserRecipeUpdate(@PathVariable("id") Long id, Model model) {
        // Current user information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();

        // Fetch user information
        Optional<User> userOpt = userService.findByUserId(currentUserId);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
        } else {
            model.addAttribute("error", "User not found.");
        }

        // Fetch recipe information
        UserRecipe userRecipe = userRecipeService.getRecipeById(id);
        if (userRecipe == null) {
            return "redirect:/userRecipe";
        }

        // Construct the URL for the main image
        String baseUrl = "https://axpt92hqzxmy.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/axpt92hqzxmy/b/bucket_ds/o/image%2F";
        String mainImageUrl = userRecipe.getMainImageUrl();
        if (mainImageUrl != null && !mainImageUrl.isEmpty()) {
            mainImageUrl = baseUrl + mainImageUrl;
            userRecipe.setMainImageUrl(mainImageUrl);
        }

        // Construct URLs for manual images
        List<String> manualList = Arrays.asList(userRecipe.getManual().split("\\|,\\|"));
        List<String> ingredientsList = Arrays.asList(userRecipe.getIngredients().split("\\|,\\|"));
        List<String> manualImageList = Arrays.asList(userRecipe.getManualImage().split("\\|,\\|"));

        List<String> updatedManualImageList = manualImageList.stream()
                .map(imageName -> {
                    if (imageName == "" || imageName.isEmpty()) {
                        return imageName; // 그대로 반환
                    } else {
                        return baseUrl + imageName; // URL로 변환
                    }
                })
                .collect(Collectors.toList());

        // Add attributes to model
        model.addAttribute("userRecipe", userRecipe);
        model.addAttribute("manualList", manualList);
        model.addAttribute("ingredientsList", ingredientsList);
        model.addAttribute("manualImageList", updatedManualImageList);

        return "pages/userRecipe/userRecipe_write";
    }


    // POST method for updating the recipe
    @PostMapping("/userRecipe_update/{id}")
    public String updateUserRecipe(@PathVariable("id") Long id,
                                   @RequestParam("rcp_nm") String recipeName,
                                   @RequestParam("rcp_way2") String cookingMethod,
                                   @RequestParam("rcp_pat2") String dishType,
                                   @RequestParam("att_file_no_main") MultipartFile mainImageFile,
                                   @RequestParam("rcp_parts_dtls") List<String> ingredients,
                                   @RequestParam("manual") List<String> manuals,
                                   @RequestParam("manual_img") List<MultipartFile> manualImages,
                                   @RequestParam("rcp_na_tip") String tips,
                                   @RequestParam("hash_tag") String hashTag,
                                   @RequestParam("user_id") String userId) throws IOException {

        // Retrieve existing recipe
        UserRecipe userRecipe = userRecipeService.getRecipeById(id);

        if (userRecipe == null) {
            return "redirect:/userRecipe"; // Redirect if the recipe is not found
        }

        // Update the recipe fields
        userRecipe.setName(recipeName);
        userRecipe.setCookingMethod(cookingMethod);
        userRecipe.setDishType(dishType);
        userRecipe.setHashTag(hashTag);
        userRecipe.setTips(tips);

        // Handle main image update
        String mainImageUrl = userRecipe.getMainImageUrl(); // Retain existing image if no new image is uploaded
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            mainImageUrl = fileTransferService.uploadFileToExternalApi(mainImageFile, recipeName).getBody().get("fileName");
        }
        userRecipe.setMainImageUrl(mainImageUrl);

        // Handle manual steps and images
        StringBuilder manualBuilder = new StringBuilder();
        StringBuilder manualImageBuilder = new StringBuilder();
        for (int i = 0; i < manuals.size(); i++) {
            manualBuilder.append(manuals.get(i));

            String manualImageUrl = null;
            if (manualImages.get(i) != null && !manualImages.get(i).isEmpty()) {
                manualImageUrl = fileTransferService.uploadFileToExternalApi(manualImages.get(i), recipeName).getBody().get("fileName");
            }
            manualImageBuilder.append(manualImageUrl != null ? manualImageUrl : "");

            if (i < manuals.size() - 1) {
                manualBuilder.append("|,|");
                manualImageBuilder.append("|,|");
            }
        }
        userRecipe.setManual(manualBuilder.toString());
        userRecipe.setManualImage(manualImageBuilder.toString());

        // Update ingredients
        userRecipe.setIngredients(String.join("|,|", ingredients));

        // Save updated recipe
        userRecipeService.saveUserRecipe(userRecipe);

        return "redirect:/userRecipe";
    }


    // DELETE method for deleting a recipe
    @PostMapping("/userRecipe_delete/{id}")
    public String deleteUserRecipe(@PathVariable("id") Long id) {
        userRecipeService.deleteUserRecipeById(id);
        return "redirect:/userRecipe";
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