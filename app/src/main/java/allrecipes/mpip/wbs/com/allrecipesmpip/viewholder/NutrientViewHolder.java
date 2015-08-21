package allrecipes.mpip.wbs.com.allrecipesmpip.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.NutriAbstractTask;
import allrecipes.mpip.wbs.com.allrecipesmpip.tasks.onNutriAbstractGet;

/**
 * Created by Martin on 8/15/2015.
 */
public class NutrientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageButton vInfo;
    public TextView vNutriName;
    public TextView vNutriDose;
    private onNutriAbstractGet listener;

    public NutrientViewHolder(View itemView, onNutriAbstractGet listener) {
        super(itemView);

        vInfo = (ImageButton) itemView.findViewById(R.id.btnInfo);
        vInfo.setOnClickListener(this);
        vNutriName = (TextView) itemView.findViewById(R.id.txtNutriName);
        vNutriDose = (TextView) itemView.findViewById(R.id.txtNutriDose);

        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        /*Context ctx = v.getContext();
        int position = getAdapterPosition();*/

        switch (vNutriName.getText().toString()) {
            case "Total Fat":
                new NutriAbstractTask(listener).execute("Fat");
                break;
            case "Vitamin A":
                new NutriAbstractTask(listener).execute("Vitamin_A");
                break;
            case "Vitamin C":
                new NutriAbstractTask(listener).execute("Vitamin_C");
                break;
            case "Calcium":
                new NutriAbstractTask(listener).execute("Calcium");
                break;
            case "Iron":
                new NutriAbstractTask(listener).execute("Iron");
                break;
            case "Thiamin":
                new NutriAbstractTask(listener).execute("Thiamine");
                break;
            case "Niacin":
                new NutriAbstractTask(listener).execute("Niacin");
                break;
            case "Vitamin B6":
                new NutriAbstractTask(listener).execute("Vitamin_B6");
                break;
            case "Magnesium":
                new NutriAbstractTask(listener).execute("Magnesium");
                break;
            case "Folate":
                new NutriAbstractTask(listener).execute("Folic_acid");
                break;
            case "Saturated Fat":
                new NutriAbstractTask(listener).execute("Saturated_fat");
                break;
            case "Cholesterol":
                new NutriAbstractTask(listener).execute("Cholesterol");
                break;
            case "Sodium":
                new NutriAbstractTask(listener).execute("Sodium");
                break;
            case "Potassium":
                new NutriAbstractTask(listener).execute("Potassium");
                break;
            case "Total Carbohydrates":
                new NutriAbstractTask(listener).execute("Carbohydrate");
                break;
            case "Dietary Fiber":
                new NutriAbstractTask(listener).execute("Dietary_fiber");
                break;
            case "Protein":
                new NutriAbstractTask(listener).execute("Protein");
                break;
            case "Sugars":
                new NutriAbstractTask(listener).execute("Sugar");
                break;
            default:
                break;
        }
    }
}
