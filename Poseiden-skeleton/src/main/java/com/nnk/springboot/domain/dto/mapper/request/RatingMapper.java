package com.nnk.springboot.domain.dto.mapper.request;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingDto;

public class RatingMapper {

    public RatingMapper() {}

    public static Rating toEntity(RatingDto dto){
        Rating rating = new Rating();

        rating.setMoodysRating(dto.getMoodysRating());
        rating.setSandPRating(dto.getSandPRating());
        rating.setFitchRating(dto.getFitchRating());
        rating.setOrderNumber(dto.getOrderNumber());

        return rating;
    }

    public static RatingDto toDto(Rating rating){
        RatingDto dto = new RatingDto();

        dto.setFitchRating(rating.getFitchRating());
        dto.setMoodysRating(rating.getMoodysRating());
        dto.setSandPRating(rating.getSandPRating());
        dto.setOrderNumber(rating.getOrderNumber());

        return dto;
    }
}
