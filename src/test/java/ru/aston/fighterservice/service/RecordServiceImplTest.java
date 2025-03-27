package ru.aston.fighterservice.service;

import ru.aston.fighterservice.dto.RecordDto;
import ru.aston.fighterservice.entity.Record;
import ru.aston.fighterservice.mapper.RecordMapper;
import ru.aston.fighterservice.repository.RecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import ru.aston.fighterservice.service.impl.RecordServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecordServiceImplTest {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordServiceImpl recordService;

    @Test
    void createRecord_ReturnsCreatedRecordDto() {
        RecordDto inputDto = new RecordDto(null, 3, 1, 0);
        Record recordEntity = RecordMapper.toEntity(inputDto);
        recordEntity.setId(UUID.randomUUID());

        when(recordRepository.save(any(Record.class))).thenReturn(recordEntity);

        RecordDto result = recordService.createRecord(inputDto);
        assertNotNull(result.getId());
        assertEquals(3, result.getWins());
    }

    @Test
    void getRecord_ReturnsRecordDto() {
        UUID id = UUID.randomUUID();
        Record record = new Record();
        record.setId(id);
        record.setWins(5);
        record.setLoses(2);
        record.setTies(1);

        when(recordRepository.findById(id)).thenReturn(Optional.of(record));

        RecordDto result = recordService.getRecord(id);
        assertEquals(id, result.getId());
        assertEquals(5, result.getWins());
    }

    @Test
    void getRecord_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(recordRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> recordService.getRecord(id));
        assertTrue(exception.getMessage().contains("Запись не найдена с id: " + id));
    }

    @Test
    void getAllRecords_ReturnsListOfRecordDtos() {
        Record record = new Record();
        record.setId(UUID.randomUUID());
        record.setWins(4);
        record.setLoses(1);
        record.setTies(0);

        when(recordRepository.findAll()).thenReturn(Collections.singletonList(record));

        var result = recordService.getAllRecords();
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getWins());
    }

    @Test
    void updateRecord_ReturnsUpdatedRecordDto() {
        UUID id = UUID.randomUUID();
        Record existingRecord = new Record();
        existingRecord.setId(id);
        existingRecord.setWins(2);
        existingRecord.setLoses(1);
        existingRecord.setTies(0);

        when(recordRepository.findById(id)).thenReturn(Optional.of(existingRecord));

        RecordDto updateDto = new RecordDto(null, 10, 2, 1);
        existingRecord.setWins(updateDto.getWins());
        existingRecord.setLoses(updateDto.getLoses());
        existingRecord.setTies(updateDto.getTies());
        when(recordRepository.save(existingRecord)).thenReturn(existingRecord);

        RecordDto result = recordService.updateRecord(id, updateDto);
        assertEquals(10, result.getWins());
        assertEquals(2, result.getLoses());
        assertEquals(1, result.getTies());
    }

    @Test
    void deleteRecord_CallsRepositoryDelete() {
        UUID id = UUID.randomUUID();
        recordService.deleteRecord(id);
        verify(recordRepository).deleteById(id);
    }
}
