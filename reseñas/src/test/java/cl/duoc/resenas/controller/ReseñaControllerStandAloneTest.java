package cl.duoc.resenas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cl.duoc.resenas.service.AuthService;
import cl.duoc.resenas.service.ReseñaService;

class ReseñaControllerStandAloneTest {
    private MockMvc mockMvc;
    private ReseñaService reseñaService;
    private AuthService authService;

    @BeforeEach
    void setup() {
        reseñaService = org.mockito.Mockito.mock(ReseñaService.class); // crea un mock de ReseñaService para simular su comportamiento durante las pruebas sin necesidad de una implementación real
        authService = org.mockito.Mockito.mock(AuthService.class); // crea un mock de AuthService para simular la validación de tokens durante las pruebas sin necesidad de una implementación real

        ReseñaController reseñaController = new ReseñaController(reseñaService, authService); // crea una instancia de ReseñaController pasando los mocks de ReseñaService y AuthService como dependencias
        mockMvc = MockMvcBuilders.standaloneSetup(reseñaController).build(); // configura MockMvc para usar la instancia de ReseñaController creada, lo que permite simular solicitudes HTTP a los endpoints del controlador durante las pruebas
    }

    @Test
    void testGetAllReseñas() throws Exception {
        mockMvc.perform(get("/api/v1/reseñas/all") // simula una solicitud GET al endpoint "/api/v1/reseñas/all" con un encabezado de autorización que contiene un token inválido
                .header("Authorization", "Bearer invalid")) // simula una solicitud GET al endpoint "/api/v1/reseñas/all" con un encabezado de autorización que contiene un token inválido
                .andExpect(status().isUnauthorized());
    }
}
