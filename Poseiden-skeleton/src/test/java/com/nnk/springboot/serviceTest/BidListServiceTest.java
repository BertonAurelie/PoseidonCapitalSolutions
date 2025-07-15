package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidListServiceTest {
    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    public void givenListBidList_whenGetBidListList_thenReturnAllList() {
        BidList bidList1 = new BidList();
        bidList1.setBidListId(1);
        bidList1.setType("type1");
        bidList1.setAccount("account1");
        bidList1.setBidQuantity(1.1);

        BidList bidList2 = new BidList();
        bidList2.setBidListId(2);
        bidList2.setType("type2");
        bidList2.setAccount("account2");
        bidList2.setBidQuantity(2.2);

        List<BidList> bidLists = new ArrayList<>();
        bidLists.add(bidList1);
        bidLists.add(bidList2);

        when(bidListRepository.findAll()).thenReturn(bidLists);

        List<BidList> result = bidListService.getBidList();

        assertEquals(2, result.size());
    }

    @Test
    public void givenValidDto_whenAddBidList_thenReturnSavedBidList() {
        BidListDto dto = new BidListDto("test", "test", null);
        BidList entity = new BidList("testAccount", "testType", 0.0);

        Mockito.when(bidListRepository.save(Mockito.any(BidList.class))).thenReturn(entity);

        BidList result = bidListService.addBidList(dto);

        assertNotNull(result);
        assertEquals("testAccount", result.getAccount());
        assertEquals("testType", result.getType());
        assertEquals(0.0, result.getBidQuantity());
        verify(bidListRepository, Mockito.times(1)).save(Mockito.any(BidList.class));
    }

    @Test
    public void givenExistingId_whenGetBidListById_thenReturnBidList() {
        BidList entity = new BidList("testAccount", "testType", 0.0);

        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        BidList result = bidListService.getBidListById(anyInt());

        assertNotNull(result);
        assertEquals("testAccount", result.getAccount());
        assertEquals("testType", result.getType());
        assertEquals(0.0, result.getBidQuantity());
        verify(bidListRepository, Mockito.times(1)).findById(anyInt());
    }


    @Test
    public void givenNonExistingId_whenGetBidListById_thenThrowRequestException() {
        RequestException exception = assertThrows(RequestException.class, () -> bidListService.getBidListById(anyInt()));
        assertEquals("Unknown bid list", exception.getMessage());

    }

    @Test
    public void givenExistingIdAndValidDto_whenUpdateBidList_thenReturnUpdatedBidList() {
        BidListDto dto = new BidListDto();
        dto.setAccount("test");
        dto.setType("test");
        dto.setBidQuantity(1.1);
        BidList entity = new BidList("testAccount", "testType", 0.0);

        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(entity));

        Mockito.when(bidListRepository.save(Mockito.any(BidList.class))).thenReturn(entity);

        BidList result = bidListService.updateBidList(anyInt(), dto);

        assertNotNull(result);
        assertEquals("test", result.getAccount());
        assertEquals("test", result.getType());
        assertEquals(1.1, result.getBidQuantity());
        verify(bidListRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void givenNonExistingId_whenUpdateBidList_thenThrowRequestException() {
        BidListDto dto = new BidListDto("test", "test", null);

        RequestException exception = assertThrows(RequestException.class, () -> bidListService.updateBidList(anyInt(), dto));
        assertEquals("bid list is empty", exception.getMessage());

    }

    @Test
    public void givenExistingId_whenDeleteBidList_thenDeleteSuccessfully() {
        BidList bidList = new BidList();
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(bidList));

        bidListService.deleteBidList(1);

        verify(bidListRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void givenNonExistingId_whenDeleteBidList_thenThrowRequestException() {
        Integer tradeId = 2;
        when(bidListRepository.findById(tradeId)).thenReturn(Optional.empty());


        assertThrows(RequestException.class, () -> bidListService.deleteBidList(tradeId));

        verify(bidListRepository).findById(tradeId);
        verify(bidListRepository, never()).deleteById(anyInt());
    }

}
