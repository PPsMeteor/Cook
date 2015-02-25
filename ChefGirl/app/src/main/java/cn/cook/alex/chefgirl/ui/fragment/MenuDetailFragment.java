package cn.cook.alex.chefgirl.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.cook.alex.chefgirl.R;
import cn.cook.alex.chefgirl.adapter.CardAnimationAdapter;
import cn.cook.alex.chefgirl.adapter.MenuDetailAdapter;
import cn.cook.alex.chefgirl.data.ImageCacheManager;
import cn.cook.alex.chefgirl.model.Menu;
import cn.cook.alex.chefgirl.ui.swipeback.SwipeBackActivity;
import cn.cook.alex.chefgirl.ui.view.PageStaggeredGridView;
import cn.cook.alex.chefgirl.utils.DensityUtils;

/**
 * Created by alex on 15/2/14.
 */
public class MenuDetailFragment extends BaseFragment {
    private static Menu sMenu;

//    @InjectView(R.id.tv_detail_title)
//    TextView title;
//    @InjectView(R.id.tv_detail_tags)
//    TextView tags;
//    @InjectView(R.id.tv_detail_material)
//    TextView material;
//    @InjectView(R.id.iv_detail_cover)
//    ImageView cover;
    @InjectView(R.id.grid_detail_step)
    PageStaggeredGridView stepGridView;

    public static MenuDetailFragment newInstance(Menu menu){
        MenuDetailFragment fragment = new MenuDetailFragment();
        sMenu = menu;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_detail, container, false);
        ButterKnife.inject(this,view);
//        title.setText(sMenu.title);
//        ImageCacheManager.loadImage(sMenu.albums[0],ImageCacheManager.getImageListener(cover,null,null),0,DensityUtils.dip2px(getActivity().getApplication(), 240));
//        tags.setText(sMenu.tags);
//        material.setText(sMenu.ingredients);
        View header = new View(getActivity());
        stepGridView.addHeaderView(header);
        stepGridView.setCanLoading(false);
        MenuDetailAdapter adapter = new MenuDetailAdapter(sMenu,getActivity(),stepGridView);
        AnimationAdapter animationAdapter = new CardAnimationAdapter(adapter);
        animationAdapter.setAbsListView(stepGridView);
        stepGridView.setAdapter(animationAdapter);
        return view;
    }
}
