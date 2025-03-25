package org.example.fighter.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fighter.dto.FighterDto;
import org.example.fighter.dto.RecordDto;
import org.example.fighter.service.FighterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для операций с Fighter.
 */
@RestController
@RequestMapping("/api/v1/fighters")
@RequiredArgsConstructor
public class FighterController {

    private final FighterService fighterService;

    /**
     * Создаёт нового бойца.
     *
     * @param fighterDto DTO бойца
     * @return созданный FighterDto
     */
    @PostMapping
    public ResponseEntity<FighterDto> createFighter(@RequestBody FighterDto fighterDto) {
        FighterDto createdFighter = fighterService.createFighter(fighterDto);
        return ResponseEntity.ok(createdFighter);
    }

    /**
     * Получает бойца по идентификатору.
     *
     * @param id идентификатор бойца
     * @return FighterDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<FighterDto> getFighter(@PathVariable Long id) {
        FighterDto fighterDto = fighterService.getFighter(id);
        return ResponseEntity.ok(fighterDto);
    }

    /**
     * Получает список всех бойцов.
     *
     * @return список FighterDto
     */
    @GetMapping
    public ResponseEntity<List<FighterDto>> getAllFighters() {
        List<FighterDto> fighterDtos = fighterService.getAllFighters();
        return ResponseEntity.ok(fighterDtos);
    }

    /**
     * Обновляет данные бойца.
     *
     * @param id идентификатор бойца
     * @param fighterDto обновлённые данные
     * @return обновлённый FighterDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<FighterDto> updateFighter(@PathVariable Long id, @RequestBody FighterDto fighterDto) {
        FighterDto updatedFighter = fighterService.updateFighter(id, fighterDto);
        return ResponseEntity.ok(updatedFighter);
    }

    /**
     * Удаляет бойца.
     *
     * @param id идентификатор бойца
     * @return ответ без контента
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFighter(@PathVariable Long id) {
        fighterService.deleteFighter(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Получить статистику конкретного бойца.
     *
     * @param id идентификатор бойца
     * @return recordDto
     */
    @GetMapping("/{id}/record")
    public ResponseEntity<RecordDto> getFighterRecord(@PathVariable Long id) {
        RecordDto recordDto=fighterService.getFighterRecord(id);
        return ResponseEntity.ok(recordDto);
    }

    /**
     * Добавить или удалить статистику конкретного бойца.
     *
     * @param id идентификатор бойца
     * @param recordDto
     * @return созданный/обновленный recordDto
     */
    @PostMapping("/{id}/record")
    public ResponseEntity<RecordDto> createOrUpdateFighterRecord(@PathVariable Long id, @Valid @RequestBody RecordDto recordDto) {
        RecordDto updateRecord = fighterService.createOrUpdateFighterRecord(id,recordDto);
        return new ResponseEntity<>(updateRecord, HttpStatus.CREATED);
    }

    /**
     * Частичное обновление бойца.
     *
     * @param id идентификатор бойца
     * @param fighterDto
     * @return обновленный боец
     */
    @PatchMapping("/{id}")
    public ResponseEntity<FighterDto> partialUpdateFighter(@PathVariable Long id, @RequestBody FighterDto fighterDto) {
        FighterDto updatedFighter = fighterService.partialUpdateFighter(id, fighterDto);
        return ResponseEntity.ok(updatedFighter);
    }

    /**
     * Частичное обновление статистики бойца.
     *
     * @param id идентификатор бойца
     * @param recordDto
     * @return обновленная статистика бойца
     */
    @PatchMapping("/{id}/record")
    public ResponseEntity<RecordDto> partialUpdateFighterRecord(@PathVariable Long id, @Valid @RequestBody RecordDto recordDto) {
        RecordDto updateRecord = fighterService.partialUpdateFighterRecord(id,recordDto);
        return ResponseEntity.ok(updateRecord);
    }
}