package com.example.a7bowersfridge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class CameraFragment extends Fragment implements Constants
{
	public static String photoName = "";
	private Camera camera;
	private SurfaceView surfaceView;
	private View progressContainer;
	private static ArrayList<FridgeItem> items = new ArrayList<FridgeItem>();
	private FridgeItem item;
	private int id;

	
	private Camera.ShutterCallback shutterCallBack = new Camera.ShutterCallback() {
		
		@Override
		public void onShutter() {
			progressContainer.setVisibility(View.VISIBLE);
			
		}
	};
	
	private Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
			FileOutputStream os = null;
			boolean success = true;
			items = FridgeItemSet.getInstance(getActivity()).getFridgeItems();
			id = getActivity().getIntent().getIntExtra(EXTRA_DATA_ID, 0);
			item = FridgeItemSet.getInstance(getActivity()).getFridgeItems().get(id);
			
			try
			{
				int fItem = items.size()-1;
				photoName = "fridgeitem" + fItem + ".jpg";
				//photoName = item.getPhoto();
				Log.i("Mike", "Making a photo named " + photoName);
				//photoName = PHOTO_FILENAME;
				os = getActivity().openFileOutput(photoName, Context.MODE_PRIVATE);//make a private file
				os.write(data);
			}
			catch(Exception e)
			{
				success = false;
			}
			finally
			{
				try {
					if(os != null)
					{ //output stream open?
						os.close();
					}
				}
				catch(Exception e)
				{
					success = false;
				}
			}
			
			if(success)
			{
				Intent intent = new Intent();
				getActivity().setResult(Activity.RESULT_OK, intent); //tell what called it that it all went well
			}
			else
			{

				getActivity().setResult(Activity.RESULT_CANCELED);
			}
			
			getActivity().finish();
		}
	};
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
	}



	@SuppressLint("NewApi") //suppresses error on old style camera call*, I think
	@Override
	public void onResume() { //when opening or resuming an open cam
		super.onResume();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
		{
			camera=Camera.open(0); //*this one
		}else
		{
			camera=Camera.open();
		}
	}
	
	

	@Override
	public void onPause() { //when closing the cam
		super.onPause();
		
		if(camera != null)
		{
			camera.release();
			camera=null;
		}
	}



	@Override
	@SuppressWarnings("deprecation")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_camera, container, false);
		progressContainer = view.findViewById(R.id.progress_container);
		progressContainer.setVisibility(View.INVISIBLE);
		
		Button takePictureButton = (Button)view.findViewById(R.id.camera_button);
		takePictureButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				camera.takePicture(shutterCallBack, null, jpegCallback);
			}
		});
		
		initSurfaceView(view);
		
		return view;
	}
	
	private void initSurfaceView(View view)
	{
		surfaceView = (SurfaceView)view.findViewById(R.id.camera_surface_view);
		SurfaceHolder holder = surfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				try {
					if(camera != null)
					{
						camera.setPreviewDisplay(holder);
					}
				}
				catch(IOException ioe) {
				}
			}
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if(camera != null)
				{
					camera.stopPreview();
				}
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { //during a rotation of the screen
				if(camera == null) return; //no cam made
				
				Parameters parameters = camera.getParameters(); //what can our cam support?
				Size size = getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(size.width, size.height);
				
				size = getBestSupportedSize(parameters.getSupportedPictureSizes(), width, height);
				parameters.setPictureSize(size.width, size.height);
				
				
				
				//can we start our preview?
				try{
					camera.startPreview();
				}
				catch(Exception e)
				{
					camera.release();
					camera = null;
				}
				
			}
		});
	}
	
	private Size getBestSupportedSize(List<Size> sizes, int width, int height)
	{ //what's the biggest cam view supported for surface view use? (There's a camera on a surface)
		Size bestSize = sizes.get(0);
		int largestArea = bestSize.width * bestSize.height;
		
		for (Size size: sizes)
		{
			int area = size.width * size.height;
			if(area > largestArea)
			{
				bestSize = size;
				largestArea = area;
			}
		}
		
		return bestSize;
	}
}
