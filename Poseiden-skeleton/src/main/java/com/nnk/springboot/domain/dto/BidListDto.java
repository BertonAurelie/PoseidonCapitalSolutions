package com.nnk.springboot.domain.dto;

import jakarta.validation.constraints.NotBlank;

public class BidListDto {
    @NotBlank(message="Account is mandatory")
    private String account;
    @NotBlank(message="Type is mandatory")
    private String type;
    private Double bidQuantity;

    public BidListDto() {}

    public BidListDto(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }
}
