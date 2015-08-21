package allrecipes.mpip.wbs.com.allrecipesmpip.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.viewholder.CategoryViewHolder;

/**
 * Created by Martin on 30-Jun-15.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private String[] categories = {"More Dinner", "More Healthy", "More Entertaining", " More Dessert", "Shopping cart", "Favorites"};
    private int[] pictures = {R.drawable.dinner2, R.drawable.healthy1, R.drawable.enter3, R.drawable.dessert2, R.drawable.shopping_cart, R.drawable.favorites};

    @Override
    public int getItemCount() {
        return categories.length;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        String category = categories[position];
        holder.txtCategory.setText(category);
        holder.imgCategory.setImageResource(pictures[position]);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_item_layout, parent, false);
        return new CategoryViewHolder(view);
    }


}
