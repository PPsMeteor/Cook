package cn.cook.alex.chefgirl.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.cook.alex.chefgirl.R;
import cn.cook.alex.chefgirl.data.ImageCacheManager;
import cn.cook.alex.chefgirl.model.Menu;
import cn.cook.alex.chefgirl.ui.view.PageStaggeredGridView;
import cn.cook.alex.chefgirl.utils.DensityUtils;

/**
 * Created by alex on 15/2/14.
 */
public class MenuDetailAdapter extends BaseAdapter{

    private Context context;
    private Menu menu;
    private PageStaggeredGridView listView;
    private Drawable mDefaultImageDrawable;
    private static final int[] COLORS = {R.color.holo_blue_light, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_purple_light, R.color.holo_red_light};
    private Resources resources;
    private static final int IMAGE_MAX_HEIGHT = 240;

    public MenuDetailAdapter(Menu menu, Context context,PageStaggeredGridView listView) {
        this.menu = menu;
        this.context = context;
        this.listView = listView;
        this.resources = context.getResources();
    }

    @Override
    public int getCount() {
        return menu.steps.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = getHolder(convertView,parent);
        Holder holder = (Holder) view.getTag();
        holder.stepTv.setText("Step "+menu.steps.get(position).step);
        mDefaultImageDrawable = new ColorDrawable(resources.getColor(COLORS[position % COLORS.length]));
        holder.imageRequest = ImageCacheManager.loadImage(menu.steps.get(position).img,ImageCacheManager.getImageListener(holder.stepIv, mDefaultImageDrawable, mDefaultImageDrawable), 0, DensityUtils.dip2px(context, IMAGE_MAX_HEIGHT));
        return view;
    }

    private View getHolder(View view,ViewGroup parent) {
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_step,parent,false);
            holder = new Holder(view);
            view.setTag(holder);
            return view;
        }
//        holder = (Holder) view.getTag();
        return view;
    }

    static class Holder{
        @InjectView(R.id.tv_step)
        TextView stepTv;
        @InjectView(R.id.iv_step)
        ImageView stepIv;

        public ImageLoader.ImageContainer imageRequest;

        public Holder(View view) {
            ButterKnife.inject(this,view);
        }
    }
}
