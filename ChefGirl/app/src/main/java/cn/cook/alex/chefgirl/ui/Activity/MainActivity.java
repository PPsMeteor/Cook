package cn.cook.alex.chefgirl.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.cook.alex.chefgirl.R;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.edit_query)
    EditText editText;
    @InjectView(R.id.btn_query)
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query: {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("search", editText.getText().toString());
                    startActivity(intent);
                }
            }
            break;
        }

    }



    public void setCategory(String category){

    }
}
