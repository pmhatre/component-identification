package org.apache.mahout.clustering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * 
 * @author praneet
 *
 * Add String names to the previously prepapred Jaccard Input
 */

public class JaccardStringInputPreparer {
	
	private static Hashtable<Integer, ArrayList<String>> stringTable;
	
	public JaccardStringInputPreparer()
	{
		stringTable = new Hashtable<Integer, ArrayList<String>> ();
	}
	
	public static Hashtable<Integer, ArrayList<String>> getStringTable()
	{
		return stringTable;
	}
	
	public static void createStringTable(Hashtable<Integer, ArrayList<Integer>> pointsTable, String inputPath) throws IOException
	{
		
		BufferedReader br = new BufferedReader(new FileReader(inputPath));
		Hashtable<Integer, String> lines = new Hashtable<Integer, String>(); 
		
		String line = br.readLine();
		int count = 0;
		
		while(line!=null)
		{
			lines.put(count, line);
			count++;
			line = br.readLine();
		}
		System.out.println(count+" lines read");
		
		for(Integer key: pointsTable.keySet())
		{
			ArrayList<Integer> tempList = pointsTable.get(key);
			Iterator<Integer> iter = tempList.iterator();
			
			ArrayList<String> value = new ArrayList<String> ();
			while(iter.hasNext())
			{
				value.add(lines.get(iter.next()));
				
			}
			
			stringTable.put(key, value);
		}
		
		
		//----------------------verify the entries--------------------------------
		
		for(Integer vkey: stringTable.keySet())
		{
			System.out.print("Cluster "+vkey+": ");
			System.out.println("Size: "+stringTable.get(vkey).size());
			//System.out.println("Entries: "+stringTable.get(vkey));
		}
		System.out.println();
		
	}

}
