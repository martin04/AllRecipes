package allrecipes.mpip.wbs.com.allrecipesmpip.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.adapters.FavoritesDBAdapter;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;

/**
 * Created by Martin on 8/8/2015.
 */
public class FillFavorites extends AsyncTask<Void, Void, List<RecipeInfo>> {
    private FavoritesDBAdapter fav;
    private Context ctx;
    private OnTaskCompleted listener;

    public FillFavorites(Context ctx, OnTaskCompleted listener) {
        this.ctx = ctx;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        fav = new FavoritesDBAdapter(ctx);
        listener.onTaskStarted();
    }

    @Override
    protected List<RecipeInfo> doInBackground(Void... params) {
        if (checkDB()) {
            try {
                fav.open();
                List<RecipeInfo> list = fav.getAllItems();
                fav.close();
                return list;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<RecipeInfo> recipeInfos) {
        if (recipeInfos.size() == 0) {
            listener.onTaskEmptyList();
        } else if (recipeInfos.size() != 0) {
            listener.onTaskCompleted(recipeInfos);
        } else {
            listener.onTaskError();
        }
    }

    private boolean checkDB() {
        File db = ctx.getApplicationContext().getDatabasePath("favorites.db");
        if (!db.exists()) {
            return false;
        } else {
            return true;
        }
    }
}
