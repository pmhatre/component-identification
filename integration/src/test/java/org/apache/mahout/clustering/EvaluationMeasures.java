package org.apache.mahout.clustering;

import java.io.*;
import java.util.*;


public class EvaluationMeasures {
	
	public static final int MAXSIZE = 826;
	public static final int MINSIZE = 52;
	
	public static void main (String args[])
	{
		
		ArrayList<String> computedCluster1 = new ArrayList<String>(Arrays.asList("one","two","five","four"));
		ArrayList<String> computedCluster2 = new ArrayList<String>(Arrays.asList("four","three","six"));
		ArrayList<String> computedCluster3 = new ArrayList<String>(Arrays.asList("one","two","three"));
		List<ArrayList<String>> computedSet = new ArrayList<ArrayList<String>>(Arrays.asList(computedCluster1,computedCluster2));
		
		ArrayList<String> goldCluster1 = new ArrayList<String>(Arrays.asList("one","two","three","four"));
		ArrayList<String> goldCluster2 = new ArrayList<String>(Arrays.asList("six","five","four"));
		ArrayList<String> goldCluster3 = new ArrayList<String>(Arrays.asList("one","two","three","four"));
		List<ArrayList<String>> goldSet = new ArrayList<ArrayList<String>>(Arrays.asList(goldCluster1,goldCluster2));
		
		resultSet results = calculateEvaluationMeasures(computedSet,goldSet);
		results.displayResults();
	}
	
	public static resultSet calculateEvaluationMeasures(List<ArrayList<String>> goldClusters, List<ArrayList<String>> ComputedClusters)
	{
		resultSet results = new resultSet();
		Hashtable<String,Integer> intraPairMap =  new Hashtable<String,Integer>();
		int pairsPresent=0;
		int totalPairsInGoldClusters=0;
		int totalPairsInComputedClusters=0;
		for (ArrayList<String> cluster : goldClusters)
		{
			for (int i=0; i<cluster.size(); i++)
			{
				for (int j = i+1; j<cluster.size();j++)
				{
					String clusterKey = cluster.get(i)+cluster.get(j);
					if (intraPairMap.containsKey(clusterKey))
					{
						totalPairsInGoldClusters++;
						int value =  intraPairMap.get(clusterKey);
						intraPairMap.put(clusterKey, value+1);
					}
					else
					{
						totalPairsInGoldClusters++;
						intraPairMap.put(clusterKey, 1);
					}
				}
			}
		}
		System.out.println("TotalPairsInGoldClusters: "+totalPairsInGoldClusters);
		// At this point the intraPairMap should be populated
		//Let go through each of the pairs in the result set and find out if they are present in the intraPairMap
		for (ArrayList<String> computedCluster : ComputedClusters)
		{
			for (int i=0; i<computedCluster.size(); i++)
			{
				String partialKey = computedCluster.get(i);
				for (int j = i+1; j<computedCluster.size();j++)
				{
					String clusterKey = partialKey+computedCluster.get(j);
					String reverseClusterKey = computedCluster.get(j)+partialKey;
					totalPairsInComputedClusters++;
					// if the pair is present in the same cluster in the obtained set
					if (intraPairMap.containsKey(clusterKey) || intraPairMap.containsKey(reverseClusterKey))
					{
						pairsPresent++;
					}
				}
			}
		}
		System.out.println("PairPresent: "+pairsPresent);
		System.out.println("TotalPairsComputedCluster: "+totalPairsInComputedClusters);
		results.precision = (double) pairsPresent/totalPairsInComputedClusters;
		results.recall = (double)pairsPresent/totalPairsInGoldClusters;
		results.fMeasure = (double)(2*results.precision*results.recall)/(results.precision+results.recall);
		results.NEDGoldSet = calculateNED(goldClusters, MINSIZE, MAXSIZE);
		results.NEDComputedSet = calculateNED(ComputedClusters, MINSIZE, MAXSIZE);
		results.NormalizedNED = (double) results.NEDComputedSet/results.NEDGoldSet;
		return results;
	}
	
	private static double calculateNED(List<ArrayList<String>> computedSet, int min, int max)
	{
		int sizeOfNonExtremeClusters = 0;
		int sizeOfAllClusters = 0;
		for (ArrayList<String> cluster : computedSet)
		{
			sizeOfAllClusters += cluster.size();
			if (!(cluster.size()<min || cluster.size()>max))
			{
				sizeOfNonExtremeClusters += cluster.size();
			}
		}
		return (double) sizeOfNonExtremeClusters/sizeOfAllClusters;
	}
}


class resultSet
{
	 double precision;
	 double recall;
	 double fMeasure;
	 double NEDGoldSet;
	 double NEDComputedSet;
	 double NormalizedNED;
	 
	 public void displayResults()
	 {
		 System.out.println("Precision:   "+this.precision);
		 System.out.println("Recall:   "+this.recall);
		 System.out.println("FMeasure:   "+this.fMeasure);
		 System.out.println("NED GoldSet:   "+this.NEDGoldSet);
		 System.out.println("NED ComputedSet:   "+this.NEDComputedSet);
		 System.out.println("Normalized NED :   "+this.NormalizedNED);

	 }
}