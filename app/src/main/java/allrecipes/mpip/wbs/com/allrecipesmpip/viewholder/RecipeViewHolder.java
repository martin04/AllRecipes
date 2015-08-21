package allrecipes.mpip.wbs.com.allrecipesmpip.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import allrecipes.mpip.wbs.com.allrecipesmpip.IFavorite;
import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.RecipeDetailsActivity;

/**
 * Created by Ace on 7/2/2015.
 */
public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public ImageView vImage;
    public TextView vTitle;
    public TextView vDescription;
    public RatingBar vRating;
    public ToggleButton vFavourite;
    public Button vDetails;

    private IFavorite listener;

    public RecipeViewHolder(View v, IFavorite listener) {
        super(v);
        vImage = (ImageView) v.findViewById(R.id.image);
        vTitle = (TextView) v.findViewById(R.id.title);
        vDescription = (TextView) v.findViewById(R.id.description);
        vRating = (RatingBar) v.findViewById(R.id.rating);
        vFavourite = (ToggleButton) v.findViewById(R.id.tbtnFavourite);
        vFavourite.setOnCheckedChangeListener(this);
        vDetails = (Button) v.findViewById(R.id.btnDetails);
        vDetails.setOnClickListener(this);

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        Context ctx = v.getContext();
        int position = getAdapterPosition();

        if (v instanceof Button) {
            Intent i = new Intent(ctx, RecipeDetailsActivity.class);
            i.putExtra("name", vTitle.getText().toString());
            ctx.startActivity(i);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Context ctx = buttonView.getContext();
        if (isChecked) {
            listener.setFav(ctx, getAdapterPosition(), vTitle.getText().toString());
        } else {
            listener.rmvFav(ctx, getAdapterPosition(), vTitle.getText().toString());
        }
    }
}
