package ru.aston.fighterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO для Record.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordDto {
    private UUID id;
    private Integer wins;
    private Integer loses;
    private Integer ties;
}
