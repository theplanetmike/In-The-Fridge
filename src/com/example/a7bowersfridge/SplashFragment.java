package com.example.a7bowersfridge;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SplashFragment extends Fragment {
	
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_splash, container, false);
		
		initImage();
		
		return view;
	}
	
	private void initImage()
	{
		ImageView imageView = (ImageView)view.findViewById(R.id.title);
		imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getActivity(), FridgeListActivity.class);
				startActivity(intent);
			}
		});
	}
}
