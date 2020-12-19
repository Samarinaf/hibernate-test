package ru.levelup.musicians.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "songs")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id", nullable = false, unique = true)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer songId;

    @Column(name = "song_name", nullable = false)
    @ToString.Include
    private String songName;

    @ManyToOne //(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    private Album album;

    public Song(Integer songId, String songName, Integer albumId) {
        this.songId = songId;
        this.songName = songName;
        this.album = new Album(albumId);
    }
}
