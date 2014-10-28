package neva.eco.rules.core;

public class Cell {
	int nValue=0;
	String sValue="";
	float fValue=0.0f;
	
	public Cell ( int val)
	{
		nValue = val;
	}
	
	public Cell ( String val)
	{
		sValue = val;
	}
	
	public int getnValue() {
		return nValue;
	}
	public void setnValue(int nValue) {
		this.nValue = nValue;
	}
	public String getsValue() {
		return sValue;
	}
	public void setsValue(String sValue) {
		this.sValue = sValue;
	}
	public float getfValue() {
		return fValue;
	}
	public void setfValue(float fValue) {
		this.fValue = fValue;
	}
	
	

}
