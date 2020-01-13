package entities;

import java.util.List;

/**
 *
 * @author Henning
 */
public class Recipe {

    private Long id;
    private String description;
    private List ingredients;
    private String prepTime;
    private String preparations;

    public Recipe(String description, List ingredients, String prepTime, String preparations) {
        this.description = description;
        this.ingredients = ingredients;
        this.prepTime = prepTime;
        this.preparations = preparations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getIngredients() {
        return ingredients;
    }

    public void setIngredients(List ingredients) {
        this.ingredients = ingredients;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getPreparations() {
        return preparations;
    }

    public void setPreparations(String preparations) {
        this.preparations = preparations;
    }

}
