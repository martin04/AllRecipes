package allrecipes.mpip.wbs.com.allrecipesmpip.viewholder;

import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.contentProvider.ShoppingCartContentProvider;

/**
 * Created by Martin on 8/15/2015.
 */
public class IngredientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageButton vAddToCart;
    public TextView vIngredName;
    public TextView vIngredQty;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        vAddToCart = (ImageButton) itemView.findViewById(R.id.btnAddToCart);
        vAddToCart.setOnClickListener(this);
        vIngredName = (TextView) itemView.findViewById(R.id.txtIngredName);
        vIngredQty = (TextView) itemView.findViewById(R.id.txtIngredQty);
    }

    @Override
    public void onClick(View v) {
        Context ctx = v.getContext();
        int position = getAdapterPosition();

        ContentResolver cr = ctx.getContentResolver();
        ContentValues values = new ContentValues();

        values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_NAME, vIngredName.getText().toString());
        values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_QUANTITY, vIngredQty.getText().toString());
        values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_DONE, 0);

        cr.insert(ShoppingCartContentProvider.CONTENT_URI, values);
        Toast.makeText(ctx, vIngredName.getText().toString() + " was added to cart", Toast.LENGTH_LONG).show();

    }

}
