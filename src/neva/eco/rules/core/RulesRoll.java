package neva.eco.rules.core;

import java.util.HashMap;
import java.util.Random;

public class RulesRoll implements RulesInf  {
	public Variable A;
	public Variable B;
	
	private boolean alreadyEval = false;
	
	public RulesRoll ()
	{
		A = new Variable();
		B = new Variable();
	}
	
	// N/A pour le moment -> eval_cell
	public Variable eval ()
	{
		int result = rollDice ( A.value.getnValue(), B.value.getnValue() );
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
			case 2:	 //Variable.TYPE_VALUE
				valA = A.value.getnValue();
				break;
			case 3:  //Variable.TYPE_VARIABLE 
				// getting variable
				if ( var.get ( A.name ) != null && (!var.get ( A.name ).rules.isAlreadyEval() || var.get ( B.name ).repeatable) ) {
					Cell res = var.get ( A.name ).rules.eval_cell(var, table);
					var.get ( A.name ).value = res;					
				}
				valA = var.get ( A.name ).value.getnValue();
				break;
		}
		
		switch ( B.type )
		{
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
				
		
		System.out.println ("RulesRoll Eval: A=" + A.name + " " + valA + " B= " + B.name + " " + valB);
		
		result = rollDice (valA, valB );				
		setAlreadyEval(true);			
		
		return new Cell ( result );
		
	}
	
	int rollDice ( int nb, int nDice )
	{
		int result = 0;
		
		//note a single Random object is reused here
		Random randomGenerator = new Random();
		for (int idx = 1; idx <= nb; ++idx)
		{
			int randomInt = 0;
			try {
				randomInt = randomGenerator.nextInt(nDice)+1;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result += randomInt;
			System.out.println("Generated : " + randomInt);
		}

		return result;
		
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

	public boolean isAlreadyEval() {
		return alreadyEval;
	}

	public void setAlreadyEval(boolean alreadyEval) {
		this.alreadyEval = alreadyEval;
	}

	@Override
	public Cell[] getCell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCell(Cell[] cell) {
		// TODO Auto-generated method stub
		
	}

}
