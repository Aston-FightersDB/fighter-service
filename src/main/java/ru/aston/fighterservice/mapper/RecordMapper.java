package ru.aston.fighterservice.mapper;

import ru.aston.fighterservice.dto.RecordDto;
import ru.aston.fighterservice.entity.Record;

/**
 * Маппер для преобразования между Record и RecordDto.
 */
public class RecordMapper {

    /**
     * Преобразует сущность Record в DTO.
     *
     * @param record сущность Record
     * @return объект RecordDto
     */
    public static RecordDto toDto(Record record) {
        if (record == null) return null;
        RecordDto dto = new RecordDto();
        dto.setId(record.getId());
        dto.setWins(record.getWins());
        dto.setLoses(record.getLoses());
        dto.setTies(record.getTies());
        return dto;
    }

    /**
     * Преобразует DTO в сущность Record.
     *
     * @param dto объект RecordDto
     * @return сущность Record
     */
    public static Record toEntity(RecordDto dto) {
        if (dto == null) return null;
        Record record = new Record();
        record.setId(dto.getId());
        record.setWins(dto.getWins());
        record.setLoses(dto.getLoses());
        record.setTies(dto.getTies());
        return record;
    }
}