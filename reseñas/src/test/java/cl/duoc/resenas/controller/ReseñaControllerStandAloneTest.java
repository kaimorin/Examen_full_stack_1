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
        reseñaService = org.mockito.Mockito.mock(ReseñaService.class); 
        authService = org.mockito.Mockito.mock(AuthService.class); 

        ReseñaController reseñaController = new ReseñaController(reseñaService, authService); 
        mockMvc = MockMvcBuilders.standaloneSetup(reseñaController).build(); 
    }

    @Test
    void testGetAllReseñas() throws Exception {
        mockMvc.perform(get("/api/v1/reseñas/all") 
                .header("Authorization", "Bearer invalid")) 
                .andExpect(status().isUnauthorized());
    }
}
