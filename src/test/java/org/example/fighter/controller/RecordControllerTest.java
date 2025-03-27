package org.example.fighter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.fighter.dto.RecordDto;
import org.example.fighter.service.RecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecordController.class)
@Import(RecordControllerTest.MockConfig.class)
public class RecordControllerTest {

    @TestConfiguration
    static class MockConfig {
        @Bean
        public RecordService recordService() {
            return Mockito.mock(RecordService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecordService recordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRecord_ReturnsCreatedRecord() throws Exception {
        RecordDto inputDto = new RecordDto();
        inputDto.setWins(3);
        inputDto.setLoses(1);
        inputDto.setTies(0);

        RecordDto outputDto = new RecordDto(UUID.randomUUID(), 3, 1, 0);

        when(recordService.createRecord(any(RecordDto.class))).thenReturn(outputDto);

        mockMvc.perform(post("/api/v1/records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wins").value(3))
                .andExpect(jsonPath("$.loses").value(1))
                .andExpect(jsonPath("$.ties").value(0));
    }

    @Test
    void getRecord_ReturnsRecord() throws Exception {
        UUID id = UUID.randomUUID();
        RecordDto recordDto = new RecordDto(id, 5, 2, 1);

        when(recordService.getRecord(id)).thenReturn(recordDto);

        mockMvc.perform(get("/api/v1/records/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.wins").value(5))
                .andExpect(jsonPath("$.loses").value(2))
                .andExpect(jsonPath("$.ties").value(1));
    }

    @Test
    void getAllRecords_ReturnsListOfRecords() throws Exception {
        RecordDto recordDto = new RecordDto(UUID.randomUUID(), 4, 1, 0);

        when(recordService.getAllRecords()).thenReturn(Collections.singletonList(recordDto));

        mockMvc.perform(get("/api/v1/records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].wins").value(4))
                .andExpect(jsonPath("$[0].loses").value(1))
                .andExpect(jsonPath("$[0].ties").value(0));
    }

    @Test
    void updateRecord_ReturnsUpdatedRecord() throws Exception {
        UUID id = UUID.randomUUID();
        RecordDto inputDto = new RecordDto(null, 2, 1, 0);
        RecordDto updatedDto = new RecordDto(id, 10, 2, 1);

        when(recordService.updateRecord(eq(id), any(RecordDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/api/v1/records/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wins").value(10))
                .andExpect(jsonPath("$.loses").value(2))
                .andExpect(jsonPath("$.ties").value(1));
    }

    @Test
    void deleteRecord_ReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/records/{id}", id))
                .andExpect(status().isNoContent());
    }
}
