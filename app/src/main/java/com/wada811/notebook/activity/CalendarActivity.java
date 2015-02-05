package com.wada811.notebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import com.wada811.notebook.CalendarQueryService;
import com.wada811.notebook.CalendarQueryService.CalendarEventReceiver;
import com.wada811.notebook.CalendarQueryService.CalendarEventReceiver.OnCalendarEventListener;
import com.wada811.notebook.CalendarQueryService.Event;
import com.wada811.notebook.CalendarWeekdayAdapter;
import com.wada811.notebook.CalendarWeekdayLabelAdapter;
import com.wada811.notebook.R;
import org.joda.time.LocalDate;
import java.util.ArrayList;


public class CalendarActivity extends ActionBarActivity{

    private GridView calendarWeekdayLabelView;
    private CalendarWeekdayLabelAdapter calendarWeekdayLabelAdapter;
    private GridView calendarWeekdayView;
    private CalendarWeekdayAdapter calendarWeekdayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarWeekdayLabelView = (GridView)findViewById(R.id.calendarWeekdayLabelView);
        calendarWeekdayLabelAdapter = new CalendarWeekdayLabelAdapter(this);
        calendarWeekdayLabelView.setAdapter(calendarWeekdayLabelAdapter);
        calendarWeekdayView = (GridView)findViewById(R.id.calendarWeekdayView);
        calendarWeekdayAdapter = new CalendarWeekdayAdapter(this);
        calendarWeekdayView.setAdapter(calendarWeekdayAdapter);
        calendarWeekdayView.setSelection(Integer.MAX_VALUE / 2);

        Intent intent = new Intent(this, CalendarQueryService.class);
        CalendarEventReceiver receiver = new CalendarEventReceiver(new Handler());
        receiver.OnCalendarEventListener(
            new OnCalendarEventListener(){
                @Override
                public void onQueryEvent(int resultCode, Bundle resultData){
                    ArrayList<Event> events = resultData.getParcelableArrayList("events");
                    for(Event event : events){
                        bindEvent(event);
                    }
                }
            }
        );
        intent.putExtra(CalendarEventReceiver.class.getSimpleName(), receiver);
        startService(intent);
    }

    public void bindEvent(Event event){
        LocalDate beginDate = new LocalDate(event.begin);
        int position = calendarWeekdayAdapter.getPosition(beginDate);
        View view = calendarWeekdayAdapter.newView(LayoutInflater.from(this), position, calendarWeekdayView);
        calendarWeekdayAdapter.bindView(beginDate, position, view);
        calendarWeekdayAdapter.bindEvent(event, position, view);
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
