package org.example.fighter.service;

import lombok.RequiredArgsConstructor;
import org.example.fighter.dto.FighterDto;
import org.example.fighter.dto.RecordDto;
import org.example.fighter.entity.Record;
import org.example.fighter.entity.Fighter;
import org.example.fighter.mapper.FighterMapper;
import org.example.fighter.mapper.InfoMapper;
import org.example.fighter.mapper.RecordMapper;
import org.example.fighter.repository.FighterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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