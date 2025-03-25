package org.example.fighter.mapper;

import org.example.fighter.dto.FighterDto;
import org.example.fighter.entity.Fighter;

/**
 * Маппер для преобразования между Fighter и FighterDto.
 */
public class FighterMapper {

    /**
     * Преобразует сущность Fighter в DTO.
     *
     * @param fighter сущность Fighter
     * @return объект FighterDto
     */
    public static FighterDto toDto(Fighter fighter) {
        if (fighter == null) return null;
        FighterDto dto = new FighterDto();
        dto.setId(fighter.getId());
        dto.setName(fighter.getName());
        dto.setInfo(InfoMapper.toDto(fighter.getInfo()));
        dto.setRecord(RecordMapper.toDto(fighter.getRecord()));
        return dto;
    }

    /**
     * Преобразует DTO в сущность Fighter.
     *
     * @param dto объект FighterDto
     * @return сущность Fighter
     */
    public static Fighter toEntity(FighterDto dto) {
        if (dto == null) return null;
        Fighter fighter = new Fighter();
        fighter.setId(dto.getId());
        fighter.setName(dto.getName());
        fighter.setInfo(InfoMapper.toEntity(dto.getInfo()));
        fighter.setRecord(RecordMapper.toEntity(dto.getRecord()));
        return fighter;
    }
}