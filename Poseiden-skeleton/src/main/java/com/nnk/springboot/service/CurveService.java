package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.domain.dto.mapper.request.CurvePointMapper;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurveService {
    private static final Logger logger = LoggerFactory.getLogger(CurveService.class);
    @Autowired
    CurvePointRepository curvePointRepository;

    public CurveService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    /**
     * Loading a list of curvePoint from the database
     * Using findAll Method of curvePointRepository
     *
     * @return curvepointList
     */
    public List<CurvePoint> getCurvePointList() {
        logger.info("loading curve point list..");
        List<CurvePoint> curvePointList = curvePointRepository.findAll();

        return curvePointList;
    }

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


    public CurvePoint getCurvePointById(Integer id) {
        logger.info("research curve point..");

        return curvePointRepository.getById(id);
    }


    public CurvePoint editCurvePoint(Integer id, CurvePointDto dto) {
        logger.info("save editedCurvePoint... {}", dto.toString());
        CurvePoint curvePointInDb = getCurvePointById(id);

        if (dto.getCurveId() != null && !(dto.getCurveId().equals(curvePointInDb.getCurveId()))) {
            curvePointInDb.setCurveId(dto.getCurveId());
        }

        if (dto.getTerm() != null && (dto.getTerm() != (curvePointInDb.getTerm()))) {
            curvePointInDb.setTerm(dto.getTerm());
        }

        if (dto.getValue() != null && (dto.getValue() != (curvePointInDb.getValue()))) {
            curvePointInDb.setValue(dto.getValue());
        }
        curvePointRepository.save(curvePointInDb);

        return curvePointInDb;
    }

    public void deleteCurvePoint(Integer id) {
        logger.info("curve point successfully deleted");
        curvePointRepository.deleteById(id);
    }

}
