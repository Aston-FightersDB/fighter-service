package org.example.fighter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для Record.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDto {
    private Long id;
    private Integer wins;
    private Integer loses;
    private Integer ties;
}
