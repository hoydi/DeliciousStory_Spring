package com.i5.ds.Recipe.UserRecipe;


import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "ds_user_recipe")
public class UserRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rcp_seq")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reg_date")
    @Temporal(TemporalType.DATE)
    private Date regDate;

    @Column(name = "rcp_nm")
    private String name;

    @Column(name = "rcp_way2")
    private String cookingMethod;

    @Column(name = "rcp_pat2")
    private String dishType;

    @Column(name = "info_eng")
    private Double energy;

    @Column(name = "info_car")
    private Double calories;

    @Column(name = "info_pro")
    private Double protein;

    @Column(name = "info_fat")
    private Double fat;

    @Column(name = "hash_tag")
    private String hashTag;

    @Column(name = "att_file_no_main")
    private String mainImageUrl;

    @Column(name = "att_file_no_mk")
    private String thumbnailImageUrl;

    @Column(name = "rcp_parts_dtls")
    private String ingredients;

    @Column(name = "rcp_na_tip")
    private String tips;

    @Column(name = "manual")
    private String manual;
    @Column(name = "manual_img")
    private String manualImage;

    public UserRecipe() {
    }

    public UserRecipe(Long id, String name, String cookingMethod, String dishType, Double energy, Double calories, Double protein, Double fat, String hashTag, String mainImageUrl, String thumbnailImageUrl, String ingredients, String tips, String manual, String manualImage, Date regDate, String userId) {
        this.calories = calories;
        this.cookingMethod = cookingMethod;
        this.dishType = dishType;
        this.energy = energy;
        this.fat = fat;
        this.id = id;
        this.ingredients = ingredients;
        this.mainImageUrl = mainImageUrl;
        this.manual = manual;
        this.name = name;
        this.protein = protein;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.tips = tips;
        this.hashTag = hashTag;
        this.manualImage = manualImage;
        this.regDate = regDate;
        this.userId = userId;
    }

    public UserRecipe(String name, String cookingMethod, String dishType, Double energy, Double calories, Double protein, Double fat, String hashTag, String mainImageUrl, String thumbnailImageUrl, String ingredients, String tips, String manual, String manualImage, Date regDate, String userId) {
        this.calories = calories;
        this.cookingMethod = cookingMethod;
        this.dishType = dishType;
        this.energy = energy;
        this.fat = fat;
        this.ingredients = ingredients;
        this.mainImageUrl = mainImageUrl;
        this.manual = manual;
        this.name = name;
        this.protein = protein;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.tips = tips;
        this.hashTag = hashTag;
        this.manualImage = manualImage;
        this.regDate = regDate;
        this.userId = userId;
    }


    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getCookingMethod() {
        return cookingMethod;
    }

    public void setCookingMethod(String cookingMethod) {
        this.cookingMethod = cookingMethod;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public String getManualImage() {
        return manualImage;
    }

    public void setManualImage(String manualImage) {
        this.manualImage = manualImage;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date date) {
        this.regDate = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
