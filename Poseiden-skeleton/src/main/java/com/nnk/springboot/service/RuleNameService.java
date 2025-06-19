package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.dto.RuleNameDto;
import com.nnk.springboot.domain.dto.mapper.request.RuleNameMapper;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {
    private static final Logger logger = LoggerFactory.getLogger(RuleNameService.class);

    private RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> getRuleNameList(){
        logger.info("loading ruleName List");
        return ruleNameRepository.findAll();
    }

    public RuleName addRuleName(RuleNameDto dto){

        RuleName ruleName = RuleNameMapper.toEntity(dto);
        ruleNameRepository.save(ruleName);
        logger.info("ruleName successfully added");
        return ruleName;

    }

    public RuleName getRuleNameById(Integer id){
        logger.info("research rule name by id....");
        Optional<RuleName> ruleNameOptional = ruleNameRepository.findById(id);

        if(!ruleNameOptional.isPresent()){
            throw new RequestException("rule name not found");
        } else {
            RuleName ruleName = ruleNameOptional.get();
            logger.info("rule name successfully found");
            return ruleName;
        }
    }

    public RuleName updateRuleName(Integer id, RuleNameDto dto){
        Optional<RuleName> ruleNameOptional = ruleNameRepository.findById(id);

        if(!ruleNameOptional.isPresent()){
            throw new RequestException("rule name is empty");
        } else {
            var entity = ruleNameOptional.get();

            if(!(dto.getName().isEmpty()) && !(dto.getName().equalsIgnoreCase(entity.getName()))){
                entity.setName(dto.getName());
            }

            if(!(dto.getDescription().isEmpty()) && !(dto.getDescription().equalsIgnoreCase(entity.getDescription()))){
                entity.setDescription(dto.getDescription());
            }

            if(!(dto.getJson().isEmpty()) && !(dto.getJson().equalsIgnoreCase(entity.getJson()))){
                entity.setJson(dto.getJson());
            }

            if(!(dto.getTemplate().isEmpty()) && !(dto.getTemplate().equalsIgnoreCase(entity.getTemplate()))){
                entity.setTemplate(dto.getTemplate());
            }

            if(!(dto.getSqlStr().isEmpty()) && !(dto.getSqlStr().equalsIgnoreCase(entity.getSqlStr()))){
                entity.setSqlStr(dto.getSqlStr());
            }

            if(!(dto.getSqlPart().isEmpty()) && !(dto.getSqlPart().equalsIgnoreCase(entity.getSqlPart()))){
                entity.setSqlPart(dto.getSqlPart());
            }

            ruleNameRepository.save(entity);
            logger.info("rule name successfully updated");
            return entity;
        }
    }


    public void deleteRuleName(Integer id){
        if (!ruleNameRepository.existsById(id)) {
            throw new RequestException("Unknown ruleName");
        }
        ruleNameRepository.deleteById(id);
        logger.info("ruleName successfully deleted");
    }

}
