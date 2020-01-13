package facades;

import entities.Item;
import entities.Item;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class ItemFacadeTest {

    private final MultiFacade<Item> ITEM_FACADE;
    private static EntityManagerFactory emf;

    Item item01 = new Item("Asparges");
    Item item02 = new Item("Eggplant");

    public ItemFacadeTest() {
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
            em.createNamedQuery("Item.removeAll").executeUpdate();
            em.getTransaction().commit();

            Stream.of(
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

  
    @Test
    public void find() {
        Assertions.assertEquals("Asparges", ((Item) ITEM_FACADE.find(item01.getId())).getName());
    }

    @Test
    public void findAll() {
        Assertions.assertEquals(2, ITEM_FACADE.findAll().size());
    }

    @Test
    public void create() {
        int size = ITEM_FACADE.findAll().size();
        ITEM_FACADE.create(new Item("Mushroom"));
        Assertions.assertEquals(size + 1, ITEM_FACADE.findAll().size());
    }

    @Test
    public void update() {
        item01.setName("Beef Jerkey");
        ITEM_FACADE.edit(item01);
        Assertions.assertEquals("Beef Jerkey", ((Item) ITEM_FACADE.find(item01.getId())).getName());
    }

    @Test
    public void removeBasic() {
        int size = ITEM_FACADE.findAll().size();
        ITEM_FACADE.remove(item01.getId());
        Assertions.assertEquals(size - 1, ITEM_FACADE.findAll().size());
    }


    //Relation testing.
    //See Uprooter in MultiFacade
    //This is to ensure, that there are no child/parent issues when deleting objects
    @Test
    public void removeUproot() {

    }
}
