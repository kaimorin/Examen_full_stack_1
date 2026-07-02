package cl.duoc.resenas.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import cl.duoc.resenas.dto.ReseñasDto;
import cl.duoc.resenas.model.Reseña;
import cl.duoc.resenas.repository.ReseñaRepository;

import static org.assertj.core.api.Assertions.assertThat;
class ReseñaServiceTest {

    @Test
    void testgetAllLeccionarioDTO() {
        ReseñaRepository reseñaRepository = Mockito.mock(ReseñaRepository.class);
        
        ReseñaService reseñaService = new ReseñaService(reseñaRepository, null);
        Reseña reseñaConConstructor = new Reseña(1l, "Test Description", 5.0, "Test User", "Test Place" 
            );
    
        Mockito.when(reseñaRepository.findAll()).thenReturn(java.util.Arrays.asList(reseñaConConstructor));
        List<ReseñasDto> result = reseñaService.findAll();
        assertThat(result).hasSize(1); 
        assertThat(result.get(0).getDescription()).isEqualTo("Test Description");
    }
}