package com.example.a7bowersfridge;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FridgeItem implements Constants {
	private String name;
	private String desc;
	private String date;
	private String photo;



	public FridgeItem(String name, String desc, String date, String photo)
	{
		this.name = name;
		this.desc = desc;
		this.date = date;
		this.photo = photo;
	}



	public FridgeItem(JSONObject json) throws JSONException
	{
		if(json.has(JSON_ITEM_NAME))
		{
			name = json.getString(JSON_ITEM_NAME);
		}
		if(json.has(JSON_ITEM_DESC))
		{
			desc = json.getString(JSON_ITEM_DESC);
		}
		if(json.has(JSON_ITEM_DATE))
		{
			date = json.getString(JSON_ITEM_DATE);
		}
		if(json.has(JSON_ITEM_PHOTO))
		{
			photo = json.getString(JSON_ITEM_PHOTO);
		}
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		json.put(JSON_ITEM_NAME, name);
		json.put(JSON_ITEM_DESC, desc);
		json.put(JSON_ITEM_DATE, date);
		json.put(JSON_ITEM_PHOTO, photo);
		
		return json;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}
	
	
	public String getPhoto() {
		return photo;
	}



	public void setPhoto(int id) {
		this.photo = "fridgeitem" + id + ".jpg";
		Log.i("Mike", "in fridgeitem photo = " + this.photo);
	}


	public String toString()
	{
		return name;
	}
}












