package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.domain.dto.mapper.request.BidListMapper;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for managing {@link BidList} entities.
 * Handles business logic related to creation, retrieval, update and deletion of bid lists.
 */
@Service
public class BidListService {
    private static final Logger logger = LoggerFactory.getLogger(BidListService.class);
    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    /**
     * Retrieves all Bid List entities from the database.
     *
     * @return a list of all Bid List entities.
     */
    public List<BidList> getBidList() {
        logger.info("loading bid list...");

        return bidListRepository.findAll();
    }


    /**
     * Retrieves a Bid List entity by its ID.
     *
     * @param id the ID of the Bid List to retrieve.
     * @return the Bid List entity found.
     * @throws RequestException if no Bid List with the given ID exists.
     */
    public BidList getBidListById(Integer id) {
        logger.info("research bid list..");

        return bidListRepository.findById(id)
                .orElseThrow(() -> new RequestException("Unknown bid list"));
    }

    /**
     * Adds a new Bid List to the database.
     * If the bid quantity is null in the DTO, they will be defaulted to 0.0.
     *
     * @param dto the DTO containing the data for the new Bid List.
     * @return the saved Bid List entity.
     */
    public BidList addBidList(BidListDto dto) {
        BidList addedBidList;

        if (dto.getBidQuantity() == null) {
            dto.setBidQuantity(0.0);
        }

        addedBidList = BidListMapper.toEntity(dto);

        return bidListRepository.save(addedBidList);
    }

    /**
     * Updates an existing Bid List in the database.
     * Only non-null and different values from the DTO will overwrite the current entity.
     *
     * @param id  the ID of the Bid List to update.
     * @param dto the DTO containing updated values.
     * @return the updated Bid List entity.
     * @throws RequestException if no Bid List with the given ID exists.
     */
    public BidList updateBidList(Integer id, BidListDto dto) {
        logger.info("save edited bid list... {}", dto.toString());

        Optional<BidList> bidListInDb = bidListRepository.findById(id);

        if (bidListInDb.isEmpty()) {
            throw new RequestException("bid list is empty");
        }

        var entity = bidListInDb.get();

        if (!(dto.getAccount().isEmpty()) && !(dto.getAccount().equalsIgnoreCase(entity.getAccount()))) {
            entity.setAccount(dto.getAccount());
        }

        if (!(dto.getType().isEmpty()) && !(dto.getType().equalsIgnoreCase(entity.getType()))) {
            entity.setType(dto.getType());
        }

        if (!(dto.getBidQuantity() == null) && !(dto.getBidQuantity().equals(entity.getBidQuantity()))) {
            entity.setBidQuantity(dto.getBidQuantity());
        }
        return bidListRepository.save(entity);

    }

    /**
     * Deletes a Bid List from the database by its ID.
     *
     * @param id the ID of the Bid List to delete.
     * @throws RequestException if no Bid List with the given ID exists.
     */
    public void deleteBidList(Integer id) {
        if (bidListRepository.findById(id).isEmpty()) {
            throw new RequestException("unknown bid list");
        }

        bidListRepository.deleteById(id);
        logger.info("bid list successfully deleted");
    }
}
