package fr.latelier.tennis.service;

import fr.latelier.tennis.domain.Country;
import fr.latelier.tennis.domain.Player;
import fr.latelier.tennis.domain.Stats;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PlayerServiceTest {

    private PlayerService playerService;

    @Before
    public void setUp() throws IOException {
        playerService = new PlayerService();
    }

    @Test
    public void getAllPlayersOrderByRankTest() {
        List<Player> players = playerService.getAllPlayersOrderByRank();
        assertThat(players.size()).isEqualTo(5);

        assertThat(players.get(0).getFirstname()).isEqualTo("Rafael");
        assertThat(players.get(0).getLastname()).isEqualTo("Nadal");
        assertThat(players.get(1).getFirstname()).isEqualTo("Novak");
        assertThat(players.get(1).getLastname()).isEqualTo("Djokovic");
    }

    @Test
    public void getPlayerByIdTest() {
        Optional<Player> player = playerService.getPlayerById(52L);

        Assert.assertTrue(player.isPresent());
        assertThat(player.get().getId()).isEqualTo(52L);
        assertThat(player.get().getFirstname()).isEqualTo("Novak");
        assertThat(player.get().getLastname()).isEqualTo("Djokovic");
        assertThat(player.get().getShortname()).isEqualTo("N.DJO");
    }

    @Test
    public void getStatistics(){
        Stats stats = playerService.getStatistics();

        assertThat(stats.getImc()).isEqualTo(23.357838995505837);
        assertThat(stats.getCountry().getCode()).isEqualTo("SRB");
        assertThat(stats.getMedianHeight()).isEqualTo(185);
    }

    @Test
    public void getCountryWithBestRatio() {
        Country country = playerService.getCountryWithBestRatio();
        assertThat(country.getPicture()).isEqualTo("https://data.latelier.co/training/tennis_stats/resources/Serbie.png");
        assertThat(country.getCode()).isEqualTo("SRB");

    }
}
