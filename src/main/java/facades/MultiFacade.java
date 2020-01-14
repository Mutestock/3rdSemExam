package facades;

import entities.Item;
import entities.DayPlan;
import entities.MenuPlan;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Henning
 */
public class MultiFacade<T> extends AbstractFacade {

    private static EntityManagerFactory emf;
    private static BasicExtensionFacade<MenuPlan> basicMenuFacade;
    private static BasicExtensionFacade<User> basicUserFacade;

    public MultiFacade(Class entityClass) {
        super(entityClass);
    }

    public MultiFacade(Class entityClass, EntityManagerFactory emf) {
        super(entityClass);
        this.emf = emf;
        if (ENTITY_CLASS.getSimpleName().toUpperCase().equals("DAYPLAN")) {
            System.out.println("Instantiating..");
            basicMenuFacade = new BasicExtensionFacade(MenuPlan.class, emf);
            basicUserFacade = new BasicExtensionFacade(User.class, emf);
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void remove(long entity) {
        System.out.println(find(entity).getClass().getSimpleName().toUpperCase());
        Uprooter.valueOf(find(entity).getClass().getSimpleName().toUpperCase()).uproot(find(entity));
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.find(ENTITY_CLASS, entity));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private enum Uprooter {
        DAYPLAN {
            @Override
            public void uproot(Object obj) {
                try {
                    for (Object mp : basicMenuFacade.findAll()) {
                        for (DayPlan dp : ((MenuPlan) mp).getDayPlanList()) {
                            if ((dp.getId().equals(((DayPlan) obj).getId()))) {
                                List<DayPlan> switcheroo = ((MenuPlan) mp).getDayPlanList();
                                switcheroo.remove(dp);
                                ((MenuPlan) mp).setDayPlanList(switcheroo);
                                basicMenuFacade.edit(mp);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Unable to uproot DAYPLAN from MENUPLAN - " + e);
                }
            }
        }, ITEM {
            @Override
            public void uproot(Object obj) {
                try {
                    for (Object mp : basicMenuFacade.findAll()) {
                        for (Item item : ((MenuPlan) mp).getItemList()) {
                            if ((item.getId().equals(((Item) obj).getId()))) {
                                List<Item> switcheroo = ((MenuPlan) mp).getItemList();
                                switcheroo.remove(item);
                                ((MenuPlan) mp).setItemList(switcheroo);
                                basicMenuFacade.edit(mp);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Unable to uproot ITEM from MENUPLAN- " + e);
                }
            }
        }, MENUPLAN {
            @Override
            public void uproot(Object obj) {
                try {
                    for (Object user : basicUserFacade.findAll()) {
                        for (MenuPlan menuPlan : ((User) user).getMenuPlanList()) {
                            if ((menuPlan.getId().equals(((Item) obj).getId()))) {
                                List<MenuPlan> switcheroo = ((User) user).getMenuPlanList();
                                switcheroo.remove(menuPlan);
                                ((User) user).setMenuPlanList(switcheroo);
                                basicUserFacade.edit(user);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Unable to uproot MENUPLAN from from USER - " + e);
                }
            }
        };

        public abstract void uproot(Object obj);
    }
}
