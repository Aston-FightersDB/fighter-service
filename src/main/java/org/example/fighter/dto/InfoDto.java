package org.example.fighter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для Info.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoDto {
    private Long id;
    private String club;
    private String manager;
}