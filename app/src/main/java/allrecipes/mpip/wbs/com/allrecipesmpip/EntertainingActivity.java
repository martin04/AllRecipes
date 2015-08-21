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

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.RecipeAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.Callback;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.DownloadXML;

/**
 * Created by Martin on 30-Jun-15.
 */
public class EntertainingActivity extends AppCompatActivity {

    private RecyclerView entertain;
    private RecipeAdapter adapter;
    private LinearLayoutManager manager;
    private List<RecipeInfo> data;
    private String url = "http://rss.allrecipes.com/daily.aspx?hubID=85";
    private ConnectivityManager cm;
    private NetworkInfo ni;
    private boolean dataFilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entertaining_activity);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        entertain = (RecyclerView) findViewById(R.id.lstEntertaining);
        manager = new LinearLayoutManager(this);
        entertain.setLayoutManager(manager);

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
                    Toast.makeText(ctx, "entertain loaded", Toast.LENGTH_SHORT).show();
                    adapter = new RecipeAdapter(result);
                    entertain.setAdapter(adapter);
                }
            }, ctx).execute(url);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!dataFilled) {
            dataFilled = true;
            final Context ctx = this;
            new DownloadXML(new Callback() {
                @Override
                public void onResult(List<RecipeInfo> result) {
                    Toast.makeText(ctx, "entertain loaded", Toast.LENGTH_SHORT).show();
                    adapter = new RecipeAdapter(result);
                    entertain.setAdapter(adapter);
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

    private void connectionDialog() {
        new AlertDialog.Builder(EntertainingActivity.this)
                .setTitle(R.string.connection_title)
                .setMessage(R.string.connection_message)
                .setNegativeButton(R.string.connection_cancel_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        NavUtils.navigateUpFromSameTask(EntertainingActivity.this);
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
