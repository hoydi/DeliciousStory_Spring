package com.i5.ds.Recipe;

import jakarta.persistence.*;


@Entity
@Table(name = "ds_site_recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rcp_seq")
    private Long id;

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

    public Recipe() {
    }

    public Recipe(Double calories, String cookingMethod, String dishType, Double energy, Double fat, Long id, String ingredients, String mainImageUrl, String manual, String name, Double protein, String thumbnailImageUrl, String tips) {
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
}
