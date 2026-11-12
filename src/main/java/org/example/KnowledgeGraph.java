package org.example;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/*A directed graph where every edge has a label, like Maya --[can take]--> Bus 1.
The graph is stored as an adjacency list: each node maps to the list of edges leaving it.*/
public class KnowledgeGraph {
    
    private LinkedHashMap<String, ArrayList<Edge>> adjacency = new LinkedHashMap<>();
    
    private int edgeCount = 0;
    
    //One labeled edge pointing at a target node
    private static class Edge {
        
        private String label;
        
        private String target;
        
        private Edge(String label, String target) {
            this.label = label;
            this.target = target;
        }
    }
    
    //Adds both nodes if they are new, then adds the labeled edge (identical edges are only added once)
    public void addEdge(String from, String label, String to) {
        
        adjacency.putIfAbsent(from, new ArrayList<>());
        
        adjacency.putIfAbsent(to, new ArrayList<>());
        
        for (Edge edge : adjacency.get(from)) {
            
            if (edge.label.equals(label) && edge.target.equals(to)) {
                
                return;
                
            }
        }
        
        adjacency.get(from).add(new Edge(label, to));
        
        edgeCount++;
    }
    
    public int getNodeCount() {
        
        return adjacency.size();
        
    }
    
    public int getEdgeCount() {
        
        return edgeCount;
        
    }
    
    //Prints out the knowledge graph
    public void print() {
        
        System.out.println("\nKnowledge graph:");
        
        for (Map.Entry<String, ArrayList<Edge>> entry : adjacency.entrySet()) {
            
            for (Edge edge : entry.getValue()) {
                
                System.out.println("  " + entry.getKey() + " --[" + edge.label + "]--> " + edge.target);
                
            }
        }
        
        System.out.println("\n" + getNodeCount() + " nodes, " + getEdgeCount() + " labeled edges");
    }
}