package allrecipes.mpip.wbs.com.allrecipesmpip.models;

/**
 * Created by Martin on 8/15/2015.
 */
public class Ingredient{

    private String name;
    private String quantity;

    public Ingredient() {
    }

    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
