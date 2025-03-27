package ru.aston.fighterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO для Info.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoDto {
    private UUID id;
    private String club;
    private String manager;
}