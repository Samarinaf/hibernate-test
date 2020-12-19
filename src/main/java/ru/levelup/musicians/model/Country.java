package ru.levelup.musicians.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false, unique = true)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer countryId;

    @Column(name = "country_name", nullable = false)
    @ToString.Include
    private String countryName;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<Musician> musicians = new ArrayList<>();

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<Band> bands = new ArrayList<>();

    public Country(Integer countryId) {
        this.countryId = countryId;
    }

    public Country(Integer countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }
}
