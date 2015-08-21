package allrecipes.mpip.wbs.com.allrecipesmpip.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.Ingredient;
import allrecipes.mpip.wbs.com.allrecipesmpip.viewholder.IngredientViewHolder;

/**
 * Created by Martin on 8/15/2015.
 */
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientViewHolder> {

    private List<Ingredient> ingredients;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_item_layout, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ing=ingredients.get(position);
        holder.vIngredName.setText(ing.getName());
        holder.vIngredQty.setText(ing.getQuantity());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
