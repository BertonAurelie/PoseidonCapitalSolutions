package com.nnk.springboot.domain.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class RatingDto {
    @NotBlank(message = "moddysRating can't be empty")
    private String moodysRating;
    @NotBlank(message = "sandPRating can't be empty")
    private String sandPRating;
    @NotBlank(message = "fitchRating can't be empty")
    private String fitchRating;
    private Integer orderNumber;

    public RatingDto() {
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
