package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.dto.BidListDto;
import com.nnk.springboot.domain.dto.mapper.request.BidListMapper;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    private static final Logger logger = LoggerFactory.getLogger(BidListService.class);
    @Autowired
    private BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    public List<BidList> getBidList() {
        logger.info("loading bid list...");
        return bidListRepository.findAll();
    }


    public BidList addBidList(BidListDto dto) {
        BidList addedBidList = new BidList();

        if (dto.getBidQuantity() == null) {
            dto.setBidQuantity(0.0);
        }

        if (!(dto.getAccount().isEmpty()) && !(dto.getType().isEmpty())) {
            addedBidList = BidListMapper.toEntity(dto);
        }

        return bidListRepository.save(addedBidList);
    }

    public BidList getBidListById(Integer id) {
        logger.info("research bid list..");

        return bidListRepository.getReferenceById(id);
    }

    public BidList updateBidList(Integer id, BidListDto dto) {
        Optional<BidList> bidListInDb = bidListRepository.findById(id);

        if (!bidListInDb.isPresent()) {
            throw new RuntimeException("bid list is empty");
        } else {
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
    }

    public void deleteBidList(Integer id) {
        if (bidListRepository.findById(id) == null) {
            throw new RequestException("unknown bidlist");
        } else {
            logger.info("bid list successfully deleted");
            bidListRepository.deleteById(id);
        }

    }
}
