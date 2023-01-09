package org.denysdudnik.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "country_language", schema = "world")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CountryLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;

    String language;

    @Column(name = "is_official", columnDefinition = "bit")
    Boolean isOfficial;

    @Column(name = "percentage")
    BigDecimal percentage;
}
