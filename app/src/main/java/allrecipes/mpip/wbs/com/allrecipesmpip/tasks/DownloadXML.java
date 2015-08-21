package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;

/**
 * Created by Ace on 7/2/2015.
 */
public class DownloadXML extends AsyncTask<String, Void, Void> {

    private ProgressDialog pDialog;
    private NodeList nodelist;
    private List<RecipeInfo> data;
    private Callback callback;
    private Context ctx;


    public DownloadXML(Callback callback, Context ctx) {
        this.callback = callback;
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        data = new ArrayList<RecipeInfo>();

        // Create a progressbar
        pDialog = new ProgressDialog(ctx);
        // Set progressbar message
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        // Show progressbar
        pDialog.show();


    }

    @Override
    protected Void doInBackground(String... Url) {
        try {
            URL url = new URL(Url[0]);
            DocumentBuilderFactory dbf = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Download the XML file
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            // Locate the Tag Name
            nodelist = doc.getElementsByTagName("item");

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void args) {

        for (int temp = 0; temp < nodelist.getLength(); temp++) {
            Node nNode = nodelist.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                // Get the title
                String title = getNode("title", eElement);
                title = title.substring(title.indexOf(":") + 1).trim();

                // Get the description
                String description = getNode("description", eElement);
                org.jsoup.nodes.Document doc = Jsoup.parse(description);

                //Get img src attribute
                String img = doc.select("img").attr("src").toString();

                //Get raiting
                String raiting = doc.select("p").get(1).text().toString();
                raiting = raiting.substring(0, raiting.indexOf("/"));

                //Get abstract
                String label = doc.select("p").get(3).text().toString().replace("\"", "");


                /*Filling objects*/
                RecipeInfo recipe = new RecipeInfo();
                recipe.setTitle(title);
                recipe.setDescription(label);
                recipe.setImageUrl(img);
                recipe.setRating(Double.parseDouble(raiting));
                recipe.setFavourite(false);

                data.add(recipe);

            }
        }
        // Close progressbar
        pDialog.dismiss();
        callback.onResult(data);
    }


    // getNode function
    private static String getNode(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

}
