package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Henning
 */
@Entity
@Table(name = "MenuPlan")
@NamedQueries({
    @NamedQuery(name = "MenuPlan.findAll", query = "SELECT m FROM MenuPlan m"),
    @NamedQuery(name = "MenuPlan.removeAll", query = "DELETE FROM MenuPlan m"),
    @NamedQuery(name = "MenuPlan.findById", query = "SELECT m FROM MenuPlan m WHERE m.id = :id")})
public class MenuPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*@JoinTable(name = "DayPlanList", joinColumns = {
        @JoinColumn(name = "DayPlan_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "MenuPlan_id", referencedColumnName = "id")})
     */
    @JoinTable(name = "DayPlanList", joinColumns = {
        @JoinColumn(name = "MenuPlan_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "DayPlan_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<DayPlan> dayPlanList = new ArrayList<>();
    @JoinTable(name = "MenuPlan_Item", joinColumns = {
        @JoinColumn(name = "MenuPlan_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "item_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Item> itemList = new ArrayList<>();
    @ManyToOne(optional = false)
    private User user;

    public MenuPlan() {
    }
    
    public MenuPlan(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
