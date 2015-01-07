package com.wada811.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import java.util.Collections;

public class CalendarAdapter extends BindableAdapter<LocalDate>{

    private final LocalDate today;
    private final int middle;

    public CalendarAdapter(Context context){
        super(context, Collections.<LocalDate>emptyList());
        today = LocalDate.now();
        middle = Integer.MAX_VALUE / 2 + today.getDayOfWeek();
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container){
        return new TextView(getContext());
    }

    @Override
    public void bindView(LocalDate item, int position, View view){
        TextView textView = (TextView)view;
        textView.setText(item.toString());
    }

    @Override
    public int getCount(){
        return Integer.MAX_VALUE - 1;
    }

    @Override
    public LocalDate getItem(int position){
        return today.plusDays(position - middle);
    }

    @Override
    public int getPosition(LocalDate item){
        return Days.daysBetween(today, item).getDays();
    }

}
