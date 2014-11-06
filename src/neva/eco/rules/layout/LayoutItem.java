package neva.eco.rules.layout;

public class LayoutItem {
	public long id;
	public String name;
	public Bound bound;
	
	public String title;
	public String layoutClassName;
	public String variableName;
	
	public LayoutItem() {		
	}
	
	public Bound getBound() {
		return bound;
	}

	public void setBound(Bound bound) {
		this.bound = bound;
	}

	

}
