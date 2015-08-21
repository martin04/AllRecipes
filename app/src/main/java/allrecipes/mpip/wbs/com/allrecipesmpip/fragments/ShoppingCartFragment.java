package allrecipes.mpip.wbs.com.allrecipesmpip.fragments;

import android.app.Fragment;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.ShoppingCartAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.contentProvider.ShoppingCartContentProvider;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.ShoppingCartItem;

/**
 * Created by Jovan on 3/14/2015.
 */
public class ShoppingCartFragment extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    //private ActionButton actionButton;
    private RecyclerView scRecyclerView;
    ShoppingCartAdapter scAdapter;
    CheckBox cbDone;

    public ShoppingCartFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shopping_cart_fragment, container, false);

        scRecyclerView = (RecyclerView) rootView.findViewById(R.id.shopping_cart_list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        scRecyclerView.setLayoutManager(llm);

        scAdapter = new ShoppingCartAdapter(getActivity().getApplicationContext());
        //scAdapter.setItems(getItems());

        scRecyclerView.setAdapter(scAdapter);

        List<ShoppingCartItem> items = scAdapter.getItems();
        for(int i = 0; i < items.size(); i++) {
            ShoppingCartItem item = items.get(i);

        }

        return rootView;

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {ShoppingCartContentProvider.DBOpenHelper.COLUMN_ID,
                ShoppingCartContentProvider.DBOpenHelper.COLUMN_NAME, ShoppingCartContentProvider.DBOpenHelper.COLUMN_QUANTITY,
                ShoppingCartContentProvider.DBOpenHelper.COLUMN_DONE};

        CursorLoader cursorLoader = new CursorLoader(getActivity().getApplicationContext(), ShoppingCartContentProvider.CONTENT_URI, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        System.out.println("DATA COUNT LOADED: " +data.getCount());
        if(data.getCount() > 0){
            List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
            while (data.moveToNext()){
                ShoppingCartItem item = new ShoppingCartItem(data.getLong(0), data.getString(1), data.getString(2), data.getInt(3));
                items.add(item);
            }
            Collections.reverse(items);
            scAdapter.addItems(items);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        scAdapter.addItems(new ArrayList<ShoppingCartItem>());
    }
}
