package com.example.travelogue;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class FacebookShare extends Activity {
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	Button sharepic;
	public String token;
	Bitmap bmp;
	private StatusCallback callback;
	int flag;	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.activity_facebook_share);
	    Bundle extras = getIntent().getExtras();
	    byte[] byteArray = extras.getByteArray("picture");

	    	Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
	    	ImageView image = (ImageView) findViewById(R.id.imageView1);

	    	image.setImageBitmap(bmp);
	    try
	    {
	    sharepic=(Button) findViewById(R.id.shareBtn);
	    sharepic.setOnClickListener(onClickListener);
	    }
	    catch (Exception e) 
	    {
	    	// TODO: handle exception
		}
	 }
	
	private OnClickListener onClickListener = new OnClickListener() 
	{
		 @Override
		    public void onClick(final View v) 
		 {
			 try
			 {
				 Session.openActiveSession(FacebookShare.this, true, new Session.StatusCallback() 
				 {
					 // callback when session changes state
					 @SuppressWarnings("deprecation")
					 @Override
					 public void call(Session session, SessionState state, Exception exception) 
					 {
						 token=session.getAccessToken();
						 //Session ses;
						 if (session.isOpened()) 
						 {
							 Request.executeMeRequestAsync(session, new Request.GraphUserCallback() 
							 {
								 @Override
								 public void onCompleted(GraphUser user, Response response) 
								 {
									 if (user != null) 
									 {
										 publishStory(); 
									 }
								 }
							 });
						 }
					 }
				 });
			
				 /*Intent i=new Intent(FacebookShare.this,AboutUs.class);
				 startActivity(i);*/
			 }
		 
			 catch(Exception e)
			 {
				 Log.i("COMPLETION",e.toString()); 
			 }
		 }
	};
	   
		
	private void publishStory() 
	{
		try
		{
			 String mohit = null;
			 Session session = Session.getActiveSession();

		if (session != null)
		{

			 	List<String> permissions = session.getPermissions();
		        if (!isSubsetOf(PERMISSIONS, permissions)) 
		        {
		            pendingPublishReauthorization = true;
		            Session.NewPermissionsRequest newPermissionsRequest = new Session
		                    .NewPermissionsRequest(this, PERMISSIONS);
		            session.requestNewPublishPermissions(newPermissionsRequest);
		            return;
		        }

		    Bundle postParams = new Bundle();
		   
		    postParams.putByteArray("picture", getImageAsData());
		    mohit= "me/photos";
		    Request.Callback callback= new Request.Callback() 
		    {
	            public void onCompleted(Response response) 
	            {
	                JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
		            String postId = null;
		            try 
		            {
		                postId = graphResponse.getString("id");
		            }
		            catch (JSONException e) 
		            {
		                String TAG = null;
						Log.i(TAG,
		                    "JSON error "+ e.getMessage());
		            }
		            FacebookRequestError error = response.getError();
		            if (error != null) 
		            {
		            	Toast.makeText(getActivity().getApplicationContext(),error.getErrorMessage(),Toast.LENGTH_SHORT).show();
		            } 
		            else 
		            {
		                   Toast.makeText(getActivity().getApplicationContext(),postId,
		                      Toast.LENGTH_LONG).show();
		            }
		        }

				private ContextWrapper getActivity() 
				{
					// TODO Auto-generated method stub
					return null;
				}
		    };

		    Request request = new Request(session, mohit, postParams, HttpMethod.POST, callback);
		    RequestAsyncTask task = new RequestAsyncTask(request);
		    task.execute();
		}
		
	}
		catch (Exception e) 
		{
			 Log.d("Authorize Token","" + e);
			// TODO: handle exception
		}
	}

	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) 
	{
		for (String string : subset) 
		{
			if (!superset.contains(string)) 
		        {
		            return false;
		        }
	    }
		    return true;
    }
		
	public  byte[] getImageAsData() 
	{
		Bundle extras = getIntent().getExtras();
	    byte[] byteArray = extras.getByteArray("picture");

	    	Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
			Bitmap bitmap = bmp;
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
		    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		    byte[] bitmapdata = stream.toByteArray();
		    return bitmapdata;
	}
	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	      super.onActivityResult(requestCode, resultCode, data);
	      Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
	     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	 }	
 }
	
