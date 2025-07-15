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

/**
 * Service layer for managing {@link RuleName} entities.
 * Handles business logic related to creation, retrieval, update and deletion of rules.
 */
@Service
public class RuleNameService {
    private static final Logger logger = LoggerFactory.getLogger(RuleNameService.class);

    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * Retrieves all rules from the database.
     *
     * @return a list of all rule entities.
     */
    public List<RuleName> getRuleNameList() {
        logger.info("loading rule List");

        return ruleNameRepository.findAll();
    }

    /**
     * Retrieves a rule by its ID.
     *
     * @param id the ID of the rule to retrieve.
     * @return the rule entity with the specified id.
     * @throws RequestException if no rule with the given ID found.
     */
    public RuleName getRuleNameById(Integer id) {
        logger.info("Fetching rule with id {}", id);

        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new RequestException("rule name not found"));
    }

    /**
     * Adds a new rule to the database.
     *
     * @param dto the DTO containing the data to create the rule.
     * @return the saved rule entity.
     */
    public RuleName addRuleName(RuleNameDto dto) {
        RuleName ruleName = RuleNameMapper.toEntity(dto);
        logger.info("ruleName successfully added");

        return ruleNameRepository.save(ruleName);
    }

    /**
     * Updates an existing rule in the database.
     * Only non-null and different values from the DTO will update the current entity.
     *
     * @param id  the ID of the rule to update.
     * @param dto the DTO containing updated data.
     * @return the updated rule entity.
     * @throws RequestException if no rule with the given ID exists.
     */
    public RuleName updateRuleName(Integer id, RuleNameDto dto) {
        logger.info("save editedRule... {}", dto.toString());

        Optional<RuleName> ruleNameOptional = ruleNameRepository.findById(id);

        if (ruleNameOptional.isEmpty()) {
            throw new RequestException("rule name is empty");
        }

        var entity = ruleNameOptional.get();

        if (!(dto.getName().isEmpty()) && !(dto.getName().equalsIgnoreCase(entity.getName()))) {
            entity.setName(dto.getName());
        }

        if (!(dto.getDescription().isEmpty()) && !(dto.getDescription().equalsIgnoreCase(entity.getDescription()))) {
            entity.setDescription(dto.getDescription());
        }

        if (!(dto.getJson().isEmpty()) && !(dto.getJson().equalsIgnoreCase(entity.getJson()))) {
            entity.setJson(dto.getJson());
        }

        if (!(dto.getTemplate().isEmpty()) && !(dto.getTemplate().equalsIgnoreCase(entity.getTemplate()))) {
            entity.setTemplate(dto.getTemplate());
        }

        if (!(dto.getSqlStr().isEmpty()) && !(dto.getSqlStr().equalsIgnoreCase(entity.getSqlStr()))) {
            entity.setSqlStr(dto.getSqlStr());
        }

        if (!(dto.getSqlPart().isEmpty()) && !(dto.getSqlPart().equalsIgnoreCase(entity.getSqlPart()))) {
            entity.setSqlPart(dto.getSqlPart());
        }

        logger.info("rule name successfully updated");
        return ruleNameRepository.save(entity);
    }

    /**
     * Deletes a rule from the database by its ID.
     *
     * @param id the ID of the rule to delete.
     * @throws RequestException if no rule with the given ID exists.
     */
    public void deleteRuleName(Integer id) {
        if (ruleNameRepository.findById(id).isEmpty()) {
            throw new RequestException("Unknown ruleName");
        }
        ruleNameRepository.deleteById(id);
        logger.info("ruleName successfully deleted");
    }

}
