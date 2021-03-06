package com.example.alex.altarixtest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    DBHelper dbHelper;
    ListView listView;
    String[] heads;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //linearLayout = (LinearLayout) findViewById(R.id.lLayout);
        listView = (ListView) findViewById(R.id.LView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        dbHelper = new DBHelper(this);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostResume() {
        int i = 0;
        //linearLayout.removeAllViews();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        heads = new String[4024];
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_HEAD);
            do{
                Log.d("mLog", "Text = " + cursor.getString(textIndex));
//                LinearLayout.LayoutParams layoutParams = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.gravity = Gravity.LEFT;
//                TextView textView = new TextView(this);
//                textView.setText(cursor.getString(textIndex));
//                linearLayout.addView(textView,layoutParams);
                heads[i] = cursor.getString(textIndex);
                i++;
            }while (cursor.moveToNext());
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, heads);
            listView.setAdapter(adapter);
        }else Log.d("mLog", "0 rows");
        cursor.close();
        dbHelper.close();


        super.onPostResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        switch (id){
            case R.id.action_add:
                Intent intent = new Intent(this, NoteAdd.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_NOTES, null, null);
        }


        return super.onOptionsItemSelected(item);
    }
}
