package neva.eco.rules.test;

import java.io.File;
import java.io.IOException;

import neva.eco.rules.core.Cell;
import neva.eco.rules.core.RulesAdd;
import neva.eco.rules.core.Variable;
import neva.eco.rules.files.TableTextReader;

public class testCore {
	int THACO_LEVEL_WIZ []= {20, 20, 19, 19, 18, 18, 17, 17, 16, 16 };
	Cell [] thaco_level_wiz;
	
	int THACO_FORCE_BONUS []= {-3, -3, -2, -2, -2, -1, -1, -1, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	Cell [] thaco_force_bonus;
	
	int SW_BONUS []= {-3, -3, -2, -2, -2, -1, -1, -1, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
	Cell [] sw_bonus;
	
	Variable thaco, thaco_sw;

	public static void main(String[] args) throws IOException {
		testCore tst = new testCore();
		tst.initTable();
		tst.test1();
		tst.test2();
		
		TableTextReader.readFileScanner ( new File ("res/xp.txt"), 3);

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
		
		thaco_sw.value = thaco_sw.eval ();
		
		System.out.println ("THACO = " + thaco_sw.rules.getA().value.getnValue());
		System.out.println ("THACO SW = " + thaco_sw.value.getnValue());
		
		
		
	}
	
}
