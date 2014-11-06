package neva.eco.rules.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

import neva.eco.rules.core.Cell;
import neva.eco.rules.core.RulesAdd;
import neva.eco.rules.core.TableCell;
import neva.eco.rules.core.Variable;
import neva.eco.rules.files.TableTextReader;
import neva.eco.rules.json.LayoutJsonReader;
import neva.eco.rules.json.RulesJsonReader;
import neva.eco.rules.layout.LayoutItem;

public class testLayout {
	
	
	HashMap <String, TableCell> table;

	public static void main(String[] args) throws Exception {
		testLayout tst = new testLayout();
		tst.init();
		tst.test1();		
				
		
	}

	public void init ()
	{			
		
		table = new HashMap<String, TableCell> ();
		try {
			table.put("xp", TableTextReader.readFileScanner ( new File ("res/xp.txt"), 3) );
			table.put("cleric", TableTextReader.readFileScanner ( new File ("res/cleric.txt"), 13) );
			table.put("armor", TableTextReader.readFileScanner ( new File ("res/armor.txt"), 7) );
			table.put("weapons", TableTextReader.readFileScanner ( new File ("res/armor.txt"), 5) );
			table.put("cleric spells", TableTextReader.readFileScanner ( new File ("res/cleric spells.txt"), 9) );
			table.put("wizard spells", TableTextReader.readFileScanner ( new File ("res/wizards spells.txt"), 11) );
			table.put("abilityScore", TableTextReader.readFileScanner ( new File ("res/abilityScore.txt"), 2) );
			table.put("Classes", TableTextReader.readFileScanner ( new File ("res/classes.txt"), 6) );
			
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		

	}
	
	
	
	
	
	public void test1 () throws Exception
	{		
		System.out.println ("=========== test JSON ===========");
		
		HashMap <String, LayoutItem> var = LayoutJsonReader.jsonReader("res/layout.txt");
		
		// eval
		for (Map.Entry<String,LayoutItem> e : var.entrySet()){
			LayoutItem v = e.getValue();
			System.out.println ("=========== Name " + v.name + " ===========");
						
			System.out.println ("Item: " + v.title + " = [" + v.layoutClassName + "] ->" + v.variableName);
		}
		
		System.out.println ("=========== test WriterJSON ===========");
		LayoutJsonReader.jsonWriter("res/layout_w.txt", var);
	}
	
}
