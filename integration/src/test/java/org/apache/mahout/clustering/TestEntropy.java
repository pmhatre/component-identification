package org.apache.mahout.clustering;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * 
 * @author praneet
 *
 * Test the entropy calculations for both clusters and concerns
 */

public class TestEntropy {
	
	public static void main(String[] args) throws IOException
	{
		
		PrintStream out;
		try {
			
			out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/entropy/final-numbers/9th/dirichlet/manhattan/dynamic/15iter10clust085.txt"));
			//out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/entropy/final-numbers/9th/kmeans/euclidean/dynamic/10iter10clust2754.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/*Run a toy example
		 * 
		 * Entropy en = new Entropy();
		Hashtable<Integer, ArrayList<String>> set1 = new Hashtable<Integer, ArrayList<String>> ();
		ArrayList<String> l1 = new ArrayList<String> ();
		ArrayList<String> l2 = new ArrayList<String> ();
		ArrayList<String> l3 = new ArrayList<String> ();
		
		l1.add("1");l1.add("2");l1.add("3");//l1.add("6"); 
		l2.add("3");l2.add("4");l2.add("5");//l2.add("6"); 
		l3.add("1");l3.add("5");l3.add("6");
		set1.put(1, l1);
		//set1.put(1, l2);
		//set1.put(1, l3);
		set1.put(2, l2);
		set1.put(3, l3);
		en.setConcerns(set1);
		en.computeConcernIncrements(set1);
		en.computeConcernEntropy();
		
		Hashtable<Integer, ArrayList<String>> set2 = new Hashtable<Integer, ArrayList<String>> (); 
		ArrayList<String> k1 = new ArrayList<String> ();
		ArrayList<String> k2 = new ArrayList<String> ();
		ArrayList<String> k3 = new ArrayList<String> ();
		
		k1.add("1");k1.add("2");k1.add("3");k1.add("4"); 
		k2.add("3");k2.add("4");k2.add("5");//k2.add("6"); 
		k3.add("1");k3.add("6");//k3.add("6");
		set2.put(1, k1);
		set2.put(2, k2);
		set2.put(3, k3);
		
		en.computeClusterIncrements(set2);
		
		double[] ent = new double[set2.size()];
		Hashtable<Integer, Double> entTable = new Hashtable<Integer, Double> (); 
		int count=0;
		System.out.println("No of clusters: "+set2.size());
		for(int key: set2.keySet())
		{
			//System.out.println("Cluster Key: "+key);
			//System.out.println("");
			//System.out.println("Value: "+ clusters.get(key));
			ent[count++] =en.overlappingClusterEntropy(set2.get(key));
			entTable.put(key, ent[count-1]);
			System.out.println("Entropy for cluster "+key+" is: "+ent[count-1]);
		}
		
		double sum=0.0;		
		for(int i=0; i<ent.length; i++)
		{
			sum += ent[i]; 
		}
		
		System.out.println("Average entropy for the clusters is: "+(sum/ent.length));*/
		
		
		
		JaccardInputPreparer jp = new JaccardInputPreparer();
		//jp.createTable("/home/praneet/Eclipse-Output/software-architecture/concernTagger/final-numbers/9th/kmeans/euclidean/dynamic/10Iter10Clust2754/output/clusteredPoints/part-m-0");
		jp.createTable("/home/praneet/Eclipse-Output/software-architecture/concernTagger/final-numbers/9th/dirichlet/manhattan/dynamic/15Iter10Clust085/output/clusteredPoints/part-m-0");
		
		Hashtable<Integer, ArrayList<Integer>> tab = jp.getPointsTable();
		
		JaccardStringInputPreparer js = new JaccardStringInputPreparer();
		//js.createStringTable(tab, "/home/praneet/software-architecture/files/input/methodsConsidered.txt");
		js.createStringTable(tab, "/home/praneet/software-architecture/files/output/final/methodsConsidered.txt");
		
		Hashtable<Integer, ArrayList<String>> clusters = js.getStringTable();
				
		MethodsByConcernPreparer mc = new MethodsByConcernPreparer();
		Hashtable<Integer, ArrayList<String>> concerns = mc.getMethodsHashtable("/home/praneet/software-architecture/files/newOutput.txt");
				
		Entropy en = new Entropy();
		en.setConcerns(concerns);
		en.computeConcernIncrements(concerns);
		en.computeConcernEntropy();
		
		en.computeClusterIncrements(clusters);
		en.computeClusterEntropy(clusters);
		
		
	}

}
