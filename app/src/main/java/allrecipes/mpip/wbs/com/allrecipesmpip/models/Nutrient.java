package allrecipes.mpip.wbs.com.allrecipesmpip.models;

/**
 * Created by Martin on 8/15/2015.
 */
public class Nutrient {

    private String name;
    private String dose;

    public Nutrient() {
    }

    public Nutrient(String name, String dose) {
        this.name = name;
        this.dose = dose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }
}
