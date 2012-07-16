package org.apache.mahout.clustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author praneet
 *
 * The core class that implements the overlapping entropy measure and its auxiliary functions
 */

public class Entropy {
	
	private static Hashtable<Integer, ArrayList<String>> concerns;
	
	private static double concernEntropy;
	
	private static Hashtable<String, Double> clusterIncrement;
	
	private static Hashtable<String, Double> concernIncrement;
	
	public Entropy()
	{
		concerns = new Hashtable<Integer, ArrayList<String>> ();
		clusterIncrement = new Hashtable<String, Double> ();
		concernIncrement = new Hashtable<String, Double> ();
	}
	
	
	public static void setConcerns(Hashtable<Integer, ArrayList<String>> input)
	{
		concerns = input;
	}
	
	public static void computeClusterIncrements(Hashtable<Integer, ArrayList<String>> clusters) throws IOException
	{
		
		Hashtable<String, Integer> counts = new Hashtable<String, Integer>();
		
		//compute the counts
		for(int key: clusters.keySet())
		{
			ArrayList<String> methods = clusters.get(key);
			
			Iterator<String> mIter = methods.iterator();
			while(mIter.hasNext())
			{
				String next = mIter.next();
				if(counts.containsKey(next))
				{
					counts.put(next, counts.get(next)+1);
				}
				else
				{
					counts.put(next, 1);
				}
			}
		}
		
		//Use the counts to generate increment values
		//System.out.println("Counts has "+counts.size()+" entries");
		for(String name: counts.keySet())
		{
			double incr = 1.0/counts.get(name);
			//System.out.println(incr);
			clusterIncrement.put(name, incr);
		}
		System.out.println("clusterIncrement has "+clusterIncrement.size()+" entries");
		
		missingMethods();
		
	}
	
	
	private static void missingMethods() throws IOException {
		// TODO Auto-generated method stub
		
		if(clusterIncrement.size()==1033)
		{
			System.out.println("All methods covered");
		}
		
		BufferedReader br = new BufferedReader(new FileReader("/home/praneet/software-architecture/files/output/final/methodsConsidered.txt"));
		ArrayList<String> methods = new ArrayList<String> ();
		String line = new String();
		while( (line=br.readLine()) != null )
		{
			if(!clusterIncrement.containsKey(line))
			{
				methods.add(line);
			}
		}
		
		System.out.println(methods.size()+" methods missing");
		System.out.println("");
		System.out.println("Missing methods");
		Iterator iter = methods.iterator();
		while(iter.hasNext())
		{
			System.out.println(iter.next());
		}
		
	}


	public static void computeConcernIncrements(Hashtable<Integer, ArrayList<String>> concerns)
	{
		
		Hashtable<String, Integer> counts = new Hashtable<String, Integer>();
		
		//compute the counts
		for(int key: concerns.keySet())
		{
			ArrayList<String> methods = concerns.get(key);
			
			Iterator<String> mIter = methods.iterator();
			while(mIter.hasNext())
			{
				String next = mIter.next();
				if(counts.containsKey(next))
				{
					counts.put(next, counts.get(next)+1);
				}
				else
				{
					counts.put(next, 1);
				}
			}
		}
		
		//Use the counts to generate increment values
		//System.out.println("Counts has "+counts.size()+" entries");
		
		//diagnostics
		//System.out.println(counts);
		
		/*int[] dist = new int[9];
		for(int i=0; i<dist.length; i++)
		{
			dist[i]=0;
		}
		for(String k: counts.keySet())
		{
			dist[counts.get(k)]++;
		}
		for(int j=0; j<dist.length; j++)
		{
			System.out.println("repeat factor "+j+": count "+dist[j]);
		}*/
		
		//--------------
		
		for(String name: counts.keySet())
		{
			double incr = 1.0/counts.get(name);
			//System.out.println(incr);
			concernIncrement.put(name, incr);
		}
		System.out.println("concernIncrement has "+concernIncrement.size()+" entries");
		
	}
	
	
	
	
	
	public static void computeConcernEntropy()
	{
		//create a global hashset of methods
		//HashSet<String> methods = new HashSet<String> ();
		
		/*ArrayList<Integer> keys = new ArrayList(concerns.keySet());
		
		for(int i=0; i<keys.size(); i++)
		{
			ArrayList<String> temp = concerns.get(keys.get(i));
			Iterator iter = temp.iterator();
			while(iter.hasNext())
			{
				String tempMethod = (String) iter.next();
				if(!methods.contains(tempMethod))
				{
					methods.add(tempMethod);
				}
				
			}
		}*/
		
		/*double[] ent = new double[concerns.size()];
		int count = 0;
		for(int key: concerns.keySet())
		{
			ent[count++]=overlappingConcernEntropy(concerns.get(key));
			System.out.println("Entropy for concern "+(count-1)+" is: "+ent[count-1]);
		}*/
		
		Hashtable<Integer, Double> ents = new Hashtable<Integer, Double> ();
		for(int key: concerns.keySet())
		{
			ents.put(key,overlappingConcernEntropy(concerns.get(key)));
			System.out.println("Entropy for concern "+key+" is: "+ents.get(key));
		}
		
		//Just average
		/*double sum=0.0;		
		for(int k:ents.keySet())
		{
			 sum += ents.get(k);
		}*/
		
		//Weighted average
		
		//Compute weights for averaging
		Hashtable<Integer, Double> weights = new Hashtable<Integer, Double> ();
		int total = 0;
		for(int c: concerns.keySet())
		{
			weights.put(c, concerns.get(c).size()*1.0);
			//System.out.println("");
			total += weights.get(c);
		}
		for(int c:weights.keySet())
		{
			weights.put(c, weights.get(c)/total);
			System.out.println("Weight for "+c+": " +weights.get(c));
		}
		
		double sum=0.0;		
		for(int k:ents.keySet())
		{
			 sum += ( ents.get(k)*weights.get(k) );
		}
		
		//System.out.println("Average entropy for the concerns is: "+(sum/ents.size()));
		System.out.println();
		System.out.println("Average entropy for the concerns is: "+(sum));
		System.out.println();
	}
	
	public static void computeClusterEntropy(Hashtable<Integer, ArrayList<String>> clusters)
	{
		Hashtable<Integer, Double> ents = new Hashtable<Integer, Double> ();
		for(int key: clusters.keySet())
		{
			ents.put(key, overlappingClusterEntropy(clusters.get(key)));
			System.out.println("Entropy for cluster "+key+" is: "+ents.get(key));
		}
		
		//Just average
		/*double sum=0.0;		
		for(int k:ents.keySet())
		{
			 sum += ents.get(k);
		}*/
		
		//Weighted average
		//Compute weights for averaging
		Hashtable<Integer, Double> weights = new Hashtable<Integer, Double> ();
		int total = 0;
		for(int c: clusters.keySet())
		{
			weights.put(c, clusters.get(c).size()*1.0);
			//System.out.println("");
			total += weights.get(c);
		}
		for(int c:weights.keySet())
		{
			weights.put(c, weights.get(c)/total);
			System.out.println("Weight for "+c+": "+weights.get(c));
		}
		
		double sum=0.0;		
		for(int k:ents.keySet())
		{
			 sum += ents.get(k)*weights.get(k);
		}
		
		//System.out.println("Average entropy for the clusters is: "+(sum/ents.size()));
		System.out.println();
		System.out.println("Average entropy for the clusters is: "+(sum));
		System.out.println();
	}
	
	
	private static double overlappingConcernEntropy(ArrayList<String> inputList) {
		
		Hashtable<Integer, Double> count = new Hashtable<Integer, Double> ();//counts instances from each concern
		Iterator<String> iter = inputList.iterator();
		Set<Integer> keys = concerns.keySet(); //concern id's
		double inputCount = 0.0;
		
		//int c = 0;
		while(iter.hasNext())
		{
			//System.out.println("Loop number "+c);
			
			String temp = iter.next();
			
			inputCount += concernIncrement.get(temp);
			
			Iterator<Integer> setIter = keys.iterator();
			while(setIter.hasNext())
			{
				int id = setIter.next();
				if(concerns.get(id).contains(temp))
				{
					//System.out.println("loop entered");
					//update count
					if(count.containsKey(id))
					{
						//System.out.println("if=true");
						count.put(id, count.get(id)+concernIncrement.get(temp));
					}
					else
					{
						//System.out.println("if=false");
						count.put(id, concernIncrement.get(temp));
					}
				}
			}
			
		}
		
		int k = count.size(); //number of concerns contributing to the cluster
		
		//System.out.println("inputCount = "+inputCount);
		
		//Now calculate entropy using the counts
		double result = 0.0;
		for(int key:count.keySet())
		{
			//System.out.println("Key: "+key);
			//System.out.println("Value: "+count.get(key));
			//System.out.println("Total: "+inputList.size());
			double p = count.get(key)*1.0/inputCount;
			result -= p * ((Math.log(p)/Math.log(8)));  //base of the log
			/*System.out.println("count, total, running value");
			System.out.println(count.get(key));
			System.out.println(inputList.size());
			System.out.println(result);*/
		}
		
		/*if(k==0)
		{
			System.out.println("No match");
		}
		else
		{
			//System.out.println("Value changed from "+result);
			result+= Math.log(k)/Math.log(8);
			//System.out.println("to "+result);
		}*/
					
		return result;
	
	}


	public static double overlappingClusterEntropy(ArrayList<String> inputList)
	{
		
		//int n = concerns.size();
		//ArrayList<ArrayList<String>> concernClusters = new ArrayList<ArrayList<String>> ();
		
		/*Hashtable<Integer, Integer> count = new Hashtable<Integer, Integer> ();//counts instances from each concern
		Iterator<String> iter = inputList.iterator();
		Set<Integer> keys = concerns.keySet(); //concern id's
				
		while(iter.hasNext())
		{
			String temp = iter.next();
			Iterator<Integer> setIter = keys.iterator();
			while(setIter.hasNext())
			{
				int id = setIter.next();
				if(concerns.get(id).contains(temp))
				{
					//System.out.println("loop entered");
					//update count
					if(count.containsKey(id))
					{
						//System.out.println("if=true");
						count.put(id, count.get(id)+1);
					}
					else
					{
						//System.out.println("if=false");
						count.put(id, 1);
					}
				}
			}
			
		}*/
		
		
		Hashtable<Integer, Double> count = new Hashtable<Integer, Double> ();//counts instances from each concern
		Iterator<String> iter = inputList.iterator();
		Set<Integer> keys = concerns.keySet(); //concern id's
		double inputCount = 0.0;
		
		//int c = 0;
		while(iter.hasNext())
		{
			//System.out.println("Loop number "+c);
			
			String temp = iter.next();
			
			inputCount += clusterIncrement.get(temp);
			
			Iterator<Integer> setIter = keys.iterator();
			while(setIter.hasNext())
			{
				int id = setIter.next();
				if(concerns.get(id).contains(temp))
				{
					//System.out.println("loop entered");
					//update count
					if(count.containsKey(id))
					{
						//System.out.println("if=true");
						count.put(id, count.get(id)+clusterIncrement.get(temp));
					}
					else
					{
						//System.out.println("if=false");
						count.put(id, clusterIncrement.get(temp));
					}
				}
			}
			
		}
		
		int k = count.size(); //number of concerns contributing to the cluster
		System.out.println("K = "+k);
		
		//System.out.println("inputCount = "+inputCount);
		
		//Now calculate entropy using the counts
		double result = 0.0;
		for(int key:count.keySet())
		{
			//System.out.println("Key: "+key);
			//System.out.println("Value: "+count.get(key));
			//System.out.println("Total: "+inputList.size());
			double p = count.get(key)*1.0/inputCount;
			result -= p * ((Math.log(p)/Math.log(8)));  //base of the log
			/*System.out.println("count, total, running value");
			System.out.println(count.get(key));
			System.out.println(inputList.size());
			System.out.println(result);*/
		}
		
		/*if(k==0)
		{
			System.out.println("No match");
		}
		else
		{
			//System.out.println("Value changed from "+result);
			result+= Math.log(k)/Math.log(8);
			//System.out.println("to "+result);
		}*/
					
		return result;
	}
	

}
