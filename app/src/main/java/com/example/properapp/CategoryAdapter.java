package com.example.properapp;

import android.content.Context;
import android.icu.util.ULocale;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<CategoryItem> {
    public CategoryAdapter(Context context, ArrayList<CategoryItem> categoryItemArrayList) {
        super(context, 0, categoryItemArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.category_spinner_layout, parent, false
            );
        }

        ImageView imageViewCategory = convertView.findViewById(R.id.category_spinner_custom_icon);
        TextView textViewName = convertView.findViewById(R.id.category_spinner_custom_catname);

        CategoryItem currentItem = getItem(position);

        if (currentItem != null) {
            imageViewCategory.setImageResource(currentItem.getCategoryImage());
            textViewName.setText(currentItem.getCategoryName());
        }

        return convertView;
    }

}
