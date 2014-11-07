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

import neva.eco.rules.core.Cell;
import neva.eco.rules.core.RulesInf;
import neva.eco.rules.core.TableCell;
import neva.eco.rules.core.Variable;
import neva.eco.rules.layout.Bound;
import neva.eco.rules.layout.LayoutItem;



public class RulesJsonReader {
	public static HashMap <String, Variable> jsonReader ( String filename, HashMap<String, TableCell> table ) throws IOException, InstantiationException, IllegalAccessException
	{
		HashMap <String, Variable> varList = new HashMap<String, Variable>();
		FileReader reader = new FileReader(filename);


		//create JsonReader object
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonRoot;
		try {
			jsonRoot = (JSONObject) jsonParser.parse(reader);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new IOException();
		}


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
			var.repeatable = (boolean) jsonObject.get("repeatable");
			
			var.rules = LayoutJsonReader.classLoader((String) jsonObject.get("rule"));

			//reading inner object from json object
			JSONObject innerJsonObjectA = (JSONObject) jsonObject.get("A");
			var.rules.setA( RulesJsonReader.getJSonRule ( innerJsonObjectA, var.rules.getA(), table, varList ) );
	
			//reading inner object from json object
			JSONObject innerJsonObjectB = (JSONObject) jsonObject.get("B");
			var.rules.setB( RulesJsonReader.getJSonRule ( innerJsonObjectB, var.rules.getB(), table, varList ) );

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
	
	private static Variable getJSonRule(JSONObject innerJsonObjectA,
			Variable A, HashMap<String, TableCell> table, HashMap <String, Variable> varList) 
	{
		//reading inner object from json object		
		String typeA = (String) innerJsonObjectA.get("type");

		// type getTable value
		// thaco.rules.getA().setValueFromTable(2, thaco_level_wiz); // level 2
		if ( typeA.equals("table"))
		{
			A.type = Variable.TYPE_TABLE;
			A.colRefvalue= (String) innerJsonObjectA.get("value");
			A.tableName =  (String) innerJsonObjectA.get("tablename");
			A.colRef =  (String) innerJsonObjectA.get("colref");
			A.colResult =  (String) innerJsonObjectA.get("colresult");
			String variable2GetValue =  (String) innerJsonObjectA.get("variable");
			
			if ( variable2GetValue != null )
				A.colRefvalue = varList.get(variable2GetValue).value.getsValue();

			TableCell tb = table.get(A.tableName);
			if ( tb != null)
			{
				A.setValueFromTable(tb,A.colRef,
						A.colRefvalue,A.colResult);
				System.out.println ("JSON assign to " + A.name + " = " + A.value.getsValue());
			}
		}
		
		if ( typeA.equals("value"))
		{
			A.type = Variable.TYPE_VALUE;
			A.value = new Cell ((String) innerJsonObjectA.get("value"));
			A.name  =  (String) innerJsonObjectA.get("name");				
		}
		
		if ( typeA.equals("variable"))
		{
			A.type = Variable.TYPE_VARIABLE;
			A.value = new Cell ((String) innerJsonObjectA.get("value"));
			A.name  =  (String) innerJsonObjectA.get("name");				
		}
		
		return A;
	}
	
	
}
