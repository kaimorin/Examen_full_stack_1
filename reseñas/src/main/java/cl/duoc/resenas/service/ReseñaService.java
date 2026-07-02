package cl.duoc.resenas.service;

import cl.duoc.resenas.dto.ApiResponse;
import cl.duoc.resenas.dto.ReseñasDto;
import cl.duoc.resenas.model.Reseña;
import cl.duoc.resenas.repository.ReseñaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReseñaService {
    private final ReseñaRepository reseñaRepository;
    private final DestinationService destinationService;

    public List<ReseñasDto> findAll() {
        return reseñaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ReseñasDto> findById(Long id) {
        return reseñaRepository.findById(id).map(this::toDto);
    }

    public ReseñasDto create(ReseñasDto reseñaDto) {
        return create(reseñaDto, null);
    }

    public ReseñasDto create(ReseñasDto reseñaDto, String token) {
        validateDestination(reseñaDto, token);

        Reseña reseña = toEntity(reseñaDto);
        return toDto(reseñaRepository.save(reseña));
    }

    public ReseñasDto update(Long id, ReseñasDto reseñaDto) {
        Reseña reseña = reseñaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con id: " + id));

        reseña.setDescription(reseñaDto.getDescription());
        reseña.setNota(reseñaDto.getNota());
        reseña.setNombreUser(reseñaDto.getNombreUser());
        reseña.setNombreLugar(reseñaDto.getNombreLugar());

        return toDto(reseñaRepository.save(reseña));
    }

    public void delete(Long id) {
        reseñaRepository.deleteById(id);
    }

    private void validateDestination(ReseñasDto reseñaDto, String token) {
        if (reseñaDto == null || reseñaDto.getDestinationId() == null || token == null || token.isBlank()) {
            return;
        }

        ApiResponse<Boolean> response = destinationService.validateDestination(reseñaDto.getDestinationId(), token);
        if (response == null || response.getCode() != 200 || Boolean.TRUE.equals(response.getData()) == false) {
            String message = response != null && response.getMessage() != null && !response.getMessage().isBlank()
                    ? response.getMessage()
                    : "El destino no existe";
            throw new IllegalArgumentException(message);
        }
    }

    private ReseñasDto toDto(Reseña reseña) {
        ReseñasDto dto = new ReseñasDto();
        dto.setId(reseña.getId());
        dto.setDescription(reseña.getDescription());
        dto.setNota(reseña.getNota());
        dto.setNombreUser(reseña.getNombreUser());
        dto.setNombreLugar(reseña.getNombreLugar());
        return dto;
    }

    private Reseña toEntity(ReseñasDto dto) {
        Reseña reseña = new Reseña();
        reseña.setId(dto.getId());
        reseña.setDescription(dto.getDescription());
        reseña.setNota(dto.getNota());
        reseña.setNombreUser(dto.getNombreUser());
        reseña.setNombreLugar(dto.getNombreLugar());
        return reseña;
    }
}
