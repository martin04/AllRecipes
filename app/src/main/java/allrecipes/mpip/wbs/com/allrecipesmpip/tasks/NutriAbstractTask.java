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

import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;

/**
 * Created by Martin on 8/20/2015.
 */
public class NutriAbstractTask extends AsyncTask<String, Void, String> {
    private String url;
    private onNutriAbstractGet listener;

    public NutriAbstractTask(onNutriAbstractGet listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        url = "http://dbpedia.org/sparql?default-graph-uri=http%3A%2F%2Fdbpedia.org&query=select+%3Fabstract+where+%7B%0D%0A%3Chttp%3A%2F%2Fdbpedia.org%2Fresource%2F" +
                params[0] + "%3E+dbo%3Aabstract+%3Fabstract.%0D%0AFILTER%28lang%28%3Fabstract%29%3D%22en%22%29%0D%0A%7D&format=application%2Fsparql-results%2Bjson&timeout=30000&debug=on";
        try {
            return jsonParser(readJsonFeed(url));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            listener.onNutriAbstractResult(s);
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

    protected String jsonParser(String json) {
        try {
            JSONObject obj = new JSONObject(json).getJSONObject("results");
            JSONArray arr = obj.getJSONArray("bindings");
            JSONObject abstractN = arr.getJSONObject(0);
            return abstractN.getJSONObject("abstract").getString("value");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
