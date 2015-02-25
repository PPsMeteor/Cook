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
    public static String  DEFAULT_MAX_ITME = "10";
    //起始条目下标
    public static String  OFFSET = "0";
    //所有分类类型
    public static enum Category{
        DISHES("10001"),CUISINE("10002"),INGREDIENTS("10003"),EFFECT("10004"),SCENE("10005"),CRAFTS("10006"),STYLE("10007");
        //菜式，菜系，食材,功效,场景,烹饪方式口味,类型，
        private Category(String value){
            this.value = value;
        }

        private String value;

        @Override
        public String toString() {
            return value;
        }
    }

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
    public static String query(String menu, String maxItem, String offset) {
        if (TextUtils.isEmpty(menu)) {
            LogUtil.e(TAG, "menu is not available");
            return "";
        }
        LogUtil.d(CookApi.class,HOST + "query?" + "key=" + APK_KEY + "&menu=" + menu + "&rn=" + maxItem + "&pn=" + offset);
        return HOST + "query?" + "key=" + APK_KEY + "&menu=" + menu + "&rn=" + maxItem + "&pn=" + offset;
    }

    public static String queryCategory(int parentId){
        return HOST+"category?key="+APK_KEY+"&parentid="+parentId;
    }

}
