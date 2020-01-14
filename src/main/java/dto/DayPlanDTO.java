package dto;

import entities.DayPlan;
import entities.Recipe;
import facades.RecipeFacade;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Henning
 */
public class DayPlanDTO {

    private String recipeID;
    private String dayOfWeek;
    private RecipeDTO recipe;

    public DayPlanDTO(DayPlan dayPlan) throws IOException, InterruptedException, ExecutionException {
        this.dayOfWeek = dayPlan.getDayOfWeek();
        this.recipeID = recipeID;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public RecipeDTO getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDTO recipe) {
        this.recipe = recipe;
    }
}
