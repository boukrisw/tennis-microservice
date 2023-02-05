package fr.latelier.tennis.web.rest;

import fr.latelier.tennis.domain.Player;
import fr.latelier.tennis.domain.Stats;
import fr.latelier.tennis.service.PlayerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/player")
@CrossOrigin
public class PlayerResource {
    private final PlayerService playerService;

    private final Logger log = LoggerFactory.getLogger(PlayerResource.class);

    /**
     * {@code GET  /api/player/all} : Permet de récupérer tous les joueurs. Triée du meilleur au moins bon.
     *
     * @return la {@link ResponseEntity} avec le statut {@code 200 (OK)} et la liste des joueurs dans le corps
     */
    @GetMapping("/all")
    public ResponseEntity<List<Player>> getAllPlayers() {
        log.debug("REST request to get all players ordered by rank");
        List<Player> page = playerService.getAllPlayersOrderByRank();
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /api/player/:id} : Permet de récupérer un joueur par son id
     *
     * @return la {@link ResponseEntity} avec le statut {@code 200 (OK)} et le joueur dans le corps
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        log.debug("REST request to get player by id {}",id);
        Optional<Player> player = playerService.getPlayerById(id);
        if(player.isPresent()){
            return ResponseEntity.ok().body(player.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * {@code GET  /api/player/statistics} : retourne les statistiques suivantes :
     * - Pays qui a le plus grand ratio de parties gagnées
     * - IMC moyen de tous les joueurs
     * - La médiane de la taille des joueurs
     *
     * @return la {@link ResponseEntity} avec le statut {@code 200 (OK)} et les statistiques dans le corps
     */
    @GetMapping("/statistics")
    public ResponseEntity<Stats> getStatistics() {
        log.debug("REST request to get statistics");
        Stats stats = playerService.getStatistics();

        return ResponseEntity.ok().body(stats);
    }
}
