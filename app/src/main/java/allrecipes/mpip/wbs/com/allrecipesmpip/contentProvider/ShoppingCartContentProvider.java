package allrecipes.mpip.wbs.com.allrecipesmpip.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Jovan on 3/14/2015.
 */
public class ShoppingCartContentProvider extends ContentProvider {

    //Potrebni promenlicvi
    private static final String AUTHORITY = "allrecipes.wbs.com.allrecipes.contentProvider";
    private static final String BASE_PATH = "items";
    private static final int ITEMS = 10;
    private static final int ITEM_ID = 20;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final Uri CONTENT_URI_ID = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH + "/#" + ITEM_ID);

    //Za komunikacija so db
    private DBOpenHelper helper;

    //uri matcher za da znaeme koja operacija da ja izvrsime pri dadeno uri
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, ITEMS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ITEM_ID);
    }


    @Override
    public boolean onCreate() {
        //go inicijalizirame helper
        helper = new DBOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        //ja postavuvame nashata tabela
        queryBuilder.setTables(DBOpenHelper.TABLE_NAME);

        //proverka dali e pobarana kolona shto ne postoi
        checkColumns(projection);

        int uriType = uriMatcher.match(uri);

        switch(uriType){
            case ITEMS:
                break;

            case ITEM_ID:
                //mu go lepime id-to na vekje dadenoto query
                queryBuilder.appendWhere(DBOpenHelper.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;

            default: throw new IllegalArgumentException("Unknown URI " +uri);
        }

        //Ne ja otvarame konekcijata se dodeka ne se pobara
        SQLiteDatabase db = helper.getWritableDatabase();

        //groupBy i having se null
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = 0;

        switch (uriType){
            case ITEMS:
                //null e nullColumnHack
                id = db.insert(DBOpenHelper.TABLE_NAME, null, values);
                break;
            default: throw new IllegalArgumentException("Unknown URI " +uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType){
            case ITEMS:
                rowsDeleted = db.delete(DBOpenHelper.TABLE_NAME, selection, selectionArgs);
                break;

            case ITEM_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowsDeleted = db.delete(DBOpenHelper.TABLE_NAME, DBOpenHelper.COLUMN_ID + "=" +id, null);
                }
                else {
                    rowsDeleted = db.delete(DBOpenHelper.TABLE_NAME, DBOpenHelper.COLUMN_ID + "=" + id + "and" + selection , selectionArgs);
                }
                break;

            default: throw new IllegalArgumentException("Unknown URI: " +uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType){
            case ITEMS:
                rowsUpdated = db.update(DBOpenHelper.TABLE_NAME, values, selection, selectionArgs);
                break;

            case ITEM_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)){
                    rowsUpdated = db.update(DBOpenHelper.TABLE_NAME, values, DBOpenHelper.COLUMN_ID + "=" +id, null);
                }
                else {
                    rowsUpdated = db.update(DBOpenHelper.TABLE_NAME, values, DBOpenHelper.COLUMN_ID + "=" + id + "and" + selection , selectionArgs);
                }
                break;

            default: throw new IllegalArgumentException("Unknown URI: " +uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    //Metod za proverka vo sluchaj da e pobarana kolona shto ne postoi
    private void checkColumns(String[] projection){
        //Kazuvame koi koloni se dostapni
        String[] available = { DBOpenHelper.COLUMN_ID, DBOpenHelper.COLUMN_NAME, DBOpenHelper.COLUMN_QUANTITY, DBOpenHelper.COLUMN_DONE };

        if(projection != null){
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));

            //Proverka dali site koloni shto se pobarani se i dostapni
            if(!availableColumns.containsAll(requestedColumns)){
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    public static class DBOpenHelper extends SQLiteOpenHelper {

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_DONE = "done";
        public static final String TABLE_NAME = "ShoppingCartItems";

        private static final int DATABASE_VERSION = 1;

        private static final String DATABASE_NAME_EXPRESSION = "ShoppingCartDatabase.db";

        private static final String DATABASE_CREATE = String.format("create table %s (%s integer primary key autoincrement, " +
                        "%s text not null, %s text not null, %s integer);",
                TABLE_NAME, COLUMN_ID, COLUMN_NAME, COLUMN_QUANTITY, COLUMN_DONE);

        public DBOpenHelper(Context ctx){
            super(ctx, String.format(DATABASE_NAME_EXPRESSION), null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //ja kreirame tabelata
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //Ja briseme prethodnata tabela i ja kreirame novata
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
            onCreate(db);
        }
    }
}
