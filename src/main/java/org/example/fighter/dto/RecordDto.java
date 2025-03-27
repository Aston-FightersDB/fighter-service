package org.example.fighter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO для Record.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {
    private UUID id;
    private Integer wins;
    private Integer loses;
    private Integer ties;
}
