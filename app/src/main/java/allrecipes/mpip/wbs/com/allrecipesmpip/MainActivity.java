package allrecipes.mpip.wbs.com.allrecipesmpip;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.CategoriesAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.SearchTask;


public class MainActivity extends AppCompatActivity{

    private RecyclerView categories;
    private CategoriesAdapter adapter;
    private StaggeredGridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categories = (RecyclerView) findViewById(R.id.lstCategories);
        categories.setHasFixedSize(true);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        categories.setLayoutManager(manager);
        categories.setItemAnimator(new DefaultItemAnimator());
        adapter = new CategoriesAdapter();
        categories.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager sm = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView sv = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


}
