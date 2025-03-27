package org.example.fighter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

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