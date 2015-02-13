package cn.cook.alex.chefgirl.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;

import java.util.ArrayList;
import java.util.List;

import cn.cook.alex.chefgirl.model.Menu;
import cn.cook.alex.chefgirl.utils.Column;
import cn.cook.alex.chefgirl.utils.SQLiteTable;

/**
 * Created by alex on 15/2/12.
 */
public class MenusDataHelper extends BaseDataHelper {

    private String mCategory;

    public MenusDataHelper(Context context,String category) {
        super(context);
        mCategory = category;
    }

    @Override
    public Uri getContentUri() {
        return DataProvider.MENUS_CONTENT_URI;
    }

    private ContentValues getContentValues(Menu menu){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MenusDBInfo.ID,menu.id);
        contentValues.put(MenusDBInfo.CATEGORY,mCategory);
        contentValues.put(MenusDBInfo.JSON,menu.toJson());
        return contentValues;
    }

    private Menu query(long id){
        Menu menu = null;
        Cursor cursor = query(null,MenusDBInfo.CATEGORY+"=?"+ " AND "+MenusDBInfo.ID+"=?",
                new String[]{mCategory,String.valueOf(id)},null);
        if (cursor.moveToFirst()){
            menu = Menu.fromCursor(cursor);
        }
        cursor.close();
        return menu;
    }

    public void bulkInsert(List<Menu> menus){
        ArrayList<ContentValues> contentValueses = new ArrayList<>();
        for (Menu menu : menus){
            ContentValues values = getContentValues(menu);
            contentValueses.add(values);
        }
        ContentValues[] valueses = new ContentValues[contentValueses.size()];
        bulkInsert(contentValueses.toArray(valueses));
    }

    public int deleteAll(){
        synchronized (DataProvider.DBLock){
            DBHelper dbHelper = DataProvider.getDBHelper();
            SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
            int row = sqLiteDatabase.delete(MenusDBInfo.TABLE_NAME,MenusDBInfo.CATEGORY+"=?",new String[]{
                    mCategory
            });
            return row;
        }
    }

    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(),getContentUri(),null,MenusDBInfo.CATEGORY+"=?",new String[]{
                mCategory
        },MenusDBInfo._ID+" ASC");
    }

    public static final class MenusDBInfo implements BaseColumns {
        public MenusDBInfo() {
        }

        public static final String TABLE_NAME = "menus";

        public static final String ID = "id";

        public static final String CATEGORY="category";

        public static final String JSON = "json";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(CATEGORY,Column.DataType.TEXT)
                .addColumn(JSON,Column.DataType.TEXT);
    }
}
