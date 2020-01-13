package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.EnumUtils;

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "DayPlan")
@NamedQueries({
    @NamedQuery(name = "DayPlan.findAll", query = "SELECT d FROM DayPlan d"),
    @NamedQuery(name = "DayPlan.findById", query = "SELECT d FROM DayPlan d WHERE d.id = :id"),
    @NamedQuery(name = "DayPlan.findByRecipe", query = "SELECT d FROM DayPlan d WHERE d.recipeID = :recipeID"),
    @NamedQuery(name = "DayPlan.removeAll", query = "DELETE FROM DayPlan d"),
    @NamedQuery(name = "DayPlan.findByDayOfWeek", query = "SELECT d FROM DayPlan d WHERE d.dayOfWeek = :dayOfWeek")})
public class DayPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Size(max = 200)
    @Column(name = "recipe")
    @JsonIgnore
    private String recipeID;
    @Size(max = 200)
    @Column(name = "day_of_week")
    private String dayOfWeek;
    @Transient
    private Recipe recipe;

    /*@JoinTable(name = "DayPlanList", joinColumns = {
        @JoinColumn(name = "DayPlan_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "MenuPlan_id", referencedColumnName = "id")})
    @ManyToMany
    private List<MenuPlan> menuPlanList;*/
    public DayPlan() {
    }

    public DayPlan(String recipeID, String dayOfWeek) {
        this.recipeID = recipeID;
        this.dayOfWeek = validateWeekDay(dayOfWeek);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = validateWeekDay(dayOfWeek);
    }

    private String validateWeekDay(String dayOfWeek) {
        if (EnumUtils.isValidEnum(AllowedDays.class, dayOfWeek.toUpperCase())) {
            return dayOfWeek;
        } else {
            throw new IllegalArgumentException("Day validation failure");
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /*public List<MenuPlan> getMenuPlanList() {
        return menuPlanList;
    }

    public void setMenuPlanList(List<MenuPlan> menuPlanList) {
        this.menuPlanList = menuPlanList;
    }
     */
    private enum AllowedDays {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
