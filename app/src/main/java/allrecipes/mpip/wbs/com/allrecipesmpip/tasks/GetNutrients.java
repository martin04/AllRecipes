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

import allrecipes.mpip.wbs.com.allrecipesmpip.models.Nutrient;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;

/**
 * Created by Martin on 8/16/2015.
 */
public class GetNutrients extends AsyncTask<Integer, Void, List<Nutrient>> {
    private String url;
    private OnDetailsResultHandler listener;

    public GetNutrients(OnDetailsResultHandler listener) {
        this.listener = listener;
    }

    @Override
    protected List<Nutrient> doInBackground(Integer... params) {
        url = "http://linkeddata.finki.ukim.mk/sparql?default-graph-uri=&query=PREFIX+ar%3A+%3Chttp%3A%2F%2Fpurl.org%2Fnet%2Fallrecipes%2Fontology%23%3E%0D%0Aselect+%3Fid+%3Frecipe+%3Fname+%3Fdose+where+%7B%0D%0Agraph%3Chttp%3A%2F%2Fpurl.org%2Fnet%2Fallrecipes%2Fdata%23%3E%7B%0D%0A%3FrecipeNutri+a+ar%3ARecipeNutri%3B+ar%3AhasRecipeNutri+%3Frecipe%3B+ar%3AhasNutri+%3Fnutri%3B+ar%3Anutri_quantity+%3Fdose.+%3Frecipe+ar%3Arecipe_id+%3Fid.+%3Fnutri+dbpprop%3Aname+%3Fname.%0D%0A%7D%0D%0AFILTER%28%3Fid%3D"
                + String.format("%d", params[0]) + "%29%0D%0A%7D%0D%0A&should-sponge=&format=application%2Fsparql-results%2Bjson&timeout=0&debug=on";

        try {
            return jsonParser(readJsonFeed(url));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Nutrient> nutrients) {
        if(nutrients!=null){
            listener.onNutrientResult(nutrients);
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

    protected List<Nutrient> jsonParser(String json){
        List<Nutrient> nutris=new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(json).getJSONObject("results");
            JSONArray arr=obj.getJSONArray("bindings");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject nutri=arr.getJSONObject(i);
                Nutrient n=new Nutrient();
                n.setName(nutri.getJSONObject("name").getString("value"));
                n.setDose(nutri.getJSONObject("dose").getString("value"));
                nutris.add(n);
            }

            return nutris;
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }
}