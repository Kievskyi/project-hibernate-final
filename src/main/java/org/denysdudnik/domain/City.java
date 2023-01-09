package org.denysdudnik.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "city", schema = "world")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    String district;

    Integer population;

    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;
}
