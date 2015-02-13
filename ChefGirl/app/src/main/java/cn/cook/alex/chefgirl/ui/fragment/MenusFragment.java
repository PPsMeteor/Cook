package cn.cook.alex.chefgirl.ui.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.cook.alex.chefgirl.App;
import cn.cook.alex.chefgirl.R;
import cn.cook.alex.chefgirl.adapter.MenusAdapter;
import cn.cook.alex.chefgirl.api.CookApi;
import cn.cook.alex.chefgirl.dao.MenusDataHelper;
import cn.cook.alex.chefgirl.data.GsonRequest;
import cn.cook.alex.chefgirl.model.Menu;
import cn.cook.alex.chefgirl.utils.LogUtil;
import cn.cook.alex.chefgirl.utils.TaskUtils;

/**
 * Created by alex on 15/2/13.
 */
public class MenusFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private String mOffset;
    private MenusDataHelper menusDataHelper;

    public static final String EXTRA_CATEGORY = "extra_category";
    private String mCategory;

    @InjectView(R.id.list_view)
    ListView listView;

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
        menusAdapter = new MenusAdapter(App.getContext(),listView);
        listView.setAdapter(menusAdapter);
        getLoaderManager().initLoader(0,null,this);
        loadFirst();
        return contentView;
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
        loadData(mOffset);
    }

    private void loadData(String mOffset) {
        executeRequest(new GsonRequest(CookApi.query(Uri.encode(mCategory)), Menu.MenuRequestData.class,requestDataListener(),errorListener()));
    }

    private Response.Listener<Menu.MenuRequestData> requestDataListener(){
        //判断加载
        return new Response.Listener<Menu.MenuRequestData>() {
            @Override
            public void onResponse(final Menu.MenuRequestData menuRequestData) {
//                LogUtil.d(MenusFragment.class,"MenuRequestData:"+menuRequestData);
                TaskUtils.extcuteAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        if (menusDataHelper == null){
                            LogUtil.e(MenusFragment.class,"datahelp is null");
                            return null;
                        }
                        menusDataHelper.deleteAll();
                        mOffset = menuRequestData.getOffset();
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
                    }
                });
            }
        };
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
        if (data != null && data.getCount() == 0){
            loadFirst();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        menusAdapter.changeCursor(null);
    }
}
