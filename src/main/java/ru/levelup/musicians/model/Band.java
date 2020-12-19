package ru.levelup.musicians.model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "bands")
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_id", nullable = false)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer bandId;

    @Column(name = "band_name", nullable = false)
    @ToString.Include
    private String bandName;

    @Column(name = "years_active", nullable = false)
    @ToString.Include
    private String yearsActive;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "band") //поле из класса MusiciansBands
    private List<MusiciansBands> musiciansBands = new ArrayList<>();

    @ManyToMany(mappedBy = "bands")
    private List<Album> albums = new ArrayList<>();

    @ManyToMany(mappedBy = "bands")
    private List<Genre> genres = new ArrayList<>();

    public Band(Integer bandId, String bandName, String yearsActive, Integer countryId) {
        this.bandId = bandId;
        this.bandName = bandName;
        this.yearsActive = yearsActive;
        this.country = new Country(countryId);
    }



}
