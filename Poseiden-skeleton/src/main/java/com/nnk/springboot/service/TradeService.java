package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.dto.TradeDto;
import com.nnk.springboot.domain.dto.mapper.request.TradeMapper;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service layer for managing {@link Trade} entities.
 * Handles business logic related to creation, retrieval, update and deletion of trades.
 */
@Service
public class TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * Retrieves all trades from the database.
     *
     * @return a list of all trade entities.
     */
    public List<Trade> getTradeList() {
        logger.info("loading trade list..");
        return tradeRepository.findAll();
    }

    /**
     * Retrieves a trade by its ID.
     *
     * @param id the ID of the trade to retrieve.
     * @return the trade entity with the specified id.
     * @throws RequestException if no tradewith the given ID found.
     */
    public Trade getTradeById(Integer id) {
        logger.info("Fetching trade with id {}", id);

        return tradeRepository.findById(id)
                .orElseThrow(() -> new RequestException("trade not found"));
    }

    /**
     * Adds a new trade to the database.
     *
     * @param dto the DTO containing the data to create the trade.
     * @return the saved trade entity.
     */
    public Trade addTrade(TradeDto dto) {
        Trade trade = TradeMapper.toEntity(dto);
        logger.info("trade successfully added");
        return tradeRepository.save(trade);
    }

    /**
     * Updates an existing trade in the database.
     * Only non-null and different values from the DTO will update the current entity.
     *
     * @param id  the ID of the trade to update.
     * @param dto the DTO containing updated data.
     * @return the updated trade entity.
     * @throws RequestException if no trade with the given ID exists.
     */
    public Trade updateTrade(Integer id, TradeDto dto) {
        logger.info("save editedTrade... {}", dto.toString());

        Optional<Trade> tradeOptional = tradeRepository.findById(id);

        if (tradeOptional.isEmpty()) {
            throw new RequestException("Unknown trade");
        }

        Trade entity = tradeOptional.get();

        if (!dto.getAccount().isEmpty() && !dto.getAccount().equalsIgnoreCase(entity.getAccount())) {
            entity.setAccount(dto.getAccount());
        }

        if (!dto.getType().isEmpty() && !dto.getType().equalsIgnoreCase(entity.getType())) {
            entity.setType(dto.getType());
        }

        if (dto.getBuyQuantity() != 0 && !Objects.equals(dto.getBuyQuantity(), entity.getBuyQuantity())) {
            entity.setBuyQuantity(dto.getBuyQuantity());
        }

        logger.info("trade successfully updated");
        return tradeRepository.save(entity);

    }

    /**
     * Deletes a trade from the database by its ID.
     *
     * @param id the ID of the trade to delete.
     * @throws RequestException if no trade with the given ID exists.
     */
    public void deleteTrade(Integer id) {
        if (tradeRepository.findById(id).isEmpty()) {
            throw new RequestException("Unknown trade");
        }
        tradeRepository.deleteById(id);
        logger.info("trade successfully deleted");
    }

}
