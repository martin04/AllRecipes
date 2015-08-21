package allrecipes.mpip.wbs.com.allrecipesmpip.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.contentProvider.ShoppingCartContentProvider;
import allrecipes.mpip.wbs.com.allrecipesmpip.fragments.AddShoppingCartItemFragment;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.ShoppingCartItem;
import allrecipes.mpip.wbs.com.allrecipesmpip.viewholder.ShoppingCartViewHolder;

/**
 * Created by Jovan on 3/14/2015.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartViewHolder>{

    private LayoutInflater mInflater;
    private List<ShoppingCartItem> items;
    private ViewGroup viewGroup;
    public Context ctx;
    public Cursor mCursor;
    public int mRowIDColumn;

    public ShoppingCartAdapter(Context ctx){
        mInflater = LayoutInflater.from(ctx);
        items = new ArrayList<ShoppingCartItem>();
        this.ctx = ctx;
    }

    public ShoppingCartAdapter(Context ctx, Cursor c){
        mInflater = LayoutInflater.from(ctx);
        items = new ArrayList<ShoppingCartItem>();
        this.ctx = ctx;
        mCursor = c;
        mRowIDColumn = c.getColumnIndexOrThrow("_id");
    }

    @Override
    public ShoppingCartViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.shopping_cart_item, viewGroup, false);

        this.viewGroup = viewGroup;

        return new ShoppingCartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShoppingCartViewHolder shoppingCartViewHolder, int i) {
        final ShoppingCartItem item = items.get(i);

        if (item.getDone() == 1) {
            shoppingCartViewHolder.cbDone.setChecked(true);
            shoppingCartViewHolder.tvName.setPaintFlags(shoppingCartViewHolder.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            shoppingCartViewHolder.tvQuantity.setPaintFlags(shoppingCartViewHolder.tvQuantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            shoppingCartViewHolder.btnDelete.setVisibility(View.VISIBLE);
        }
        else {
            shoppingCartViewHolder.cbDone.setChecked(false);
            shoppingCartViewHolder.tvName.setPaintFlags(0);
            shoppingCartViewHolder.tvQuantity.setPaintFlags(0);
            shoppingCartViewHolder.btnDelete.setVisibility(View.INVISIBLE);

        }
        shoppingCartViewHolder.tvName.setText(item.getName());
        shoppingCartViewHolder.tvQuantity.setText(item.getQuantity());

        final int position = i;
        //System.out.println("Position: "+position);


        shoppingCartViewHolder.cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ContentResolver cr = ((Activity) v.getContext()).getContentResolver();
                if (cb.isChecked()){
                    items.get(position).setDone(1);
                }
                else {
                    items.get(position).setDone(0);
                }
                if(items.get(position).getDone() == 1) {
                    shoppingCartViewHolder.tvName.setPaintFlags(shoppingCartViewHolder.tvName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    shoppingCartViewHolder.tvQuantity.setPaintFlags(shoppingCartViewHolder.tvQuantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    shoppingCartViewHolder.btnDelete.setVisibility(View.VISIBLE);
                    update(items.get(position), cr);
                }
                else {
                    shoppingCartViewHolder.tvName.setPaintFlags(0);
                    shoppingCartViewHolder.tvQuantity.setPaintFlags(0);
                    shoppingCartViewHolder.btnDelete.setVisibility(View.INVISIBLE);
                    update(items.get(position), cr);
                }
            }
        });



        shoppingCartViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = v.getContext().getContentResolver();

                Uri uri = ContentUris.withAppendedId(ShoppingCartContentProvider.CONTENT_URI, getItemId(position));
                cr.delete(uri, null, null);

                remove(item);
            }
        });

        shoppingCartViewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", getItemId(position));
                AddShoppingCartItemFragment fragment = new AddShoppingCartItemFragment();
                FragmentManager fm = ((Activity) v.getContext()).getFragmentManager();
                fragment.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.shopping_cart_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        shoppingCartViewHolder.tvQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", getItemId(position));
                AddShoppingCartItemFragment fragment = new AddShoppingCartItemFragment();
                FragmentManager fm = ((Activity) v.getContext()).getFragmentManager();
                fragment.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.shopping_cart_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public long getItemId(int position) {
        if (mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                System.out.println(mCursor.getLong(mRowIDColumn));
                return mCursor.getLong(mRowIDColumn);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public void setmCursor(Cursor mCursor) {
        this.mCursor = mCursor;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public List<ShoppingCartItem> getItems(){
        return items;
    }


    public void addItems(List<ShoppingCartItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItem(ShoppingCartItem item){
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void clear(){
        this.items.clear();
        notifyDataSetChanged();
    }

    public void remove(ShoppingCartItem item){
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void update(ShoppingCartItem item, ContentResolver cr) {
        int position = items.indexOf(item);
        Uri uri = ContentUris.withAppendedId(ShoppingCartContentProvider.CONTENT_URI, getItemId(position));
        ContentValues values = new ContentValues();
        values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_NAME, item.getName());
        values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_QUANTITY, item.getQuantity());
        values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_DONE, item.getDone());
        cr.update(uri, values, null, null);
    }

}
