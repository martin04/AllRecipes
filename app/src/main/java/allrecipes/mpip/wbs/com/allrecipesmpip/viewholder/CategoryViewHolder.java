package allrecipes.mpip.wbs.com.allrecipesmpip.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import allrecipes.mpip.wbs.com.allrecipesmpip.DessertActivity;
import allrecipes.mpip.wbs.com.allrecipesmpip.DinnerActivity;
import allrecipes.mpip.wbs.com.allrecipesmpip.EntertainingActivity;
import allrecipes.mpip.wbs.com.allrecipesmpip.FavoritesActivity;
import allrecipes.mpip.wbs.com.allrecipesmpip.HealthyActivity;
import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.ShoppingCartActivity;

/**
 * Created by Martin on 30-Jun-15.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imgCategory;
    public TextView txtCategory;

    public CategoryViewHolder(View itemView) {
        super(itemView);

        imgCategory = (ImageView) itemView.findViewById(R.id.imgCategory);
        txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);

        txtCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context ctx = itemView.getContext();
        int position = getPosition();
        switch (position) {
            case 0:
                ctx.startActivity(new Intent(ctx,DinnerActivity.class));
                break;
            case 1:
                ctx.startActivity(new Intent(ctx, HealthyActivity.class));
                break;
            case 2:
                ctx.startActivity(new Intent(ctx, EntertainingActivity.class));
                break;
            case 3:
                ctx.startActivity(new Intent(ctx, DessertActivity.class));
                break;
            case 4:
                ctx.startActivity(new Intent(ctx, ShoppingCartActivity.class));
                break;
            case 5:
                ctx.startActivity(new Intent(ctx, FavoritesActivity.class));
                break;
            default:
                Toast.makeText(ctx, "Oops there's something wrong! Please try again.", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
