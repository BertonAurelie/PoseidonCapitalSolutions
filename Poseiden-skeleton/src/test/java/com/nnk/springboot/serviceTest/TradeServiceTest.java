package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDto;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {
    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    public void givenTradeExist_whenGetTradeList_thenReturnAllTrades() {
        Trade trade1 = new Trade("account1", "type1", 1.1);
        trade1.setId(1);
        Trade trade2 = new Trade("account2", "type2", 2.2);

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade1);
        tradeList.add(trade2);

        when(tradeRepository.findAll()).thenReturn(tradeList);

        List<Trade> result = tradeService.getTradeList();

        assertEquals(2, result.size());
    }


    @Test
    public void givenValidDto_whenAddTrade_thenReturnSavedTrade() {
        TradeDto dto = new TradeDto();
        dto.setAccount("account1");
        dto.setType("type1");
        dto.setBuyQuantity(1.1);
        Trade trade1 = new Trade("account1", "type1", 1.1);

        when(tradeRepository.save(any())).thenReturn(trade1);

        Trade result = tradeService.addTrade(dto);

        assertEquals("account1", result.getAccount());
        assertEquals("type1", result.getType());
        assertEquals(1.1, result.getBuyQuantity());
    }

    @Test
    public void givenExistingId_whenGetTradeById_thenReturnTrade() {
        Trade trade1 = new Trade("account1", "type1", 1.1);

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade1));

        Trade result = tradeService.getTradeById(1);

        assertEquals("account1", result.getAccount());
        assertEquals("type1", result.getType());
        assertEquals(1.1, result.getBuyQuantity());
    }

    @Test
    public void givenNonExistingId_whenGetTradeById_thenThrowRequestException() {
        RequestException exception = assertThrows(RequestException.class, () -> tradeService.getTradeById(1));
        assertEquals("trade not found", exception.getMessage());
    }


    @Test
    public void givenExistingIdAndValidDto_whenUpdateTrade_thenReturnUpdatedTrade() {
        Trade trade1 = new Trade("account1", "type1", 1.1);
        Trade trade2 = new Trade("account2", "type2", 2.2);
        TradeDto dto = new TradeDto("account2", "type2", 2.2);

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade1));
        when(tradeRepository.save(any())).thenReturn(trade2);

        Trade result = tradeService.updateTrade(1, dto);

        assertEquals("account2", result.getAccount());
        assertEquals("type2", result.getType());
        assertEquals(2.2, result.getBuyQuantity());

    }

    @Test
    public void givenNonExistingId_whenUpdateTrade_thenThrowRequestException() {
        TradeDto dto = new TradeDto("account2", "type2", 2.2);
        RequestException exception = assertThrows(RequestException.class, () -> tradeService.updateTrade(1, dto));

        assertEquals("Unknown trade", exception.getMessage());

    }

    @Test
    public void givenExistingId_whenDeleteTrade_thenDeleteSuccessfully() {
        Trade trade = new Trade();
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));
        tradeService.deleteTrade(1);

        verify(tradeRepository).findById(anyInt());
        verify(tradeRepository).deleteById(anyInt());
        verify(tradeRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void givenNonExistingId_whenDeleteTrade_thenThrowRequestException() {
        Integer tradeId = 2;
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RequestException.class, () -> tradeService.deleteTrade(tradeId));

        verify(tradeRepository).findById(tradeId);
        verify(tradeRepository, never()).deleteById(anyInt());
    }
}
