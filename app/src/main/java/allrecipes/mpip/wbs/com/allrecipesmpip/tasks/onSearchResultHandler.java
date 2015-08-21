package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;

import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeLOD;

/**
 * Created by Martin on 8/20/2015.
 */
public interface onSearchResultHandler {
    void onSearchStart();
    void onSearchResult(List<RecipeLOD> list);
    void onSearchError(String err);

}
