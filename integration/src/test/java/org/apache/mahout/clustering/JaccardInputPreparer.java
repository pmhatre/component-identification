package org.apache.mahout.clustering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

//import org.apache.hadoop.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.mahout.clustering.WeightedVectorWritable;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.PathType;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileDirIterable;

/**
 * 
 * @author praneet
 *
 * Prepare the input for Jaccard Similarity calculations and other set based computations
 */


public class JaccardInputPreparer {
	
	private static Hashtable<Integer,ArrayList<Integer>> pointsTable;
	/*private static int numOfClusters;
	
	public static void setNum(int n)
	{
		numOfClusters = n;
	}*/
	
	public static Hashtable<Integer,ArrayList<Integer>> getPointsTable()
	{
		return pointsTable;
	}
	
	public static void createTable(String inputPath) throws IOException
	{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		
		Path path = new Path(inputPath);
		//Path path = new Path("/home/praneet/Eclipse-Output/software-architecture/concernTagger/dirichlet/40Iter10Clust/output/clusteredPoints/part-m-0");
	    //SequenceFile.Reader reader = new SequenceFile.Reader(fs,path,conf);
		SequenceFileDirIterable sf = new SequenceFileDirIterable(path, PathType.LIST, conf);
		
	    //Integer key = new IntWritable();
	    int key;
		WeightedVectorWritable value = new WeightedVectorWritable();
	    
	    Hashtable<Integer, ArrayList<Integer>> clusteredPoints = new Hashtable<Integer,ArrayList<Integer>>();
	    
	    Iterator<Pair<IntWritable,WeightedVectorWritable>> iter = sf.iterator();
	    
	    while(iter.hasNext())
	    {
	     //System.out.println(value.toString() +" is in cluster " + key.toString() );
	     //System.out.println("Key: "+key.toString()+" value: "+);
	     
	    	//count++;
	    	//System.out.println("Loop number "+count);
	    	
	    	Pair<IntWritable, WeightedVectorWritable> temp = iter.next();
	    	key = temp.getFirst().get();
	    	value = temp.getSecond();
	    	
	    	int point;
	    	String val = value.toString();
	    	int begin = val.indexOf('(');
	    	int end = val.indexOf(')');
	    	point = Integer.parseInt(val.substring(begin+1, end));	    	
	    	
	    	if(clusteredPoints.containsKey(key))
	    	{
	    		ArrayList<Integer> tempList = clusteredPoints.get(key);	    			    		
	    		tempList.add(point);
	    		//System.out.println("Point "+point+" added to cluster "+key);
	    		clusteredPoints.put(key, tempList);
	    	}
	    	else
	    	{
	    		ArrayList<Integer> tempList = new ArrayList<Integer> ();
	    		tempList.add(point);
	    		//System.out.println("Point "+point+" added to cluster "+key);
	    		clusteredPoints.put(key, tempList);
	    	}
	    	
	    	
	    }
	    
	    pointsTable = clusteredPoints;
		
	    //-------------------- print the clusters --------------------
	    
	    /*for(int cluster: clusteredPoints.keySet())
	    {
	    	ArrayList<Integer> points = clusteredPoints.get(cluster);
	    	//System.out.println("\nPoints in cluster "+key+": \n");
	    	
	    	System.out.println("Points in cluster "+cluster+": ");
	    	System.out.println("Total points: "+points.size());
	    	Iterator pts = points.iterator(); 
	    	while(pts.hasNext())
	    	{
	    		System.out.println(pts.next());	    		
	    		
	    	}
	    }*/
	    
	}
	
}
