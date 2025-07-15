package com.nnk.springboot.serviceTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.dto.RuleNameDto;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {
    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @Test
    public void givenRuleNameExist_whenGetRuleNameList_thenReturnAllRuleNames() {
        RuleName ruleName = new RuleName();
        List<RuleName> ruleNameList = new ArrayList<>();
        ruleNameList.add(ruleName);

        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);

        List<RuleName> result = ruleNameService.getRuleNameList();

        assertEquals(1, result.size());
        verify(ruleNameRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void givenValidDto_whenAddRuleName_thenReturnSavedRuleName() {
        RuleName ruleName1 = new RuleName();
        ruleName1.setName("name");
        ruleName1.setDescription("description");
        ruleName1.setJson("json");
        ruleName1.setTemplate("template");
        ruleName1.setSqlStr("str");
        ruleName1.setSqlPart("part");

        RuleNameDto dto = new RuleNameDto();
        dto.setName("name");
        dto.setDescription("description");
        dto.setJson("json");
        dto.setTemplate("template");
        dto.setSqlStr("str");
        dto.setSqlPart("part");

        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName1);

        RuleName result = ruleNameService.addRuleName(dto);
        assertEquals("name", result.getName());
        assertEquals("description", result.getDescription());
        assertEquals("json", result.getJson());
        assertEquals("template", result.getTemplate());
        assertEquals("str", result.getSqlStr());
        assertEquals("part", result.getSqlPart());
        verify(ruleNameRepository, Mockito.times(1)).save(any(RuleName.class));
    }

    @Test
    public void givenExistingId_whenGetRuleNameById_thenReturnRuleName() {
        RuleName ruleName = new RuleName();
        ruleName.setName("name");

        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName));

        RuleName result = ruleNameService.getRuleNameById(1);

        assertEquals("name", result.getName());
        verify(ruleNameRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    public void givenNonExistingId_whenGetRuleNameById_thenThrowRequestException() {
        RequestException exception = assertThrows(RequestException.class, () -> ruleNameService.getRuleNameById(anyInt()));
        assertEquals("rule name not found", exception.getMessage());

    }

    @Test
    public void givenExistingIdAndValidDto_whenUpdateRuleName_thenReturnUpdatedRuleName() {
        RuleName ruleName1 = new RuleName();
        ruleName1.setId(1);
        ruleName1.setName("name");
        ruleName1.setDescription("description");
        ruleName1.setJson("json");
        ruleName1.setTemplate("template");
        ruleName1.setSqlStr("str");
        ruleName1.setSqlPart("part");

        RuleNameDto dto = new RuleNameDto();
        dto.setName("name1");
        dto.setDescription("description1");
        dto.setJson("json1");
        dto.setTemplate("template1");
        dto.setSqlStr("str1");
        dto.setSqlPart("part1");

        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName1));
        when(ruleNameRepository.save(ruleName1)).thenReturn(ruleName1);

        RuleName result = ruleNameService.updateRuleName(1, dto);

        assertEquals(1, result.getId());
        assertEquals("name1", result.getName());
        assertEquals("description1", result.getDescription());
        assertEquals("json1", result.getJson());
        assertEquals("template1", result.getTemplate());
        assertEquals("str1", result.getSqlStr());
        assertEquals("part1", result.getSqlPart());
        verify(ruleNameRepository, Mockito.times(1)).save(any(RuleName.class));
        verify(ruleNameRepository, Mockito.times(1)).findById(anyInt());

    }

    @Test
    public void givenNonExistingId_whenUpdateRuleName_thenThrowRequestException() {
        RuleNameDto dto = new RuleNameDto();
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        RequestException exception = assertThrows(RequestException.class, () -> ruleNameService.updateRuleName(1, dto));
        assertEquals("rule name is empty", exception.getMessage());
    }

    @Test
    public void givenExistingId_whenDeleteRuleName_thenDeleteSuccessfully() {
        RuleName ruleName = new RuleName();

        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName));

        ruleNameService.deleteRuleName(1);

        verify(ruleNameRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void givenNonExistingId_whenDeleteRuleName_thenThrowRequestException() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        RequestException exception = assertThrows(RequestException.class, () -> ruleNameService.deleteRuleName(anyInt()));
        assertEquals("Unknown ruleName", exception.getMessage());
    }
}
