package allrecipes.mpip.wbs.com.allrecipesmpip;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.SearchAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.SearchTask;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.onSearchResultHandler;

/**
 * Created by Martin on 30-Jun-15.
 */
public class SearchResultActivity extends AppCompatActivity implements onSearchResultHandler {

    private ProgressDialog pDialog;
    private RecyclerView rec;
    private LinearLayoutManager manager;
    private SearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        assert getSupportActionBar()!=null;
        getSupportActionBar().setTitle(R.string.search_result);

        rec=(RecyclerView)findViewById(R.id.lstResult);
        rec.setHasFixedSize(true);
        manager=new LinearLayoutManager(this);
        rec.setLayoutManager(manager);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);

        SearchManager sm=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView sv=(SearchView)menu.findItem(R.id.menu_search).getActionView();
        sv.setSearchableInfo(sm.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query=intent.getStringExtra(SearchManager.QUERY);
            SearchTask search=new SearchTask(this,this);
            search.execute(query);
        }
    }

    @Override
    public void onSearchStart() {
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
    }

    @Override
    public void onSearchResult(List<RecipeLOD> recipes) {
        adapter=new SearchAdapter(recipes);
        rec.setAdapter(adapter);
        pDialog.cancel();
    }

    @Override
    public void onSearchError(String err) {
        Toast.makeText(this,err,Toast.LENGTH_LONG).show();
        pDialog.cancel();
    }
}
