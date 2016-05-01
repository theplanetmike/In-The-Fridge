package com.example.a7bowersfridge;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;


public class FridgeListFragment extends ListFragment implements Constants {
	ArrayList<FridgeItem> items;
	private Callbacks callbacks;
	
	public interface Callbacks
	{
		public void onFridgeItemSelected(int fridgeItemId);
	}
	
	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		callbacks = (Callbacks)activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		callbacks=null;
	}
	
	public void updateUI(){
		((FridgeItemAdapter)getListAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		items = FridgeItemSet.getInstance(getActivity()).getFridgeItems();
		
		ArrayAdapter<FridgeItem> adapter = new FridgeItemAdapter(items);
		
		setListAdapter(adapter);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		ListView listView = (ListView)view.findViewById(android.R.id.list);
		registerForContextMenu(listView);
		
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fridge_item_list, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.menu_item_new_item:
				Intent intent = new Intent(getActivity(), FridgeItemAddActivity.class);
				intent.putExtra(EXTRA_DATA_ID, -1);
				startActivity(intent);

				//callbacks.onFridgeItemSelected(position);
				
				Log.i("Mike", "In menu_item_new_item"); //works!
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.fridge_item_list_context, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		int position = info.position;
		FridgeItemAdapter adapter = (FridgeItemAdapter)getListAdapter();
		FridgeItem fridgeItem = adapter.getItem(position);
		
		switch(item.getItemId())
		{
			case R.id.menu_item_delete_item:
				FridgeItemSet.deleteFridgeItem(fridgeItem);
				adapter.notifyDataSetChanged();
				return true;
		}
		
		return super.onContextItemSelected(item);
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);

		callbacks.onFridgeItemSelected(position);
		
		//Intent intent = new Intent(getActivity(), FridgeItemPagerActivity.class);
		//intent.putExtra(EXTRA_DATA_ID, position);
		//startActivity(intent);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((FridgeItemAdapter) getListAdapter()).notifyDataSetChanged();
	}
	
	private class FridgeItemAdapter extends ArrayAdapter<FridgeItem>
	{
		public FridgeItemAdapter(ArrayList<FridgeItem> items){
			super(getActivity(), 0, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_list, null);
			}
			
			FridgeItem item = getItem(position);
			
			TextView titleView = (TextView)convertView.findViewById(R.id.textview_item_name);
			titleView.setText(item.getName());
			
			//TextView descView = (TextView)convertView.findViewById(R.id.textview_item_desc);
			//descView.setText(item.getDesc());
			
			TextView dateView = (TextView)convertView.findViewById(R.id.textview_item_date);
			dateView.setText(item.getDate());
			
			//want an imageview icon here in the list?
			
			return convertView;
		}		
		
	}
}





