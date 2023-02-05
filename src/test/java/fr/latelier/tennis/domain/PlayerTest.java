package fr.latelier.tennis.domain;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PlayerTest {

    private Player player;


    @Before
    public void setUp() {
        player = new Player();
        PlayerData playerData = new PlayerData();
        playerData.setWeight(85000);
        playerData.setHeight(185);
        playerData.setLast(new int[]{ 1, 0, 0, 0, 1});
        player.setData(playerData);
    }

    @Test
    public final void getImcTest() {
        assertThat(String.format("%,.1f", player.getImc())).isEqualTo("24,8");
    }

    @Test
    public final void getRatioWinTest() {
        assertThat(String.format("%,.2f", player.getRatioWin())).isEqualTo("40,00");
    }
}
