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
		System.out.println ("Eval: A=" + A.tableName + " " + A.value.getnValue() + " B= " + B.tableName + " " + B.value.getnValue());
				
		int result = rollDice (A.value.getnValue(), B.value.getnValue() );
		Variable vRes = new Variable ();
		
		setAlreadyEval(true);
		return new Cell ( result );
		
	}
	
	int rollDice ( int nb, int nDice )
	{
		int result = 0;
		
		//note a single Random object is reused here
	    Random randomGenerator = new Random();
	    for (int idx = 1; idx <= nb; ++idx){
	      int randomInt = randomGenerator.nextInt(nDice)+1;
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

}
