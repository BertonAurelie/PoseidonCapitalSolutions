package com.nnk.springboot;

import com.nnk.springboot.service.BidListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;


    @Test
    @WithMockUser
    public void givenUri_whenShowPage_thenReturnBidListListPage() throws Exception {
        this.mockMvc
                .perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attributeExists("username"));
    }

    @Test
    @WithMockUser
    public void givenUri_whenShowPage_thenReturnBidListAddPage() throws Exception {
        this.mockMvc
                .perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("BidListDto"));
    }

}
