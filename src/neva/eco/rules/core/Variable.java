package neva.eco.rules.core;

import java.util.HashMap;

/*
 * Classe d'entree pour les regles
 * une donnee est une variables 
 * chaque variable peut avoir une regle
 * une regle est le resultat de 2 operandes ( A et B )
 * une operande peut etre une varable
 */
public class Variable {
	
	public RulesInf rules;
	public String name;
	public long id;
	
	public String colRefvalue;
	public String tableName;
	public String colRef;
	public String colResult;
	
	static public final int TYPE_TABLE = 1;
	static public final int TYPE_VALUE = 2;
	static public final int TYPE_VARIABLE = 3;
	
	public int type=0;	
		
	public Cell value = new Cell (0);
	public boolean repeatable;	
	
	
	public Cell getValue() {
		return value;
	}

	public void setValue(Cell value) {
		this.value = value;
	}

	public void setValueFromTable ( int x, Cell table[]  )
	{
		value = table[x];
	}
	
	// eval recursif
	public Cell eval(HashMap<String, Variable> var, HashMap<String, TableCell> table)
	//public Cell eval ()
	{
		if ( rules != null )
		{
			// eval child first 
			
			if ( rules.getA().rules != null && !rules.getA ().rules.isAlreadyEval() ) rules.getA().value = rules.getA().eval(var, table);
			if ( rules.getB().rules != null && !rules.getB ().rules.isAlreadyEval() ) rules.getB().value = rules.getB().eval(var, table);
			// eval parent
			value = rules.eval_cell(var, table);
			return value;
		}
		return null;
	}

	public Cell setValueFromTable(TableCell tb, String colRef, String val, String colResult) {
		int colRefPos = tb.getColPosFromName ( colRef ); // get ref col pos
		int rowRef = tb.getRowPos ( colRefPos, val ); // return la row pos 
		
		if ( rowRef == -1 )			
		{
			System.out.println ( "setValueFromTable not found:" + colRef + "-" + val + "-" +colResult);
			return null; // not found
		}
		
		int colResultPos = tb.getColPosFromName ( colResult ); // get ref col pos
		Cell newval = tb.getRowValue ( colResultPos, rowRef );
		if ( newval != null ) this.value = newval;
		return newval;
		
	}


}
