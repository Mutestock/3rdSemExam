package dto;

import entities.DayPlan;
import entities.Recipe;

/**
 *
 * @author Henning
 */
public class DayPlanDTO {

    //private String recipeID;
    private String dayOfWeek;
    private Recipe recipe;

    public DayPlanDTO(DayPlan dayPlan) {
        this.dayOfWeek = dayPlan.getDayOfWeek();
        this.recipe = dayPlan.getRecipe();
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

}
