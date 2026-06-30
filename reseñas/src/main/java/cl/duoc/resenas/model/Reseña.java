package cl.duoc.resenas.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

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

    @Column(nullable = false, unique = true)
    private double nota;

    @Column(nullable = false, unique = true)
    private String nombreUser;

    @Column(nullable = false, unique = true)
    private String nombreLugar;

   
}
