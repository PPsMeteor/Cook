package cn.cook.alex.chefgirl.ui.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.cook.alex.chefgirl.App;
import cn.cook.alex.chefgirl.R;
import cn.cook.alex.chefgirl.adapter.CardAnimationAdapter;
import cn.cook.alex.chefgirl.adapter.MenusAdapter;
import cn.cook.alex.chefgirl.api.CookApi;
import cn.cook.alex.chefgirl.dao.MenusDataHelper;
import cn.cook.alex.chefgirl.data.GsonRequest;
import cn.cook.alex.chefgirl.model.Menu;
import cn.cook.alex.chefgirl.ui.view.LoadingFooter;
import cn.cook.alex.chefgirl.ui.view.OnLoadNextListener;
import cn.cook.alex.chefgirl.ui.view.PageStaggeredGridView;
import cn.cook.alex.chefgirl.utils.LogUtil;
import cn.cook.alex.chefgirl.utils.TaskUtils;

/**
 * Created by alex on 15/2/13.
 */
public class MenusFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>,SwipeRefreshLayout.OnRefreshListener{
    private static final String DEFAULT_MAX_ITEM = "5";

    private String mOffset;
    private String mMaxItem = DEFAULT_MAX_ITEM;
    private String mTotalNum;
    private int itemCount = 0;//项目总数
    private MenusDataHelper menusDataHelper;

    public static final String EXTRA_CATEGORY = "extra_category";
    private String mCategory;

    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @InjectView(R.id.grid_view)
    PageStaggeredGridView gridView;

    MenusAdapter menusAdapter;

    public static MenusFragment newInstance(String category){
        MenusFragment fragment = new MenusFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CATEGORY,category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragme_menus,container,false);
        ButterKnife.inject(this,contentView);
        parseArgument();
        menusDataHelper = new MenusDataHelper(App.getContext(),mCategory);
        menusAdapter = new MenusAdapter(App.getContext(),gridView);

        View header = new View(getActivity());
        gridView.addHeaderView(header);
        AnimationAdapter animationAdapter = new CardAnimationAdapter(menusAdapter);
        animationAdapter.setAbsListView(gridView);
        gridView.setAdapter(animationAdapter);
        gridView.setLoadNextListener(new OnLoadNextListener() {
            @Override
            public void onLoadNext() {
                loadNext();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        getLoaderManager().initLoader(0,null,this);
        loadFirst();
        return contentView;
    }

    private void loadNext() {
        int next = itemCount+1;
        LogUtil.d(MenusFragment.class,"loadnext : "+ next);
        if (!TextUtils.isEmpty(mTotalNum) && Integer.valueOf(mTotalNum) <= next)
        {
            gridView.setState(LoadingFooter.State.TheEnd);
            return;
        }
        loadData(String.valueOf(next));
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        mCategory = bundle.getString(EXTRA_CATEGORY);
    }

    public void loadFirstAndScrollToTop(){
        loadFirst();
    }

    private void loadFirst() {
        mOffset = "0";
        itemCount = 0;
        loadData(mOffset);
    }

    private void loadData(String offset) {
        if (!swipeRefreshLayout.isRefreshing() && ("0".equals(offset))){
            setRefreshing(true);
        }
        executeRequest(new GsonRequest(CookApi.query(Uri.encode(mCategory),mMaxItem,offset), Menu.MenuRequestData.class,requestDataListener(),errorListener()));
    }

    private Response.Listener<Menu.MenuRequestData> requestDataListener(){
        //判断加载
        final boolean isRefreshFromTop = ("0".equals(mOffset));
        return new Response.Listener<Menu.MenuRequestData>() {
            @Override
            public void onResponse(final Menu.MenuRequestData menuRequestData) {
                TaskUtils.extcuteAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        if (menusDataHelper == null){
                            LogUtil.e(MenusFragment.class,"datahelp is null");
                            return null;
                        }
                        if (isRefreshFromTop) {
                            menusDataHelper.deleteAll();
                        }
                        mOffset = menuRequestData.getOffset();
//                        mMaxItem = menuRequestData.getMaxItem();
                        mTotalNum = menuRequestData.getTotalNum();
                        LogUtil.d(MenusFragment.class,"offset = "+ mOffset + " maxItem = "+menuRequestData.getMaxItem()+" total =" + mTotalNum);
                        itemCount += Integer.valueOf(mOffset);
                        ArrayList<Menu> menus = menuRequestData.getMenus();
                        if (menus != null)
                            menusDataHelper.bulkInsert(menus);
//                      存入数据库
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        //刷新UI
                        if (mTotalNum.equals("0")){
                            gridView.setState(LoadingFooter.State.TheEnd);
                        }
                        if (isRefreshFromTop){
                            setRefreshing(false);
                        }else {
                            gridView.setState(LoadingFooter.State.Idle,300);
                        }
                    }
                });
            }
        };
    }

    @Override
    protected Response.ErrorListener errorListener() {
        setRefreshing(false);
        gridView.setState(LoadingFooter.State.Idle, 3000);
        return super.errorListener();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        LogUtil.d(MenusFragment.class,"onCreateLoader");
        return menusDataHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        LogUtil.d(MenusFragment.class,"onLoadFinished");
        menusAdapter.changeCursor(data);
//        if (data != null && data.getCount() == 0){
//            loadFirst();
//        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        menusAdapter.changeCursor(null);
    }

    @Override
    public void onRefresh() {
        LogUtil.d(MenusFragment.class,"onRefresh");
        loadFirst();
    }

    private void setRefreshing(boolean refreshing){
        swipeRefreshLayout.setRefreshing(refreshing);
        //其他操作
        if (refreshing){
            gridView.setState(LoadingFooter.State.Idle);
        }
    }
}
