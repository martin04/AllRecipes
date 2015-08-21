package allrecipes.mpip.wbs.com.allrecipesmpip.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.RecipeDetailsActivity;

/**
 * Created by Martin on 8/20/2015.
 */
public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView vId;
    public TextView vName;

    public SearchViewHolder(View itemView) {
        super(itemView);
        vId=(TextView)itemView.findViewById(R.id.txtID);
        vName=(TextView)itemView.findViewById(R.id.txtName);
        vName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Context ctx=v.getContext();
        Intent i=new Intent(ctx,RecipeDetailsActivity.class);
        i.putExtra("name",vName.getText().toString());
        ctx.startActivity(i);
    }
}
