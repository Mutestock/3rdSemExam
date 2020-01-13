package dto;

import entities.DayPlan;
import entities.Item;
import entities.MenuPlan;
import entities.User;
import java.util.List;

/**
 *
 * @author Henning
 */
public class MenuPlanDTO {

    private List<DayPlan> dayPlanList;
    private List<Item> itemList;
    private User user;

    public MenuPlanDTO(MenuPlan menuPlan) {
    }

    public List<DayPlan> getDayPlanList() {
        return dayPlanList;
    }

    public void setDayPlanList(List<DayPlan> dayPlanList) {
        this.dayPlanList = dayPlanList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
