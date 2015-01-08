package com.wada811.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.joda.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CalendarWeekdayLabelAdapter extends BindableAdapter<String>{

    private static List<String> weekdayLabels = new ArrayList<String>();
    static {
        LocalDate sunday = LocalDate.parse("1989-01-01");
        for(int i = 0; i < 7; i++){
            DateTimeFormatter
            String weekdayLabel = sunday.plusDays(i).toString("E");
            weekdayLabels.add(weekdayLabel);
        }
    }

    public CalendarWeekdayLabelAdapter(Context context){
        super(context, weekdayLabels);
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container){
        View view = inflater.inflate(R.layout.view_calendar_weekday_label, container, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(String item, int position, View view){
        ViewHolder holder = (ViewHolder)view.getTag();
        holder.calendarWeekdayLabel.setText(item);
    }

    class ViewHolder{

        @InjectView(R.id.calendarWeekdayLabel)
        TextView calendarWeekdayLabel;

        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }
    }
}
