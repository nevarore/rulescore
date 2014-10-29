package neva.eco.rules.core;

import java.util.HashMap;

public interface RulesInf {

	public abstract Variable getA();

	public abstract void setA(Variable a);

	public abstract Variable getB();

	public abstract void setB(Variable b);

	public abstract Variable eval();
	public abstract Cell eval_cell(HashMap<String, Variable> var, HashMap<String, TableCell> table);

	public abstract boolean isAlreadyEval();
	public abstract void setAlreadyEval(boolean alreadyEval) ;
}