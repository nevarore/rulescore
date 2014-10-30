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
import neva.eco.rules.json.RulesJsonReader;

public class testCore {
	int THACO_LEVEL_WIZ []= {20, 20, 19, 19, 18, 18, 17, 17, 16, 16 };
	Cell [] thaco_level_wiz;
	
	int THACO_FORCE_BONUS []= {-3, -3, -2, -2, -2, -1, -1, -1, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	Cell [] thaco_force_bonus;
	
	int SW_BONUS []= {-3, -3, -2, -2, -2, -1, -1, -1, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	Cell [] sw_bonus;
	
	Variable thaco, thaco_sw;
	
	HashMap <String, TableCell> table;

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ParseException {
		testCore tst = new testCore();
		tst.initTable();
		tst.test1();
		tst.test2();
		tst.test3();
				
		
	}

	public void initTable ()
	{		
		thaco_level_wiz = new Cell [ THACO_LEVEL_WIZ.length ];

		for (int i=0; i< THACO_LEVEL_WIZ.length; i++ )
		{
			thaco_level_wiz[i] = new Cell ( THACO_LEVEL_WIZ[i]);
		}
		
		thaco_force_bonus = new Cell [ THACO_FORCE_BONUS.length ];

		for (int i=0; i< THACO_FORCE_BONUS.length; i++ )
		{
			thaco_force_bonus[i] = new Cell ( THACO_FORCE_BONUS[i]);
		}
		
		sw_bonus = new Cell [ SW_BONUS.length ];

		for (int i=0; i< SW_BONUS.length; i++ )
		{
			sw_bonus[i] = new Cell ( SW_BONUS[i]);
		}
		
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
	
	public void test1 ()
	{
		thaco = new Variable();
		thaco.name = "THACO";
		
		thaco.rules = new RulesAdd();
		thaco.rules.getA().setValueFromTable(2, thaco_level_wiz); // level 2
		thaco.rules.getB().setValueFromTable(18, thaco_force_bonus); // force 18
		
		//thaco.value = thaco.rules.eval_cell ();
		
		System.out.println ("THACO_LVL = " + thaco.rules.getA().value.getnValue());
		System.out.println ("THACO_BONUS = " + thaco.rules.getB().value.getnValue());
		System.out.println ("THACO = " + thaco.value.getnValue());
		
	
	}
	
	public void test2 ()
	{
		//if ( thaco == null ) return ;
		
		System.out.println ("=========== test THACO + sw bonus ===========");
		
		thaco_sw = new Variable();
		thaco_sw.name = "THACO+SW";
		
		thaco_sw.rules = new RulesAdd();
		thaco_sw.rules.setA(thaco);
		thaco_sw.rules.getB().setValueFromTable(18, sw_bonus); // force 18
		
		thaco_sw.value = thaco_sw.eval (null, null);
		
		System.out.println ("THACO = " + thaco_sw.rules.getA().value.getnValue());
		System.out.println ("THACO SW = " + thaco_sw.value.getnValue());
		
		
		
	}
	
	public void test3 () throws InstantiationException, IllegalAccessException, IOException, ParseException
	{		
		System.out.println ("=========== test JSON ===========");
		
		HashMap <String, Variable> var = RulesJsonReader.jsonReader("res/variables.txt", table);
		
		// eval
		for (Map.Entry<String,Variable> e : var.entrySet()){
			Variable v = e.getValue();
			System.out.println ("=========== Eval " + v.name + " ===========");
			if ( !v.rules.isAlreadyEval() || v.repeatable ) v.eval ( var, table);
			
			System.out.println ("Variable: " + v.name + " = [" + v.value.getsValue() + "] ->" + v.value.getnValue());
		}
		
		// final var status 
		System.out.println ("=========== Final Result ===========");
		for (Map.Entry<String,Variable> e : var.entrySet()){
			Variable v = e.getValue();
			
			System.out.println ("Variable: " + v.name + " = [" + v.value.getsValue() + "] ->" + v.value.getnValue());
		}
		
		//System.out.println ("THACO = " + thaco_sw.rules.getA().value.getnValue());
		//System.out.println ("THACO SW = " + thaco_sw.value.getnValue());
		
		
		
	}
	
}
