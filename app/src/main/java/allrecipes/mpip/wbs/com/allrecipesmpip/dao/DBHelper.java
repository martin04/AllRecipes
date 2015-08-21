package allrecipes.mpip.wbs.com.allrecipesmpip.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Martin on 8/8/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorites.db";
    public static final int DATABASE_VERSION = 1;

    //table favorites
    public static final String TABLE_FAV = "favorites";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "img_url";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_FAV = "favorite";

    static final String createFavorites = String.format("create table %s(%s integer primary key autoincrement, " +
                    "%s text not null, %s text not null, %s text not null, %s real not null, %s integer not null);", TABLE_FAV, COLUMN_ID,
            COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_IMAGE, COLUMN_RATING, COLUMN_FAV);

    public DBHelper(Context ctx) {
        super(ctx, String.format(DATABASE_NAME), null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if(!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createFavorites);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_FAV));
        onCreate(db);
    }
}
