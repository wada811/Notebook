package com.wada811.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;

public abstract class BindableAdapter<T> extends ArrayAdapter<T>{

    private final Context context;
    private final LayoutInflater inflater;

    public BindableAdapter(Context context, List<T> items){
        super(context, 0, items);
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public final View getView(int position, View view, ViewGroup container){
        if(view == null){
            view = newView(inflater, position, container);
            if(view == null){
                throw new IllegalStateException("newView result must not be null.");
            }
        }
        bindView(getItem(position), position, view);
        return view;
    }

    public abstract View newView(LayoutInflater inflater, int position, ViewGroup container);

    public abstract void bindView(T item, int position, View view);

}
