package ru.aston.fighterservice.mapper;

import ru.aston.fighterservice.dto.InfoDto;
import ru.aston.fighterservice.entity.Info;

/**
 * Маппер для преобразования между Info и InfoDto.
 */
public class InfoMapper {

    /**
     * Преобразует сущность Info в DTO.
     *
     * @param info сущность Info
     * @return объект InfoDto
     */
    public static InfoDto toDto(Info info) {
        if (info == null) return null;
        InfoDto dto = new InfoDto();
        dto.setId(info.getId());
        dto.setClub(info.getClub());
        dto.setManager(info.getManager());
        return dto;
    }

    /**
     * Преобразует DTO в сущность Info.
     *
     * @param dto объект InfoDto
     * @return сущность Info
     */
    public static Info toEntity(InfoDto dto) {
        if (dto == null) return null;
        Info info = new Info();
        info.setId(dto.getId());
        info.setClub(dto.getClub());
        info.setManager(dto.getManager());
        return info;
    }
}