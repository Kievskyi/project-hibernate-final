package org.denysdudnik.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "country", schema = "world")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@ToString(exclude = {
        "languages", "capital"
})
@EqualsAndHashCode(exclude = {
        "languages", "capital"
})
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String code;

    @Column(name = "code_2")
    String alternativeCode;

    String name;

    @Column(name = "continent")
    @Enumerated(value = EnumType.ORDINAL)
    Continent continent;

    String region;

    @Column(name = "surface_area")
    BigDecimal surfaceArea;

    @Column(name = "indep_year")
    Short indepYear;

    Integer population;

    @Column(name = "life_expectancy")
    BigDecimal lifeExpectancy;

    BigDecimal gnp;

    @Column(name = "gnpo_id")
    BigDecimal gnpoId;

    @Column(name = "local_name")
    String localName;

    @Column(name = "government_form")
    String governmentForm;

    @Column(name = "head_of_state")
    String headOfState;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capital")
    City capital;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    Set<CountryLanguage> languages;
}
