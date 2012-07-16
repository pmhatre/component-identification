package org.apache.mahout.math.stats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ShardingAuc {
	
	public static void main(String[] args) throws IOException
	{
		OnlineAuc aucFull = new GlobalOnlineAuc();
		OnlineAuc aucSharded = new GlobalOnlineAuc();
		
		BufferedReader br1 = new BufferedReader(new FileReader("/home/praneet/0.6-workspace/feature-sharding/data/output/KDD/labels/after20iterfull20Shards.txt"));
		BufferedReader br2 = new BufferedReader(new FileReader("/home/praneet/0.6-workspace/feature-sharding/data/output/KDD/labels/after20iterSharded5ShardsNoOverlap.txt"));
		
		String line = new String();
		String line2 = new String();
		while( (line=br1.readLine()) != null && (line2=br2.readLine()) != null)
		{
			String[] val = line.split(",");
			aucFull.addSample(Integer.parseInt(val[0]), Double.parseDouble(val[1]));
			aucSharded.addSample(Integer.parseInt(val[0]), Double.parseDouble(line2));
			//System.out.println("Current AUC: "+auc.auc());
		}
		System.out.println("Full AUC: "+aucFull.auc());
		System.out.println("Sharded AUC: "+aucSharded.auc());
		
		//change and check
		/*String line2 = new String();
		while( (line2=br2.readLine()) != null )
		{
			String[] val = line2.split(",");
			auc.addSample(Integer.parseInt(val[0]), Double.parseDouble(val[1]));
			//System.out.println("Current AUC: "+auc.auc());
		}
		System.out.println("Sharded AUC: "+auc.auc());*/
		
	}

}
