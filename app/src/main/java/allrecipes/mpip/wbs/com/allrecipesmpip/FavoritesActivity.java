package allrecipes.mpip.wbs.com.allrecipesmpip;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.FavoritesAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.FillFavorites;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.OnTaskCompleted;

/**
 * Created by Martin on 8/8/2015.
 */
public class FavoritesActivity extends AppCompatActivity implements OnTaskCompleted {

    private RecyclerView favorites;
    private FavoritesAdapter adapter;
    private LinearLayoutManager manager;
    private ProgressDialog pdLoading;
    private TextView noResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_activity);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noResult=(TextView)findViewById(R.id.txtNoFav);

        favorites = (RecyclerView) findViewById(R.id.lstFavorites);
        favorites.setHasFixedSize(true);
        manager = new LinearLayoutManager(FavoritesActivity.this);
        favorites.setLayoutManager(manager);

       new FillFavorites(this, this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_food, menu);
        menu.findItem(R.id.menu_favorites).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskStarted() {
        pdLoading = new ProgressDialog(this);
        pdLoading.setMessage("Loading...");
        pdLoading.show();
    }

    @Override
    public void onTaskCompleted(List<RecipeInfo> favs) {
        adapter = new FavoritesAdapter(favs);
        favorites.setAdapter(adapter);
        pdLoading.cancel();
    }

    @Override
    public void onTaskError() {
        Toast.makeText(this, "Oops there's sth wrong!", Toast.LENGTH_SHORT).show();
        pdLoading.cancel();
    }

    @Override
    public void onTaskEmptyList() {
        favorites.setVisibility(View.GONE);
        noResult.setVisibility(View.VISIBLE);
        pdLoading.cancel();
    }
}
