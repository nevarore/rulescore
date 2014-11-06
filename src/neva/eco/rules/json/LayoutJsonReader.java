package neva.eco.rules.json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import neva.eco.rules.core.Cell;
import neva.eco.rules.core.RulesInf;
import neva.eco.rules.core.TableCell;
import neva.eco.rules.core.Variable;
import neva.eco.rules.layout.Bound;
import neva.eco.rules.layout.LayoutItem;



public class LayoutJsonReader {

	public static HashMap <String, LayoutItem> jsonReader ( String filename ) throws Exception
	{
		HashMap <String, LayoutItem> varList = new HashMap<String, LayoutItem>();
		FileReader reader = new FileReader(filename);


		//create JsonReader object
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonRoot;
		try {
			jsonRoot = (JSONObject) jsonParser.parse(reader);
		} catch (ParseException e) {			
			e.printStackTrace();
			throw new IOException();
		}
	
		
		JSONArray jsonVariables = (JSONArray) jsonRoot.get("layout");
		// take the elements of the json array
		for(int i=0; i<jsonVariables.size(); i++){
			System.out.println("The " + i + " element of the array: "+jsonVariables.get(i));
		}
		Iterator i = jsonVariables.iterator();

		// take each value from the json array separately
		while (i.hasNext()) {
			JSONObject jsonObject = (JSONObject) i.next();


			//Retrieve data from JsonObject and create Employee bean		
			LayoutItem var = new LayoutItem();

			var.id = (long) jsonObject.get("id");
			var.name = (String) jsonObject.get("name");
			var.title = (String) jsonObject.get("title");
			var.variableName = (String) jsonObject.get("variable");
			var.layoutClassName = (String) jsonObject.get("layout");
			
			//var.rules = RulesJsonReader.classLoader((String) jsonObject.get("rule"));

			//reading inner object from json object
			JSONObject innerJsonObjectA = (JSONObject) jsonObject.get("Bound");
			var.setBound( LayoutJsonReader.getJSonBound ( innerJsonObjectA ) );
				
			varList.put(var.name, var);
		}
		
        
        return varList;
	}
	/**
	 * "id":1,
    "name":"XP",    
    "title":"Exp",
    "layout":"neva.eco.rules.layout.Item"
    "variable":"XP"
	 * @param filename
	 * @param var
	 */
	public static void jsonWriter ( String filename, HashMap <String, LayoutItem> var) 
	{
		JSONObject obj = new JSONObject();
		obj.put("filename", filename);

		JSONArray list = new JSONArray();

		for (Map.Entry<String,LayoutItem> e : var.entrySet()){
			LayoutItem v = e.getValue();
			
			JSONObject item = new JSONObject();

			item.put("id",v.id);
			item.put("name",v.name);
			item.put("title",v.title);
			item.put("layout",v.layoutClassName);
			item.put("variable",v.variableName);
			
			JSONObject bound = new JSONObject();
			bound.put ("x", v.getBound().x);
			bound.put ("y", v.getBound().y);
			bound.put ("sizex", v.getBound().sizex);
			bound.put ("sizey", v.getBound().sizey);
			
			
			item.put("bound", bound);
			
			list.add(item); 
		}
		obj.put("layout", list);
		
		try {
			 
			FileWriter file = new FileWriter(filename);
			//file.write(obj.toJSONString());
			obj.writeJSONString(file);
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static RulesInf classLoader(String className ) throws InstantiationException, IllegalAccessException
	{

		ClassLoader classLoader = LayoutJsonReader.class.getClassLoader();

		try {
			Class aClass = classLoader.loadClass(className);
			System.out.println("aClass.getName() = " + aClass.getName());

			RulesInf object = (RulesInf) aClass.newInstance();
			return object;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	
	
	private static Bound getJSonBound(JSONObject innerJsonObjectA )
	{
		//reading inner object from json object		
		long x = (long) innerJsonObjectA.get("x");
		long y = (long) innerJsonObjectA.get("y");
		long sizex = (long) innerJsonObjectA.get("sizex");
		long sizey = (long) innerJsonObjectA.get("sizey");
		
		return new Bound ( x, y, sizex, sizey);
	}


}
