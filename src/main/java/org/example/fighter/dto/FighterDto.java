package org.example.fighter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для Fighter.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FighterDto {
    private Long id;
    private InfoDto info;
    private String name;
    private RecordDto record;
}
