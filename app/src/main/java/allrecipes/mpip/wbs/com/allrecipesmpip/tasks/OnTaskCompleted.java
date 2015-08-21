package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;

/**
 * Created by Martin on 8/8/2015.
 */
public interface OnTaskCompleted {
    void onTaskStarted();
    void onTaskCompleted(List<RecipeInfo> drugs);
    void onTaskError();
    void onTaskEmptyList();
}
