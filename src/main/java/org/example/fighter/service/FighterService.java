package org.example.fighter.service;

import org.example.fighter.dto.FighterDto;
import org.example.fighter.dto.RecordDto;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс сервиса для операций с Fighter.
 */
public interface FighterService {

    /**
     * Создаёт нового бойца.
     *
     * @param fighterDto DTO бойца
     * @return созданный FighterDto
     */
    FighterDto createFighter(FighterDto fighterDto);

    /**
     * Получает бойца по идентификатору.
     *
     * @param id идентификатор бойца
     * @return FighterDto
     */
    FighterDto getFighter(UUID id);

    /**
     * Получает список всех бойцов.
     *
     * @return список FighterDto
     */
    List<FighterDto> getAllFighters();

    /**
     * Обновляет данные бойца.
     *
     * @param id идентификатор бойца
     * @param fighterDto обновлённые данные
     * @return обновлённый FighterDto
     */
    FighterDto updateFighter(UUID id, FighterDto fighterDto);

    /**
     * Удаляет бойца.
     *
     * @param id идентификатор бойца
     */
    void deleteFighter(UUID id);

    /**
     * Получить статистику бойца по идентификатору бойца.
     *
     * @param fighterId
     * @return RecordDto бойца
     */
    RecordDto getFighterRecord(UUID fighterId);

    /**
     * Создать или обновить статистику бойца.
     *
     * @param fighterId идентификатор бойца
     * @param recordDto
     * @return обновленная статистика бойца RecordDto
     */
    RecordDto createOrUpdateFighterRecord(UUID fighterId, RecordDto recordDto);

    /**
     * Частично обновить данные бойца.
     *
     * @param fighterId идентификатор бойца
     * @param fighterDto
     * @return обновленная информация бойца
     */
    FighterDto partialUpdateFighter(UUID fighterId, FighterDto fighterDto);

    /**
     * Частично обновить статистику бойца.
     *
     * @param fighterId идентификатор бойца
     * @param recordDto
     * @return обновленная статистика бойца
     */
    RecordDto partialUpdateFighterRecord(UUID fighterId, RecordDto recordDto);
}