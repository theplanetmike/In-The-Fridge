package com.example.a7bowersfridge;

import android.support.v4.app.Fragment;
import android.util.Log;

public class FridgeItemAddActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new FridgeItemAddFragment();
	}
	
}