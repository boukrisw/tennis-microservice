package fr.latelier.tennis.web.rest;

import fr.latelier.tennis.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerResourceTest {
    private static final String ENTITY_API_URL = "/api/player/";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc restPlayerMockMvc;

    @Test
    void getAllPlayersIT() throws Exception {
        restPlayerMockMvc
                .perform(
                        get(ENTITY_API_URL+"all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()").value(5))
                .andExpect(jsonPath("$[0].firstname").value("Rafael"))
                .andExpect(jsonPath("$[0].lastname").value("Nadal"))
                .andExpect(jsonPath("$[1].firstname").value("Novak"))
                .andExpect(jsonPath("$[1].lastname").value("Djokovic"));
    }

    @Test
    void getPlayerByIdIT() throws Exception {
        restPlayerMockMvc
                .perform(
                        get(ENTITY_API_URL+"65"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.firstname").value("Stan"))
                .andExpect(jsonPath("$.lastname").value("Wawrinka"))
                .andExpect(jsonPath("$.shortname").value("S.WAW"))
                .andExpect(jsonPath("$.sex").value("M"));

        restPlayerMockMvc
                .perform(
                        get(ENTITY_API_URL+"17"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.firstname").value("Rafael"))
                .andExpect(jsonPath("$.lastname").value("Nadal"))
                .andExpect(jsonPath("$.shortname").value("R.NAD"))
                .andExpect(jsonPath("$.sex").value("M"));
    }

    @Test
    void getStatisticsIT() throws Exception {
        restPlayerMockMvc
                .perform(
                        get(ENTITY_API_URL+"statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.imc").value(23.357838995505837))
                .andExpect(jsonPath("$.country.code").value("SRB"));
    }
}
