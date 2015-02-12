package cn.cook.alex.chefgirl.api;

import android.text.TextUtils;

import cn.cook.alex.chefgirl.utils.LogUtil;

/**
 * Created by alex on 15/2/12.
 */
public class CookApi {
    private static final String TAG = CookApi.class.getSimpleName();

    public static final String APK_KEY = "6f9919d671be91c0557f46a50d40ee28";
    public static final String HOST = "http://apis.juhe.cn/cook/";

    //页数
    public static int PAGE = 0;
    //默认返回条目数目
    public static int DEFAULT_MAX_ITME = 10;
    //起始条目下标
    public static int OFFSET = 0;


    /**
     * 搜索食谱
     *
     * @param menu 搜索字段
     * @return
     */
    public static String query(String menu) {
        return query(menu, DEFAULT_MAX_ITME, OFFSET);
    }

    /**
     * 搜索食谱
     *
     * @param menu    搜索字段
     * @param maxItem 返回条目数 最大为30
     * @param offset  返回起始下标
     * @return
     */
    public static String query(String menu, int maxItem, int offset) {
        if (TextUtils.isEmpty(menu)) {
            LogUtil.e(TAG, "menu is not available");
            return "";
        }
        return HOST + "query?" + "key=" + APK_KEY + "&menu=" + menu + "&rn=" + maxItem + "&pn=" + offset;
    }

}
