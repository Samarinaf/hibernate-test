package ru.levelup.musicians.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Table(name = "musicians_bands")
public class MusiciansBands {

    @EmbeddedId
    private MusiciansBandsId id;
//    private Integer musicianId;
//    private Integer bandId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @MapsId("musician_id") //с какой колонкой связывается музыкант (идет в связке с EmbeddedId)
    @JoinColumn(name = "musician_id")
    private Musician musician;

    @ManyToOne
    @MapsId("band_id") //с какой колонкой связывается группа
    @JoinColumn(name = "band_id")
    private Band band;


}
