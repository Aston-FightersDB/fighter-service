package org.example.fighter.service;

import org.example.fighter.dto.InfoDto;

import java.util.List;

/**
 * Интерфейс сервиса для операций с Info.
 */
public interface InfoService {

    /**
     * Создаёт новую информацию.
     *
     * @param infoDto DTO информации
     * @return созданный InfoDto
     */
    InfoDto createInfo(InfoDto infoDto);

    /**
     * Получает информацию по идентификатору.
     *
     * @param id идентификатор информации
     * @return InfoDto
     */
    InfoDto getInfo(Long id);

    /**
     * Получает список всех записей Info.
     *
     * @return список InfoDto
     */
    List<InfoDto> getAllInfos();

    /**
     * Обновляет информацию.
     *
     * @param id идентификатор информации
     * @param infoDto обновлённые данные
     * @return обновлённый InfoDto
     */
    InfoDto updateInfo(Long id, InfoDto infoDto);

    /**
     * Удаляет информацию.
     *
     * @param id идентификатор информации
     */
    void deleteInfo(Long id);
}