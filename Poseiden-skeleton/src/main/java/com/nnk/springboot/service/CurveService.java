package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.domain.dto.mapper.request.CurvePointMapper;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link CurvePoint} entities.
 * Handles business logic related to creation, retrieval, update and deletion of curve points.
 */
@Service
public class CurveService {
    private static final Logger logger = LoggerFactory.getLogger(CurveService.class);

    private final CurvePointRepository curvePointRepository;

    public CurveService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Retrieves all curve points from the database.
     *
     * @return a list of all CurvePoint entities.
     */
    public List<CurvePoint> getCurvePointList() {
        logger.info("loading curve point list..");

        return curvePointRepository.findAll();
    }

    /**
     * Retrieves a curve point by its ID.
     *
     * @param id the ID of the curve point to retrieve.
     * @return the curve point entity with the specified id.
     * @throws RequestException if no curve point with the given ID found.
     */
    public CurvePoint getCurvePointById(Integer id) {
        logger.info("Fetching curve point with id {}", id);

        return curvePointRepository.findById(id)
                .orElseThrow(() -> new RequestException("Unknown curve point"));
    }

    /**
     * Adds a new curve point to the database.
     * If the term or value is null in the provided DTO, they are defaulted to 0.0.
     *
     * @param newCurvePoint the DTO containing the data to create the curve point.
     * @return the saved curve point entity.
     */
    public CurvePoint addCurvePoint(CurvePointDto newCurvePoint) {
        if (newCurvePoint.getTerm() == null) {
            newCurvePoint.setTerm(0.0);
        }

        if (newCurvePoint.getValue() == null) {
            newCurvePoint.setValue(0.0);
        }

        CurvePoint curvePoint = CurvePointMapper.toEntity(newCurvePoint);
        logger.info("save new curve point...");

        return curvePointRepository.save(curvePoint);
    }

    /**
     * Updates an existing CurvePoint in the database.
     * Only non-null and different values from the DTO will update the current entity.
     *
     * @param id  the ID of the CurvePoint to update.
     * @param dto the DTO containing updated data.
     * @return the updated curve point entity.
     * @throws RequestException if no curve point with the given ID exists.
     */
    public CurvePoint updateCurvePoint(Integer id, CurvePointDto dto) {
        logger.info("save editedCurvePoint... {}", dto.toString());

        Optional<CurvePoint> curvePointInDb = curvePointRepository.findById(id);

        if (curvePointInDb.isEmpty()) {
            throw new RequestException("CurvePoint is empty");

        }

        var entity = curvePointInDb.get();

        if (dto.getCurveId() != null && !dto.getCurveId().equals(entity.getCurveId())) {
            entity.setCurveId(dto.getCurveId());
        }

        if (dto.getTerm() != null && !dto.getTerm().equals(entity.getTerm())) {
            entity.setTerm(dto.getTerm());
        }

        if (dto.getValue() != null && !dto.getValue().equals(entity.getValue())) {
            entity.setValue(dto.getValue());
        }

        return curvePointRepository.save(entity);

    }

    /**
     * Deletes a curve point from the database by its ID.
     *
     * @param id the ID of the curve point to delete.
     * @throws RequestException if no CurvePoint with the given ID exists.
     */
    public void deleteCurvePoint(Integer id) {
        if (curvePointRepository.findById(id).isEmpty()) {
            throw new RequestException("Unknown CurvePoint");
        }
        curvePointRepository.deleteById(id);
        logger.info("CurvePoint successfully deleted");
    }
}
