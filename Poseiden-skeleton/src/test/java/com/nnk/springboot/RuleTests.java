package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void ruleTest() {
		//RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		//
		//		// Save
		//		rule = ruleNameRepository.save(rule);
		//		assertNotNull(rule.getId());
		//		assertTrue(rule.getName().equals("Rule Name"));
		//
		//		// Update
		//		rule.setName("Rule Name Update");
		//		rule = ruleNameRepository.save(rule);
		//		assertTrue(rule.getName().equals("Rule Name Update"));
		//
		//		// Find
		//		List<RuleName> listResult = ruleNameRepository.findAll();
		//		assertTrue(listResult.size() > 0);
		//
		//		// Delete
		//		Integer id = rule.getId();
		//		ruleNameRepository.delete(rule);
		//		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		//		assertFalse(ruleList.isPresent());
	}
}
