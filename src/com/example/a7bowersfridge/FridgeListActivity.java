package com.example.a7bowersfridge;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FridgeListActivity extends SingleFragmentActivity implements FridgeListFragment.Callbacks, Constants {

	@Override
	protected Fragment createFragment() {
		
		return new FridgeListFragment();
	}

	@Override
	public void onFridgeItemSelected(int fridgeItemId) {
		if (findViewById(R.id.detail_fragment_container) == null) //portrait
		{
			Intent intent = new Intent(this, FridgeItemPagerActivity.class);
			intent.putExtra(EXTRA_DATA_ID, fridgeItemId);
			startActivity(intent);
		}else
		{ //landscape
			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			
			Fragment oldFragment = manager.findFragmentById(R.id.detail_fragment_container);
			Fragment newFragment = FridgeItemPagerFragment.newInstance(fridgeItemId);
			
			if (oldFragment != null) //if there was an item in the details
			{
				transaction.remove(oldFragment);
			}
			transaction.add(R.id.detail_fragment_container, newFragment);
			transaction.commit();
		}
	}


}
