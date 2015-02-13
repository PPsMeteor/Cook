package cn.cook.alex.chefgirl.model;

import android.database.Cursor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.cook.alex.chefgirl.dao.MenusDataHelper;

/**
 * Created by alex on 15/2/12.
 */
public class Menu extends BaseModel {
    public static final HashMap<String,Menu> CACHE = new HashMap<>();

    public String id;
    public String title;
    public String tags;     //标签
    public String imtro;    //功能简介
    public String ingredients;  //食材
    public String burden;   //配料
    /**
     * 封面图片url
     */
    public String[] albums;

    private List<Step> steps;

    private class Step{
        public String step;
        public String img;
    }

    @Override
    public String toString() {
        return "id:"+id+"\ttitle:"+title+"\tstep:"+steps.get(0);
    }

    public static void addToCache(Menu menu) {
        CACHE.put(menu.id,menu);
    }

    public static Menu getFromCache(String id) {
        return CACHE.get(id);
    }

    public static Menu fromJson(String json) {
        return new Gson().fromJson(json,Menu.class);
    }

    public static Menu fromCursor(Cursor c) {
        String id = c.getString(c.getColumnIndex(MenusDataHelper.MenusDBInfo.ID));
        Menu menu = getFromCache(id);
        if (menu != null){
            return menu;
        }
        menu = new Gson().fromJson(c.getString(c.getColumnIndex(MenusDataHelper.MenusDBInfo.JSON))
        ,Menu.class);
        addToCache(menu);
        return menu;
    }

    public static class MenuRequestData {
        public String resultcode;
        public String reason;
        public String errorcode;
        public Result result;

        @Override
        public String toString() {
            return "result:"+result;
        }

        public String  getOffset(){
            if (result == null){
                return "0";
            }
            return result.rn;
        }

        public ArrayList<Menu> getMenus()
        {
            if (result == null){
                return null;
            }
            return result.data;
        }
    }

    private class Result{
        public ArrayList<Menu> data;
        public String totalNum;
        public String pn;
        public String rn;

        @Override
        public String toString() {
            return "totalNum:"+totalNum+"\tdata:"+data.toString();
        }
    }
}
