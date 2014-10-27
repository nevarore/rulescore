package neva.eco.rules.core;

public class RulesAdd implements RulesInf  {
	public Variable A;
	public Variable B;
	
	public RulesAdd ()
	{
		A = new Variable();
		B = new Variable();
	}
	
	// N/A pour le moment -> eval_cell
	public Variable eval ()
	{
		int result = A.value.nValue + B.value.nValue;
		Variable vRes = new Variable ();
		vRes.setValue(new Cell ( result));
		
		return vRes;
	}
	
	// eval_cell n'est plus appele -> variable.eval ()
	public Cell eval_cell ()
	{
		int result = A.value.nValue + B.value.nValue;
		Variable vRes = new Variable ();
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

}
