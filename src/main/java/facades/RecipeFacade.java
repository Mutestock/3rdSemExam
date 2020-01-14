package facades;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import dto.RecipeDTO;
import entities.Recipe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataFetcher;

/**
 *
 * @author Henning
 */
public class RecipeFacade {

    private static ExecutorService executor = Executors.newCachedThreadPool();
    private static List<Future<Recipe>> futureList = new ArrayList();
    private static final Gson GSON = new Gson();
    private static JsonParser parser = new JsonParser();
    private static ExecutorService ES;
    private static Queue<Future<Object>> futures;

    private static String URL = "http://46.101.217.16:4000/";

    public Recipe getRecipeFromID(String id) throws IOException, InterruptedException, ExecutionException {
        return getRecipeFutureByID(id).get();
    }

//    public List<Recipe> getAllRecipes() throws IOException {
//        System.out.println("get All Recipes");
//        List<String> idList = DataFetcher.fetchAsObject(List.class, URL + "allRecipes");
//        List<Recipe> recipeList = new ArrayList();
//        System.out.println(idList.size());
//        for (String s : idList) {
//            System.out.println("s was: " + s);
//            recipeList.add(getRecipeFromID(s));
//            System.out.println("added..");
//        }
//        return recipeList;
//    }
    public Future<Recipe> getRecipeFutureByID(String id) {
        return executor.submit(() -> {
            return DataFetcher.fetchAsObject(Recipe.class, URL + "recipe/" + id);
        });
    }

    public Future<List<String>> getAllRecipeStringsFutureSimple() {
        return executor.submit(() -> {
            return DataFetcher.fetchAsObject(List.class, URL + "allRecipes");
        });
    }

    public List<Recipe> getAllRecipes() throws IOException, InterruptedException, ExecutionException {
        Future<List<String>> future01 = getAllRecipeStringsFutureSimple();
        List<Recipe> recipeList = new ArrayList();
        while (!(future01.isDone())) {
            for(String s : future01.get()){
                Future<Recipe> futRec = getRecipeFutureByID(s);
                while(!(futRec.isDone())){
                    recipeList.add(futRec.get());
                }
            }
        }
        return recipeList;
    }


    /*public Future<List<Recipe>> getAllRecipesFuture() throws IOException, InterruptedException, ExecutionException {
        List<String> idList = DataFetcher.fetchAsObject(List.class, URL + "allRecipes");
        List<Recipe> recipeList = new ArrayList();
        for (String s : idList) {
            futureList.add(getRecipeFutureByID(s));
        }
        return executor.submit(() -> {
            return DataFetcher.fetchAsObject(Recipe.class, URL + "recipe/" + id);

        });
    }*/
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
    public RecipeDTO fetchRecipe(String id) throws IOException, InterruptedException, ExecutionException {
        long startTime = System.currentTimeMillis();
        ES = Executors.newCachedThreadPool();
        futures = new ArrayBlockingQueue<>(20); // we do not know how many elements we'll need - can we avoid fixed queue size?

        String url = URL + id;
        Recipe recipe = DataFetcher.fetchAsObject(Recipe.class, url); // Person with urls as children
        RecipeDTO result = new RecipeDTO(recipe); // Person with objects as children

        futures.add(ES.submit(() -> DataFetcher.fetchAsObject(Recipe.class, url)));
        //DataFetcher.submitFetchForExecution(ES, futures, person.getHomeworld(), Recipe.class);
        while (!futures.isEmpty()) {
            Future<Object> future = futures.poll();
            if (future.isDone()) {
                Object obj = future.get();
            } else {
                futures.add(future);
            }
        }

        ES.shutdown();
        System.out.println("Execution time: " + ((double) (System.currentTimeMillis() - startTime) / 1000.) + " seconds...");
        return result;
    }
}
