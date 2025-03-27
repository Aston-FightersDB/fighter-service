package ru.aston.fighterservice.service.impl;

import lombok.RequiredArgsConstructor;
import ru.aston.fighterservice.dto.FighterDto;
import ru.aston.fighterservice.dto.RecordDto;
import ru.aston.fighterservice.entity.Record;
import ru.aston.fighterservice.entity.Fighter;
import ru.aston.fighterservice.mapper.FighterMapper;
import ru.aston.fighterservice.mapper.InfoMapper;
import ru.aston.fighterservice.mapper.RecordMapper;
import ru.aston.fighterservice.repository.FighterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import ru.aston.fighterservice.service.FighterService;

/**
 * Реализация сервиса для операций с Fighter.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class FighterServiceImpl implements FighterService {

    private final FighterRepository fighterRepository;


    @Override
    public FighterDto createFighter(FighterDto fighterDto) {
        Fighter fighter = FighterMapper.toEntity(fighterDto);
        Fighter savedFighter = fighterRepository.save(fighter);
        return FighterMapper.toDto(savedFighter);
    }

    @Override
    public FighterDto getFighter(UUID id) {
        Fighter fighter = fighterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Боец не найден с id: " + id));
        return FighterMapper.toDto(fighter);
    }

    @Override
    public List<FighterDto> getAllFighters() {
        return fighterRepository.findAll().stream()
                .map(FighterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FighterDto updateFighter(UUID id, FighterDto fighterDto) {
        Fighter existingFighter = fighterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Боец не найден с id: " + id));
        existingFighter.setName(fighterDto.getName());
        existingFighter.setInfo(InfoMapper.toEntity(fighterDto.getInfo()));
        existingFighter.setRecord(RecordMapper.toEntity(fighterDto.getRecord()));
        Fighter updatedFighter = fighterRepository.save(existingFighter);
        return FighterMapper.toDto(updatedFighter);
    }

    @Override
    public void deleteFighter(UUID id) {
        fighterRepository.deleteById(id);
    }

    @Override
    public RecordDto getFighterRecord(UUID fighterId) {
        Fighter fighter = fighterRepository.findById(fighterId)
                .orElseThrow(() -> new RuntimeException("Боец не найден с id: " + fighterId));
        if (fighter.getRecord() == null) {
            throw new RuntimeException("Статистика не найдена для бойца с id: " + fighterId);
        }
        return RecordMapper.toDto(fighter.getRecord());
    }

    @Override
    public RecordDto createOrUpdateFighterRecord(UUID fighterId, RecordDto recordDto) {
        Fighter fighter = fighterRepository.findById(fighterId)
                .orElseThrow(() -> new RuntimeException("Боец не найден с id: " + fighterId));
        Record record = fighter.getRecord();
        if (record == null) {
            record = RecordMapper.toEntity(recordDto);
            fighter.setRecord(record);
        } else {
            record.setWins(recordDto.getWins());
            record.setLoses(recordDto.getLoses());
            record.setTies(recordDto.getTies());
        }
        fighter = fighterRepository.save(fighter);
        return RecordMapper.toDto(fighter.getRecord());
    }

    @Override
    public FighterDto partialUpdateFighter(UUID fighterId, FighterDto fighterDto) {
        Fighter fighter = fighterRepository.findById(fighterId)
                .orElseThrow(() -> new RuntimeException("Боец не найден с id: " + fighterId));
        if (fighterDto.getName() != null) {
            fighter.setName(fighterDto.getName());
        }
        fighter = fighterRepository.save(fighter);
        return FighterMapper.toDto(fighter);
    }

    @Override
    public RecordDto partialUpdateFighterRecord(UUID fighterId, RecordDto recordDto) {
        Fighter fighter = fighterRepository.findById(fighterId)
                .orElseThrow(() -> new RuntimeException("Боец не найден с id: " + fighterId));
        Record record = fighter.getRecord();
        if (record == null) {
            record = RecordMapper.toEntity(recordDto);
            fighter.setRecord(record);
        } else {
            if (recordDto.getWins() != null) {
                record.setWins(recordDto.getWins());
            }
            if (recordDto.getLoses() != null) {
                record.setLoses(recordDto.getLoses());
            }
            if (recordDto.getTies() != null) {
                record.setTies(recordDto.getTies());
            }
        }
        fighter = fighterRepository.save(fighter);
        return RecordMapper.toDto(fighter.getRecord());
    }
}