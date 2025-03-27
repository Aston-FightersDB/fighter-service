package org.example.fighter.controller;

import org.example.fighter.dto.InfoDto;
import org.example.fighter.service.InfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для операций с Info.
 */
@RestController
@RequestMapping("/api/v1/infos")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    /**
     * Создаёт новую информацию.
     *
     * @param infoDto DTO информации
     * @return созданный InfoDto
     */
    @PostMapping
    public ResponseEntity<InfoDto> createInfo(@RequestBody InfoDto infoDto) {
        InfoDto createdInfo = infoService.createInfo(infoDto);
        return ResponseEntity.ok(createdInfo);
    }

    /**
     * Получает информацию по идентификатору.
     *
     * @param id идентификатор информации
     * @return InfoDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<InfoDto> getInfo(@PathVariable UUID id) {
        InfoDto infoDto = infoService.getInfo(id);
        return ResponseEntity.ok(infoDto);
    }

    /**
     * Получает список всей информации.
     *
     * @return список InfoDto
     */
    @GetMapping
    public ResponseEntity<List<InfoDto>> getAllInfos() {
        List<InfoDto> infos = infoService.getAllInfos();
        return ResponseEntity.ok(infos);
    }

    /**
     * Обновляет информацию.
     *
     * @param id идентификатор информации
     * @param infoDto обновлённые данные
     * @return обновлённый InfoDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<InfoDto> updateInfo(@PathVariable UUID id, @RequestBody InfoDto infoDto) {
        InfoDto updatedInfo = infoService.updateInfo(id, infoDto);
        return ResponseEntity.ok(updatedInfo);
    }

    /**
     * Удаляет информацию.
     *
     * @param id идентификатор информации
     * @return ответ без контента
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInfo(@PathVariable UUID id) {
        infoService.deleteInfo(id);
        return ResponseEntity.noContent().build();
    }
}