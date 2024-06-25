package com.maple.maple_boss_now.controller;

import com.maple.maple_boss_now.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class) // 컨트롤러와 관련된 부분만 로드
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;




    @Test
    void CharacterBasicInfo() throws Exception {
        // given
        String characterName = "붓면";


        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search/character")
                        .param("characterName", characterName))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.characterName").value(characterBasicInfoResponse.getCharacterName()))
                .andDo(print());
    }

}