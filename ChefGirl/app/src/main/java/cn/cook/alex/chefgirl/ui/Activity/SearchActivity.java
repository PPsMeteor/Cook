package cn.cook.alex.chefgirl.ui.Activity;

import android.os.Bundle;
import android.text.TextUtils;

import cn.cook.alex.chefgirl.R;
import cn.cook.alex.chefgirl.ui.fragment.MenusFragment;
import cn.cook.alex.chefgirl.ui.swipeback.SwipeBackActivity;

/**
 * Created by alex on 15/2/13.
 */
public class SearchActivity extends SwipeBackActivity {

    private MenusFragment menusFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        String tag = getIntent().getStringExtra("search");
        if (TextUtils.isEmpty(tag)){
            finish();
        }
        menusFragment = MenusFragment.newInstance(tag);
        replaceFragment(R.id.search_content_frame,menusFragment);
    }

}
