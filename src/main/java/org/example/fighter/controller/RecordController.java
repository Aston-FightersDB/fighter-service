package org.example.fighter.controller;

import org.example.fighter.dto.RecordDto;
import org.example.fighter.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для операций с Record.
 */
@RestController
@RequestMapping("/api/v1/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Создаёт новую запись
     *
     * @param recordDto DTO запись
     * @return созданный RecordDto
     */
    @PostMapping
    public ResponseEntity<RecordDto> createRecord(@RequestBody RecordDto recordDto) {
        RecordDto createdRecord = recordService.createRecord(recordDto);
        return ResponseEntity.ok(createdRecord);
    }

    /**
     * Получает запись по идентификатору.
     *
     * @param id идентификатор записи
     * @return RecordDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecordDto> getRecord(@PathVariable UUID id) {
        RecordDto recordDto = recordService.getRecord(id);
        return ResponseEntity.ok(recordDto);
    }

    /**
     * Получает список всех записей
     *
     * @return список RecordDto
     */
    @GetMapping
    public ResponseEntity<List<RecordDto>> getAllRecords() {
        List<RecordDto> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    /**
     * Обновляет запись
     *
     * @param id идентификатор записи
     * @param recordDto обновлённые данные
     * @return обновлённый RecordDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecordDto> updateRecord(@PathVariable UUID id, @RequestBody RecordDto recordDto) {
        RecordDto updatedRecord = recordService.updateRecord(id, recordDto);
        return ResponseEntity.ok(updatedRecord);
    }

    /**
     * Удаляет запись
     *
     * @param id идентификатор записи
     * @return ответ без контента
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable UUID id) {
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}