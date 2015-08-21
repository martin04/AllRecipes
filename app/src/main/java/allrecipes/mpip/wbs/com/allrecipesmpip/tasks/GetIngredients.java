package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;

import android.os.AsyncTask;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.models.Ingredient;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.Nutrient;

/**
 * Created by Martin on 8/16/2015.
 */
public class GetIngredients extends AsyncTask<Integer, Void, List<Ingredient>> {
    private String url;
    private OnDetailsResultHandler listener;

    public GetIngredients(OnDetailsResultHandler listener) {
        this.listener = listener;
    }

    @Override
    protected List<Ingredient> doInBackground(Integer... params) {
        url = "http://linkeddata.finki.ukim.mk/sparql?default-graph-uri=&query=PREFIX+ar%3A+%3Chttp%3A%2F%2Fpurl.org%2Fnet%2Fallrecipes%2Fontology%23%3E%0D%0APREFIX+fo%3A+%3Chttp%3A%2F%2Fpurl.org%2Fontology%2Ffo%2F%3E%0D%0Aselect+%3Fid+%3Frecipe+%3Fname+%3Fqty+where+%7B%0D%0Agraph%3Chttp%3A%2F%2Fpurl.org%2Fnet%2Fallrecipes%2Fdata%23%3E%7B%0D%0A%3FrecipeIngred+a+ar%3ARecipeIngredient%3B+ar%3AhasRecipeIngred+%3Frecipe%3B+ar%3AhasIngredient+%3Fingred%3B+fo%3Aquantity+%3Fqty.+%3Frecipe+ar%3Arecipe_id+%3Fid.+%3Fingred+dbpprop%3Aname+%3Fname.%0D%0A%7D%0D%0AFILTER%28%3Fid%3D"
                +String.format("%d",params[0])+"%29%0D%0A%7D%0D%0A&should-sponge=&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on";

        try {
            return jsonParser(readJsonFeed(url));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Ingredient> ingredients) {
        if(ingredients!=null){
            listener.onIngredientResult(ingredients);
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

    protected List<Ingredient> jsonParser(String json){
        List<Ingredient> ingreds=new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json).getJSONObject("results");
            JSONArray arr=obj.getJSONArray("bindings");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject ingredient=arr.getJSONObject(i);
                Ingredient in=new Ingredient();
                in.setName(ingredient.getJSONObject("name").getString("value"));
                in.setQuantity(ingredient.getJSONObject("qty").getString("value"));
                ingreds.add(in);
            }

            return ingreds;
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }
}
