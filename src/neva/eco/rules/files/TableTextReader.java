package neva.eco.rules.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import neva.eco.rules.core.Cell;
import neva.eco.rules.core.TableCell;

public class TableTextReader {
	
	private static final String COMMA_DELIMETER = ";";
    private static volatile Pattern commaPattern = Pattern
            .compile(COMMA_DELIMETER);
    
    private static final String LINE_SEPARATOR_PATTERN = "\r\n|[\n\r\u2028\u2029\u0085]";
    private static final String LINE_PATTERN = ".*(" + LINE_SEPARATOR_PATTERN  + ")|.+$";
    private static volatile Pattern linePattern = Pattern.compile(LINE_PATTERN);

	static void readFromText ( String filename )
	{
		String thisLine;
		//Open the file for reading
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			while ((thisLine = br.readLine()) != null) { // while loop begins here
				
				System.out.println(thisLine);
			} // end while 
		} // end try
		catch (IOException e) {
			System.err.println("Error: " + e);
		}
	}
	
	public static TableCell readFileScanner(File file, int colCount)
            throws IOException {
        
		TableCell table = new TableCell ( colCount );
		
        try (Scanner br = new Scanner(file, "UTF-8")) 
        {
        	boolean firstLine = true;
        	//int colCount = 0;
        	String data;
        	int line = 0;
        	
            while (br.hasNext()) {
            	br.useDelimiter(commaPattern);
            	
            	if ( firstLine )
            	{            	         	
            		for ( int c = 0; c<colCount; c++)
            		{
            			String header = br.next();
            			System.out.println("Col Name:" + header);
            			table.headerCol[c] = header;
            			
            		}
            		firstLine = false;            		
            	} else
            	{        	
            		for ( int c = 0; c<colCount; c++)
            		{
            			data = br.next();
            			System.out.println("data col :" + c + "=>" + data + "<=");
            			table.col.get(c).put(line, new Cell ( data));
            			
            		}               
            	}
            	br.useDelimiter(linePattern);
            	br.nextLine();
            	line ++;
            }
        }
        return table;
    }


	
	

}
