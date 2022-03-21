package com.company;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


/** This class stores the data we need to perform a breadth-first search
 * We use a Queue to store which nodes we need to visit
 * We use a Hash Map to store visited nodes
 */
public class PathData {
    public Queue<Node> toVisit = new LinkedList<Node>();
    public HashMap<Integer, Node> visited = new HashMap<Integer, Node>();

    public PathData(User user)
    {
        Node startNode = new Node(user, null);
        toVisit.add(startNode);
        visited.put(user.getID(), startNode);
    }
    public Queue<Node> getToVisit() {
        return toVisit;
    }
    public HashMap<Integer, Node> getVisited() {
        return visited;
    }
}
