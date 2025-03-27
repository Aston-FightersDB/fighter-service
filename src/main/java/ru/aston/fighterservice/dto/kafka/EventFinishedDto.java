package ru.aston.fighterservice.dto.kafka;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFinishedDto {
    private UUID eventId;
    private UUID loserId;
    private UUID winnerId;


}
