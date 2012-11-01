package org.apache.mahout.clustering;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class TestEvaluationMetrics {
	
	public static void main(String[] args) throws IOException
	{
		PrintStream out;
		try {
			
			//out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/evaluationMeasures/dirichlet/manhattan/text/10iter10clust110.txt"));
			out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/evaluationMeasures/dirichlet/euclidean/dynamic/10iter10clust068.txt"));
			
			//out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/evaluationMeasures/kmeans/euclidean/text/100200/10iter10clust100200.txt"));
			//out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/evaluationMeasures/fuzzykmeans/euclidean/combined/140280/40iter10clust140280.txt"));
			
			//out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/entropy/final-numbers/9th/kmeans/euclidean/dynamic/10iter10clust2754.txt"));
			
			System.setOut(out);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Generation of cluster hashtables
		
		JaccardInputPreparer jp = new JaccardInputPreparer();
		//jp.createTable("/home/praneet/software-architecture/concerntagger/final-numbers/kmeans/euclidean/text/100200/10Iter10Clust100200/output/clusteredPoints/part-m-0");
		//jp.createTable("/home/praneet/software-architecture/concernTagger/final-numbers/9th/dirichlet/manhattan/dynamic/15Iter10Clust085/output/clusteredPoints/part-m-0");
		//jp.createTable("/home/praneet/rhino10Iter10Clust087/output/clusteredPoints/part-m-0");
		
		jp.createTable("/home/praneet/software-architecture/concerntagger/final-numbers/9th/dirichlet/euclidean/dynamic/10Iter10Clust068/output/clusteredPoints/part-m-0");
		//jp.createTable("/home/praneet/Eclipse-Output/software-architecture/concernTagger/final-numbers/new/dirichlet/euclidean/dynamic/10Iter10Clust0667/output/clusteredPoints/part-m-0");
		
		//jp.createTable("/home/praneet/software-architecture/concerntagger/final-numbers/kmeans/euclidean/text/125250/10Iter10Clust125250/output/clusteredPoints/part-m-0");
		//jp.createTable("/home/praneet/software-architecture/concerntagger/final-numbers/fuzzykmeans/euclidean/combined/140280/40Iter10Clust140280/output/clusteredPoints/part-m-0");
		
		Hashtable<Integer, ArrayList<Integer>> tab = jp.getPointsTable();
		
		JaccardStringInputPreparer js = new JaccardStringInputPreparer();
		//js.createStringTable(tab, "/home/praneet/software-architecture/files/input/methodsConsidered.txt");
		js.createStringTable(tab, "/home/praneet/software-architecture/files/output/final/methodsConsidered.txt");
		
		Hashtable<Integer, ArrayList<String>> clusters = js.getStringTable();
				
		MethodsByConcernPreparer mc = new MethodsByConcernPreparer();
		Hashtable<Integer, ArrayList<String>> concerns = mc.getMethodsHashtable("/home/praneet/software-architecture/files/newOutput.txt");
				
		//---------------- end of hashtable generation ---------------------
		
		//Conversion of tables to lists to use with the evaluation API
		
		Set<Integer> keysComputed = clusters.keySet();
		//System.out.println("Size of keysComputed: "+keysComputed.size());
		List<ArrayList<String>> computed = new ArrayList<ArrayList<String>> ();
		for(int key1:keysComputed)
		{
			computed.add(clusters.get(key1));
			//System.out.println("Adding a cluster");
		}
		System.out.println("Size of computed: "+computed.size());
		
		Set<Integer> keysGoldset = concerns.keySet();
		List<ArrayList<String>> goldSet = new ArrayList<ArrayList<String>> ();
		for(int key2:keysGoldset)
		{
			goldSet.add(concerns.get(key2));
		}
		System.out.println("Size of Goldset: "+goldSet.size());
		
		//------------ List generation complete -----------------------------
		
		//Use the evaluation measures to generate statistics
		
		EvaluationMeasures em = new EvaluationMeasures();
		//resultSet results = em.calculateEvaluationMeasures(computed, goldSet);
		resultSet results = em.calculateEvaluationMeasures(goldSet, computed);
		results.displayResults();
		
	}

}
