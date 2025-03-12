package org.ademun.timetableapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "time_start", nullable = false)
    private Time timeStart;
    @Column(name = "time_end", nullable = false)
    private Time timeEnd;
}
