package com.nnk.springboot.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TradeDto {
    @NotBlank(message = "account is mandatory")
    String account;
    @NotBlank(message = "type is mandatory")
    String type;
    @NotNull(message = "buyQuantity is mandatory")
    Double buyQuantity;

    public TradeDto() {
    }

    public TradeDto(String account, String type, Double buyQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
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

    public Double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

}
