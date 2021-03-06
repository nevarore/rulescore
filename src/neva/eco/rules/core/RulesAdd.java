package neva.eco.rules.core;

import java.util.HashMap;

public class RulesAdd implements RulesInf  {
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

	public RulesAdd ()
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

	// eval_cell n'est plus appele -> variable.eval ()
	public Cell eval_cell (HashMap<String, Variable> var, HashMap<String, TableCell> table)
	{
		int result;
		int valA=0, valB=0;

		switch ( A.type )
		{
		case 1: //table
			//if ( A.colRefvalue != null )
			//	A.colRefvalue = var.get(A.colRefvalue).value.getsValue();

			TableCell tb = table.get(A.tableName);
			if ( tb != null)
			{
				A.value = A.setValueFromTable(tb,A.colRef,A.colRefvalue,A.colResult);
				valA = A.value.getnValue();					
			}
			break;
		case 2:	 //Variable.TYPE_VALUE
			valA = A.value.getnValue();
			break;
		case 3:  //Variable.TYPE_VARIABLE 
			// getting variable
			if ( var.get ( A.name ) != null && (!var.get ( A.name ).rules.isAlreadyEval() || var.get ( B.name ).repeatable)) {
				Cell res = var.get ( A.name ).rules.eval_cell(var, table);
				var.get ( A.name ).value = res;				
			}
			valA = var.get ( A.name ).value.getnValue();
			break;
		}

		switch ( B.type )
		{
		case 1: //table
			//if ( B.colRefvalue != null )
			//	B.colRefvalue = var.get(B.colRefvalue).value.getsValue();

			TableCell tb = table.get(B.tableName);
			if ( tb != null)
			{
				B.value = B.setValueFromTable(tb,B.colRef,B.colRefvalue,B.colResult);
				valB = B.value.getnValue();					
			}
			break;
		case 2:	 //Variable.TYPE_VALUE
			valB = B.value.getnValue();
			break;
		case 3:  //Variable.TYPE_VARIABLE 
			// getting variable
			if ( var.get ( B.name ) != null && (!var.get ( B.name ).rules.isAlreadyEval() || var.get ( B.name ).repeatable)) {
				Cell res = var.get ( B.name ).rules.eval_cell(var, table);
				var.get ( B.name ).value = res;				
			}
			valB = var.get ( B.name ).value.getnValue();
			break;
		}


		System.out.println ("RulesAdd Eval: A=" + A.tableName + " " + valA + " B= " + B.tableName + " " + valB);

		result = valA + valB;		
		
		setAlreadyEval(true);	
		return new Cell ( result);

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
