package ru.aston.fighterservice.service;

import ru.aston.fighterservice.dto.RecordDto;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс сервиса для операций с Record.
 */
public interface RecordService {

    /**
     * Создаёт новую запись
     *
     * @param recordDto DTO записи
     * @return созданный RecordDto
     */
    RecordDto createRecord(RecordDto recordDto);

    /**
     * Получает запись по идентификатору.
     *
     * @param id идентификатор записи
     * @return RecordDto
     */
    RecordDto getRecord(UUID id);

    /**
     * Получает список всех записей
     *
     * @return список RecordDto
     */
    List<RecordDto> getAllRecords();

    /**
     * Обновляет запись
     *
     * @param id идентификатор записи
     * @param recordDto обновлённые данные
     * @return обновлённый RecordDto
     */
    RecordDto updateRecord(UUID id, RecordDto recordDto);

    /**
     * Удаляет запись
     *
     * @param id идентификатор записи
     */
    void deleteRecord(UUID id);
}