package fr.latelier.tennis.domain;

import fr.latelier.tennis.domain.ennumeration.Sex;
import lombok.Data;
import java.util.Arrays;

@Data
public class Player {

    private Long id;
    private String firstname;
    private String lastname;
    private String shortname;
    private Sex sex;
    private Country country;
    private String picture;
    private PlayerData data;

    /**
     * Calcule le IMC de joueur poids en (Kg) / la taille en (m)
     *
     * @return IMC de joueur
     */
    public double getImc(){
        return (data.getWeight()/1000) / Math.pow(data.getHeight()/100,2);
    }

    /**
     * Calcule le ratio de parties gagnées de joueur
     *
     * @return Ratio de parties gagnées de joueur
     */
    public double getRatioWin(){
        return Arrays.stream(data.getLast()).filter( elt->elt == 1).count() * 100 / data.getLast().length ;
    }

}
