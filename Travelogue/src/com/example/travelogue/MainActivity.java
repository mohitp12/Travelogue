package com.example.travelogue;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;


@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity 
{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    Intent i;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    String regId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view)
            {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null)
        {
            //selectItem(0);
        }
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	    
	    registerReceiver(recever, new IntentFilter(Config.DISPLAY_MESSAGE_ACTION));
		
		try
		{
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
		}
		
		catch(Exception e)
		{
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
		
		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
			
			@Override
			protected Void doInBackground(Void... params) 
			{
				
				try
				{
					
					GCMRegistrar.register(MainActivity.this, Config.SENDER_ID);
					
					if(GCMRegistrar.isRegistered(MainActivity.this))
					{
					
						Log.i("GCM Registration","  registerd  ");
					}
					else
					{
						Log.i("GCM Registration"," not registerd  ");
						GCMRegistrar.setRegisteredOnServer(MainActivity.this, true);
					}
					
				}
				catch (Exception e) 
				{
					Log.e("error "," "+e);
					
					e.printStackTrace();
				}

				return null;
			}
		};
		
		regId = GCMRegistrar.getRegistrationId(this);
		 
		 if (regId == "") 
		 {
			 task.execute(null,null,null);
		 }
		 else
		 {
			 
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

            return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener 
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
        {
            selectItem(position);
        }
    }

	private void selectItem(int position) {
        Intent intent; 
        
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        if(position==0)
        {
        	
        }
        else if(position==1)
        {
        	intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media")); 
        	startActivity(intent); 
        }
        else if(position==2)
        {
        	intent = new Intent(this,CurrLoc.class);
        	startActivity(intent);
        }
        else if(position==3)
        {
        	intent =new Intent (this,WeatherInfo.class);
        	startActivity(intent);
        }
        else if(position==4)
        {
        	intent =new Intent (this,Capture.class);
        	startActivity(intent);
        }
        else if(position==5)
        {
        	intent =new Intent (this,MenuTransport.class);
        	startActivity(intent);
        }
        else if(position==6)
        {
        	intent=new Intent(this,AddPlace.class);
        	startActivity(intent);
        }
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) 
    {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) 
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    BroadcastReceiver recever = new BroadcastReceiver()
	{
		
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			intent.getStringExtra("price");
		}
	};
	
	BroadcastReceiver receiver = new BroadcastReceiver()
	{
		
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			// Add the buttons
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // User clicked OK button
			           }
			       });
			builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // User cancelled the dialog
			           }
			       });
			// Set other dialog properties

			// Create the AlertDialog
			AlertDialog dialog = builder.create();
			
			dialog.show();
		}
	};
	
	public void ShowDailog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Add the buttons
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		           }
		       });
		builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User cancelled the dialog
		           }
		       });
		// Set other dialog properties

		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		
		dialog.show();
	}

   
    public void weatherBtn(View v)
	{
		i = new Intent(MainActivity.this, WeatherInfo.class);
        startActivity(i);
	}
	
	public void srcPlace(View v)
	{
			  i = new Intent(MainActivity.this, Places.class);
		      startActivity(i);
	}
	
	public void currLoc(View v)
	{
		i =new Intent(MainActivity.this, CurrLoc.class);
		startActivity(i);
	}
}