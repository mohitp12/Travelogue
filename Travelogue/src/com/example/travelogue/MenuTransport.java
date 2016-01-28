package com.example.travelogue;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MenuTransport extends Activity {
	EditText sTransport,dTransport;
	String src,dest;
	private static final int TRAIN = 0;
    private static final int BUS = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_transport);
		sTransport=(EditText)findViewById(R.id.srcetxt);
		dTransport=(EditText)findViewById(R.id.destetxt);
		
	}

	public void transOption(View v)
	{
		src=sTransport.getText().toString();
		dest=dTransport.getText().toString();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final CharSequence train = getResources().getString(R.string.action_train);
		final CharSequence bus = getResources().getString(R.string.action_bus);
		builder.setCancelable(true).setItems(new CharSequence[] {train, bus}, new DialogInterface.OnClickListener() {
			@Override
               public void onClick(DialogInterface dialogInterface, int i) 
			{
                   if (i == TRAIN) 
                   {
                       startTrainActivity();
                   } 
                   else if (i == BUS) 
                   {
                	   startBusActivity();
                   }
			}
			
		});	
		builder.show();	
}
	

	private void startBusActivity() 
	{
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,Bus.class);
		Bundle b=new Bundle();
		b.putString("mSource", src);
		b.putString("mDestination", dest);
		intent.putExtras(b);
		startActivity(intent);
	}

	private void startTrainActivity() 
	{
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,Train.class);
		//intent.putExtra("destination", district);
		startActivity(intent);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_transport, menu);
		return true;
	}

}
