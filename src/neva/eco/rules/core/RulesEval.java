package neva.eco.rules.core;

import java.util.HashMap;

public class RulesEval implements RulesInf  {
	public Variable A;
	public Variable B;
	private boolean alreadyEval = false;
	
	private Cell cell[];

	public Cell[] getCell() {
		return cell;
	}

	public void setCell(Cell[] cell) {
		this.cell = cell;
	}
	
	public RulesEval ()
	{
		A = new Variable();
		B = new Variable();
	}
	
	// N/A pour le moment -> eval_cell
	public Variable eval ()
	{
		int result = A.value.getnValue() + B.value.getnValue();
		Variable vRes = new Variable ();
		vRes.setValue(new Cell ( result));
		
		return vRes;
	}
	
	// A = table to look inside
	// B = variable to evaluate and use as parameter for B
	public Cell eval_cell (HashMap<String, Variable> var, HashMap<String, TableCell> table)
	{
		System.out.println ("RuleEval: A=" + A.name + " " + " B = " + B.name );
				
		// look for B variable and assign it
		// this following code make a copy but don't use original
		/*Variable newB = var.get ( B.name );
		if ( newB.rules != null ) newB.rules.eval_cell(var, table);
		B = newB;*/
		
		// getting variable
		if ( var.get ( B.name ) != null && (!var.get ( B.name ).rules.isAlreadyEval() || var.get ( B.name ).repeatable) ) {
			Cell res = var.get ( B.name ).rules.eval_cell(var, table);
			var.get ( B.name ).value = res;			
		}

		TableCell tb = table.get(A.tableName);
		if ( tb != null)
		{
			A.setValueFromTable(tb,A.colRef,
					var.get ( B.name ).value.getsValue(),A.colResult);
			System.out.println ("RulesEval JSON assign to " + A.name + " = " + A.value.getsValue());
		} else System.out.println ("Table Not found " + A.tableName); 
		return A.value;
		
	}

	/* (non-Javadoc)
	 * @see core.RulesInf#getA()
	 */
	@Override
	public Variable getA() {
		return A;
	}

	/* (non-Javadoc)
	 * @see core.RulesInf#setA(core.Variable)
	 */
	@Override
	public void setA(Variable a) {
		A = a;
	}

	/* (non-Javadoc)
	 * @see core.RulesInf#getB()
	 */
	@Override
	public Variable getB() {
		return B;
	}

	/* (non-Javadoc)
	 * @see core.RulesInf#setB(core.Variable)
	 */
	@Override
	public void setB(Variable b) {
		B = b;
	}
	@Override
	public boolean isAlreadyEval() {
		return alreadyEval;
	}
	@Override
	public void setAlreadyEval(boolean alreadyEval) {
		this.alreadyEval = alreadyEval;
	}

}
