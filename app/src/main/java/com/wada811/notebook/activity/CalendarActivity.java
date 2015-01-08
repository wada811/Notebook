package com.wada811.notebook.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import com.wada811.notebook.CalendarWeekdayAdapter;
import com.wada811.notebook.CalendarWeekdayLabelAdapter;
import com.wada811.notebook.R;


public class CalendarActivity extends ActionBarActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        GridView calendarWeekdayLabelView = (GridView)findViewById(R.id.calendarWeekdayLabelView);
        CalendarWeekdayLabelAdapter calendarWeekdayLabelAdapter = new CalendarWeekdayLabelAdapter(this);
        calendarWeekdayLabelView.setAdapter(calendarWeekdayLabelAdapter);
        GridView calendarWeekdayView = (GridView)findViewById(R.id.calendarWeekdayView);
        CalendarWeekdayAdapter calendarWeekdayAdapter = new CalendarWeekdayAdapter(this);
        calendarWeekdayView.setAdapter(calendarWeekdayAdapter);
        calendarWeekdayView.setSelection(Integer.MAX_VALUE / 2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }
}
