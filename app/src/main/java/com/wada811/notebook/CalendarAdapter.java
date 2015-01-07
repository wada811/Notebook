package com.wada811.notebook;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import java.util.Collections;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class CalendarAdapter extends BindableAdapter<LocalDate>{

    private final LocalDate today;
    private final int todayPosition;

    public CalendarAdapter(Context context){
        super(context, Collections.<LocalDate>emptyList());
        today = LocalDate.now();
        todayPosition = Integer.MAX_VALUE / 2 + today.getDayOfWeek();
    }

    @Override
    public int getCount(){
        return Integer.MAX_VALUE - 1;
    }

    @Override
    public LocalDate getItem(int position){
        return today.plusDays(position - todayPosition);
    }

    @Override
    public int getPosition(LocalDate item){
        return Days.daysBetween(today, item).getDays();
    }


    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container){
        View view = inflater.inflate(R.layout.view_calendar_day, container, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(LocalDate item, int position, View view){
        ViewHolder holder = (ViewHolder)view.getTag();
        if(position == todayPosition){
            holder.calendarDayTitle.setBackgroundResource(R.color.md_blue_500);
            holder.calendarDayDate.setTextColor(getContext().getResources().getColor(R.color.md_white));
            holder.calendarDayBody.setBackgroundResource(R.color.md_amber_50);
        }
        if(item.getDayOfWeek() == DateTimeConstants.SUNDAY){
            holder.calendarDayDate.setTextColor(getContext().getResources().getColor(R.color.md_red_500));
            holder.calendarDayDate.setTypeface(Typeface.DEFAULT_BOLD);
        }else if(item.getDayOfWeek() == DateTimeConstants.SATURDAY){
            holder.calendarDayDate.setTextColor(getContext().getResources().getColor(R.color.md_blue_500));
            holder.calendarDayDate.setTypeface(Typeface.DEFAULT_BOLD);
        }
        holder.calendarDayDate.setText(item.toString());
    }

    class ViewHolder{

        @InjectView(R.id.calendarDayTitle)
        LinearLayout calendarDayTitle;
        @InjectView(R.id.calendarDayBody)
        LinearLayout calendarDayBody;
        @InjectView(R.id.calendarDayDate)
        TextView calendarDayDate;
        @InjectView(R.id.calendarDayOverflowPlan)
        TextView calendarDayOverflowPlan;
        @InjectView(R.id.calendarDayPlan1)
        TextView calendarDayPlan1;
        @InjectView(R.id.calendarDayPlan2)
        TextView calendarDayPlan2;
        @InjectView(R.id.calendarDayPlan3)
        TextView calendarDayPlan3;

        public ViewHolder(View view){
            ButterKnife.inject(this, view);
        }
    }
}
