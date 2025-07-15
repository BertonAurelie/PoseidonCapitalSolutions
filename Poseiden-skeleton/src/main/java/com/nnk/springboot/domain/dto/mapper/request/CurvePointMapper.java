package com.nnk.springboot.domain.dto.mapper.request;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointDto;

public class CurvePointMapper {

    public CurvePointMapper() {
    }

    public static CurvePoint toEntity(CurvePointDto dto) {
        CurvePoint curvePoint = new CurvePoint();

        curvePoint.setCurveId(dto.getCurveId());
        curvePoint.setTerm(dto.getTerm());
        curvePoint.setValue(dto.getValue());

        return curvePoint;
    }

    public static CurvePointDto toDto(CurvePoint curvePoint) {
        CurvePointDto dto = new CurvePointDto(curvePoint.getCurveId(), curvePoint.getTerm(), curvePoint.getTerm());
        return dto;
    }
}
