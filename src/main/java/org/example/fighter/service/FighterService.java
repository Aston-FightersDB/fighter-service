package org.example.fighter.service;

import org.example.fighter.dto.FighterDto;
import org.example.fighter.dto.RecordDto;

import java.util.List;

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
    FighterDto getFighter(Long id);

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
    FighterDto updateFighter(Long id, FighterDto fighterDto);

    /**
     * Удаляет бойца.
     *
     * @param id идентификатор бойца
     */
    void deleteFighter(Long id);

    /**
     * Получить статистику бойца по идентификатору бойца.
     *
     * @param fighterId
     * @return RecordDto бойца
     */
    RecordDto getFighterRecord(Long fighterId);

    /**
     * Создать или обновить статистику бойца.
     *
     * @param fighterId идентификатор бойца
     * @param recordDto
     * @return обновленная статистика бойца RecordDto
     */
    RecordDto createOrUpdateFighterRecord(Long fighterId, RecordDto recordDto);

    /**
     * Частично обновить данные бойца.
     *
     * @param fighterId идентификатор бойца
     * @param fighterDto
     * @return обновленная информация бойца
     */
    FighterDto partialUpdateFighter(Long fighterId, FighterDto fighterDto);

    /**
     * Частично обновить статистику бойца.
     *
     * @param fighterId идентификатор бойца
     * @param recordDto
     * @return обновленная статистика бойца
     */
    RecordDto partialUpdateFighterRecord(Long fighterId, RecordDto recordDto);
}