package org.gamein.marketservergamein2022.core.sharedkernel.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class TeamDate {

    @Id
    private Long team_id;

    @Column(name = "date")
    LocalDateTime dateTime;


}
