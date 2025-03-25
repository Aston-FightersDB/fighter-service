package org.example.fighter.service;

import org.example.fighter.dto.RecordDto;

import java.util.List;

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
    RecordDto getRecord(Long id);

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
    RecordDto updateRecord(Long id, RecordDto recordDto);

    /**
     * Удаляет запись
     *
     * @param id идентификатор записи
     */
    void deleteRecord(Long id);
}