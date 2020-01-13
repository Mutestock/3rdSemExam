package facades;

import entities.DayPlan;
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
public class DayPlanFacadeTest {

    private final MultiFacade<DayPlan> DAYPLAN_FACADE;
    private static EntityManagerFactory emf;

    DayPlan dayPlan01 = new DayPlan("Cheese and bacon stuffed pasta shells", "Wednesday");
    DayPlan dayPlan02 = new DayPlan("Moist garlic roasted chicken", "Friday");

    public DayPlanFacadeTest() {
        DAYPLAN_FACADE = new MultiFacade(DayPlan.class, emf);
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
            em.createNamedQuery("DayPlan.removeAll").executeUpdate();
            em.getTransaction().commit();

            Stream.of(
                    dayPlan01,
                    dayPlan02
            ).forEach(o -> {
                em.getTransaction().begin();
                em.persist(o);
                em.getTransaction().commit();
            });
        } finally {
            em.close();
        }
    }

    @Test
    public void find() {
        Assertions.assertEquals("Wednesday", ((DayPlan) DAYPLAN_FACADE.find(dayPlan01.getId())).getDayOfWeek());
    }

    @Test
    public void findAll() {
        Assertions.assertEquals(2, DAYPLAN_FACADE.findAll().size());
    }

    @Test
    public void create() {
        int size = DAYPLAN_FACADE.findAll().size();
        DAYPLAN_FACADE.create(new DayPlan("Pistachio chicken with pomegranate sauce", "Saturday"));
        Assertions.assertEquals(size + 1, DAYPLAN_FACADE.findAll().size());
    }

    @Test
    public void update() {
        dayPlan01.setDayOfWeek("Sunday");
        DAYPLAN_FACADE.edit(dayPlan01);
        Assertions.assertEquals("Sunday", ((DayPlan) DAYPLAN_FACADE.find(dayPlan01.getId())).getDayOfWeek());
    }

    @Test
    public void removeBasic() {
        int size = DAYPLAN_FACADE.findAll().size();
        DAYPLAN_FACADE.remove(dayPlan01.getId());
        Assertions.assertEquals(size - 1, DAYPLAN_FACADE.findAll().size());
    }

    @Test
    public void testEnumValidation() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            DayPlan dayPlanDefect = new DayPlan("I can't be instantiated", "FlemDay");
        });
        DayPlan dayPlanCorrect01 = new DayPlan("I can be instantiated", "SaTURDaY");
    }
    
   
}
