package allrecipes.mpip.wbs.com.allrecipesmpip;

import android.content.Context;

/**
 * Created by Martin on 8/8/2015.
 */
public interface IFavorite {
    void setFav(Context ctx, int pos, String title);
    void rmvFav(Context ctx, int pos, String title);
}
