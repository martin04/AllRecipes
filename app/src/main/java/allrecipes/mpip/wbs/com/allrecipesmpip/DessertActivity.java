package allrecipes.mpip.wbs.com.allrecipesmpip;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.RecipeAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.Callback;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.DownloadXML;

/**
 * Created by Martin on 30-Jun-15.
 */
public class DessertActivity extends AppCompatActivity {

    private RecyclerView desserts;
    private RecipeAdapter adapter;
    private LinearLayoutManager manager;
    private List<RecipeInfo> data;
    private String url = "http://rss.allrecipes.com/daily.aspx?hubID=79";
    private ConnectivityManager cm;
    private NetworkInfo ni;
    private boolean dataFilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dessert_activity);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        desserts = (RecyclerView) findViewById(R.id.lstDessert);
        manager = new LinearLayoutManager(this);
        desserts.setLayoutManager(manager);

        dataFilled = false;

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean isConnected = ni != null && ni.isConnectedOrConnecting();
        if (!isConnected) {
            dataFilled = false;
            connectionDialog();
        } else {
            dataFilled = true;
            final Context ctx = this;
            new DownloadXML(new Callback() {
                @Override
                public void onResult(List<RecipeInfo> result) {
                    Toast.makeText(ctx, "Desert loaded", Toast.LENGTH_SHORT).show();
                    adapter = new RecipeAdapter(result);
                    desserts.setAdapter(adapter);
                }
            }, ctx).execute(url);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!dataFilled) {
            final Context ctx = this;
            new DownloadXML(new Callback() {
                @Override
                public void onResult(List<RecipeInfo> result) {
                    Toast.makeText(ctx, "Desert loaded", Toast.LENGTH_SHORT).show();
                    adapter = new RecipeAdapter(result);
                    desserts.setAdapter(adapter);
                }

            }, ctx).execute(url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_food, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        if (id == R.id.menu_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private List<RecipeInfo> getDessert() {

        RecipeInfo r1 = new RecipeInfo("Easy Valentine's Day Cake",
                "Impress your favorite person on Valentine's day with a double-layer heart-shaped cake covered in homemade vanilla icing.",
                "http://images.media-allrecipes.com/userphotos/250x250/01/01/71/1017105.jpg",
                1.2,
                true);

        RecipeInfo r2 = new RecipeInfo("Brown Butter Cookies",
                "Using butter (NOT margarine) is essential for the success of this rich, but highly addictive cookie! You may need as little as 3 cups confectioners' sugar for the icing; just stop adding it when you've reached the desired consistency.",
                "http://images.media-allrecipes.com/userphotos/250x250/00/74/43/744345.jpg",
                4.71,
                true);

        RecipeInfo r3 = new RecipeInfo("Easy Valentine's Day Cake",
                "Impress your favorite person on Valentine's day with a double-layer heart-shaped cake covered in homemade vanilla icing.",
                "http://images.media-allrecipes.com/userphotos/250x250/01/01/71/1017105.jpg",
                1.2,
                true);

        RecipeInfo r4 = new RecipeInfo("Brown Butter Cookies",
                "Using butter (NOT margarine) is essential for the success of this rich, but highly addictive cookie! You may need as little as 3 cups confectioners' sugar for the icing; just stop adding it when you've reached the desired consistency.",
                "http://images.media-allrecipes.com/userphotos/250x250/00/74/43/744345.jpg",
                4.71,
                true);

        RecipeInfo r5 = new RecipeInfo("Easy Valentine's Day Cake",
                "Impress your favorite person on Valentine's day with a double-layer heart-shaped cake covered in homemade vanilla icing.",
                "http://images.media-allrecipes.com/userphotos/250x250/01/01/71/1017105.jpg",
                1.2,
                true);

        RecipeInfo r6 = new RecipeInfo("Brown Butter Cookies",
                "Using butter (NOT margarine) is essential for the success of this rich, but highly addictive cookie! You may need as little as 3 cups confectioners' sugar for the icing; just stop adding it when you've reached the desired consistency.",
                "http://images.media-allrecipes.com/userphotos/250x250/00/74/43/744345.jpg",
                4.71,
                true);


        RecipeInfo r7 = new RecipeInfo("Easy Valentine's Day Cake",
                "Impress your favorite person on Valentine's day with a double-layer heart-shaped cake covered in homemade vanilla icing.",
                "http://images.media-allrecipes.com/userphotos/250x250/01/01/71/1017105.jpg",
                1.2,
                true);

        RecipeInfo r8 = new RecipeInfo("Brown Butter Cookies",
                "Using butter (NOT margarine) is essential for the success of this rich, but highly addictive cookie! You may need as little as 3 cups confectioners' sugar for the icing; just stop adding it when you've reached the desired consistency.",
                "http://images.media-allrecipes.com/userphotos/250x250/00/74/43/744345.jpg",
                4.71,
                true);

        RecipeInfo r9 = new RecipeInfo("Easy Valentine's Day Cake",
                "Impress your favorite person on Valentine's day with a double-layer heart-shaped cake covered in homemade vanilla icing.",
                "http://images.media-allrecipes.com/userphotos/250x250/01/01/71/1017105.jpg",
                1.2,
                true);

        RecipeInfo r10 = new RecipeInfo("Brown Butter Cookies",
                "Using butter (NOT margarine) is essential for the success of this rich, but highly addictive cookie! You may need as little as 3 cups confectioners' sugar for the icing; just stop adding it when you've reached the desired consistency.",
                "http://images.media-allrecipes.com/userphotos/250x250/00/74/43/744345.jpg",
                4.71,
                true);

        RecipeInfo r11 = new RecipeInfo("Easy Valentine's Day Cake",
                "Impress your favorite person on Valentine's day with a double-layer heart-shaped cake covered in homemade vanilla icing.",
                "http://images.media-allrecipes.com/userphotos/250x250/01/01/71/1017105.jpg",
                1.2,
                true);

        RecipeInfo r12 = new RecipeInfo("Brown Butter Cookies",
                "Using butter (NOT margarine) is essential for the success of this rich, but highly addictive cookie! You may need as little as 3 cups confectioners' sugar for the icing; just stop adding it when you've reached the desired consistency.",
                "http://images.media-allrecipes.com/userphotos/250x250/00/74/43/744345.jpg",
                4.71,
                true);

        List<RecipeInfo> lista = new ArrayList<RecipeInfo>();
        lista.add(r1);
        lista.add(r2);
        lista.add(r3);
        lista.add(r4);
        lista.add(r5);
        lista.add(r6);
        lista.add(r7);
        lista.add(r8);
        lista.add(r9);
        lista.add(r10);
        lista.add(r11);
        lista.add(r12);

        return lista;
    }


    private void connectionDialog() {
        new AlertDialog.Builder(DessertActivity.this)
                .setTitle(R.string.connection_title)
                .setMessage(R.string.connection_message)
                .setNegativeButton(R.string.connection_cancel_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        NavUtils.navigateUpFromSameTask(DessertActivity.this);
                    }
                })
                .setPositiveButton(R.string.connection_ok_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                    }
                })
                .create()
                .show();
    }

}
