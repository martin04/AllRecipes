package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;

/**
 * Created by Ace on 7/2/2015.
 */
public interface Callback {
    void onResult(List<RecipeInfo> result);
}
