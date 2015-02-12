package cn.cook.alex.chefgirl.model;

import com.google.gson.Gson;

/**
 * Created by alex on 15/2/12.
 */
public class BaseModel {
    public String toJson(){
        return new Gson().toJson(this);
    }
}
