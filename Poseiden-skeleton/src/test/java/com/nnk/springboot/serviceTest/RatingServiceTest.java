package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.dto.RatingDto;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {
    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;


    @Test
    public void givenRatingsExist_whenGetRatingList_thenReturnAllRatings() {
        Rating rating1 = new Rating(1, "rating1", "sandPRating1", "fitchRating1", 1);
        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating1);

        when(ratingRepository.findAll()).thenReturn(ratingList);

        List<Rating> result = ratingService.getRatingList();

        assertEquals(1, result.size());
        verify(ratingRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void givenValidDto_whenAddRating_thenReturnSavedRating() {
        RatingDto dto = new RatingDto();
        dto.setMoodysRating("moodysRating");
        dto.setSandPRating("sandPRating");
        dto.setFitchRating("fitchRating");
        dto.setOrderNumber(null);
        Rating rating1 = new Rating(1, "moodysRating", "sandPRating", "fitchRating", 1);
        rating1.setId(2);

        when(ratingRepository.save(any(Rating.class))).thenReturn(rating1);

        Rating result = ratingService.addRating(dto);

        assertEquals(2, result.getId());
        assertEquals("moodysRating", result.getMoodysRating());
        assertEquals("sandPRating", result.getSandPRating());
        assertEquals("fitchRating", result.getFitchRating());
        assertEquals(1, result.getOrderNumber());
        verify(ratingRepository, Mockito.times(1)).save(any(Rating.class));
    }

    @Test
    public void givenExistingId_whenGetRatingById_thenReturnRating() {
        Rating rating1 = new Rating(1, "moodysRating", "sandPRating", "fitchRating", 1);

        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating1));

        Rating result = ratingService.getRatingById(1);

        assertEquals("moodysRating", result.getMoodysRating());
        assertEquals("sandPRating", result.getSandPRating());
        assertEquals("fitchRating", result.getFitchRating());
        assertEquals(1, result.getOrderNumber());
        verify(ratingRepository, Mockito.times(1)).findById(1);
    }

    @Test
    public void givenNonExistingId_whenGetRatingById_thenThrowRequestException() {
        RequestException exception = assertThrows(RequestException.class, () -> ratingService.getRatingById(1));

        assertEquals("Unknown rating", exception.getMessage());

    }

    @Test
    public void givenExistingIdAndValidDto_whenUpdateRating_thenReturnUpdatedRating() {
        Rating rating1 = new Rating(1, "moodysRating", "sandPRating", "fitchRating", 1);
        Rating ratingUpdated = new Rating(1, "moodysRating1", "sandPRating1", "fitchRating1", 2);
        RatingDto dto = new RatingDto();
        dto.setMoodysRating("moodysRating1");
        dto.setSandPRating("sandPRating1");
        dto.setFitchRating("fitchRating1");
        dto.setOrderNumber(2);

        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating1));
        when(ratingRepository.save(any(Rating.class))).thenReturn(ratingUpdated);

        Rating result = ratingService.updateRating(1, dto);

        assertEquals("moodysRating1", result.getMoodysRating());
        assertEquals("sandPRating1", result.getSandPRating());
        assertEquals("fitchRating1", result.getFitchRating());
        assertEquals(2, result.getOrderNumber());
        verify(ratingRepository, Mockito.times(1)).findById(anyInt());
        verify(ratingRepository, Mockito.times(1)).save(rating1);
    }

    @Test
    public void givenNonExistingId_whenUpdateRating_thenThrowRequestException() {
        RatingDto dto = new RatingDto();
        dto.setMoodysRating("moodysRating1");
        dto.setSandPRating("sandPRating1");
        dto.setFitchRating("fitchRating1");
        dto.setOrderNumber(2);

        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        RequestException exception = assertThrows(RequestException.class, () -> ratingService.updateRating(anyInt(), dto));
        assertEquals("rating is empty", exception.getMessage());
    }

    @Test
    public void givenExistingId_whenDeleteRating_thenDeleteSuccessfully() {
        Rating rating = new Rating();

        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating));

        ratingService.deleteRating(1);

        verify(ratingRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void givenNonExistingId_whenDeleteRating_thenThrowRequestException() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        RequestException exception = assertThrows(RequestException.class, () -> ratingService.deleteRating(anyInt()));

        assertEquals("Unknown rating", exception.getMessage());

    }

}
