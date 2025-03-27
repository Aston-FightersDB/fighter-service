package ru.aston.fighterservice.service.impl;

import ru.aston.fighterservice.dto.RecordDto;
import ru.aston.fighterservice.mapper.RecordMapper;
import ru.aston.fighterservice.repository.RecordRepository;
import ru.aston.fighterservice.entity.Record;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import ru.aston.fighterservice.service.RecordService;

/**
 * Реализация сервиса для операций с Record.
 */
@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public RecordDto createRecord(RecordDto recordDto) {
        Record record = RecordMapper.toEntity(recordDto);
        Record savedRecord = recordRepository.save(record);
        return RecordMapper.toDto(savedRecord);
    }

    @Override
    public RecordDto getRecord(UUID id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись не найдена с id: " + id));
        return RecordMapper.toDto(record);
    }

    @Override
    public List<RecordDto> getAllRecords() {
        return recordRepository.findAll().stream()
                .map(RecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RecordDto updateRecord(UUID id, RecordDto recordDto) {
        Record existingRecord = recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись не найдена с id: " + id));
        existingRecord.setWins(recordDto.getWins());
        existingRecord.setLoses(recordDto.getLoses());
        existingRecord.setTies(recordDto.getTies());
        Record updatedRecord = recordRepository.save(existingRecord);
        return RecordMapper.toDto(updatedRecord);
    }

    @Override
    public void deleteRecord(UUID id) {
        recordRepository.deleteById(id);
    }
}