package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingDto;
import com.nnk.springboot.domain.dto.mapper.request.RatingMapper;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    private RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    public List<Rating> getRatingList(){
        logger.info("loading rating list...");
        return ratingRepository.findAll();
    }


    public Rating addRating(RatingDto dto){
        if(dto.getOrderNumber() == null){
            dto.setOrderNumber(0);
        }

        Rating addedRating = RatingMapper.toEntity(dto);
        ratingRepository.save(addedRating);
        logger.info("rating successfully added");

        return addedRating;
    }

    public Rating getRatingById(Integer id){
        logger.info("research rating by id....");
        Rating entity = new Rating();
        Optional<Rating> ratingOptional = ratingRepository.findById(id);

        if(ratingOptional.isPresent()){
            logger.info("rating successfully found");
            entity = ratingOptional.get();
        }

        return entity;
    }

    public Rating updateRating(Integer id, RatingDto dto){
        Optional<Rating> ratingToUpdate = ratingRepository.findById(id);

        if(!ratingToUpdate.isPresent()){
           throw  new RuntimeException("rating is empty");
        } else {
            var entity = ratingToUpdate.get();

            if(dto.getMoodysRating() != null && !(entity.getMoodysRating().equalsIgnoreCase(dto.getMoodysRating())) ){
                entity.setMoodysRating(dto.getMoodysRating());
            }

            if(dto.getFitchRating() != null && !(entity.getFitchRating().equalsIgnoreCase(dto.getFitchRating())) ){
                entity.setFitchRating(dto.getFitchRating());
            }

            if(dto.getSandPRating() != null && !(entity.getSandPRating().equalsIgnoreCase(dto.getSandPRating())) ){
                entity.setSandPRating(dto.getSandPRating());
            }

            if(dto.getOrderNumber() != null && !(entity.getOrderNumber() ==(dto.getOrderNumber())) ){
                entity.setOrderNumber(dto.getOrderNumber());
            }

            return ratingRepository.save(entity);
        }
    }

    public void deleteRating(Integer id){
        if(ratingRepository.findById(id) == null){
            throw  new RuntimeException("Unknown rating");
        } else {
            ratingRepository.deleteById(id);
            logger.info("rating successfully deleted");
        }
    }
}
