package com.example.foodapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodapp.R;
import com.squareup.picasso.Picasso;

public class FoodCusorAdapter extends CursorAdapter {
    Activity activity;
    public FoodCusorAdapter(Activity activity, Cursor c) {
        super(activity, c);
        this.activity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_food, null);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView thumbnailImg = view.findViewById(R.id.if_image);
        TextView txtTitle = view.findViewById(R.id.if_title);
        TextView txtContent = view.findViewById(R.id.if_content);
        TextView txtPrice = view.findViewById(R.id.if_price);

        String thumbnail = cursor.getString(cursor.getColumnIndexOrThrow("thumbnail"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
        int price = cursor.getInt(cursor.getColumnIndexOrThrow("price"));

        Picasso.get().load(thumbnail).placeholder(R.mipmap.ic_launcher).error(R.drawable.image_error).into(thumbnailImg);
        txtTitle.setText(title);
        txtContent.setText(content);
        txtPrice.setText(String.valueOf(price));
    }
}
