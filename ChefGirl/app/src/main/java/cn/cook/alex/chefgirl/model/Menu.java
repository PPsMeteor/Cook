package cn.cook.alex.chefgirl.model;

import android.database.Cursor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public String[] albums; //封面图片url

    private List<Step> steps;

    private class Step{
        public int count;
        public String step;
        public String img;
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
        return null;
    }

    public static class MenuRequestData {
        public ArrayList<Menu> data;
        public Paging paging;

        public String getPage(){
            return paging.next;
        }
    }

    private class Paging{
        public String next;
    }
}
