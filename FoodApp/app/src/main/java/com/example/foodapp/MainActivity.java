package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.foodapp.adapter.FoodCusorAdapter;
import com.example.foodapp.db.DBHelper;
import com.example.foodapp.entity.FoodModify;
import com.example.foodapp.models.Food;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FoodCusorAdapter foodAdapter;
    int currentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper.getInstance(this);
        listView = findViewById(R.id.am_listview);

        Cursor cursor = FoodModify.findAll();
        foodAdapter = new FoodCusorAdapter(this, cursor);
        listView.setAdapter(foodAdapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()){
            case R.id.cm_edit:
                this.currentIndex = index;
                showDialog();
                return true;
            case R.id.cm_delete:
                Cursor cursor = foodAdapter.getCursor();
                cursor.moveToPosition(index);
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                FoodModify.delete(id);
                save();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.om_add:
                showDialog();
                return true;
            case R.id.om_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_food, null);
        EditText edtThumbnail = view.findViewById(R.id.df_thumbnail);
        EditText edtTitle = view.findViewById(R.id.df_title);
        EditText edtContent = view.findViewById(R.id.df_content);
        EditText edtPrice = view.findViewById(R.id.df_price);

        if(currentIndex >= 0){
            Cursor cursor = foodAdapter.getCursor();
            cursor.moveToPosition(currentIndex);

            String thumbnail = cursor.getString(cursor.getColumnIndexOrThrow("thumbnail"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

            edtThumbnail.setText(thumbnail);
            edtTitle.setText(title);
            edtContent.setText(content);
            edtPrice.setText(String.valueOf(price));
        }

        builder.setView(view);

        builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String thumbnail = edtThumbnail.getText().toString();
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                int price = Integer.parseInt(edtPrice.getText().toString());

                if (currentIndex >= 0) {

                    Cursor cursor = foodAdapter.getCursor();
                    cursor.moveToPosition(currentIndex);
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                    Food food = new Food(thumbnail, title, content, price);
                    food.setId(id);
                    FoodModify.update(food);
                    currentIndex = -1;
                } else {
                    Food food = new Food(thumbnail, title, content, price);
                    FoodModify.insert(food);
                }
                save();
            }
        }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void save() {
        Cursor cursor = FoodModify.findAll();
        foodAdapter.changeCursor(cursor);
        foodAdapter.notifyDataSetChanged();
    }
}