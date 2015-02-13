package cn.cook.alex.chefgirl.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.cook.alex.chefgirl.R;
import cn.cook.alex.chefgirl.data.ImageCacheManager;
import cn.cook.alex.chefgirl.model.Menu;
import cn.cook.alex.chefgirl.utils.DensityUtils;


/**
 * Created by alex on 15/2/13.
 */
public class MenusAdapter extends CursorAdapter {


    private static final int[] COLORS = {R.color.holo_blue_light, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_purple_light, R.color.holo_red_light};
    private static final int IMAGE_MAX_HEIGHT = 240;
    private Resources resources;

    private ListView listView;

    private Drawable mDefaultImageDrawable;

    private LayoutInflater layoutInflater;

    public MenusAdapter(Context context, ListView listView) {
        super(context, null, false);
        resources = context.getResources();
        this.listView = listView;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Menu getItem(int position) {
        mCursor.moveToPosition(position);
        return Menu.fromCursor(mCursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.list_item_menu, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holeder = getHolder(view);
        if (holeder.imageRequest != null) {
            holeder.imageRequest.cancelRequest();
        }

        Menu menu = Menu.fromCursor(cursor);
        mDefaultImageDrawable = new ColorDrawable(resources.getColor(COLORS[cursor.getPosition() % COLORS.length]));
        new ColorDrawable(resources.getColor(COLORS[cursor.getPosition() % COLORS.length]));
        if (menu == null) {
            return;
        }
        if (!TextUtils.isEmpty(menu.title)) {
            holeder.title.setText(menu.title);
        }
        holeder.imageRequest = ImageCacheManager.loadImage(menu.albums[0], ImageCacheManager.getImageListener(holeder.cover, mDefaultImageDrawable, mDefaultImageDrawable), 0, DensityUtils.dip2px(context, IMAGE_MAX_HEIGHT));
    }

    private Holder getHolder(final View view) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder(view);
            view.setTag(holder);
        }
        return holder;
    }

    static class Holder {
        @InjectView(R.id.tv_title)
        TextView title;
        @InjectView(R.id.iv_cover)
        ImageView cover;

        public ImageLoader.ImageContainer imageRequest;

        public Holder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
