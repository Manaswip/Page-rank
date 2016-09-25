import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class WikiTennisRanker {
	
	public static void main(String args[]) throws IOException 
	{
		PageRank pageRank;
		String[] topKPageRank ;
		String[] topKInDegree ;
		String[] topKOutDegree ;
		ArrayList<String> listTopPageRanks ;
	    ArrayList<String> listTopPageRanks1;
	    ArrayList<String> listTopInDegree ;
	    ArrayList<String> listTopOutDegree ;
		int unionSizePageRankInDegree,unionSizePageRankOutDegree,unionSizeInDegreeOutDegree;
		float simPageRankInDegree, simPageRankOutDegree, simInDegreeOutDegree;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		//Computing Page ranks with epsilon=0.01
		System.out.println("Enter the approximation parameter ");
		float approxParameter = Float.parseFloat(reader.readLine());
		
		//Computing pages with top 100 Page rank, in degree, out degree
		System.out.println("Enter the value of k to find top k values and jaccard similarity");
		int k = Integer.parseInt(reader.readLine());

		pageRank = new PageRank("WikiTennisGraph.txt", approxParameter);
		System.out.println("Page with highest Page rank "+ Arrays.deepToString(pageRank.topKPageRank(1)));
		System.out.println("Page with highest in-degree "+ Arrays.deepToString(pageRank.topKInDegree(1)));
		System.out.println("Page with highest out degree "+ Arrays.deepToString(pageRank.topKOutDegree(1))+"\n");
		System.out.println("Number of steps to converge "+ pageRank.numConvergeSteps+"\n");
 
		topKPageRank = pageRank.topKPageRank(k);
		topKInDegree = pageRank.topKInDegree(k);
		topKOutDegree = pageRank.topKOutDegree(k);
		
		System.out.println("Pages with top 15 page ranks"+"\n");
		for (int i=0;i<k;i++)
		{
			System.out.println(topKPageRank[i]);
		}
		System.out.println("\n");
		System.out.println("Pages with top 15 in degree"+"\n");
		for (int i=0;i<k;i++)
		{
			System.out.println(topKInDegree[i]);
		}
		System.out.println("\n");
		System.out.println("Pages with top 15 out degree"+"\n");
		for (int i=0;i<k;i++)
		{
			System.out.println(topKOutDegree[i]);
		}
		System.out.println("\n");
        listTopPageRanks = new ArrayList<String>();
	    listTopPageRanks1 = new ArrayList<String>();
	    listTopInDegree = new ArrayList<String>();
	    listTopOutDegree = new ArrayList<String>();
	    
	    for (int i=0;i<topKPageRank.length;i++)
	    {
	    	listTopPageRanks.add(topKPageRank[i]);
	    	listTopPageRanks1.add(topKPageRank[i]);
	    }
	    for (int i=0;i<topKInDegree.length;i++)
	    {
	    	listTopInDegree.add(topKInDegree[i]);
	    }
	    for (int i=0;i<topKOutDegree.length;i++)
	    {
	    	listTopOutDegree.add(topKOutDegree[i]);
	    }
	    
	    unionSizePageRankInDegree = listTopPageRanks.size()+listTopInDegree.size();
	    unionSizePageRankOutDegree = listTopPageRanks.size()+listTopOutDegree.size();
	    unionSizeInDegreeOutDegree = listTopInDegree.size()+listTopOutDegree.size();
	    
	    //Jaccard Similarity between topKPageRank and topkInDegree
	    listTopPageRanks.retainAll(listTopInDegree);
	    simPageRankInDegree = listTopPageRanks.size()/(float)(unionSizePageRankInDegree - listTopPageRanks.size());
	    System.out.println("Exact Similarity between top "+ k + " page ranks and top "+ k + " pages with in-degree "+ simPageRankInDegree);
	    
	  //Jaccard Similarity between topKPageRank and topkOutDegree
	    listTopPageRanks1.retainAll(listTopOutDegree);
	    simPageRankOutDegree = listTopPageRanks1.size()/(float)(unionSizePageRankOutDegree - listTopPageRanks1.size());
	    System.out.println("Exact Similarity between top "+ k + " page ranks and top "+ k + " pages with out-degree "+ simPageRankOutDegree);
	    
	  //Jaccard Similarity between topKInDegree and topkOutDegree
	    listTopInDegree.retainAll(listTopOutDegree);
	    simInDegreeOutDegree = listTopInDegree.size()/(float)(unionSizeInDegreeOutDegree - listTopInDegree.size());
	    System.out.println("Exact Similarity between top "+ k + " pages with in-degree and top "+ k + " pages with out-degree "+ simInDegreeOutDegree);
	    
	  
	}

}
