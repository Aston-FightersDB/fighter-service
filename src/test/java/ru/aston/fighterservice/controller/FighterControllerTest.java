package ru.aston.fighterservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.fighterservice.dto.FighterDto;
import ru.aston.fighterservice.dto.InfoDto;
import ru.aston.fighterservice.dto.RecordDto;
import ru.aston.fighterservice.service.FighterService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FighterController.class)
@Import(FighterControllerTest.MockConfig.class)
public class FighterControllerTest {

    @TestConfiguration
    static class MockConfig {
        @Bean
        public FighterService fighterService() {
            return Mockito.mock(FighterService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FighterService fighterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createFighter_ReturnsCreatedFighter() throws Exception {
        FighterDto inputDto = new FighterDto();
        inputDto.setName("John Doe");
        inputDto.setInfo(new InfoDto(null, "Club A", "Manager A"));
        inputDto.setRecord(new RecordDto(null, 3, 1, 0));

        FighterDto outputDto = new FighterDto(UUID.randomUUID(),
                new InfoDto(UUID.randomUUID(), "Club A", "Manager A"),
                "John Doe",
                new RecordDto(UUID.randomUUID(), 3, 1, 0));

        when(fighterService.createFighter(any(FighterDto.class))).thenReturn(outputDto);

        mockMvc.perform(post("/api/v1/fighters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.info.club").value("Club A"))
                .andExpect(jsonPath("$.record.wins").value(3));
    }

    @Test
    void getFighter_ReturnsFighter() throws Exception {
        UUID id = UUID.randomUUID();
        FighterDto fighterDto = new FighterDto(id,
                new InfoDto(UUID.randomUUID(), "Club B", "Manager B"),
                "Jane Doe",
                new RecordDto(UUID.randomUUID(), 5, 2, 1));

        when(fighterService.getFighter(id)).thenReturn(fighterDto);

        mockMvc.perform(get("/api/v1/fighters/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.info.club").value("Club B"))
                .andExpect(jsonPath("$.record.wins").value(5));
    }

    @Test
    void getAllFighters_ReturnsListOfFighters() throws Exception {
        FighterDto fighterDto = new FighterDto(null,
                new InfoDto(null, "Club C", "Manager C"),
                "Fighter 1",
                new RecordDto(null, 4, 1, 0));

        when(fighterService.getAllFighters()).thenReturn(Collections.singletonList(fighterDto));

        mockMvc.perform(get("/api/v1/fighters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Fighter 1"))
                .andExpect(jsonPath("$[0].info.club").value("Club C"))
                .andExpect(jsonPath("$[0].record.wins").value(4));
    }

    @Test
    void updateFighter_ReturnsUpdatedFighter() throws Exception {
        UUID id = UUID.randomUUID();
        FighterDto inputDto = new FighterDto();
        inputDto.setName("Old Name");
        inputDto.setInfo(new InfoDto(null, "Old Club", "Old Manager"));
        inputDto.setRecord(new RecordDto(null, 1, 0, 0));

        FighterDto updatedDto = new FighterDto(id,
                new InfoDto(UUID.randomUUID(), "New Club", "New Manager"),
                "New Name",
                new RecordDto(UUID.randomUUID(), 2, 1, 0));

        when(fighterService.updateFighter(eq(id), any(FighterDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/api/v1/fighters/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.info.club").value("New Club"))
                .andExpect(jsonPath("$.record.wins").value(2));
    }

    @Test
    void deleteFighter_ReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/fighters/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void getFighterRecord_ReturnsRecord() throws Exception {
        UUID id = UUID.randomUUID();
        RecordDto recordDto = new RecordDto(UUID.randomUUID(), 10, 2, 1);

        when(fighterService.getFighterRecord(id)).thenReturn(recordDto);

        mockMvc.perform(get("/api/v1/fighters/{id}/record", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wins").value(10))
                .andExpect(jsonPath("$.loses").value(2))
                .andExpect(jsonPath("$.ties").value(1));
    }

    @Test
    void createOrUpdateFighterRecord_ReturnsCreatedRecord() throws Exception {
        UUID id = UUID.randomUUID();
        RecordDto inputRecord = new RecordDto();
        inputRecord.setWins(5);
        inputRecord.setLoses(1);
        inputRecord.setTies(0);

        RecordDto outputRecord = new RecordDto(UUID.randomUUID(), 5, 1, 0);

        when(fighterService.createOrUpdateFighterRecord(eq(id), any(RecordDto.class)))
                .thenReturn(outputRecord);

        mockMvc.perform(post("/api/v1/fighters/{id}/record", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputRecord)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.wins").value(5))
                .andExpect(jsonPath("$.loses").value(1))
                .andExpect(jsonPath("$.ties").value(0));
    }

    @Test
    void partialUpdateFighter_ReturnsUpdatedFighter() throws Exception {
        UUID id = UUID.randomUUID();
        FighterDto partialDto = new FighterDto();
        partialDto.setName("Partial Name");

        FighterDto updatedDto = new FighterDto(id,
                new InfoDto(UUID.randomUUID(), "Club Partial", "Manager Partial"),
                "Partial Name",
                new RecordDto(UUID.randomUUID(), 3, 2, 1));

        when(fighterService.partialUpdateFighter(eq(id), any(FighterDto.class))).thenReturn(updatedDto);

        mockMvc.perform(patch("/api/v1/fighters/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Partial Name"));
    }

    @Test
    void partialUpdateFighterRecord_ReturnsUpdatedRecord() throws Exception {
        UUID id = UUID.randomUUID();
        RecordDto partialRecord = new RecordDto();
        partialRecord.setWins(7);

        RecordDto updatedRecord = new RecordDto(UUID.randomUUID(), 7, 3, 2);

        when(fighterService.partialUpdateFighterRecord(eq(id), any(RecordDto.class))).thenReturn(updatedRecord);

        mockMvc.perform(patch("/api/v1/fighters/{id}/record", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialRecord)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wins").value(7))
                .andExpect(jsonPath("$.loses").value(3))
                .andExpect(jsonPath("$.ties").value(2));
    }
}
