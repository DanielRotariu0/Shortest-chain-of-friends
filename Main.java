package com.company;

import java.util.*;

/** Discussion
 * 1) How did you represent the social network? Why did you choose this representation?
 *
 *    The problem reduced to a shortest path problem, so I chose a graph to represent the social network.
 *    Each node represents a user and an edge between two nodes indicates that the two users are friends.
 *    Since friendship is transitive, the best structure for our social network would be an unweighted, undirected graph.
 *
 * 2) What algorithm did you use to compute the shortest chain of friends?
 * What alternatives did you consider? Why did you choose this algorithm over the alternatives?
 *
 *    The algorithm I used to determine the shortest chain of friends was a bidirectional breadth first search.
 *    This means doing two breadth first searches, one from the first user and one from the second user.
 *    When we have a collision between those two searches, we’ve found a path.
 *    I chose this approach instead of a simple breadth first search because it is faster and it reduces
 *    the amount of nodes we need to visit.
 *    If branching factor of tree is N and distance between two users is M, then the normal breadth first search
 *    complexity would be O(N^M). If we execute two search operations then the complexity would be O(N^{M/2})
 *    for each search and total complexity would be O(N^{M/2}+N^{M/2}) = O(N^{M/2}) which is far less than O(N^M).
 *    I also considered Dijkstra's Algorithm, but the major disadvantage of this algorithm is the fact that it does
 *    a blind search, so it wastes a lot of time processing.
 *
 * 3) Please enumerate the test cases you considered and explain their relevance.
 *
 *     I created a sample graph and I tested the algorithm on different pairs of users to ensure it's correct.
 *     The graph contains cycles, unreachable users, multiple paths to the same user.
 *     These tests do not ensure performance, as the graph is pretty small.
 *     Note: Open graph.png to see the graph, go to line 84 to see the tests
 */
public class Main {
    public static void main(String[] args) {

        List<User> userList = new ArrayList<>(30);
        /* We create our users */
        for(int i=0;i<30;i++)
        {
            userList.add(new User(i, ""+i));
        }

        /* We define each user's friend list
        * Since friendship is transitive, we only need to create it once
        */
        HashMap<Integer, List<Integer>> friendshipMap = new HashMap<>();

        friendshipMap.put(0, Arrays.asList(1, 2, 3, 4, 10));
        friendshipMap.put(1, Arrays.asList(2, 17, 20, 22));
        friendshipMap.put(2, Arrays.asList(8, 9));
        friendshipMap.put(3, Arrays.asList(18));
        friendshipMap.put(4, Arrays.asList(18, 20));
        friendshipMap.put(5, Arrays.asList(6, 11, 13, 14));
        friendshipMap.put(6, Arrays.asList(7, 13));
        friendshipMap.put(7, Arrays.asList(11));
        friendshipMap.put(8, Arrays.asList(9));
        friendshipMap.put(9, Arrays.asList(15, 16, 17));
        friendshipMap.put(11, Arrays.asList(12, 23, 24));
        friendshipMap.put(12, Arrays.asList(24));
        friendshipMap.put(18, Arrays.asList(19));
        friendshipMap.put(19, Arrays.asList(20, 21));
        friendshipMap.put(20, Arrays.asList(22));
        friendshipMap.put(25, Arrays.asList(26, 28));
        friendshipMap.put(26, Arrays.asList(27, 28));
        friendshipMap.put(27, Arrays.asList(28));

        /* We create our social network and add the users*/
        SocialNetwork socialNetwork = new SocialNetwork();

        for(int i=0;i<30;i++)
        {
            socialNetwork.addUser(userList.get(i));
        }

        /* We create our graph*/
        for (Map.Entry<Integer,List<Integer>> entry : friendshipMap.entrySet()) {
            for(int i=0;i<entry.getValue().size();i++)
            {
                User friend = userList.get(entry.getValue().get(i));
                socialNetwork.createFriendship(userList.get(entry.getKey()), friend);
            }
        }

        /* Testing */
        printShortestChainOfFriends(socialNetwork, userList.get(0), userList.get(16));
        printShortestChainOfFriends(socialNetwork, userList.get(0), userList.get(21));
        printShortestChainOfFriends(socialNetwork, userList.get(15), userList.get(18));
        printShortestChainOfFriends(socialNetwork, userList.get(2), userList.get(21));
        printShortestChainOfFriends(socialNetwork, userList.get(21), userList.get(2));
        printShortestChainOfFriends(socialNetwork, userList.get(14), userList.get(12));
        printShortestChainOfFriends(socialNetwork, userList.get(14), userList.get(23));
        printShortestChainOfFriends(socialNetwork, userList.get(14), userList.get(24));
        printShortestChainOfFriends(socialNetwork, userList.get(1), userList.get(11));
        printShortestChainOfFriends(socialNetwork, userList.get(25), userList.get(27));
        printShortestChainOfFriends(socialNetwork, userList.get(7), userList.get(27));
        printShortestChainOfFriends(socialNetwork, userList.get(0), userList.get(29));
        printShortestChainOfFriends(socialNetwork, userList.get(3), userList.get(3));

        /* Output:
        * The shortest chain of friends between user 0 and user 16 is: 0 -> 2 -> 9 -> 16, length = 3
        * The shortest chain of friends between user 0 and user 21 is: 0 -> 1 -> 20 -> 19 -> 21, length = 4
        * The shortest chain of friends between user 15 and user 18 is: 15 -> 9 -> 2 -> 0 -> 3 -> 18, length = 5
        * The shortest chain of friends between user 2 and user 21 is: 2 -> 1 -> 20 -> 19 -> 21, length = 4
        * The shortest chain of friends between user 21 and user 2 is: 21 -> 19 -> 20 -> 1 -> 2, length = 4
        * The shortest chain of friends between user 14 and user 12 is: 14 -> 5 -> 11 -> 12, length = 3
        * The shortest chain of friends between user 14 and user 23 is: 14 -> 5 -> 11 -> 23, length = 3
        * The shortest chain of friends between user 14 and user 24 is: 14 -> 5 -> 11 -> 24, length = 3
        * There is no chain of friends between user 1 and user 11
        * The shortest chain of friends between user 25 and user 27 is: 25 -> 26 -> 27, length = 2
        * There is no chain of friends between user 7 and user 27
        * There is no chain of friends between user 0 and user 29
        * The shortest chain of friends between user 3 and user 3 is: 3, length = 0
         */
    }

    static void printShortestChainOfFriends(SocialNetwork socialNetwork, User user1, User user2)
    {o
        List<User> shortestChain;
        shortestChain = socialNetwork.findShortestChainOfFriends(user1, user2);
        if(shortestChain==null){
            System.out.println("There is no chain of friends between user "+user1.getID()+" and user "+user2.getID());
        }
        else {
            System.out.print("The shortest chain of friends between user "+user1.getID()+" and user "+user2.getID()+" is: ");
            for (int i = 0; i < shortestChain.size() - 1; i++) {
                System.out.print(shortestChain.get(i).getName() + " -> ");
            }
            System.out.print(shortestChain.get(shortestChain.size() - 1).getName());
            System.out.println(", length = " + (shortestChain.size() - 1));
        }
    }
}
