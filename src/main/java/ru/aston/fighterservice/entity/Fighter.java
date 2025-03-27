package ru.aston.fighterservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

/**
 * Сущность Fighter.
 */
@Entity
@Table(name = "fighter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fighter {

    /**
     * Уникальный идентификатор бойца.
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
     * Информация о бойце (связь с таблицей info).
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "info_id", referencedColumnName = "id", nullable = false)
    private Info info;

    /**
     * Имя бойца.
     */
    private String name;

    /**
     * Записи о бойце (связь с таблицей record).
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private Record record;
}