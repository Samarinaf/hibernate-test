package ru.levelup.musicians.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "albums")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", nullable = false)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer albumId;

    @Column(name = "album_name", nullable = false)
    @ToString.Include
    private String albumName;

    @Column(name = "album_year", nullable = false)
    @ToString.Include
    private LocalDate albumYear;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(
            name = "musicians_albums",
            joinColumns = @JoinColumn(name = "album_id"), //имя колонки из табл.("musicians_albums") -- ключ на текущую табл.
            inverseJoinColumns = @JoinColumn(name = "musician_id") //ключ на музыканта
    )
    private List<Musician> musicians = new ArrayList<>();

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(
            name = "bands_albums",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "band_id")
    )
    private List<Band> bands = new ArrayList<>();

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Song> songs = new ArrayList<>();

    public Album(Integer albumId, String albumName, LocalDate albumYear) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumYear = albumYear;
    }

    public Album() {
//        this.musicians = new ArrayList<>();
//        this.bands = new ArrayList<>();
//        this.songs = new ArrayList<>();
    }

    public Album(Integer albumId) {
        this.albumId = albumId;
    }
}
