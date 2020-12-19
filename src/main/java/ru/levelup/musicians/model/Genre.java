package ru.levelup.musicians.model;

import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Table(name = "genres")
@ToString(onlyExplicitlyIncluded = true)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id", nullable = false, unique = true)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer genreId;

    @Column(name = "genre_name")
    @ToString.Include
    private String genreName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "bands_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "band_id")
    )
    private List<Band> bands;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "musicians_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "musician_id")
    )
    private List<Musician> musicians;

    public Genre(Integer genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }
}
