package ru.aston.fighterservice.service;

import ru.aston.fighterservice.dto.FighterDto;
import ru.aston.fighterservice.dto.InfoDto;
import ru.aston.fighterservice.dto.RecordDto;
import ru.aston.fighterservice.entity.Fighter;
import ru.aston.fighterservice.entity.Record;
import ru.aston.fighterservice.mapper.FighterMapper;
import ru.aston.fighterservice.mapper.InfoMapper;
import ru.aston.fighterservice.mapper.RecordMapper;
import ru.aston.fighterservice.repository.FighterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import ru.aston.fighterservice.service.impl.FighterServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FighterServiceImplTest {

    @Mock
    private FighterRepository fighterRepository;

    @InjectMocks
    private FighterServiceImpl fighterService;

    @Test
    void createFighter_ReturnsCreatedFighterDto() {
        // Подготовка входных данных
        FighterDto inputDto = new FighterDto(null,
                new InfoDto(null, "Club A", "Manager A"),
                "John Doe",
                new RecordDto(null, 3, 1, 0));

        // Преобразование в сущность (используем маппер)
        Fighter fighterEntity = FighterMapper.toEntity(inputDto);
        // Симулируем, что репозиторий заполнил id
        fighterEntity.setId(UUID.randomUUID());

        when(fighterRepository.save(any(Fighter.class))).thenReturn(fighterEntity);

        FighterDto result = fighterService.createFighter(inputDto);

        assertNotNull(result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("Club A", result.getInfo().getClub());
        assertEquals(3, result.getRecord().getWins());
        verify(fighterRepository).save(any(Fighter.class));
    }

    @Test
    void getFighter_ReturnsFighterDto() {
        UUID id = UUID.randomUUID();
        // Создаем сущность бойца
        Fighter fighter = new Fighter();
        fighter.setId(id);
        fighter.setName("Jane Doe");
        fighter.setInfo(InfoMapper.toEntity(new InfoDto(null, "Club B", "Manager B")));
        fighter.setRecord(RecordMapper.toEntity(new RecordDto(null, 5, 2, 1)));

        when(fighterRepository.findById(id)).thenReturn(Optional.of(fighter));

        FighterDto result = fighterService.getFighter(id);

        assertEquals(id, result.getId());
        assertEquals("Jane Doe", result.getName());
        assertEquals("Club B", result.getInfo().getClub());
        assertEquals(5, result.getRecord().getWins());
    }

    @Test
    void getFighter_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(fighterRepository.findById(id)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> fighterService.getFighter(id));
        assertTrue(exception.getMessage().contains("Боец не найден с id: " + id));
    }

    @Test
    void getAllFighters_ReturnsListOfFighterDtos() {
        Fighter fighter = new Fighter();
        fighter.setId(UUID.randomUUID());
        fighter.setName("Fighter 1");
        fighter.setInfo(InfoMapper.toEntity(new InfoDto(null, "Club C", "Manager C")));
        fighter.setRecord(RecordMapper.toEntity(new RecordDto(null, 4, 1, 0)));

        when(fighterRepository.findAll()).thenReturn(Collections.singletonList(fighter));

        var result = fighterService.getAllFighters();
        assertEquals(1, result.size());
        assertEquals("Fighter 1", result.get(0).getName());
    }

    @Test
    void updateFighter_ReturnsUpdatedFighterDto() {
        UUID id = UUID.randomUUID();
        Fighter existingFighter = new Fighter();
        existingFighter.setId(id);
        existingFighter.setName("Old Name");
        existingFighter.setInfo(InfoMapper.toEntity(new InfoDto(null, "Old Club", "Old Manager")));
        existingFighter.setRecord(RecordMapper.toEntity(new RecordDto(null, 1, 0, 0)));

        when(fighterRepository.findById(id)).thenReturn(Optional.of(existingFighter));

        FighterDto updateDto = new FighterDto(null,
                new InfoDto(null, "New Club", "New Manager"),
                "New Name",
                new RecordDto(null, 2, 1, 0));

        // Симулируем обновленную сущность
        Fighter updatedFighter = existingFighter;
        updatedFighter.setName(updateDto.getName());
        updatedFighter.setInfo(InfoMapper.toEntity(updateDto.getInfo()));
        updatedFighter.setRecord(RecordMapper.toEntity(updateDto.getRecord()));

        when(fighterRepository.save(existingFighter)).thenReturn(updatedFighter);

        FighterDto result = fighterService.updateFighter(id, updateDto);

        assertEquals("New Name", result.getName());
        assertEquals("New Club", result.getInfo().getClub());
        assertEquals(2, result.getRecord().getWins());
    }

    @Test
    void deleteFighter_CallsRepositoryDelete() {
        UUID id = UUID.randomUUID();
        fighterService.deleteFighter(id);
        verify(fighterRepository).deleteById(id);
    }

    @Test
    void getFighterRecord_ReturnsRecordDto() {
        UUID id = UUID.randomUUID();
        Fighter fighter = new Fighter();
        fighter.setId(id);
        fighter.setName("Test Fighter");
        // Устанавливаем статистику
        fighter.setRecord(RecordMapper.toEntity(new RecordDto(null, 10, 2, 1)));

        when(fighterRepository.findById(id)).thenReturn(Optional.of(fighter));

        RecordDto result = fighterService.getFighterRecord(id);
        assertEquals(10, result.getWins());
        assertEquals(2, result.getLoses());
    }

    @Test
    void getFighterRecord_RecordNotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        Fighter fighter = new Fighter();
        fighter.setId(id);
        fighter.setName("Test Fighter");
        fighter.setRecord(null);

        when(fighterRepository.findById(id)).thenReturn(Optional.of(fighter));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fighterService.getFighterRecord(id));
        assertTrue(exception.getMessage().contains("Статистика не найдена для бойца с id: " + id));
    }

    @Test
    void createOrUpdateFighterRecord_CreatesNewRecord_IfNotPresent() {
        UUID fighterId = UUID.randomUUID();
        Fighter fighter = new Fighter();
        fighter.setId(fighterId);
        fighter.setName("Test Fighter");
        fighter.setRecord(null);

        when(fighterRepository.findById(fighterId)).thenReturn(Optional.of(fighter));

        RecordDto recordDto = new RecordDto(null, 5, 1, 0);
        // При создании новой записи, маппер преобразует recordDto в Record
        Record newRecord = RecordMapper.toEntity(recordDto);
        // Симулируем, что после сохранения в fighter установится созданная запись
        fighter.setRecord(newRecord);
        when(fighterRepository.save(fighter)).thenReturn(fighter);

        RecordDto result = fighterService.createOrUpdateFighterRecord(fighterId, recordDto);
        assertEquals(5, result.getWins());
    }

    @Test
    void createOrUpdateFighterRecord_UpdatesExistingRecord() {
        UUID fighterId = UUID.randomUUID();
        Fighter fighter = new Fighter();
        fighter.setId(fighterId);
        fighter.setName("Test Fighter");
        // Создаем существующую запись со старыми данными
        Record existingRecord = RecordMapper.toEntity(new RecordDto(null, 3, 1, 0));
        fighter.setRecord(existingRecord);

        when(fighterRepository.findById(fighterId)).thenReturn(Optional.of(fighter));

        RecordDto recordDto = new RecordDto(null, 7, 2, 1);
        // При обновлении записи изменяются поля
        existingRecord.setWins(recordDto.getWins());
        existingRecord.setLoses(recordDto.getLoses());
        existingRecord.setTies(recordDto.getTies());
        when(fighterRepository.save(fighter)).thenReturn(fighter);

        RecordDto result = fighterService.createOrUpdateFighterRecord(fighterId, recordDto);
        assertEquals(7, result.getWins());
        assertEquals(2, result.getLoses());
    }

    @Test
    void partialUpdateFighter_UpdatesNameOnly() {
        UUID fighterId = UUID.randomUUID();
        Fighter fighter = new Fighter();
        fighter.setId(fighterId);
        fighter.setName("Old Name");
        fighter.setInfo(InfoMapper.toEntity(new InfoDto(null, "Club", "Manager")));
        fighter.setRecord(RecordMapper.toEntity(new RecordDto(null, 2, 1, 0)));

        when(fighterRepository.findById(fighterId)).thenReturn(Optional.of(fighter));
        when(fighterRepository.save(fighter)).thenReturn(fighter);

        FighterDto partialDto = new FighterDto();
        partialDto.setName("Updated Name");

        FighterDto result = fighterService.partialUpdateFighter(fighterId, partialDto);
        assertEquals("Updated Name", result.getName());
    }

    @Test
    void partialUpdateFighterRecord_UpdatesExistingRecordFields() {
        UUID fighterId = UUID.randomUUID();
        Fighter fighter = new Fighter();
        fighter.setId(fighterId);
        // Создаем существующую запись
        Record existingRecord = RecordMapper.toEntity(new RecordDto(null, 3, 1, 0));
        fighter.setRecord(existingRecord);

        when(fighterRepository.findById(fighterId)).thenReturn(Optional.of(fighter));
        when(fighterRepository.save(fighter)).thenReturn(fighter);

        RecordDto partialRecord = new RecordDto();
        partialRecord.setWins(8);
        // Обновляем только wins

        RecordDto result = fighterService.partialUpdateFighterRecord(fighterId, partialRecord);
        assertEquals(8, result.getWins());
        // Если поля loses/ties не заданы, они остаются прежними (1 и 0)
        assertEquals(1, result.getLoses());
        assertEquals(0, result.getTies());
    }

    @Test
    void partialUpdateFighterRecord_CreatesNewRecord_IfNotExists() {
        UUID fighterId = UUID.randomUUID();
        Fighter fighter = new Fighter();
        fighter.setId(fighterId);
        fighter.setRecord(null);

        when(fighterRepository.findById(fighterId)).thenReturn(Optional.of(fighter));
        when(fighterRepository.save(fighter)).thenReturn(fighter);

        RecordDto partialRecord = new RecordDto();
        partialRecord.setWins(4);
        partialRecord.setLoses(0);
        partialRecord.setTies(0);

        RecordDto result = fighterService.partialUpdateFighterRecord(fighterId, partialRecord);
        assertEquals(4, result.getWins());
    }
}
