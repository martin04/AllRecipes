package allrecipes.mpip.wbs.com.allrecipesmpip.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import allrecipes.mpip.wbs.com.allrecipesmpip.dao.DBHelper;
import allrecipes.mpip.wbs.com.allrecipesmpip.models.RecipeInfo;

/**
 * Created by Martin on 8/8/2015.
 */
public class FavoritesDBAdapter {

    private DBHelper helper;
    private SQLiteDatabase db;

    private String[] columns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_TITLE, DBHelper.COLUMN_DESCRIPTION, DBHelper.COLUMN_IMAGE, DBHelper.COLUMN_RATING, DBHelper.COLUMN_FAV};

    public FavoritesDBAdapter(Context ctx) {
        helper = new DBHelper(ctx);
    }

    public void open() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        db.close();
        helper.close();
    }

    public boolean insert(RecipeInfo ri) {
        if (ri.getId() != null) {
            return update(ri);
        }

        ContentValues cv = new ContentValues();
        if (ri.getId() != null) {
            cv.put(DBHelper.COLUMN_ID, ri.getId());
        }
        cv.put(DBHelper.COLUMN_TITLE, ri.getTitle());
        cv.put(DBHelper.COLUMN_DESCRIPTION, ri.getDescription());
        cv.put(DBHelper.COLUMN_IMAGE, ri.getImageUrl());
        cv.put(DBHelper.COLUMN_RATING, ri.getRating());
        cv.put(DBHelper.COLUMN_FAV, ri.isFavourite() ? 1 : 0);

        long rowID = db.insert(DBHelper.TABLE_FAV, null, cv);
        if (rowID > 0) {
            ri.setId(rowID);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(RecipeInfo ri) {
        ContentValues cv = new ContentValues();
        if (ri.getId() != null) {
            cv.put(DBHelper.COLUMN_ID, ri.getId());
        }
        cv.put(DBHelper.COLUMN_TITLE, ri.getTitle());
        cv.put(DBHelper.COLUMN_DESCRIPTION, ri.getDescription());
        cv.put(DBHelper.COLUMN_IMAGE, ri.getImageUrl());
        cv.put(DBHelper.COLUMN_RATING, ri.getRating());
        cv.put(DBHelper.COLUMN_FAV, ri.isFavourite() ? 1 : 0);

        return db.update(DBHelper.TABLE_FAV, cv, DBHelper.COLUMN_ID + "=" + ri.getId(), null) > 0;
    }

    public boolean delete(long rowID) {
        return db.delete(DBHelper.TABLE_FAV, DBHelper.COLUMN_ID + "=" + rowID, null) > 0;
    }

    public void deleteByTitle(String title) {
        db.execSQL("delete from " + DBHelper.TABLE_FAV + " where title=\"" + title + "\"");
    }

    public List<RecipeInfo> getAllItems() {
        List<RecipeInfo> drugs = new ArrayList<RecipeInfo>();

        Cursor c = db.rawQuery("select * from " + DBHelper.TABLE_FAV, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                RecipeInfo d = new RecipeInfo();
                d.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_ID)));
                d.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TITLE)));
                d.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));
                d.setImageUrl(c.getString(c.getColumnIndex(DBHelper.COLUMN_IMAGE)));
                d.setRating(c.getDouble(c.getColumnIndex(DBHelper.COLUMN_RATING)));
                d.setFavourite(c.getInt(c.getColumnIndex(DBHelper.COLUMN_FAV)) == 1);
                drugs.add(d);
                c.moveToNext();
            }
        }
        c.close();
        return drugs;
    }

    public RecipeInfo getRecipe(String title) {
        RecipeInfo d = new RecipeInfo();
        Cursor c = db.query(DBHelper.TABLE_FAV, columns, DBHelper.COLUMN_TITLE + "='" + title + "'", null, null, null, null);
        try {
            if (c.moveToFirst()) {
                d.setId(c.getLong(c.getColumnIndex(DBHelper.COLUMN_ID)));
                d.setTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TITLE)));
                d.setDescription(c.getString(c.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)));
                d.setImageUrl(c.getString(c.getColumnIndex(DBHelper.COLUMN_IMAGE)));
                d.setRating(c.getDouble(c.getColumnIndex(DBHelper.COLUMN_RATING)));
                d.setFavourite(c.getInt(c.getColumnIndex(DBHelper.COLUMN_FAV)) == 1);
                return d;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            c.close();
        }
    }


}
