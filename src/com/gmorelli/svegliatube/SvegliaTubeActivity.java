package com.gmorelli.svegliatube;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SvegliaTubeActivity extends Activity {
	
	private Button btnSetAlarm_;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		   setContentView(R.layout.main);
		   
		   btnSetAlarm_ = (Button) findViewById(R.id.main_btn_setalarm);
		   btnSetAlarm_.setOnClickListener(bntSetAlarmListener_);
		   
    }
    
	View.OnClickListener bntSetAlarmListener_ = new View.OnClickListener() {

		
		@Override
		public void onClick(View v) {
			
		    //Create an offset from the current time in which the alarm will go off.
		    Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.SECOND, 15);
		
		    //Create a new PendingIntent and add it to the AlarmManager
		    Intent intent = new Intent(getApplicationContext(), AlarmReceiverActivity.class);
		    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		    AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
		    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
            pendingIntent);
			
		}
	};
    
    
}