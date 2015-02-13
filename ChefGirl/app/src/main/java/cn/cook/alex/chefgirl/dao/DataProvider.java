package cn.cook.alex.chefgirl.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import cn.cook.alex.chefgirl.App;
import cn.cook.alex.chefgirl.utils.LogUtil;

/**
 * Created by alex on 15/2/12.
 */
public class DataProvider extends ContentProvider{

    private static final String TAG =DataProvider.class.getSimpleName();

    static final Object DBLock = new Object();

    public static final String AUTHORITY = "cn.cook.alex.chefgirl.provider";

    public static final String SCHEME = "content://";

    //messages
    public static final String PATH_MENUS = "/menus";

    public static final Uri MENUS_CONTENT_URI = Uri.parse(SCHEME+AUTHORITY+PATH_MENUS);

    private static final int MENUS = 0;

    /*
     * MIME type definitions
     */
    public static final String MENU_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.cn.cook.alex.chefgirl.provider.menu";

    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY,"menus",MENUS);
    }

    private static DBHelper mDBHelper;

    public static DBHelper getDBHelper(){
        if (mDBHelper == null){
            mDBHelper = new DBHelper(App.getContext());
        }
        return mDBHelper;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        synchronized (DBLock){
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            String table = matchTable(uri);
            queryBuilder.setTables(table);

            SQLiteDatabase db = getDBHelper().getReadableDatabase();
            Cursor cursor = queryBuilder.query(
                    db,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
            return cursor;
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)){
            case MENUS:
                return MENU_CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        synchronized (DBLock) {
            String table = matchTable(uri);
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            long rowId = 0;
            db.beginTransaction();
            try {
                rowId = db.insert(table, null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage());
            } finally {
                db.endTransaction();
            }
            if (rowId > 0) {
                Uri returnUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(uri, null);
                return returnUri;
            }
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();

            int count = 0;
            String table = matchTable(uri);
            db.beginTransaction();
            try {
                count = db.delete(table, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        synchronized (DBLock) {
            SQLiteDatabase db = getDBHelper().getWritableDatabase();
            int count;
            String table = matchTable(uri);
            db.beginTransaction();
            try {
                count = db.update(table, values, selection, selectionArgs);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);

            return count;
        }
    }

    private String matchTable(Uri uri){
        String table = null;
        switch (sUriMatcher.match(uri)){
            case MENUS:
                table = MenusDataHelper.MenusDBInfo.TABLE_NAME;
                break;
            default:
                throw new IllegalAccessError("unknown URI "+uri);
        }
        return table;
    }
}
