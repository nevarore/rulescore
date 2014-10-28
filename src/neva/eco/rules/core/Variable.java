package neva.eco.rules.core;

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
	
	public Cell value = new Cell (0);
	
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
	public Cell eval ()
	{
		if ( rules != null )
		{
			// eval child first 
			if ( rules.getA().rules != null ) rules.getA().value = rules.getA().eval();
			if ( rules.getB().rules != null ) rules.getB().value = rules.getB().eval();
			// eval parent
			value = rules.eval_cell();
			return value;
		}
		return null;
	}

	public void setValueFromTable(TableCell tb, String colRef, String val, String colResult) {
		int colRefPos = tb.getColPosFromName ( colRef ); // get ref col pos
		int rowRef = tb.getRowPos ( colRefPos, val ); // return la row pos 
		
		int colResultPos = tb.getColPosFromName ( colResult ); // get ref col pos
		this.value = tb.getRowValue ( colResultPos, rowRef );
		
	}
	

}
