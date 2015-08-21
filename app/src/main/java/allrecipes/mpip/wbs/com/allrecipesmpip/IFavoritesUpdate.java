package allrecipes.mpip.wbs.com.allrecipesmpip;

import android.content.Context;

/**
 * Created by Martin on 8/9/2015.
 */
public interface IFavoritesUpdate {
    void deleteFavorite(Context ctx, String title, int pos);
}
