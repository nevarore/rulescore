package neva.eco.rules.core;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

public class TableCell {
	
	public String [] headerCol;
	
	public ArrayList<HashMap<Integer, Cell>> col;
	
	int colCount;	
	
	public TableCell ( int colCount )
	{
		headerCol = new String [colCount];
		this.colCount = colCount;
		
		// allocate col		
		col = new ArrayList<HashMap<Integer, Cell>>();
		for ( int i=0; i<colCount; i++)
			col.add(i, new HashMap<Integer, Cell> () );
		
	}

}
