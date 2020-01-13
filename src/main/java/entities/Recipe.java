package entities;

import java.util.List;

/**
 *
 * @author Henning
 */
public class Recipe {

    private String id;
    private String description;
    private List<String> ingredients;
    private String prep_time;
    private List<String> preparaion_steps;

    public Recipe(String id, String description, List ingredients, String prep_time, List<String> preparation_steps) {
        this.description = description;
        this.ingredients = ingredients;
        this.prep_time = prep_time;
        this.preparaion_steps = preparation_steps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrepTime() {
        return prep_time;
    }

    public void setPrepTime(String prep_time) {
        this.prep_time = prep_time;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getPreparations() {
        return preparaion_steps;
    }

    public void setPreparations(List<String> preparation_steps) {
        this.preparaion_steps = preparation_steps;
    }
}
