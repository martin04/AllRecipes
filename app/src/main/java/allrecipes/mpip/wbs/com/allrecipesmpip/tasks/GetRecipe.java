package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;

import android.net.Uri;
import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;

/**
 * Created by Martin on 8/20/2015.
 */
public class GetRecipe extends AsyncTask<String, Void, RecipeLOD> {
    private String url;
    private OnDetailsResultHandler listener;

    public GetRecipe(OnDetailsResultHandler listener) {
        this.listener = listener;
    }

    @Override
    protected RecipeLOD doInBackground(String... params) {
        url = "http://linkeddata.finki.ukim.mk/sparql?default-graph-uri=&query=PREFIX+ar%3A+%3Chttp%3A%2F%2Fpurl.org%2Fnet%2Fallrecipes%2Fontology%23%3E%0D%0APREFIX+fo%3A+%3Chttp%3A%2F%2Fpurl.org%2Fontology%2Ffo%2F%3E%0D%0Aselect+%3Fid+%3Fname+%3Fcuisine+%3Fservings+%3Fssize+where+%7B%0D%0Agraph%3Chttp%3A%2F%2Fpurl.org%2Fnet%2Fallrecipes%2Fdata%23%3E%7B%0D%0A%3Frecipe+a+ar%3ARecipe%3B+ar%3Arecipe_id+%3Fid%3B+dbpprop%3Aname+%3Fname%3B+fo%3Acuisine+%3Fcuisine%3B+ar%3Aservings+%3Fservings%3B+ar%3Aservings_size+%3Fssize.%0D%0A%7D%0D%0AFILTER%28lcase%28str%28%3Fname%29%29%3Dlcase%28%27"
                + Uri.encode(params[0]) + "%27%29%29%0D%0A%7D+%0D%0A&should-sponge=&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on";

        try{
            return jsonParser(readJsonFeed(url));
        }catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(RecipeLOD recipeLOD) {
        if(recipeLOD!=null){
            listener.onRecipeResult(recipeLOD);
        }else{
            listener.onDetailsError();
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

    protected RecipeLOD jsonParser(String json) {

        try {
            JSONObject obj = new JSONObject(json).getJSONObject("results");
            JSONArray arr = obj.getJSONArray("bindings");
            JSONObject recipe = arr.getJSONObject(0);
            RecipeLOD r = new RecipeLOD();
            r.setId(recipe.getJSONObject("id").getInt("value"));
            r.setName(recipe.getJSONObject("name").getString("value"));
            r.setCuisine(recipe.getJSONObject("cuisine").getString("value"));
            r.setServings(recipe.getJSONObject("servings").getString("value"));
            r.setServings_size(recipe.getJSONObject("ssize").getString("value"));
            return r;
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
