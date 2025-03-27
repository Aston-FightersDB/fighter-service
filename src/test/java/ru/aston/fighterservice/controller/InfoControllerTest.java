package ru.aston.fighterservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.aston.fighterservice.dto.InfoDto;
import ru.aston.fighterservice.service.InfoService;
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

@WebMvcTest(InfoController.class)
@Import(InfoControllerTest.MockConfig.class)
public class InfoControllerTest {

    @TestConfiguration
    static class MockConfig {
        @Bean
        public InfoService infoService() {
            return Mockito.mock(InfoService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InfoService infoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createInfo_ReturnsCreatedInfo() throws Exception {
        InfoDto inputDto = new InfoDto();
        inputDto.setClub("Club A");
        inputDto.setManager("Manager A");

        InfoDto outputDto = new InfoDto(UUID.randomUUID(), "Club A", "Manager A");

        when(infoService.createInfo(any(InfoDto.class))).thenReturn(outputDto);

        mockMvc.perform(post("/api/v1/infos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.club").value("Club A"))
                .andExpect(jsonPath("$.manager").value("Manager A"));
    }

    @Test
    void getInfo_ReturnsInfo() throws Exception {
        UUID id = UUID.randomUUID();
        InfoDto infoDto = new InfoDto(id, "Club B", "Manager B");

        when(infoService.getInfo(id)).thenReturn(infoDto);

        mockMvc.perform(get("/api/v1/infos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.club").value("Club B"))
                .andExpect(jsonPath("$.manager").value("Manager B"));
    }

    @Test
    void getAllInfos_ReturnsListOfInfos() throws Exception {
        InfoDto infoDto = new InfoDto(UUID.randomUUID(), "Club C", "Manager C");

        when(infoService.getAllInfos()).thenReturn(Collections.singletonList(infoDto));

        mockMvc.perform(get("/api/v1/infos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].club").value("Club C"))
                .andExpect(jsonPath("$[0].manager").value("Manager C"));
    }

    @Test
    void updateInfo_ReturnsUpdatedInfo() throws Exception {
        UUID id = UUID.randomUUID();
        InfoDto inputDto = new InfoDto(null, "Old Club", "Old Manager");

        InfoDto updatedDto = new InfoDto(id, "New Club", "New Manager");

        when(infoService.updateInfo(eq(id), any(InfoDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/api/v1/infos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.club").value("New Club"))
                .andExpect(jsonPath("$.manager").value("New Manager"));
    }

    @Test
    void deleteInfo_ReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/infos/{id}", id))
                .andExpect(status().isNoContent());
    }
}
