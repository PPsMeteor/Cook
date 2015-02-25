package cn.cook.alex.chefgirl.ui.Activity;

import android.os.Bundle;

import cn.cook.alex.chefgirl.App;
import cn.cook.alex.chefgirl.R;
import cn.cook.alex.chefgirl.ui.fragment.MenuDetailFragment;
import cn.cook.alex.chefgirl.ui.swipeback.SwipeBackActivity;

/**
 * Created by alex on 15/2/13.
 */
public class MenuDetailActivity extends SwipeBackActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        replaceFragment(R.id.search_content_frame,MenuDetailFragment.newInstance(App.getsMenu()));
    }
}
