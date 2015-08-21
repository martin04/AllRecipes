package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;

/**
 * Created by Martin on 8/20/2015.
 */
public class SearchTask extends AsyncTask<String, Void, List<RecipeLOD>> {

    private String url = "";
    private String sparqlEnpoint = "http://linkeddata.finki.ukim.mk/sparql";
    private String sparqlQuery = "";
    /*private Query query;
     private QueryExecution exec;
     private ResultSet results;*/
    private Context ctx;
    private onSearchResultHandler listener;

    public SearchTask(Context ctx, onSearchResultHandler listener) {
        this.ctx = ctx;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onSearchStart();
    }

    @Override
    protected List<RecipeLOD> doInBackground(String... params) {
        String searchWord = params[0].toLowerCase();
        url = "http://linkeddata.finki.ukim.mk/sparql?default-graph-uri=&query=PREFIX+ar%3A+%3Chttp%3A%2F%2Fpurl.org%2Fnet%2Fallrecipes%2Fontology%23%3E%0D%0A%0D%0Aselect+%3Fid+%3Fname+where+%7B%0D%0Agraph%3Chttp%3A%2F%2Fpurl.org%2Fnet%2Fallrecipes%2Fdata%23%3E%7B%0D%0A%3Frecipe+a+ar%3ARecipe%3B+dbpprop%3Aname+%3Fname%3B+ar%3Arecipe_id+%3Fid%0D%0A%7D%0D%0AFILTER%28contains%28%3Fname%2C%27" +
                searchWord + "%27%29+%7C%7C+contains%28%3Fname%2C%27" +
                ((Character.toUpperCase(searchWord.charAt(0))) + searchWord.substring(1)) + "%27%29%29%0D%0A%7D&should-sponge=&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on";

        sparqlQuery = "PREFIX ar: <http://purl.org/net/allrecipes/ontology#>" +
                "select ?id ?name where{" +
                "graph<http://purl.org/net/allrecipes/data#>{ ?recipe a ar:Recipe; ar:recipe_id ?id; dbpprop:name ?name." +
                "}"
                + "FILTER(contains(?name,'" + searchWord + "') || contains(?name,'" + ((Character.toUpperCase(searchWord.charAt(0))) + searchWord.substring(1)) + "'))"
                + "}";
        try {
            /*query = QueryFactory.create(sparqlQuery);
            exec = QueryExecutionFactory.sparqlService(sparqlEnpoint, sparqlQuery);
            results = exec.execSelect();
            ResultSetFormatter.out(System.out, results);*/

            String json = readJsonFeed(url);
            return jsonParser(json);
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<RecipeLOD> recipeLODs) {
        if (recipeLODs != null) {
            listener.onSearchResult(recipeLODs);
        } else {
            listener.onSearchError("Some error occured");
        }

    }


    protected String readJsonFeed(String urlLink) {
        try {
            URL url = new URL(urlLink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // read the response
            System.out.println("Response Code: " + conn.getResponseCode());
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String response = IOUtils.toString(in, "UTF-8");
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    protected List<RecipeLOD> jsonParser(String json){
        List<RecipeLOD> recipes=new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json).getJSONObject("results");
            JSONArray arr=obj.getJSONArray("bindings");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject recipe=arr.getJSONObject(i);
                RecipeLOD r=new RecipeLOD();
                r.setId(recipe.getJSONObject("id").getInt("value"));
                r.setName(recipe.getJSONObject("name").getString("value"));
                recipes.add(r);
            }

            return recipes;
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
