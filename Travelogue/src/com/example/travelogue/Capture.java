package com.example.travelogue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

@SuppressLint("SimpleDateFormat")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class Capture extends Activity
{
    private Uri photoUri=null;
	private Uri tempUri = null;
	
	private BaseAdapter adapter;
	private static final String PHOTO_URI_KEY = " ";
    private static final String TEMP_URI_KEY = "temp_uri";
    private static final String FILE_PREFIX = "Travelogue_";
    private static final String FILE_SUFFIX = ".jpg";
    int requestCode;
    
    ImageView imageView;
    Bitmap bit;
    private static int RESULT_LOAD_IMAGE = 1;
    Button sharepic;
    
    GPSTracker gps;
	double latitude,longitude;
    GoogleMap googleMap;
	String city=null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.activity_capture);
        
        getResources().getString(R.string.action_photo_camera);

        startCameraActivity();
        
        File file[] = Environment.getExternalStorageDirectory().listFiles();  


        for (File f : file)
        {
            if (f.isDirectory()) 
            { 
                Log.i("Name", f.getPath()+"");
                System.out.print("Hello");
            }
        }

	}
	
	@Override
    protected void onSaveInstanceState(Bundle bundle) {
        if (photoUri != null) {
            bundle.putParcelable(PHOTO_URI_KEY, photoUri);
        }
        if (tempUri != null) {
            bundle.putParcelable(TEMP_URI_KEY, tempUri);
        }
    }
	
	protected void onActivityResult(Intent data) {
        if (tempUri != null) {
            photoUri = tempUri;
        } else if (data != null) {
            photoUri = data.getData();
        }
        setPhotoText();
    }
	
	private void setPhotoText() {
        if (photoUri == null) {
            setText2(getResources().getString(R.string.action_photo_default));
        } else {
            setText2(getResources().getString(R.string.action_photo_ready));
        }
    }

	public void setText2(String text2) {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
	}
	
        public void startCameraActivity() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           try{
            tempUri = getTempUri();
            if (tempUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
            }
            startActivityForResult(intent, getRequestCode());
           }
           catch(Exception e)
           {
        	   Log.i("CAMERA START", e.toString());
           }
           }

        public int getRequestCode() {
        	
            return requestCode;
        }
        
                /*************************************Same as photoHandler******************************************************/
        private Uri getTempUri() {
        	String imgFileName;
        		gps = new GPSTracker(Capture.this);
        		
                // check if GPS enabled    
                if(gps.canGetLocation())
                {     
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                     String cooorrdinates=latitude +","+ longitude;
                    
                     Log.i("Latitude and Longitude are:", cooorrdinates);
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(Capture.this, Locale.getDefault());
                    try
                    {
                    	addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    	addresses.get(0).getAddressLine(0);
                    	city = addresses.get(0).getAddressLine(1);
                    	addresses.get(0).getAddressLine(2);
                    }
                    catch(Exception e)
                    {
                    	Log.i("LOCATION", e.toString());
                    }
                }
                else
                {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                   
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
            	String date = dateFormat.format(new Date());
                String cityName=null;

                if(city!=null && city.contains(","))
                {
                	cityName= city.substring(0, city.indexOf(",")); 
                	imgFileName= cityName+ date + FILE_SUFFIX;
        		}
        		else
        		{
        			imgFileName = FILE_PREFIX + date + FILE_SUFFIX;
        		}
                
                
            File pictureFileDir = new File(Environment.getExternalStorageDirectory()+"/Travelogue");
            File image = new File(Environment.getExternalStorageDirectory()+"/Travelogue", imgFileName);
            
            /*File  gallery = Environment.getExternalStorageDirectory();
            File image1 = new File(pictureFileDir, imgFileName);
           
            /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(android.os.Environment.getExternalStorageDirectory(), imgFileName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(intent, 1);*/
            
            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) 
            {
    			try
    			{
    			Toast.makeText(Capture.this, "Can't create directory to save image.",
    					Toast.LENGTH_LONG).show();
    			}
    			catch(Exception e)
    			{
    				Log.i("photo", e.toString());
    			}
    		}

            return Uri.fromFile(image);
            
           
        }
        

        
        public void imagePicker(View v)
        {
        	sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
        	Intent i = new Intent(
        			Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
        			startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
        @Override
	      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	            super.onActivityResult(requestCode, resultCode, data);
	             
	            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	                Uri selectedImage = data.getData();
	                String[] filePathColumn = { MediaStore.Images.Media.DATA };
	     
	                Cursor cursor = getContentResolver().query(selectedImage,
	                        filePathColumn, null, null, null);
	                cursor.moveToFirst();
	     
	                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                String picturePath = cursor.getString(columnIndex);
	                cursor.close();
	                 
	                imageView = (ImageView) findViewById(R.id.imgView);
	                bit=BitmapFactory.decodeFile(picturePath);
	                imageView.setImageBitmap(bit);
	                
	                Intent i=new Intent(Capture.this,FacebookShare.class);
                	ByteArrayOutputStream stream = new ByteArrayOutputStream();
                	bit.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                	//bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
                	byte[] byteArray = stream.toByteArray();
                	i.putExtra("picture", byteArray);
                	startActivity(i);	
	            }
	        }
        
 }	      
        
