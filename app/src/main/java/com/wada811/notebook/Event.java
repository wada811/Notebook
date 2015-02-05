package com.wada811.notebook;

import android.os.Parcelable;
import auto.parcel.AutoParcel;

@AutoParcel
public abstract class Event implements Parcelable {

    public long id;
    public long eventId;
    public String title;
    public long begin;
    public long end;
    public boolean allDay;
    public String description;

    public static Event newInstance(){
        return new AutoParcel_CalendarQueryService_Event();
    }

}
