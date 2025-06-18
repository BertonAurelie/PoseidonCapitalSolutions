package com.nnk.springboot.domain.dto.mapper.request;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.dto.RuleNameDto;
import org.h2.bnf.Rule;

public class RuleNameMapper {

    public RuleNameMapper() {}

    public static RuleName toEntity(RuleNameDto dto){
        RuleName ruleName = new RuleName();

        ruleName.setName(dto.getName());
        ruleName.setDescription(dto.getDescription());
        ruleName.setJson(dto.getJson());
        ruleName.setSqlStr(dto.getSqlStr());
        ruleName.setSqlPart(dto.getSqlPart());
        ruleName.setTemplate(dto.getTemplate());

        return ruleName;
    }

    public static RuleNameDto toDto(RuleName ruleName){
        RuleNameDto dto = new RuleNameDto();

        dto.setName(ruleName.getName());
        dto.setJson(ruleName.getJson());
        dto.setDescription(ruleName.getDescription());
        dto.setSqlStr(ruleName.getSqlStr());
        dto.setSqlPart(ruleName.getSqlPart());
        dto.setTemplate(ruleName.getTemplate());

        return dto;
    }
}
