package com.example.a7bowersfridge;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class FridgeItemPagerActivity extends FragmentActivity implements Constants
{
	private ViewPager viewPager;
	private ArrayList<FridgeItem> data = FridgeItemSet.getInstance(this).getFridgeItems();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		viewPager = new ViewPager(this);
		viewPager.setId(R.id.viewPager);
		setContentView(viewPager);
		
		//data  = FridgeItemSet.getInstance(this).getFridgeItems();
		
		FragmentManager manager = getSupportFragmentManager();
		viewPager.setAdapter(new FragmentStatePagerAdapter(manager){

			@Override
			public Fragment getItem(int position) {
				//Log.i("Mike", "in getItem");
				return FridgeItemPagerFragment.newInstance(position);
			}

			@Override
			public int getCount() {
				return data.size();
				//return 1; //testing with a value
			}
			
		});
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}

			@Override
			public void onPageSelected(int pos) {
				FridgeItem item = data.get(pos);
				setTitle(item.getName()); //sets the title bar -- works!
			}
			
		});
		
		int id = getIntent().getIntExtra(EXTRA_DATA_ID, 0);
		viewPager.setCurrentItem(id);
	}
}