package com.i5.ds.Recipe.SiteRecipe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    
    @Column(name = "manual_img")
    private String manualImageUrl;

    public Recipe() {
    }

    public Recipe(Double calories, String cookingMethod, String dishType, Double energy, Double fat, Long id, String ingredients, String mainImageUrl, String manual, String name, Double protein, String thumbnailImageUrl, String tips, String manualImageUrl) {
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
        this.manualImageUrl = manualImageUrl;
    }
}
