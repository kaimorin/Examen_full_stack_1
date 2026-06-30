package cl.duoc.resenas.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.resenas.dto.ReseñasDto;
import cl.duoc.resenas.service.ReseñaService;

import java.util.List;

@Tag(name = "Review Controller", description = "Endpoints para gestión de reseñas")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reseñas")
public class ReseñaController {
    private final ReseñaService reseñaService;

    @GetMapping
    @Operation(summary = "Listar todas las reseñas", description = "Obtiene todas las reseñas")
    public ResponseEntity<List<ReseñasDto>> getAllReseñas() {
        return ResponseEntity.ok(reseñaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener reseña por ID", description = "Obtiene una reseña específica por su ID")
    public ResponseEntity<ReseñasDto> getReseñaById(@PathVariable Long id) {
        return reseñaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nueva reseña", description = "Crea una nueva reseña")
    public ResponseEntity<ReseñasDto> createReseña(@RequestBody ReseñasDto reseñaDto) {
        return ResponseEntity.ok(reseñaService.create(reseñaDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reseña existente", description = "Actualiza una reseña existente por su ID")
    public ResponseEntity<ReseñasDto> updateReseña(@PathVariable Long id, @RequestBody ReseñasDto reseñaDto) {
        return ResponseEntity.ok(reseñaService.update(id, reseñaDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña específica por su ID")
    public ResponseEntity<Void> deleteReseña(@PathVariable Long id) {
        reseñaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
