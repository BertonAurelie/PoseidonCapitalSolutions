package com.nnk.springboot.domain.dto.mapper.request;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDto;

public class TradeMapper {
    public TradeMapper() {}

    public static Trade toEntity(TradeDto dto){
        Trade trade = new Trade();

        trade.setAccount(dto.getAccount());
        trade.setType(dto.getType());
        trade.setBuyQuantity(dto.getBuyQuantity());

        return trade;
    }

    public static TradeDto toDto(Trade trade){
        TradeDto dto = new TradeDto();

        dto.setAccount(trade.getAccount());
        dto.setType(trade.getType());
        dto.setBuyQuantity(trade.getBuyQuantity());

        return dto;
    }
}
