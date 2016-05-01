package com.example.a7bowersfridge;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;


public class FridgeItemSet {
	private static FridgeItemSet fridgeItemSet;
	private static ArrayList<FridgeItem> items = new ArrayList<FridgeItem>();
	private static final String FILE_NAME = "items.json";
	private Context context;
	private FridgeItemJSONSerializer serializer;
	
	private FridgeItemSet(Context context)
	{//needs a context and file name
		this.context = context;
		serializer = new FridgeItemJSONSerializer(context, FILE_NAME);
		
		try{
		items = serializer.loadFridgeItems();
		} catch (Exception e) {
			items = new ArrayList<FridgeItem>();
			Log.e("Error", "file trouble");
		}
		
	}
	
	public boolean saveFridgeItems()
	{
		
		try{
			serializer.saveFridgeItems(items);
			return true;
		} catch (Exception e) {
			Log.e("Error", "not worthy of saving");
			return false;
		}
	}
	
	public static FridgeItemSet getInstance(Context context)
	{
		if(fridgeItemSet == null)
		{
			fridgeItemSet = new FridgeItemSet(context);
		}
		
		return fridgeItemSet;
	}
	
	public ArrayList<FridgeItem> getFridgeItems()
	{
		return items;
	}
	
	public static void addFridgeItem(FridgeItem item)
	{
		items.add(item);
	}
	
	public static void deleteFridgeItem(FridgeItem item)
	{
		items.remove(item);
	}

}



