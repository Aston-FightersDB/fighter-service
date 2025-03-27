package ru.aston.fighterservice.service;

import ru.aston.fighterservice.dto.InfoDto;
import ru.aston.fighterservice.entity.Info;
import ru.aston.fighterservice.mapper.InfoMapper;
import ru.aston.fighterservice.repository.InfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import ru.aston.fighterservice.service.impl.InfoServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InfoServiceImplTest {

    @Mock
    private InfoRepository infoRepository;

    @InjectMocks
    private InfoServiceImpl infoService;

    @Test
    void createInfo_ReturnsCreatedInfoDto() {
        InfoDto inputDto = new InfoDto(null, "Club A", "Manager A");
        Info infoEntity = InfoMapper.toEntity(inputDto);
        infoEntity.setId(UUID.randomUUID());

        when(infoRepository.save(any(Info.class))).thenReturn(infoEntity);

        InfoDto result = infoService.createInfo(inputDto);
        assertNotNull(result.getId());
        assertEquals("Club A", result.getClub());
    }

    @Test
    void getInfo_ReturnsInfoDto() {
        UUID id = UUID.randomUUID();
        Info info = new Info();
        info.setId(id);
        info.setClub("Club B");
        info.setManager("Manager B");

        when(infoRepository.findById(id)).thenReturn(Optional.of(info));

        InfoDto result = infoService.getInfo(id);
        assertEquals(id, result.getId());
        assertEquals("Club B", result.getClub());
    }

    @Test
    void getInfo_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(infoRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> infoService.getInfo(id));
        assertTrue(exception.getMessage().contains("Информация не найдена с id: " + id));
    }

    @Test
    void getAllInfos_ReturnsListOfInfoDtos() {
        Info info = new Info();
        info.setId(UUID.randomUUID());
        info.setClub("Club C");
        info.setManager("Manager C");

        when(infoRepository.findAll()).thenReturn(Collections.singletonList(info));

        var result = infoService.getAllInfos();
        assertEquals(1, result.size());
        assertEquals("Club C", result.get(0).getClub());
    }

    @Test
    void updateInfo_ReturnsUpdatedInfoDto() {
        UUID id = UUID.randomUUID();
        Info existingInfo = new Info();
        existingInfo.setId(id);
        existingInfo.setClub("Old Club");
        existingInfo.setManager("Old Manager");

        when(infoRepository.findById(id)).thenReturn(Optional.of(existingInfo));

        InfoDto updateDto = new InfoDto(null, "New Club", "New Manager");
        existingInfo.setClub(updateDto.getClub());
        existingInfo.setManager(updateDto.getManager());
        when(infoRepository.save(existingInfo)).thenReturn(existingInfo);

        InfoDto result = infoService.updateInfo(id, updateDto);
        assertEquals("New Club", result.getClub());
        assertEquals("New Manager", result.getManager());
    }

    @Test
    void deleteInfo_CallsRepositoryDelete() {
        UUID id = UUID.randomUUID();
        infoService.deleteInfo(id);
        verify(infoRepository).deleteById(id);
    }
}
