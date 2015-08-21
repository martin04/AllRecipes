package allrecipes.mpip.wbs.com.allrecipesmpip.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.IFavorite;
import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;
import allrecipes.mpip.wbs.com.allrecipesmpip.viewholder.RecipeViewHolder;

/**
 * Created by Ace on 7/2/2015.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> implements IFavorite {

    private List<RecipeInfo> recipeList;
    private ViewGroup viewGroup;
    private FavoritesDBAdapter fav;

    public RecipeAdapter(List<RecipeInfo> contactList) {
        this.recipeList = contactList;
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder recipeViewHolder, int i) {
        RecipeInfo ri = recipeList.get(i);

        Picasso.with(viewGroup.getContext()).load(ri.getImageUrl()).into(recipeViewHolder.vImage);
        recipeViewHolder.vTitle.setText(ri.getTitle());
        recipeViewHolder.vDescription.setText(ri.getDescription());
        recipeViewHolder.vRating.setRating((float) ri.getRating());

        //recipeViewHolder.vFavourite.setChecked(ri.isFavourite());
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.recipe_item_layout, viewGroup, false);

        this.viewGroup = viewGroup;

        return new RecipeViewHolder(itemView, this);

    }

    @Override
    public void setFav(Context ctx, int pos, String title) {
        fav = new FavoritesDBAdapter(ctx);
        RecipeInfo recipe = recipeList.get(pos);
        try {
            fav.open();
            RecipeInfo r = fav.getRecipe(title);
            fav.close();
            if (r == null) {
                fav.open();
                fav.insert(recipe);
                fav.close();
            } else {
                Toast.makeText(ctx, "Already in favorites", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void rmvFav(Context ctx, int pos,String title) {
        fav=new FavoritesDBAdapter(ctx);
        try {
            fav.open();
            fav.deleteByTitle(title);
            fav.close();
            notifyDataSetChanged();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
