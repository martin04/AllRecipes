package allrecipes.mpip.wbs.com.allrecipesmpip.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.Nutrient;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.NutriAbstractTask;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.onNutriAbstractGet;
import allrecipes.mpip.wbs.com.allrecipesmpip.viewholder.NutrientViewHolder;

/**
 * Created by Martin on 8/15/2015.
 */
public class NutrientsAdapter extends RecyclerView.Adapter<NutrientViewHolder> implements onNutriAbstractGet {

    private List<Nutrient> nutrients;
    private Context ctx;

    public NutrientsAdapter(List<Nutrient> nutrients, Context ctx) {
        this.nutrients = nutrients;
        this.ctx = ctx;
    }

    @Override
    public NutrientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nutrient_item_layout, parent, false);
        return new NutrientViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(NutrientViewHolder holder, int position) {
        Nutrient nutri = nutrients.get(position);
        holder.vNutriName.setText(nutri.getName());
        if (nutri.getName().equalsIgnoreCase("Sodium")
                || nutri.getName().equalsIgnoreCase("Potassium")
                || nutri.getName().equalsIgnoreCase("Cholesterol")) {

            holder.vNutriDose.setText(nutri.getDose() + " mg");

        } else if (nutri.getName().equalsIgnoreCase("Total fat")
                || nutri.getName().equalsIgnoreCase("Saturated fat")
                || nutri.getName().equalsIgnoreCase("Total Carbohydrates")
                || nutri.getName().equalsIgnoreCase("Dietary Fiber")
                || nutri.getName().equalsIgnoreCase("Protein")
                || nutri.getName().equalsIgnoreCase("Sugars")) {

            holder.vNutriDose.setText(nutri.getDose() + " mg");
        } else {
            holder.vNutriDose.setText(nutri.getDose() + " %");
        }

    }

    @Override
    public int getItemCount() {
        return nutrients.size();
    }

    @Override
    public void onNutriAbstractResult(String name) {
        new AlertDialog.Builder(ctx)
                .setTitle("Abstract")
                .setMessage(name)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}
