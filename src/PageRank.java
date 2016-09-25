import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class PageRank {
	
	public double approxParameter;
	public HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
	public HashMap<String, Integer> outDegree = new HashMap<String,Integer>();
	public HashMap<String, Integer> inDegree = new HashMap<String,Integer>(); 
	public ArrayList<String> vertexList = new ArrayList<String>();
	public String fileName;
	public HashMap<String, Double> pageRank = new HashMap<String,Double>(); 
	public HashMap<String, Double> beforePageRank = new HashMap<String,Double>(); 
	public int noOfVertices;
	public int numConvergeSteps =0;
	
	public  PageRank(String fileName, double approxParameter) throws IOException
	{
		this.fileName = fileName;
		this.approxParameter = approxParameter;
		intializeVertexs();
		pageRank = new HashMap<String,Double>(noOfVertices);
		beforePageRank = new HashMap<String,Double>(noOfVertices);
		calculateNextPageRankVector();
		
	}

	// Returns the page rank of the passed vertex
	public double pageRankOf(String vertexName)
	{
		return pageRank.get(vertexName);
	}
	
	//Returns number of outgoing edges from the passed vertex
	public int outDegreeOf(String vertexName)
	{
		return outDegree.get(vertexName);	
	}
	
	//Returns number of incoming edges to the passed vertex
	public int inDegreeOf(String vertexName)
	{
		return inDegree.get(vertexName);
	}
	
	//Returns number of edges in the graph
	public int numEdges()
	{
		int size=0;
		for (String s: graph.keySet())
		{
			size = size+graph.get(s).size();
		}
		return size;
	}
	
	// Returns the name of vertices with top k page ranks
	public String[] topKPageRank(int k)
	{
		String[] vertices = new String[k];
		Object[] allvertices = new String[pageRank.size()];
		allvertices = pageRank.keySet().toArray();
		for(int i=0;i<k;i++ )
		{
			vertices[i]=(String) allvertices[i];
		}
		return vertices;
	}
	
	// Returns the name of the vertices with top k in degree
	public String[] topKInDegree(int k)
	{
		String[] vertices = new String[k];
		Object[] allvertices = new String[inDegree.size()];
		allvertices = inDegree.keySet().toArray();
		for(int i=0;i<k;i++ )
		{
			vertices[i]=(String) allvertices[i];
		}
		return vertices;
	}
	
	// Returns the name of the vertices with top k out degree
	public String[] topKOutDegree(int k)
	{
		String[] vertices = new String[k];
		Object[] allvertices = new String[outDegree.size()];
		allvertices = outDegree.keySet().toArray();
		for(int i=0;i<k;i++ )
		{
			vertices[i]=(String) allvertices[i];
		}
		return vertices;
	}
	
	//Algorithm to calculate page rank
	public void calculateNextPageRankVector()
	{
		boolean converged = false;
		ArrayList<String> outGoingVertex = new ArrayList<String>();
		double x1=0.0,y1=0.0;
		
		for (String vertex: vertexList)
		{
			beforePageRank.put(vertex, ((1/ (double)noOfVertices)));
		}
		
		while(!converged)
		{
			this.numConvergeSteps=this.numConvergeSteps+1;
		//Initializing P(n+1) 	
		for (String vertex: vertexList)
		{
			pageRank.put(vertex, ((0.15/ (double)noOfVertices)));
		}
	    // Calculating P(n+1) for each vertex
		for (String vertex: vertexList)
		{
			if(graph.containsKey(vertex))
			{
				outGoingVertex = graph.get(vertex);
		    }
			else
			{
				outGoingVertex = vertexList;
			}
			
			for (String s: outGoingVertex)
			{   
				double x= pageRank.get(s);
				x = x+  ((0.85*beforePageRank.get(vertex))/(double)(outGoingVertex.size()));
				pageRank.put(s, x);
			}
		}
			double x=0;
			//Calculating norm(p(n+1)-p(n))
			for (String s: vertexList)
			{
				x = x + Math.abs(beforePageRank.get(s)-pageRank.get(s));
			}
			
			if (x<=approxParameter)
			{
				converged = true;
			}
			else
			{
				beforePageRank = new HashMap<String,Double> (pageRank);
			}
		}
		
		
		//Sorting pageRank HashMap in descending order of page rank
		List<HashMap.Entry<String,Double>> list = new LinkedList<HashMap.Entry<String,Double>>(pageRank.entrySet());
		Collections.sort(list, new Comparator<HashMap.Entry<String,Double>>() {
			public int compare(HashMap.Entry<String, Double> o1,
					HashMap.Entry<String, Double> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		
		HashMap<String,Double> result = new LinkedHashMap<String,Double>();
        for (HashMap.Entry<String,Double> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        
      //Sorting inDegree HashMap in descending order of number of in degree
        List<HashMap.Entry<String,Integer>> listOutDegree = new LinkedList<HashMap.Entry<String,Integer>>(outDegree.entrySet());
		Collections.sort(listOutDegree, new Comparator<HashMap.Entry<String,Integer>>() {
			public int compare(HashMap.Entry<String, Integer> o1,
					HashMap.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		
		HashMap<String,Integer> resultOutDegree = new LinkedHashMap<String,Integer>();
        for (HashMap.Entry<String,Integer> entry : listOutDegree)
        {
        	resultOutDegree.put( entry.getKey(), entry.getValue() );
        }
        
        //Sorting outDegree HashMap in descending order of number of out degree
        List<HashMap.Entry<String,Integer>> listInDegree = new LinkedList<HashMap.Entry<String,Integer>>(inDegree.entrySet());
		Collections.sort(listInDegree, new Comparator<HashMap.Entry<String,Integer>>() {
			public int compare(HashMap.Entry<String, Integer> o1,
					HashMap.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		
		HashMap<String,Integer> resultInDegree = new LinkedHashMap<String,Integer>();
        for (HashMap.Entry<String,Integer> entry : listInDegree)
        {
        	resultInDegree.put( entry.getKey(), entry.getValue() );
        }
        
        
       // System.out.println(pageRank);
        //System.out.println(outDegree);
        //System.out.println(inDegree);
		pageRank = result;
		outDegree = resultOutDegree;
		inDegree = resultInDegree;
		//System.out.println(pageRank);
		//System.out.println(outDegree);
		//System.out.println(inDegree);
	}
	
	//Forming Hash map for the vertex and its corresponding edge list
	public void intializeVertexs() throws IOException
	{
		FileReader inputFile = new FileReader(this.fileName);
		BufferedReader reader = new BufferedReader(inputFile);
		String line;
		String[] array = new String[2];
		ArrayList<String> outGoingVertex;;
		line = reader.readLine();
		while ((line = reader.readLine()) != null)
		{
			outGoingVertex = new ArrayList<String>();
			array = line.split(" ");
			
			if (!vertexList.contains(array[0].toLowerCase()))
			{
				vertexList.add(array[0].toLowerCase());
			}
			if (!vertexList.contains(array[1].toLowerCase()))
			{
				vertexList.add(array[1].toLowerCase());
			}

		    if (graph.containsKey(array[0].toLowerCase()))
		    {
		    	outGoingVertex = graph.get(array[0].toLowerCase());
		    	outGoingVertex.add(array[1].toLowerCase());
		    	graph.put(array[0].toLowerCase(), outGoingVertex);
		    }
		    else
		    {
		    	outGoingVertex.add(array[1].toLowerCase());
		    	graph.put(array[0].toLowerCase(), outGoingVertex);
		    }
		    
		    if(outDegree.containsKey(array[0].toLowerCase()))
		    {
		    	int x = outDegree.get(array[0].toLowerCase());
		    	outDegree.put(array[0].toLowerCase(), x+1);
		    	
		    }
		    else
		    {
		    	outDegree.put(array[0].toLowerCase(), 1);
		    }
		    
		    if(inDegree.containsKey(array[1].toLowerCase()))
		    {
		    	int x = inDegree.get(array[1].toLowerCase());
		    	inDegree.put(array[1].toLowerCase(), x+1);
		    	
		    }
		    else
		    {
		    	inDegree.put(array[1].toLowerCase(), 1);
		    }
		}
		
		for (String vertex: vertexList)
		{
			if(!inDegree.containsKey(vertex.toLowerCase()))
			{
				inDegree.put(vertex.toLowerCase(), 0);
			}
			
			if (!outDegree.containsKey(vertex.toLowerCase()))
			{
				outDegree.put(vertex.toLowerCase(), 0);
			}
		}
		
		this.noOfVertices = vertexList.size();
		
	
	}

}
