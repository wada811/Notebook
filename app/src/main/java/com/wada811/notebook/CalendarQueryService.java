package com.wada811.notebook;

import android.app.Activity;
import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.provider.CalendarContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Data;
import android.text.format.Time;
import java.util.ArrayList;
import java.util.List;
import auto.parcel.AutoParcel;

public class CalendarQueryService extends IntentService{


    public static class CalendarEventReceiver extends ResultReceiver{

        private OnCalendarEventListener listener;

        public interface OnCalendarEventListener{

            public void onQueryEvent(int resultCode, Bundle resultData);

        }

        public CalendarEventReceiver(Handler handler){
            super(handler);
        }

        public CalendarEventReceiver OnCalendarEventListener(OnCalendarEventListener listener){
            this.listener = listener;
            return this;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData){
            if(listener != null){
                listener.onQueryEvent(resultCode, resultData);
            }
        }
    }


    private static final String[] INSTANCE_PROJECTION = {
        CalendarContract.Instances._ID,
        CalendarContract.Instances.EVENT_ID,
        CalendarContract.Instances.TITLE,
        CalendarContract.Instances.BEGIN,
        CalendarContract.Instances.END,
        CalendarContract.Instances.ALL_DAY,
        CalendarContract.Instances.DESCRIPTION,
        CalendarContract.Instances.ORGANIZER
    };

    private static final String[] CONTACT_PROJECTION = new String[]{
        Data._ID,
        Data.CONTACT_ID
    };
    private static final String CONTACT_SELECTION = Email.ADDRESS + " = ?";

    public CalendarQueryService(){
        super(CalendarQueryService.class.getSimpleName());
    }

    private static ArrayList<Event> queryEvents(Context context, long beginTime, long endTime){
        ContentResolver contentResolver = context.getContentResolver();
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, beginTime);
        ContentUris.appendId(builder, endTime);

        Cursor cursor = contentResolver.query(
            builder.build(), INSTANCE_PROJECTION, null /* selection */, null /* selectionArgs */, null /* sortOrder */
        );
        try{
            cursor.moveToFirst();
            int idIdx = cursor.getColumnIndex(CalendarContract.Instances._ID);
            int eventIdIdx = cursor.getColumnIndex(CalendarContract.Instances.EVENT_ID);
            int titleIdx = cursor.getColumnIndex(CalendarContract.Instances.TITLE);
            int beginIdx = cursor.getColumnIndex(CalendarContract.Instances.BEGIN);
            int endIdx = cursor.getColumnIndex(CalendarContract.Instances.END);
            int allDayIdx = cursor.getColumnIndex(CalendarContract.Instances.ALL_DAY);
            int descIdx = cursor.getColumnIndex(CalendarContract.Instances.DESCRIPTION);
            int ownerEmailIdx = cursor.getColumnIndex(CalendarContract.Instances.ORGANIZER);

            ArrayList<Event> events = new ArrayList<Event>(cursor.getCount());
            while(cursor.moveToNext()){
                Event event = Event.newInstance();
                event.id = cursor.getLong(idIdx);
                event.eventId = cursor.getLong(eventIdIdx);
                event.title = cursor.getString(titleIdx);
                event.begin = cursor.getLong(beginIdx);
                event.end = cursor.getLong(endIdx);
                event.allDay = cursor.getInt(allDayIdx) != 0;
                event.description = cursor.getString(descIdx);
                String ownerEmail = cursor.getString(ownerEmailIdx);
                Cursor contactCursor = contentResolver.query(
                    Data.CONTENT_URI, CONTACT_PROJECTION, CONTACT_SELECTION, new String[]{ ownerEmail }, null
                );
                int ownerIdIdx = contactCursor.getColumnIndex(Data.CONTACT_ID);
                long ownerId = -1;
                if(contactCursor.moveToFirst()){
                    ownerId = contactCursor.getLong(ownerIdIdx);
                }
                contactCursor.close();
                events.add(event);
            }
            return events;
        }finally{
            cursor.close();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent){
        ResultReceiver receiver = intent.getParcelableExtra(CalendarEventReceiver.class.getSimpleName());

        // Query calendar events in the next 24 hours.
        Time time = new Time();
        time.setToNow();
        long beginTime = time.toMillis(true);
        time.monthDay++;
        time.normalize(true);
        long endTime = time.normalize(true);

        ArrayList<Event> events = queryEvents(this, beginTime, endTime);

        if(events.isEmpty()){
            receiver.send(Activity.RESULT_CANCELED, null);
        }else{
            Bundle resultData = new Bundle();
            resultData.putParcelableArrayList("events", events);
            receiver.send(Activity.RESULT_OK, resultData);
        }
    }

}