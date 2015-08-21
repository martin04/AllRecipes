package allrecipes.mpip.wbs.com.allrecipesmpip.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;
import allrecipes.mpip.wbs.com.allrecipesmpip.viewholder.SearchViewHolder;

/**
 * Created by Martin on 8/20/2015.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private List<RecipeLOD> recipes;

    public SearchAdapter(List<RecipeLOD> recipes) {
        this.recipes = recipes;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_layout,parent,false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        RecipeLOD r=recipes.get(position);
        holder.vId.setText(String.format("%d",r.getId()));
        holder.vName.setText(r.getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
