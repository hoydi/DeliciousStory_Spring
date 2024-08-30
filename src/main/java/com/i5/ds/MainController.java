package com.i5.ds;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    //--------레시피류 임시 매핑--------------------------------

//    @GetMapping("/site_recipe_page")
//    public String siteRecipeDetail() {
//        return "pages/siteRecipe/siteRecipe_detail"; // 뷰 파일 이름
//    }
//
//    @GetMapping("/user_recipe")
//    public String userRecipeList() {
//        return "pages/userRecipe/userRecipe_list"; // 뷰 파일 이름
//    }
//
//    @GetMapping("/user_recipe_page")
//    public String userRecipeDetail() {
//        return "pages/userRecipe/userRecipe_detail"; // 뷰 파일 이름
//    }
}
