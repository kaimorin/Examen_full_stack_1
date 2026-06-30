package cl.duoc.resenas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReseñasDto {

    private long id;
    private String description;
    private double nota;
    private String nombreUser;
    private String nombreLugar;
}
