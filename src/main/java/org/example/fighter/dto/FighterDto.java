package org.example.fighter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO для Fighter.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FighterDto {
    private UUID id;
    private InfoDto info;
    private String name;
    private RecordDto record;
}
