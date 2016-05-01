package com.example.a7bowersfridge;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FridgeItemAddFragment extends Fragment implements Constants
{
	private FridgeItem item;
	private static ArrayList<FridgeItem> items = new ArrayList<FridgeItem>();
	//private String photoName = "";
	private int id;
	private View view;
	private static final int REQUEST_PHOTO = 1;
	
	
	public static FridgeItemAddFragment newInstance(int id)
	{ //returns a FridgeItemAddFragment with save state data set as an arg
		Bundle args = new Bundle();
		args.putInt(EXTRA_DATA_ID, id);
		FridgeItemAddFragment dFrag = new FridgeItemAddFragment();
		dFrag.setArguments(args);
		
		return dFrag;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{ //puts the instance's id... string in item?
		super.onCreate(savedInstanceState);
		
		items = FridgeItemSet.getInstance(getActivity()).getFridgeItems(); //used later

		//id = getArguments().getInt(EXTRA_DATA_ID);
		id = getActivity().getIntent().getIntExtra(EXTRA_DATA_ID, 0);
		Log.i("Mike", "in addfrag oncreate id = " + id);
		
		if(id == -1)
		{
			item = new FridgeItem("", "", "", "fridgeitem" + items.size() + ".jpg");
		}
		else
		{
			item = FridgeItemSet.getInstance(getActivity()).getFridgeItems().get(id);
		}
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_add_item, container, false);
		initName(view);
		initDesc(view);
		initDate(view);
		initPicture(view);
		initCameraButton();
		return view;
	}
	
	private void initName(View view)
	{
		TextView textView = (TextView)view.findViewById(R.id.textview_fridge_item_name);
		textView.setText(item.getName());

		EditText editText = (EditText)view.findViewById(R.id.edittext_fridge_item_name);
		editText.setText(item.getName());
	}
	
	private void initDesc(View view)
	{
		TextView textView = (TextView)view.findViewById(R.id.textview_fridge_item_desc);
		textView.setText(item.getDesc());

		EditText editText = (EditText)view.findViewById(R.id.edittext_fridge_item_desc);
		editText.setText(item.getDesc());
	}
	
	private void initDate(View view)
	{
		TextView textView = (TextView)view.findViewById(R.id.textview_fridge_item_date);
		textView.setText(item.getDate());

		EditText editText = (EditText)view.findViewById(R.id.edittext_fridge_item_date);
		editText.setText(item.getDate());
	}
	
	private void initPicture(View view)
	{
		ImageView photoView = (ImageView)view.findViewById(R.id.imageview_fridge_item_photo);
		//TextView detailsLink = (TextView)view.findViewById(R.id.textview_item_desc);

		//String photoFileName = "fridgeitem" + items.size() + ".jpg";
		String photoFileName = item.getPhoto();
		Log.i("Mike", "Trying to display a photo named " + photoFileName + " in addFrag initPict");
		
		BitmapDrawable bitmap = null;
		
		if(getActivity().getFileStreamPath(photoFileName) == null) return;
	
		String path = getActivity().getFileStreamPath(photoFileName).getAbsolutePath();
		if(path == null) return;
		
		bitmap = PictureUtils.getScaledDrawable(getActivity(), path);
		
		if(bitmap != null) photoView.setImageDrawable(bitmap);

		//photoView.setImageResource(item.getPhotoId()); //old version
	}
	
	public void initCameraButton()
	{
		Button button = (Button)view.findViewById(R.id.button_show_camera);
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), CameraActivity.class);
				startActivityForResult(intent, REQUEST_PHOTO);
			}
			
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode != Activity.RESULT_OK) return;
		
		if(requestCode == REQUEST_PHOTO)
		{
			initPicture(view);
		}
	}
		
		
		

	@Override
	public void onPause() {
		super.onPause();
		
		EditText editText = (EditText)view.findViewById(R.id.edittext_fridge_item_name);
		item.setName(editText.getText().toString());
		
		editText = (EditText)view.findViewById(R.id.edittext_fridge_item_desc);
		item.setDesc(editText.getText().toString());
		
		editText = (EditText)view.findViewById(R.id.edittext_fridge_item_date);
		item.setDate(editText.getText().toString());
		
		ImageView imageView = (ImageView)view.findViewById(R.id.imageview_fridge_item_photo);
		Log.i("Mike", "onPause items.size = " + items.size() + " and id = " + id);
		
		//item.setPhoto(items.size()); //id is -1
		
		
		if(id == -1){
			FridgeItemSet.addFridgeItem(item);
			id = items.size();
		}
		
		FridgeItemSet.getInstance(getActivity()).saveFridgeItems();
	}
	
}