package org.example.fighter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Сущность Record.
 */
@Entity
@Table(name = "record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {

    /**
     * Уникальный идентификатор записи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Количество побед.
     */
    private Integer wins;

    /**
     * Количество поражений.
     */
    private Integer loses;

    /**
     * Количество ничьих.
     */
    private Integer ties;
}