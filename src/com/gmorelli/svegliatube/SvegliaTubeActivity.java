package com.gmorelli.svegliatube;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class SvegliaTubeActivity extends Activity {
	
	private Button btnNewAlarm_;
	private TextView txtDate_;
	private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    static final int TIME_DIALOG_ID = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   setContentView(R.layout.main);
		   
		   txtDate_ = (TextView) findViewById(R.id.main_txt_date);
		   btnNewAlarm_ = (Button) findViewById(R.id.main_btn_newalarm);
		   btnNewAlarm_.setOnClickListener(btnNewAlarmListener_);
		   
		   final Calendar c = Calendar.getInstance();
           mYear = c.get(Calendar.YEAR);
           mMonth = c.get(Calendar.MONTH);
           mDay = c.get(Calendar.DAY_OF_MONTH);
		   
    }
    
    
    private View.OnClickListener btnNewAlarmListener_ = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
        	showDialog(TIME_DIALOG_ID);
        }
    };

    
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        
        case TIME_DIALOG_ID:
        	    return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
        }
        return null;
    }
    
    
	protected void onPrepareDialog(int id, Dialog dialog) {
	        switch (id) {
	        case TIME_DIALOG_ID:
	                ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
	                break;
	        }
	}  
	
	
	private void updateDisplay() {
		
		txtDate_.setText(
				new StringBuilder().append("Next alarm set on ").append(mDay).append("-").append(mMonth + 1).append("-").append(mYear).append(" ").append(mHour).append(":").append(mMinute));
		
	    //Create an offset from the current time in which the alarm will go off.
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, mYear);
	    cal.set(Calendar.MONTH, mMonth);
	    cal.set(Calendar.DAY_OF_MONTH, mDay);
	    cal.set(Calendar.HOUR_OF_DAY, mHour);
	    cal.set(Calendar.MINUTE, mMinute);
	    cal.set(Calendar.SECOND, 0);
	    
	    long time = cal.getTimeInMillis();
	    
	    System.out.println(time);
	    
 	    //Create a new PendingIntent and add it to the AlarmManager
	    Intent intent = new Intent(getApplicationContext(), AlarmReceiverActivity.class);
	    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	    AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
	    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
        pendingIntent);
		
	}
	
	
	private TimePickerDialog.OnTimeSetListener mTimeSetListener =
	        new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                updateDisplay();
				
			}
	};
	
}