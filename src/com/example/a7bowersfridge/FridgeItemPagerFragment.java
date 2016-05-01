package com.example.a7bowersfridge;

import java.util.ArrayList;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class FridgeItemPagerFragment extends Fragment implements Constants
{
	private FridgeItem item;
	//private static ArrayList<FridgeItem> items = new ArrayList<FridgeItem>();
	//private String photoName = "";
	private int id;
	private View view;
	
	public static FridgeItemPagerFragment newInstance(int id)
	{ //returns a FridgeItemPagerFragment with save state data set as an arg
		Bundle args = new Bundle();
		args.putInt(EXTRA_DATA_ID, id);
		FridgeItemPagerFragment dFrag = new FridgeItemPagerFragment();
		dFrag.setArguments(args);
		
		return dFrag;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{ //puts the instance's id... string in item?
		super.onCreate(savedInstanceState);
		

		id = getArguments().getInt(EXTRA_DATA_ID);
		//id = getActivity().getIntent().getIntExtra(EXTRA_DATA_ID, 0);
		
		if(id == -1)
		{
			item = new FridgeItem("", "", "", "");
		}
		else
		{
			item = FridgeItemSet.getInstance(getActivity()).getFridgeItems().get(id);
		}
	
		//int id = getArguments().getInt(EXTRA_DATA_ID);
		//item = FridgeItemSet.getInstance(getActivity()).getFridgeItems().get(id);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_item, container, false);
		Log.i("Mike", "in onCreateView");
		initName(view);
		initDesc(view);
		initDate(view);
		initPicture(view);
		return view;
	}
	
	private void initName(View view)
	{
		TextView textView = (TextView)view.findViewById(R.id.textview_fridge_item_name);
		textView.setText(item.getName());
	}
	
	private void initDesc(View view)
	{
		TextView textView = (TextView)view.findViewById(R.id.textview_fridge_item_desc);
		textView.setText(item.getDesc());
	}
	
	private void initDate(View view)
	{
		TextView textView = (TextView)view.findViewById(R.id.textview_fridge_item_date);
		textView.setText(item.getDate());
	}
	
	private void initPicture(View view)
	{
		ImageView photoView = (ImageView)view.findViewById(R.id.imageview_fridge_item_photo);
		//TextView detailsLink = (TextView)view.findViewById(R.id.textview_item_desc);

		String photoFileName = item.getPhoto();
		Log.i("Mike", "pagerfrag initPicture has photoFileName = " + photoFileName);
		
		BitmapDrawable bitmap = null;
		
		//don't go any farther and try to look for a file that we know doesn't exist
		if(photoFileName == null) {
			return;
		}
		
		if(getActivity().getFileStreamPath(photoFileName) == null) {
			return;
		}
	
		String path = getActivity().getFileStreamPath(photoFileName).getAbsolutePath();
		if(path == null) return;
		
		bitmap = PictureUtils.getScaledDrawable(getActivity(), path);
		
		if(bitmap != null) photoView.setImageDrawable(bitmap);
	}
	
}







