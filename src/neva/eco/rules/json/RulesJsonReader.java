package neva.eco.rules.json;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import neva.eco.rules.core.RulesInf;
import neva.eco.rules.core.TableCell;
import neva.eco.rules.core.Variable;



public class RulesJsonReader {

	public static HashMap <String, Variable> jsonReader ( String filename, HashMap<String, TableCell> table ) throws IOException, InstantiationException, IllegalAccessException, ParseException
	{
		HashMap <String, Variable> varList = new HashMap<String, Variable>();
		FileReader reader = new FileReader(filename);


		//create JsonReader object
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonRoot = (JSONObject) jsonParser.parse(reader);


		/**
		 * We can create JsonReader from Factory also
			JsonReaderFactory factory = Json.createReaderFactory(null);
			jsonReader = factory.createReader(fis);
		 */

		//get JsonObject from JsonReader
		/*JsonObject jsonObject = jsonReader.readObject();

		//we can close IO resource and JsonReader now
		jsonReader.close();
		fis.close();*/
		
		JSONArray jsonVariables = (JSONArray) jsonRoot.get("variables");
		// take the elements of the json array
		for(int i=0; i<jsonVariables.size(); i++){
			System.out.println("The " + i + " element of the array: "+jsonVariables.get(i));
		}
		Iterator i = jsonVariables.iterator();

		// take each value from the json array separately
		while (i.hasNext()) {
			JSONObject jsonObject = (JSONObject) i.next();


			//Retrieve data from JsonObject and create Employee bean		
			Variable var = new Variable();

			var.id = (long) jsonObject.get("id");
			var.name = (String) jsonObject.get("name");
			var.rules = RulesJsonReader.classLoader((String) jsonObject.get("rule"));

			//reading inner object from json object
			JSONObject innerJsonObjectA = (JSONObject) jsonObject.get("A");
			String typeA = (String) innerJsonObjectA.get("type");

			// type getTable value
			// thaco.rules.getA().setValueFromTable(2, thaco_level_wiz); // level 2
			if ( typeA.equals("table"))
			{
				String value = (String) innerJsonObjectA.get("value");
				String tableName =  (String) innerJsonObjectA.get("tablename");
				String colRef =  (String) innerJsonObjectA.get("colref");
				String colResult =  (String) innerJsonObjectA.get("colresult");

				TableCell tb = table.get(tableName);
				if ( tb != null)
				{
					var.rules.getA().setValueFromTable(tb,colRef,value,colResult);
					System.out.println ("JSON assign to " + var.name + " = " + var.rules.getA().value.getsValue());
				}
			}
			
			//reading inner object from json object
			JSONObject innerJsonObjectB = (JSONObject) jsonObject.get("B");
			String typeB = (String) innerJsonObjectB.get("type");

			// type getTable value
			// thaco.rules.getA().setValueFromTable(2, thaco_level_wiz); // level 2
			if ( typeB.equals("table"))
			{
				String value = (String) innerJsonObjectB.get("value");
				String tableName =  (String) innerJsonObjectB.get("tablename");
				String colRef =  (String) innerJsonObjectB.get("colref");
				String colResult =  (String) innerJsonObjectB.get("colresult");

				TableCell tb = table.get(tableName);
				if ( tb != null)
				{
					var.rules.getB().setValueFromTable(tb,colRef,value,colResult);
					System.out.println ("JSON assign to " + var.name + " = " + var.rules.getB().value.getsValue());
				}
			}

			varList.put(var.name, var);
		}
		
        
        return varList;
	}

	public static RulesInf classLoader(String className ) throws InstantiationException, IllegalAccessException
	{

		ClassLoader classLoader = RulesJsonReader.class.getClassLoader();

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

}
