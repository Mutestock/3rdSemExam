package dto;

import entities.DayPlan;
import entities.Recipe;
import facades.RecipeFacade;
import java.io.IOException;

/**
 *
 * @author Henning
 */
public class DayPlanDTO {

    //private String recipeID;
    private String dayOfWeek;
    private Recipe recipe;

    public DayPlanDTO(DayPlan dayPlan) throws IOException {
        this.dayOfWeek = dayPlan.getDayOfWeek();
        this.recipe = RecipeFacade.getRecipeFromID(dayPlan.getRecipeID());
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
