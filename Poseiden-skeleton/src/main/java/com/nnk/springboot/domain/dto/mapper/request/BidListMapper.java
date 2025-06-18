package com.nnk.springboot.domain.dto.mapper.request;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDto;

public class BidListMapper {

    public BidListMapper() {}

    public static BidList toEntity(BidListDto dto){
        BidList bidList = new BidList();
        bidList.setAccount(dto.getAccount());
        bidList.setType(dto.getType());
        bidList.setBidQuantity(dto.getBidQuantity());

        return bidList;
    }

    public static BidListDto toDto(BidList bidList){
        BidListDto dto = new BidListDto();
        dto.setAccount(bidList.getAccount());
        dto.setType(bidList.getType());
        dto.setBidQuantity(bidList.getBidQuantity());

        return dto;
    }
}
