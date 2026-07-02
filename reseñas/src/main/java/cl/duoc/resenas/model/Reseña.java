package cl.duoc.resenas.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Reseña {

    @Id
    @GeneratedValue
    private long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false )
    private double nota;

    @Column(nullable = false)
    private String nombreUser;

    @Column(nullable = false)
    private String nombreLugar;

   
}
