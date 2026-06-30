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
    @GetMapping
    @Operation(summary = "Listar todas las reseñas", description = "Obtiene todas las reseñas")
    public ResponseEntity<ApiResponse<Object>> getAllReseñas(@RequestHeader("Authorization") String authHeader) {
           String token = authHeader.replace("Bearer ", "");
        ApiResponse<UserDTO> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200 || validationResponse.getData() == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(401, "Token inválido", null));
        }

        List<ReseñasDto> reseñas = reseñaService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Reseñas obtenidas correctamente", reseñas));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID", description = "Obtiene una reseña específica por su ID")
    public ResponseEntity<ApiResponse<Object>> getReseñaById(@RequestHeader("Authorization") String authHeader,@PathVariable Long id) {
           String token = authHeader.replace("Bearer ", "");
        ApiResponse<UserDTO> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200 || validationResponse.getData() == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(401, "Token inválido", null));
        }
        Optional<ReseñasDto> rese = reseñaService.findById(id);
        if (rese.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(404, "Reseña no encontrada", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Reseña obtenida correctamente", rese));
    }

    @PostMapping
    @Operation(summary = "Crear nueva reseña", description = "Crea una nueva reseña")
    public ResponseEntity<ApiResponse<Object>> createReseña(@RequestHeader("Authorization") String authHeader,@RequestBody ReseñasDto reseñaDto) {
           String token = authHeader.replace("Bearer ", "");
        ApiResponse<UserDTO> validationResponse = authService.validateToken(token);

        if (validationResponse == null || validationResponse.getCode() != 200 || validationResponse.getData() == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(401, "Token inválido", null));
        }
        ReseñasDto createdReseña = reseñaService.create(reseñaDto);
        return ResponseEntity.ok(new ApiResponse<>(201, "Reseña creada correctamente", createdReseña));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña existente", description = "Actualiza una reseña existente por su ID")
    public ResponseEntity<ApiResponse<Object>> updateReseña(@RequestHeader("Authorization") String authHeader,@PathVariable Long id, @RequestBody ReseñasDto reseñaDto) {
        ReseñasDto updatedReseña = reseñaService.update(id, reseñaDto);
        return ResponseEntity.ok(new ApiResponse<>(200, "Reseña actualizada correctamente", updatedReseña));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña específica por su ID")
    public ResponseEntity<ApiResponse<Object>> deleteReseña(@RequestHeader("Authorization") String authHeader,@PathVariable Long id) {
        reseñaService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Reseña eliminada correctamente", null));
    }
}
