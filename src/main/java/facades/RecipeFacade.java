package facades;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import dto.Person;
import dto.PersonDTO;
import dto.Planet;
import dto.Species;
import dto.Starship;
import dto.Vehicle;
import entities.Recipe;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import utils.DataFetcher;

/**
 *
 * @author Henning
 */
public class RecipeFacade {

    private static ExecutorService executor = Executors.newCachedThreadPool();
    private static List<Future<String>> futureList = new ArrayList();
    private static final Gson GSON = new Gson();
    private static JsonParser parser = new JsonParser();
    private static ExecutorService ES;
    private static Queue<Future<Object>> futures;

    private static String URL = "http://46.101.217.16:4000/recipe/";

    public static Recipe getRecipeFromID(String id) throws IOException {
        return DataFetcher.fetchAsObject(Recipe.class, URL + id);
    }

//    public static void main(String args[]) throws Exception {
//
//        //
//        //String URL = "http://46.101.217.16:4000/recipe/Cheese%20and%20bacon%20stuffed%20pasta%20shells";
//        //String recipeString = DataFetcher.fetchJsonFromUrl(URL);
//        System.out.println("Fetching");
//        System.out.println("fetched: " + getRecipeFromID("Cheese%20and%20bacon%20stuffed%20pasta%20shells"));
//        Recipe rec = getRecipeFromID("Cheese%20and%20bacon%20stuffed%20pasta%20shells");
//        // Recipe rec = DataFetcher.fetchAsObject(Recipe.class, "http://46.101.217.16:4000/recipe/Cheese%20and%20bacon%20stuffed%20pasta%20shells");
//        System.out.println(rec.getId());
//        System.out.println(rec.getDescription());
//        System.out.println(rec.getPreparations());
//        System.out.println(rec.getPrepTime());
//        System.out.println(rec.getIngredients());
//        System.out.println("Fetching");
//
//    }
//
//    private void futureHandling(String id) {
//
//        for (int i = 0; i < 100; i++) {
//            Future<String> future = executor.submit(new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    return Thread.currentThread().getName();
//                }
//            });
//            //String result = future.get();
//            //System.out.println(result);
//            futureList.add(future);
//        }
//        for (Future fut : futureList) {
//           
//        }
//
//        Future<String> future = executor.submit(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return Thread.currentThread().getName();
//            }
//
//        }
//        for (Future fut : futureList) {
//            System.out.println("\t\tStatus:" + fut.get());
//        }
//        executor.shutdown();
//    }
//
//    public static String getStatus(String url) throws IOException {
//
//        String result = "Error";
//        try {
//            URL siteURL = new URL(url);
//            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//
//            int code = connection.getResponseCode();
//            if (code == 200) {
//                result = "Green";
//            }
//            if (code == 301) {
//                result = "Redirect";
//            }
//        } catch (Exception e) {
//            result = "->Red<-";
//        }
//        return result;
//    }
//
//    public PersonDTO fetchPerson(int id) throws IOException, InterruptedException, ExecutionException {
//        long startTime = System.currentTimeMillis();
//        ES = Executors.newCachedThreadPool();
//        futures = new ArrayBlockingQueue<>(20); // we do not know how many elements we'll need - can we avoid fixed queue size?
//
//        String url = "https://swapi.co/api/people/" + id;
//        Person person = DataFetcher.fetchAsObject(Person.class, url); // Person with urls as children
//        PersonDTO result = new PersonDTO(person); // Person with objects as children
//
//        DataFetcher.submitFetchForExecution(ES, futures, person.getHomeworld(), Recipe.class);
//        while (!futures.isEmpty()) {
//            Future<Object> future = futures.poll();
//            if (future.isDone()) {
//                Object obj = future.get();
//                if (obj instanceof Planet) {
//                    result.setHomeworld((Planet) obj);
//                } else if (obj instanceof Species) {
//                    result.addSpecies((Species) obj);
//                } else if (obj instanceof Starship) {
//                    result.addStarships((Starship) obj);
//                } else if (obj instanceof Vehicle) {
//                    result.addVehicles((Vehicle) obj);
//                }
//            } else {
//                futures.add(future);
//            }
//        }
//
//        ES.shutdown();
//        System.out.println("Execution time: " + ((double) (System.currentTimeMillis() - startTime) / 1000.) + " seconds...");
//        return result;
//    }
}
