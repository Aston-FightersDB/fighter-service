package org.example.fighter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

/**
 * Сущность Info.
 */
@Entity
@Table(name = "info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {

    /**
     * Уникальный идентификатор информации.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название клуба.
     */
    private String club;

    /**
     * Имя менеджера.
     */
    private String manager;
}