package neva.eco.rules.core;

public class Cell {
	private int nValue=0;
	private String sValue="";
	private float fValue=0.0f;
	
	public Cell ( int val)
	{		
		setnValue(val);
	}
	
	public Cell ( String val)
	{		
		setsValue(val);
	}
	
	public int getnValue() {
		return nValue;
	}
	public void setnValue(int nValue) {
		this.nValue = nValue;
		this.sValue = String.valueOf(nValue);
	}
	public String getsValue() {
		return sValue;
	}
	public void setsValue(String sValue) {
		this.sValue = sValue;
		try {
			this.nValue = Integer.parseInt(sValue);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		
	}
	public float getfValue() {
		return fValue;
	}
	public void setfValue(float fValue) {
		this.fValue = fValue;
	}
	
	

}
