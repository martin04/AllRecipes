package allrecipes.mpip.wbs.com.allrecipesmpip.models;

/**
 * Created by Martin on 8/20/2015.
 */
public class RecipeLOD {

    private int id;
    private String name;
    private String cuisine;
    private String servings;
    private String servings_size;

    public RecipeLOD(int id, String name, String cuisine, String servings, String servings_size) {

        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.servings = servings;
        this.servings_size = servings_size;
    }

    public RecipeLOD() {
    }

    public RecipeLOD(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getServings_size() {
        return servings_size;
    }

    public void setServings_size(String servings_size) {
        this.servings_size = servings_size;
    }
}
