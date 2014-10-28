package neva.eco.rules.core;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	public int getColPosFromName ( String name )
	{		
		
		for ( int i=0; i< headerCol.length; i++)
		{
			if ( headerCol[i].equalsIgnoreCase(name) )
				return i;
		}
		return -1; // not found
	}

	/**
	 * return row pos from colonne position on arraylist 
	 * look for equal value
	 * @param colRefPos
	 * @param rowRef
	 * @return
	 */
	public int getRowPos(int colRefPos, String rowRefValue) {
		HashMap <Integer, Cell> colonne = col.get(colRefPos);
		int pos = 1;
		
		for (Map.Entry<Integer,Cell> e : colonne.entrySet()){
			Cell cell = e.getValue();
			if ( cell.sValue.equalsIgnoreCase(rowRefValue) )
			{
				System.out.println("getRowPos key: " + e.getKey() + " : " + e.getValue() + " Pos= " + pos);
				return pos;
			}
			pos++;
		}
		return -1;
	}
	
	public Cell  getRowValue(int colRefPos, int rowRefPos) {
		HashMap <Integer, Cell> colonne = col.get(colRefPos);
		
		Cell value = colonne.get(rowRefPos);
		
		return value;
	}

}
