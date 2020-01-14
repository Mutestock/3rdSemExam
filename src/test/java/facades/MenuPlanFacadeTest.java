package facades;

import entities.DayPlan;
import entities.Item;
import entities.MenuPlan;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Henning
 */
//I understand fully well, that these tests shouldn't be using other facade methods in their tests.
//They take time to design correctly
//And time is a thing I don't have a lot of.
//Normally I'd use the NamedQueries for this.
@Disabled
public class MenuPlanFacadeTest {

    private MultiFacade<MenuPlan> MENUPLAN_FACADE;
    private MultiFacade<DayPlan> DAYPLAN_FACADE;
    private MultiFacade<Item> ITEM_FACADE;
    private static EntityManagerFactory emf;

    MenuPlan menuPlan01 = new MenuPlan();
    MenuPlan menuPlan02 = new MenuPlan();

    DayPlan dayPlan01 = new DayPlan("Cheese and bacon stuffed pasta shells", "Wednesday");
    DayPlan dayPlan02 = new DayPlan("Moist garlic roasted chicken", "Friday");

    Item item01 = new Item("Asparges");
    Item item02 = new Item("Eggplant");

    public MenuPlanFacadeTest() {
        MENUPLAN_FACADE = new MultiFacade(MenuPlan.class, emf);
        DAYPLAN_FACADE = new MultiFacade(DayPlan.class, emf);
        ITEM_FACADE = new MultiFacade(Item.class, emf);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
    }

    @BeforeEach
    public void setUp() {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("MenuPlan.removeAll").executeUpdate();
            em.createNamedQuery("DayPlan.removeAll").executeUpdate();
            em.createNamedQuery("Item.removeAll").executeUpdate();
            em.getTransaction().commit();

            Stream.of(
                    menuPlan01,
                    menuPlan02,
                    dayPlan01,
                    dayPlan02,
                    item01,
                    item02
            ).forEach(o -> {
                em.getTransaction().begin();
                em.persist(o);
                em.getTransaction().commit();
            });
        } finally {
            em.close();
        }
    }

    //Not a whole lot to test with here...
    @Test
    public void find() {
        Assertions.assertEquals(menuPlan02.getId(), ((MenuPlan) MENUPLAN_FACADE.find(menuPlan02.getId())).getId());
    }

    @Test
    public void findAll() {
        Assertions.assertEquals(2, MENUPLAN_FACADE.findAll().size());
    }

    @Test
    public void create() {
        int size = MENUPLAN_FACADE.findAll().size();
        MENUPLAN_FACADE.create(new MenuPlan());
        Assertions.assertEquals(size + 1, MENUPLAN_FACADE.findAll().size());
    }

    @Test
    public void update() {
        List<DayPlan> dayPlanList = menuPlan01.getDayPlanList();
        dayPlanList.add(dayPlan01);
        menuPlan01.setDayPlanList(dayPlanList);
        MENUPLAN_FACADE.edit(menuPlan01);
        Assertions.assertEquals(1, ((MenuPlan) MENUPLAN_FACADE.find(menuPlan01.getId())).getDayPlanList().size());
        Assertions.assertEquals("Wednesday", ((MenuPlan) MENUPLAN_FACADE.find(menuPlan01.getId())).getDayPlanList().get(0).getDayOfWeek());
    }

    @Test
    public void removeBasic() {
        int size = MENUPLAN_FACADE.findAll().size();
        MENUPLAN_FACADE.remove(menuPlan01.getId());
        Assertions.assertEquals(size - 1, MENUPLAN_FACADE.findAll().size());
    }

    //Relation testing.
    //See Uprooter in MultiFacade
    //This is to ensure, that there are no child/parent issues when deleting objects
    @Test
    public void removeUproot01() {
        List<DayPlan> dayPlanList = new ArrayList<>();
        dayPlanList.add(dayPlan01);
        dayPlanList.add(dayPlan02);
        menuPlan01.setDayPlanList(dayPlanList);
        MENUPLAN_FACADE.edit(menuPlan01);
        Assertions.assertEquals(2, MENUPLAN_FACADE.findAll().size());
        Assertions.assertEquals(2, ((MenuPlan) MENUPLAN_FACADE.find(menuPlan01.getId())).getDayPlanList().size());

        DAYPLAN_FACADE.remove(dayPlan01.getId());
        Assertions.assertEquals(1, DAYPLAN_FACADE.findAll().size());

        Assertions.assertEquals(1, ((MenuPlan) MENUPLAN_FACADE.find(menuPlan01.getId())).getDayPlanList().size());
    }

    @Test
    public void removeUproot02() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item01);
        itemList.add(item02);
        menuPlan01.setItemList(itemList);
        MENUPLAN_FACADE.edit(menuPlan01);
        Assertions.assertEquals(2, MENUPLAN_FACADE.findAll().size());
        Assertions.assertEquals(2, ((MenuPlan) MENUPLAN_FACADE.find(menuPlan01.getId())).getItemList().size());

        ITEM_FACADE.remove(item01.getId());
        Assertions.assertEquals(1, ITEM_FACADE.findAll().size());

        Assertions.assertEquals(1, ((MenuPlan) MENUPLAN_FACADE.find(menuPlan01.getId())).getItemList().size());
    }
}
