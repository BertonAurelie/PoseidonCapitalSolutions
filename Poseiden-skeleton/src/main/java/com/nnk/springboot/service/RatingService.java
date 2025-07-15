package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingDto;
import com.nnk.springboot.domain.dto.mapper.request.RatingMapper;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service layer for managing {@link Rating} entities.
 * Handles business logic related to creation, retrieval, update and deletion of ratings.
 */
@Service
public class RatingService {
    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Retrieves all ratings from the database.
     *
     * @return a list of all rating entities.
     */
    public List<Rating> getRatingList() {
        logger.info("loading rating list...");
        return ratingRepository.findAll();
    }

    /**
     * Retrieves a rating by its ID.
     *
     * @param id the ID of the rating to retrieve.
     * @return the rating entity with the specified id.
     * @throws RequestException if no rating with the given ID found.
     */
    public Rating getRatingById(Integer id) {
        logger.info("research rating by id....");

        return ratingRepository.findById(id)
                .orElseThrow(() -> new RequestException("Unknown rating"));
    }

    /**
     * Adds a new rating to the database.
     * If order number is null in the provided DTO, they are defaulted to 0.0.
     *
     * @param dto the DTO containing the data to create the rating.
     * @return the saved rating entity.
     */
    public Rating addRating(RatingDto dto) {
        if (dto.getOrderNumber() == null) {
            dto.setOrderNumber(0);
        }

        Rating addedRating = RatingMapper.toEntity(dto);
        logger.info("rating successfully added");

        return ratingRepository.save(addedRating);
    }

    /**
     * Updates an existing rating in the database.
     * Only non-null and different values from the DTO will update the current entity.
     *
     * @param id  the ID of the rating to update.
     * @param dto the DTO containing updated data.
     * @return the updated rating entity.
     * @throws RequestException if no rating with the given ID exists.
     */
    public Rating updateRating(Integer id, RatingDto dto) {
        logger.info("save editedRating... {}", dto.toString());

        Optional<Rating> ratingToUpdate = ratingRepository.findById(id);

        if (ratingToUpdate.isEmpty()) {
            throw new RequestException("rating is empty");
        }

        var entity = ratingToUpdate.get();

        if (dto.getMoodysRating() != null && !(entity.getMoodysRating().equalsIgnoreCase(dto.getMoodysRating()))) {
            entity.setMoodysRating(dto.getMoodysRating());
        }

        if (dto.getFitchRating() != null && !(entity.getFitchRating().equalsIgnoreCase(dto.getFitchRating()))) {
            entity.setFitchRating(dto.getFitchRating());
        }

        if (dto.getSandPRating() != null && !(entity.getSandPRating().equalsIgnoreCase(dto.getSandPRating()))) {
            entity.setSandPRating(dto.getSandPRating());
        }

        if (dto.getOrderNumber() != null && !(Objects.equals(entity.getOrderNumber(), dto.getOrderNumber()))) {
            entity.setOrderNumber(dto.getOrderNumber());
        }

        return ratingRepository.save(entity);

    }

    /**
     * Deletes a rating from the database by its ID.
     *
     * @param id the ID of the rating to delete.
     * @throws RequestException if no rating with the given ID exists.
     */
    public void deleteRating(Integer id) {
        if (ratingRepository.findById(id).isEmpty()) {
            throw new RequestException("Unknown rating");
        }

        ratingRepository.deleteById(id);
        logger.info("rating successfully deleted");
    }
}
