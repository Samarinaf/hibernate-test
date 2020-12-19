package ru.levelup.musicians.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "musicians")
public class Musician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "first_name", nullable = false)
    @ToString.Include
    private String firstName;

    @Column(name = "middle_name")
    @ToString.Include
    private String middleName;

    @Column(name = "last_name", nullable = false)
    @ToString.Include
    private String lastName;

    @Column(nullable = false, length = 6)
    @ToString.Include
    private String sex;

    @Column(name = "date_of_birth", nullable = false)
    @ToString.Include
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany(mappedBy = "musicians", fetch = FetchType.LAZY) //поле из класса Album
    private List<Album> albums = new ArrayList<>();

    @ManyToMany(mappedBy = "musicians", fetch = FetchType.LAZY) //поле из класса Genre
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "musician", fetch = FetchType.LAZY)  //поле из класса MusiciansBands
    private List<MusiciansBands> musiciansBands = new ArrayList<>();

    public Musician(Integer id, String firstName, String middleName, String lastName, String sex, LocalDate dateOfBirth, Integer countryId) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.country = new Country(countryId);
    }

    public Musician() {
    }
}
