package com.example.springbootbackend.controller;

import com.example.springbootbackend.model.Downtime;
import com.example.springbootbackend.model.DowntimeToFrom;
import com.example.springbootbackend.repository.DowntimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@WebMvcTest(DowntimeContoller.class)
class DowntimeContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    DowntimeRepository downtimeRepository;

    @Test
    void getAllDowntimeUnit () throws Exception {
//      assertEquals(2,downtimeService.getAllDowntime().size());
        List<Downtime> downtimeList = new ArrayList<>();
        downtimeList.add(new Downtime(1,"Jio","fast",LocalDateTime.parse("2021-08-12T19:52:20.371278"),LocalDateTime.parse("2022-08-12T19:52:20.371278")));
        downtimeList.add(new Downtime(2,"Airtel","slow", LocalDateTime.parse("2022-07-11T19:52:20.371278"),LocalDateTime.parse("2022-07-12T19:52:20.371278")));
        Mockito.when(downtimeRepository.findAll()).thenReturn(downtimeList);
        MvcResult mvcResult = mockMvc.perform(get("/base-url/get-downtimes")).andExpect(status().isOk()).andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(downtimeList);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
    }

    @Test
    void getDowntimeByIdUnit() throws Exception {
        long downtimeId = 1L;
        Downtime downtime = new Downtime(1,"Jio","fast",LocalDateTime.parse("2022-07-12T19:52:20.371278"),LocalDateTime.parse("2022-08-12T19:52:20.371278"));
        Mockito.when(downtimeRepository.findById(downtimeId)).thenReturn(Optional.of(downtime));
        MvcResult response = mockMvc.perform(get("/base-url/get-downtime/{id}", downtimeId)).andExpect(status().isOk()).andReturn();
        String actualResponse = response.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(downtime);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
    }

    @Test
    void createDowntimeUnit() throws Exception {
        Downtime downtime = new Downtime(1,"Jio","fast",LocalDateTime.parse("2022-07-12T19:52:20.371278"),LocalDateTime.parse("2022-08-12T19:52:20.371278"));
        DowntimeToFrom downtimeToFrom = new DowntimeToFrom(LocalDateTime.parse("2022-07-12T19:52:20.371278"),LocalDateTime.parse("2022-08-12T19:52:20.371278"));
        Mockito.when(downtimeRepository.save(downtime)).thenReturn(downtime);
        mockMvc.perform((post("/base-url/create-downtime"))
                        .param("provider","Jio")
                        .param("flow_name","fast")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(downtimeToFrom))).andExpect(status().isOk());
    }

    @Test
    void updataDowntimeByIdUnit() throws Exception {
        long id = 1L;
        Downtime savedDowntime = new Downtime(1,"Jio","fast",LocalDateTime.parse("2021-08-12T19:52:20.371278"),LocalDateTime.parse("2022-08-12T19:52:20.371278"));
        Downtime updatedDowntime = new Downtime(2,"Airtel","slow", LocalDateTime.parse("2022-07-11T19:52:20.371278"),LocalDateTime.parse("2022-07-12T19:52:20.371278"));

        Mockito.when(downtimeRepository.findById(id)).thenReturn(Optional.of(updatedDowntime));
        Mockito.when(downtimeRepository.save(updatedDowntime)).thenReturn(updatedDowntime);
        MvcResult response = mockMvc.perform((put("/base-url/update-downtime/{id}",id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDowntime))).andExpect(status().isOk()).andReturn();
        String actualResponse = response.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(updatedDowntime);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(expectedResponse);
    }

    @Test
    void deleteDowntimeByIdUnit() throws Exception{
        long downtimeId = 1L;
        Mockito.doNothing().when(downtimeRepository).deleteById(downtimeId);
        mockMvc.perform(delete("/base-url/delete-downtime/{id}", downtimeId)).andExpect(status().isOk());
    }
}