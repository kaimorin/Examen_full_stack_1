package cl.duoc.resenas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReseñasDto {

    private long id;
    @NotBlank(message = "El nombre del destino no puede estar vacío")
    @Size(min = 3, max = 100, message = "La descripción debe tener entre 3 y 100 caracteres")
    private String description;
    @NotBlank(message = "El nombre del destino no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del destino debe tener entre 3 y 100 caracteres")
    private String nombreDestino;
    private UUID destinationId;
    @NotNull(message = "La nota no puede estar vacía")
    @Size(min = 1, max = 5, message = "La nota debe ser entre 1 y 5")
    private double nota;
    private String nombreUser;
    private String nombreLugar;
}
