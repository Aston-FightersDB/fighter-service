package org.example.fighter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * Название клуба.
     */
    private String club;

    /**
     * Имя менеджера.
     */
    private String manager;
}