package ru.aston.fighterservice.service.impl.kafka;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.aston.fighterservice.dto.RecordDto;
import ru.aston.fighterservice.dto.kafka.EventFinishedDto;
import ru.aston.fighterservice.repository.FighterRepository;
import ru.aston.fighterservice.service.FighterService;
import ru.aston.fighterservice.service.RecordService;

@Service
@AllArgsConstructor
public class KafkaConsumerService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FighterService fighterService;
    private final RecordService recordService;

    @KafkaListener(topics = "event-finished", groupId = "fighter-service-group")
    public void listenCardFinished(ConsumerRecord<String, String> record) {
        try {
            EventFinishedDto eventResult = objectMapper.readValue(record.value(), EventFinishedDto.class);
            System.out.println("Received event result: " + eventResult);

            // Обновляем бойцов
            RecordDto recordDto = fighterService.getFighterRecord(eventResult.getWinnerId());
            recordDto.setWins(recordDto.getWins() + 1);
            recordService.updateRecord(eventResult.getWinnerId(), recordDto);
            recordDto = fighterService.getFighterRecord(eventResult.getLoserId());
            recordDto.setLoses(recordDto.getLoses() + 1);
            recordService.updateRecord(eventResult.getLoserId(), recordDto);
            System.out.println("Fighters records updated");
        } catch (Exception e) {
            System.err.println("Error parsing event result: " + e.getMessage());
        }
    }
}