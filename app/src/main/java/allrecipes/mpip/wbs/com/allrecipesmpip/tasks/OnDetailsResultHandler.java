package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.models.Ingredient;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.Nutrient;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;

/**
 * Created by Martin on 8/20/2015.
 */
public interface OnDetailsResultHandler {
    void onRecipeResult(RecipeLOD r);
    void onIngredientResult(List<Ingredient> ingredients);
    void onNutrientResult(List<Nutrient> nutrients);
    void onDetailsError();
}
