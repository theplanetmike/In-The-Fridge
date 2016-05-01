package com.example.a7bowersfridge;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class FridgeItemJSONSerializer 
{
	private Context context;
	private String fileName;
	
	public FridgeItemJSONSerializer(Context context, String fileName)
	{
		this.context = context;
		this.fileName = fileName;
	}
	
	public ArrayList<FridgeItem> loadFridgeItems() throws IOException, JSONException
	{
		ArrayList<FridgeItem> items = new ArrayList<FridgeItem>();
		BufferedReader reader = null;
		
		try {
			InputStream in = context.openFileInput(fileName);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while((line = reader.readLine()) != null)
			{
				jsonString.append(line);
			}
			
			JSONArray jsonArray = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			
			for(int i=0; i < jsonArray.length(); i++)
			{
				items.add(new FridgeItem(jsonArray.getJSONObject(i)));
			}
		}
		catch(FileNotFoundException e)
		{
			//this happens when starting a new file. it's ok
		}
		finally {
			if(reader != null)
			{
				reader.close();
			}
		}
		
		return items;
	}
	
	public void saveFridgeItems(ArrayList<FridgeItem> items)
		throws JSONException, IOException
	{
		JSONArray jsonArray = new JSONArray();
		for(FridgeItem item : items)
		{
			jsonArray.put(item.toJSON());
		}
		
		Writer writer = null;
		
		try {
			OutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(jsonArray.toString());
		}
		finally {
			if(writer != null)
				writer.close();
		}
	}
}
