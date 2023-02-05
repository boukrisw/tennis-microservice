package fr.latelier.tennis.service;

import com.fasterxml.jackson.core.type.TypeReference;
import fr.latelier.tennis.domain.Country;
import fr.latelier.tennis.domain.Player;
import fr.latelier.tennis.domain.Stats;
import fr.latelier.tennis.util.comparator.PlayerRankingComparator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import static java.util.stream.Collectors.groupingBy;

@Service
public class PlayerService {
    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private List<Player> players;

    public PlayerService() throws IOException {
        ClassPathResource staticDataResource = new ClassPathResource("headtohead.json");
        String staticDataString = IOUtils.toString(staticDataResource.getInputStream(), StandardCharsets.UTF_8);
        players = new ObjectMapper().readValue(
                staticDataString, new TypeReference<List<Player>>() { }
        );
    }

    /**
     * Permet de récupérer tous les joueurs. Triée du meilleur au moins bon.
     *
     * @return la liste des joueurs
     */
    public List<Player> getAllPlayersOrderByRank() {
        log.debug("Request to get all players ordered by rank");
        PlayerRankingComparator playerComparator = new PlayerRankingComparator();

        return this.players.stream().sorted(playerComparator).toList();
    }

    /**
     * Permet de récupérer un joueur par son id
     *
     * @return le joueur
     */
    public Optional<Player> getPlayerById(Long id) {
        log.debug("Request to get player by id {}",id);
        return this.players.stream().filter( p -> p.getId() == id).findFirst();
    }

    /**
     * Retourne les statistiques suivantes :
     * - Pays qui a le plus grand ratio de parties gagnées
     * - IMC moyen de tous les joueurs
     * - La médiane de la taille des joueurs
     *
     * @return les statistiques
     */
    public Stats getStatistics(){
        log.debug("Request to get statistics");

        Stats stats = new Stats();

        // Pays qui a le plus grand ratio de parties gagnées
        stats.setCountry(getCountryWithBestRatio());

        // Calcul de IMC moyen
        double imcMoyen = this.players.stream().map(Player::getImc).reduce(0.0, Double::sum) / this.players.size();
        stats.setImc(imcMoyen);

        // La médiane de la taille des joueurs
        List<Double> heights = this.players.stream().map(p -> p.getData().getHeight()).sorted().collect(Collectors.toList());
        double medianHeight = heights.size()%2 == 0?
                (heights.get(heights.size()/2-1) + heights.get(heights.size()/2))/2 :
                heights.get(heights.size()/2);
        stats.setMedianHeight(medianHeight);

        return stats;
    }

    /**
     * Retourne le pays qui a le plus grand ratio de parties gagnées
     *
     * @return Pays qui a le plus grand ratio de parties gagnées
     */
    public Country getCountryWithBestRatio() {
        Country country = null;
        double bestRatio = 0;

        // Groupe les joueur par nationalité
        Map<Country, List<Player>> playersPerCountry = this.players.stream()
                .collect(groupingBy(Player::getCountry));

        for (Map.Entry<Country, List<Player>> entry : playersPerCountry.entrySet()) {
            double currentRatio = 0;
            for (Player p:entry.getValue()) {
                currentRatio += p.getRatioWin();
            }
            currentRatio /= entry.getValue().size();

            if(currentRatio > bestRatio){
                country = entry.getKey();
                bestRatio = currentRatio;
            }
        }
        return country;
    }
}
