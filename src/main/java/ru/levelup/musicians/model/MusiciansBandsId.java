package ru.levelup.musicians.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MusiciansBandsId implements Serializable {

    @Column(name = "musician_id")
    private Integer musicianId;

    @Column(name = "band_id")
    private Integer bandId;
}
