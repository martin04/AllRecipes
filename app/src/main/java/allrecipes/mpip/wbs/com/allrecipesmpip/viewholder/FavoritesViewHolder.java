package allrecipes.mpip.wbs.com.allrecipesmpip.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import allrecipes.mpip.wbs.com.allrecipesmpip.IFavoritesUpdate;
import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.RecipeDetailsActivity;

/**
 * Created by Martin on 8/8/2015.
 */
public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView vImage;
    public TextView vTitle;
    public TextView vDescription;
    public RatingBar vRating;
    public ImageButton vRemoveFromList;
    public Button vDetails;

    private IFavoritesUpdate listener;

    public FavoritesViewHolder(View v, IFavoritesUpdate listener) {
        super(v);
        vImage = (ImageView) v.findViewById(R.id.image);
        vTitle = (TextView) v.findViewById(R.id.title);
        vDescription = (TextView) v.findViewById(R.id.description);
        vRating = (RatingBar) v.findViewById(R.id.rating);
        vRemoveFromList = (ImageButton) v.findViewById(R.id.btnRemove);
        vRemoveFromList.setOnClickListener(this);
        vDetails = (Button) v.findViewById(R.id.btnDetails);
        vDetails.setOnClickListener(this);

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        Context ctx = v.getContext();
        int position = getAdapterPosition();

        if (v instanceof ImageButton) {
            listener.deleteFavorite(ctx, vTitle.getText().toString(), position);
        } else if (v instanceof Button) {
            Intent i = new Intent(ctx, RecipeDetailsActivity.class);
            i.putExtra("name", vTitle.getText().toString());
            ctx.startActivity(i);
        }
    }


}
