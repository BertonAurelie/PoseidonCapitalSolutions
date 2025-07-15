package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.dto.CurvePointDto;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurveServiceTest {
    @Mock
    private CurvePointRepository curvePointRepository;
    @InjectMocks
    private CurveService curveService;

    @Test
    public void givenCurveExist_whenGetCurvePointList_thenReturnAllCurvePoints() {
        CurvePoint curvePoint = new CurvePoint(1, 1.0, 1.1);
        List<CurvePoint> list = new ArrayList<>();
        list.add(curvePoint);

        when(curvePointRepository.findAll()).thenReturn(list);

        List<CurvePoint> result = curveService.getCurvePointList();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void givenValidDto_whenAddCurvePoint_thenReturnSavedCurvePoint() {
        CurvePointDto dto = new CurvePointDto(1, null, null);
        CurvePoint entity = new CurvePoint(2, 2.0, 2.1);
        entity.setId(1);
        dto.setCurveId(1);

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(entity);

        CurvePoint result = curveService.addCurvePoint(dto);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(2, result.getCurveId());
        assertEquals(2.0, result.getTerm());
        assertEquals(2.1, result.getValue());
        verify(curvePointRepository, Mockito.times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void givenExistingId_whenGetCurvePointById_thenReturnCurvePoint() {
        CurvePoint entity = new CurvePoint(2, 2.0, 2.1);

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        CurvePoint result = curveService.getCurvePointById(anyInt());

        assertNotNull(result);
        assertEquals(2, result.getCurveId());
        assertEquals(2.0, result.getTerm());
        assertEquals(2.1, result.getValue());
        verify(curvePointRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void givenNonExistingId_whenGetCurvePointById_thenThrowRequestException() {
        RequestException exception = assertThrows(RequestException.class, () -> curveService.getCurvePointById(anyInt()));
        assertEquals("Unknown curve point", exception.getMessage());
    }

    @Test
    public void givenExistingIdAndValidDto_whenUpdateCurvePoint_thenReturnUpdatedCurvePoint() {
        CurvePointDto dto = new CurvePointDto(1, 1.0, 1.1);
        CurvePoint entity = new CurvePoint(2, 2.0, 2.1);

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(entity));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(entity);

        CurvePoint result = curveService.updateCurvePoint(anyInt(), dto);

        assertNotNull(result);
        assertEquals(1, result.getCurveId());
        assertEquals(1.0, result.getTerm());
        assertEquals(1.1, result.getValue());
        verify(curvePointRepository, Mockito.times(1)).save(any(CurvePoint.class));
    }

    @Test
    public void givenNonExistingId_whenUpdateCurvePoint_thenThrowRequestException(){
        CurvePointDto dto = new CurvePointDto(1, 1.0, 1.1);

        RequestException exception = assertThrows(RequestException.class, () -> curveService.updateCurvePoint(anyInt(), dto));
        assertEquals("CurvePoint is empty", exception.getMessage());
    }

    @Test
    public void givenExistingId_whenDeleteCurvePoint_thenDeleteSuccessfully() {
        CurvePoint curvePoint = new CurvePoint();

        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(curvePoint));

        curveService.deleteCurvePoint(1);

        verify(curvePointRepository, Mockito.times(1)).deleteById(1);
    }



    @Test
    public void givenNonExistingId_whenDeleteCurvePoint_thenThrowRequestException(){
        RequestException exception = assertThrows(RequestException.class, () -> curveService.deleteCurvePoint(anyInt()));
        assertEquals("Unknown CurvePoint", exception.getMessage());
    }
}
