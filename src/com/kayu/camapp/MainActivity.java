package com.kayu.camapp;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends Activity {

    private ImageButton camerabutton;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    protected String path;
	private Uri mFileUri;
    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalize();
    }

    private void initalize() {    	
		camerabutton = (ImageButton)findViewById(R.id.imageButton1);
		camerabutton.setOnClickListener(new Button.OnClickListener() { 
         	public void onClick (View v){
         		opencamera();
         		}        
		});     	
    
        	
		
	}

	protected void opencamera() {
		Toast.makeText(this,R.string.camera_message, 10).show();
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraTest");
		mediaStorageDir.mkdir(); // make sure you got this folder
		Log.v("mylog",mediaStorageDir.toString());
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
		mFileUri = Uri.fromFile(mediaFile); 	   
	    startActivityForResult( intent ,CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE );
	    
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				
				Toast.makeText(this, R.string.result_ok , 10).show();
				backMainPage();
 
				try {
					Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mFileUri);
					Toast.makeText(this,"Saved : " +bitmap.getWidth()+ " width" , 10).show();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
					
			}
							
			else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, R.string.result_canceled , 10).show();
				Log.v("mylog","camera canceled");
				backMainPage();
				
			}
			else {
				Toast.makeText(this, R.string.error_camera, 10).show();
				Log.v("mylog","camera error");
				backMainPage();
			}
		}
	}

	

	private void backMainPage() {
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.item1:
	            newCamApp();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void newCamApp() {
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		
	}
}

