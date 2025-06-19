package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDto;
import com.nnk.springboot.domain.dto.mapper.request.TradeMapper;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);
    private TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> getTradeList(){
        return tradeRepository.findAll();
    }

    public Trade addTrade(TradeDto dto){
        Trade trade = TradeMapper.toEntity(dto);
        tradeRepository.save(trade);
        logger.info("trade successfully added");
        return trade;
    }

    public Trade getRuleNameById(Integer id){
        logger.info("research rule name by id....");
        Optional<Trade> tradeOptional = tradeRepository.findById(id);

        if(!tradeOptional.isPresent()){
            throw new RequestException("trade not found");
        } else {
            Trade trade = tradeOptional.get();
            logger.info("trade successfully found");
            return trade;
        }
    }

    public Trade updateTrade(Integer id, TradeDto dto){
        Optional<Trade> tradeOptional = tradeRepository.findById(id);

        if(!tradeOptional.isPresent()){
            throw new RequestException("Unknown trade");
        } else {
            var entity = tradeOptional.get();

            if(!dto.getAccount().isEmpty() && !dto.getAccount().equalsIgnoreCase(entity.getAccount())){
                entity.setAccount(dto.getAccount());
            }

            if(!dto.getType().isEmpty() && !dto.getType().equalsIgnoreCase(entity.getType())){
                entity.setType(dto.getType());
            }

            if(dto.getBuyQuantity() != 0 && dto.getBuyQuantity() != entity.getBuyQuantity()){
                entity.setBuyQuantity(dto.getBuyQuantity());
            }

            tradeRepository.save(entity);
            logger.info("trade successfully updated");
            return entity;
        }
    }


    public void deleteTrade(Integer id){
        if (!tradeRepository.existsById(id)) {
            throw new RequestException("Unknown trade");
        }
        tradeRepository.deleteById(id);
        logger.info("trade successfully deleted");

    }
}
