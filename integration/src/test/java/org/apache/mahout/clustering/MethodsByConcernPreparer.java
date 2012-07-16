package org.apache.mahout.clustering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 
 * @author praneet
 *
 * Create a mapping of concerns to methods
 */


public class MethodsByConcernPreparer {
	
	public static Hashtable<Integer, ArrayList<String>> getMethodsHashtable(String inputPath) throws IOException
	{
		
		/*PrintStream out;
		try {
			
			out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/tempConcern.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		
		Hashtable<Integer, ArrayList<String>> result = new Hashtable<Integer, ArrayList<String>> ();
		
		BufferedReader br = new BufferedReader(new FileReader(inputPath));
		
		String line = new String();
		//int count = 0;
		while( (line=br.readLine()) != null )
		{
			//count++;
			//if(count>5)
			//	break;
			
			String[] content = line.split("\t");
			
			ArrayList<String> tempList = new ArrayList<String> ();
			for(int i=1; i<content.length; i++)
			{
				if(!content[i].isEmpty())
				{
					tempList.add(content[i]);
				}
			}
			
			result.put(Integer.parseInt(content[0]), tempList);
			//System.out.println("Added to concern "+content[0]);
			//System.out.println(tempList);
		}
		
//----------------------verify the entries--------------------------------
		
		for(Integer vkey: result.keySet())
		{
			System.out.println("Concern "+vkey+": "+" Size: "+result.get(vkey).size());
			//System.out.println();
		}
		System.out.println();
		
		
		return result;
	}

}
