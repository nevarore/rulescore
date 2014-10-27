package neva.eco.rules.core;

public interface RulesInf {

	public abstract Variable getA();

	public abstract void setA(Variable a);

	public abstract Variable getB();

	public abstract void setB(Variable b);

	public abstract Variable eval();
	public abstract Cell eval_cell();

}