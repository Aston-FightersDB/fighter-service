package ru.aston.fighterservice.service;

import ru.aston.fighterservice.dto.InfoDto;
import ru.aston.fighterservice.entity.Info;
import ru.aston.fighterservice.mapper.InfoMapper;
import ru.aston.fighterservice.repository.InfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для операций с Info.
 */
@Service
@Transactional
public class InfoServiceImpl implements InfoService {

    private final InfoRepository infoRepository;

    public InfoServiceImpl(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    @Override
    public InfoDto createInfo(InfoDto infoDto) {
        Info info = InfoMapper.toEntity(infoDto);
        Info savedInfo = infoRepository.save(info);
        return InfoMapper.toDto(savedInfo);
    }

    @Override
    public InfoDto getInfo(UUID id) {
        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Информация не найдена с id: " + id));
        return InfoMapper.toDto(info);
    }

    @Override
    public List<InfoDto> getAllInfos() {
        return infoRepository.findAll().stream()
                .map(InfoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InfoDto updateInfo(UUID id, InfoDto infoDto) {
        Info existingInfo = infoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Информация не найдена с id: " + id));
        existingInfo.setClub(infoDto.getClub());
        existingInfo.setManager(infoDto.getManager());
        Info updatedInfo = infoRepository.save(existingInfo);
        return InfoMapper.toDto(updatedInfo);
    }

    @Override
    public void deleteInfo(UUID id) {
        infoRepository.deleteById(id);
    }
}