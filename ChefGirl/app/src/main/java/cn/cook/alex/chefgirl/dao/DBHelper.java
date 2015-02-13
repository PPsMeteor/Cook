package cn.cook.alex.chefgirl.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alex on 15/2/12.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "chefgirl.db";

    private static final int VERSION = 1;

    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        MenusDataHelper.MenusDBInfo.TABLE.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
