package dto;

import entities.Recipe;
import java.util.List;

/**
 *
 * @author Henning
 */
public class RecipeDTO {

    private String description;
    private List ingredients;
    private String prepTime;
    private List<String> preparations;

    public RecipeDTO(Recipe recipe) {
        this.description = recipe.getDescription();
        this.ingredients = recipe.getIngredients();
        this.prepTime = recipe.getPrepTime();
        this.preparations = recipe.getPreparations();
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

    public String getCookingTime() {
        return prepTime;
    }

    public void setCookingTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public List<String> getPreparations() {
        return preparations;
    }

    public void setPreparations(List<String> preparations) {
        this.preparations = preparations;
    }

}
