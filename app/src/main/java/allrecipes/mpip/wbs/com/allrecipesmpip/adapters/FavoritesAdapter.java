package allrecipes.mpip.wbs.com.allrecipesmpip.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.IFavoritesUpdate;
import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;
import allrecipes.mpip.wbs.com.allrecipesmpip.viewholder.FavoritesViewHolder;

/**
 * Created by Martin on 8/8/2015.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> implements IFavoritesUpdate {
    private List<RecipeInfo> recipeList;
    private ViewGroup viewGroup;

    private FavoritesDBAdapter fav;

    public FavoritesAdapter(List<RecipeInfo> contactList) {
        this.recipeList = contactList;
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder recipeViewHolder, int i) {
        RecipeInfo ri = recipeList.get(i);

        Picasso.with(viewGroup.getContext()).load(ri.getImageUrl()).into(recipeViewHolder.vImage);
        recipeViewHolder.vTitle.setText(ri.getTitle());
        recipeViewHolder.vDescription.setText(ri.getDescription());
        recipeViewHolder.vRating.setRating((float) ri.getRating());
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fav_recipe_item_layout, viewGroup, false);

        this.viewGroup = viewGroup;

        return new FavoritesViewHolder(itemView, this);

    }

    @Override
    public void deleteFavorite(Context ctx, String title,int pos) {
        fav = new FavoritesDBAdapter(ctx);
        try {
            fav.open();
            fav.deleteByTitle(title);
            fav.close();
            recipeList.remove(pos);
            notifyItemRemoved(pos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
