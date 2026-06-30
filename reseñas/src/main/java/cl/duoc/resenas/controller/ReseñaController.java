package cl.duoc.resenas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.resenas.dto.ApiResponse;
import cl.duoc.resenas.dto.ReseñasDto;
import cl.duoc.resenas.dto.UserDTO;
import cl.duoc.resenas.service.AuthService;
import cl.duoc.resenas.service.ReseñaService;

import java.util.List;
import java.util.Optional;

@Tag(name = "Review Controller", description = "Endpoints para gestión de reseñas")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reseñas")
public class ReseñaController {
    private final ReseñaService reseñaService;
    private final AuthService authService;


    @GetMapping("/all")
    @Operation(summary = "Listar todas las reseñas", description = "Obtiene todas las reseñas")
    public ResponseEntity<ApiResponse<Object>> getAllReseñas(@RequestHeader("Authorization") String authHeader) {
           String token = authHeader.replace("Bearer ", "");
        ApiResponse<UserDTO> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200 || validationResponse.getData() == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(401, "Token inválido", null));
        }
        try {
            List<ReseñasDto> reseñas = reseñaService.findAll();
            return ResponseEntity.ok(new ApiResponse<>(200, "Reseñas obtenidas correctamente", reseñas));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(500, "Error al obtener reseñas: " + e.getMessage(), null));
        }
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Obtener reseña por ID", description = "Obtiene una reseña específica por su ID")
    public ResponseEntity<ApiResponse<Object>> getReseñaById(@RequestHeader("Authorization") String authHeader,@PathVariable Long id) {
           String token = authHeader.replace("Bearer ", "");
        ApiResponse<UserDTO> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200 || validationResponse.getData() == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(401, "Token inválido", null));
        }
        try {
            Optional<ReseñasDto> rese = reseñaService.findById(id);
            if (rese.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(new ApiResponse<>(404, "Reseña no encontrada", null));
            }
            return ResponseEntity.ok(new ApiResponse<>(200, "Reseña obtenida correctamente", rese.get()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(500, "Error al obtener reseña: " + e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Crear nueva reseña", description = "Crea una nueva reseña")
    public ResponseEntity<ApiResponse<Object>> createReseña(@RequestHeader("Authorization") String authHeader,@RequestBody ReseñasDto reseñaDto) {
           String token = authHeader.replace("Bearer ", "");
        ApiResponse<UserDTO> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200 || validationResponse.getData() == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(401, "Token inválido", null));
        }
        try {
            ReseñasDto createdReseña = reseñaService.create(reseñaDto);
            return ResponseEntity.ok(new ApiResponse<>(201, "Reseña creada correctamente", createdReseña));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(500, "Error al crear reseña: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Actualizar reseña existente", description = "Actualiza una reseña existente por su ID")
    public ResponseEntity<ApiResponse<Object>> updateReseña(@RequestHeader("Authorization") String authHeader,@PathVariable Long id, @RequestBody ReseñasDto reseñaDto) {
        try {
            ReseñasDto updatedReseña = reseñaService.update(id, reseñaDto);
            return ResponseEntity.ok(new ApiResponse<>(200, "Reseña actualizada correctamente", updatedReseña));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(500, "Error al actualizar reseña: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña específica por su ID")
    public ResponseEntity<ApiResponse<Object>> deleteReseña(@RequestHeader("Authorization") String authHeader,@PathVariable Long id) {
        try {
            reseñaService.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Reseña eliminada correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(500, "Error al eliminar reseña: " + e.getMessage(), null));
        }
    }
}
