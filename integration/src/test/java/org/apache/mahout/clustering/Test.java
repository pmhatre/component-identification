package org.apache.mahout.clustering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import org.apache.avro.mapred.SequenceFileReader;
import org.apache.commons.collections.MultiHashMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.PathType;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileDirIterable;

/**
 * 
 * @author praneet
 *
 * Test the clustering phase of the component identification process
 */


public class Test {

	public static void main(String args[]) throws IOException
	  {
		//Write the result out to a file --------------------
		PrintStream out;
		try {
			
			out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/concernTagger/final-numbers/9th/dirichlet/manhattan/dynamic/rhino15Iter10Clust085.txt"));
			//out = new PrintStream(new FileOutputStream("/home/praneet/Eclipse-Output/software-architecture/concernTagger/final-numbers/9th/kmeans/euclidean/dynamic/rhino10Iter10Clust2754.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//------------------------------------------------------
		
		TestClusterDumper testDumper = new TestClusterDumper();
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		   
		try {
			  testDumper.setUp();
			  testDumper.testDirichlet2();
			//testDumper.testKmeans();
			//testDumper.testFuzzyKmeans();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				
		}
		
		//Read the clustered points
	    Path path = new Path("/home/praneet/Eclipse-Output/software-architecture/concernTagger/final-numbers/9th/dirichlet/manhattan/dynamic/15Iter10Clust085/output/clusteredPoints/part-m-0");
	    SequenceFileDirIterable sf = new SequenceFileDirIterable(path, PathType.LIST, conf);
	    
	    Iterator<Pair<IntWritable,WeightedVectorWritable>> iter = sf.iterator();
	    	    
	    //create a hashtable to store cluster mappings
	    Map<IntWritable,ArrayList<WeightedVectorWritable>> clusteredPoints = new HashMap<IntWritable,ArrayList<WeightedVectorWritable>> (); 
	    	    
	    while(iter.hasNext())
	    {
	    	Pair<IntWritable, WeightedVectorWritable> temp = iter.next();
	    	if(clusteredPoints.containsKey(temp.getFirst())) 
	    	{
	    		ArrayList<WeightedVectorWritable> tempList = clusteredPoints.get(temp.getFirst());
	    		tempList.add(temp.getSecond());
	    		clusteredPoints.put(temp.getFirst(), tempList);
	    	}
	    	else
	    	{
	    		ArrayList<WeightedVectorWritable> tempList = new ArrayList<WeightedVectorWritable> ();
	    		tempList.add(temp.getSecond());
	    		clusteredPoints.put(temp.getFirst(), tempList);
	    	}
	    	
	    	//System.out.println(temp.getSecond().toString() + "is in cluster" + temp.getFirst().toString());
	    }
	    
	    System.out.println("ClusteredPoints has "+clusteredPoints.size()+" entries");
	    
	    //go through the map and print all the cluster assignments
	    for(IntWritable key: clusteredPoints.keySet())
	    {
	    	ArrayList<WeightedVectorWritable> points = clusteredPoints.get(key);
	    	//System.out.println("\nPoints in cluster "+key+": \n");
	    	
	    	System.out.println("Points in cluster "+key+": ");
	    	System.out.println("Total points: "+points.size());
	    	Iterator pts = points.iterator(); 
	    	while(pts.hasNext())
	    	{
	    		System.out.println(pts.next());	    		
	    		
	    	}
	    }
	    
	    
	  }
	
	
	
}

