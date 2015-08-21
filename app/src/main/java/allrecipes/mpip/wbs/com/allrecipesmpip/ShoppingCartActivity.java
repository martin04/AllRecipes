package allrecipes.mpip.wbs.com.allrecipesmpip;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.ShoppingCartAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.contentProvider.ShoppingCartContentProvider;
import allrecipes.mpip.wbs.com.allrecipesmpip.fragments.AddShoppingCartItemFragment;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.ShoppingCartItem;

/**
 * Created by Mac on 7/1/15.
 */
public class ShoppingCartActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView cart_items;
    private ShoppingCartAdapter adapter;
    private LinearLayoutManager manager;
    private ImageView btn_delete;

    private static final String KEY_SHOPPING_CART_ITEMS = "SHOPPING_CART_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shopping_cart_fragment);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cart_items = (RecyclerView) findViewById(R.id.shopping_cart_list);
        manager = new LinearLayoutManager(this);
        cart_items.setLayoutManager(manager);
        cart_items.setItemAnimator(new DefaultItemAnimator());
        adapter = new ShoppingCartAdapter(this);
        cart_items.setAdapter(adapter);


        final ActionButton btn = (ActionButton) findViewById(R.id.fab);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddShoppingCartItemFragment fragment = new AddShoppingCartItemFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.shopping_cart_layout, fragment).addToBackStack(null).commit();
//                fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//                    @Override
//                    public void onBackStackChanged() {
//
//                    }
//                });
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<ShoppingCartItem> all = savedInstanceState.getParcelableArrayList(KEY_SHOPPING_CART_ITEMS);
        adapter.addItems(all);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<ShoppingCartItem> all = (ArrayList<ShoppingCartItem>) adapter.getItems();
        outState.putParcelableArrayList(KEY_SHOPPING_CART_ITEMS, all);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {ShoppingCartContentProvider.DBOpenHelper.COLUMN_ID,
                ShoppingCartContentProvider.DBOpenHelper.COLUMN_NAME, ShoppingCartContentProvider.DBOpenHelper.COLUMN_QUANTITY,
                ShoppingCartContentProvider.DBOpenHelper.COLUMN_DONE};

        CursorLoader cursorLoader = new CursorLoader(this, ShoppingCartContentProvider.CONTENT_URI, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        System.out.println("DATA COUNT LOADED: " + data.getCount());
        if (data.getCount() > 0) {
            List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
            while (data.moveToNext()) {
                ShoppingCartItem item = new ShoppingCartItem(data.getLong(0), data.getString(1), data.getString(2), data.getInt(3));
                items.add(item);
            }
            //Collections.reverse(items);
            adapter.addItems(items);
            adapter.setmCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.addItems(new ArrayList<ShoppingCartItem>());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.menu_search).setVisible(false);
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
}
