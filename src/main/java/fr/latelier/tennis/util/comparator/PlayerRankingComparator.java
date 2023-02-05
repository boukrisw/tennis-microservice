package fr.latelier.tennis.util.comparator;

import fr.latelier.tennis.domain.Player;

import java.util.Comparator;

public class PlayerRankingComparator implements Comparator<Player> {

    @Override
    public int compare(Player firstPlayer, Player secondPlayer) {
        return Integer.compare(firstPlayer.getData().getRank(), secondPlayer.getData().getRank());
    }
}