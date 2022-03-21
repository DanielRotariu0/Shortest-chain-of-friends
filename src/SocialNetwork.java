package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/** Represents our social network
 * We identify our users using their IDs, and we store them in a Hash Map
 */
public class SocialNetwork {
    private final HashMap<Integer, User> users = new HashMap<>();

    public void addUser(User user)
    {
        users.put(user.getID(), user);
    }

    public void createFriendship(User userA, User userB)
    {
        /* Friendship is transitive */
        userA.addFriend(userB);
        userB.addFriend(userA);
    }

    /** Function used to find the shortest chain of friends between two users
     * We perform two breadth-first searches starting from both users
     * We search one level at a time until we have a collision
     */
    public LinkedList<User> findShortestChainOfFriends(User startUser, User endUser)
    {
        PathData start = new PathData(startUser);
        PathData end = new PathData(endUser);

        while (!start.getToVisit().isEmpty() && !end.getToVisit().isEmpty())
        {
            /* Search from first user */
            User collisonUser = searchFriends(start, end);
            if (collisonUser != null)
                return buildChain(start, end, collisonUser);

            /* Search from second user */
            collisonUser = searchFriends(end, start);
            if (collisonUser != null)
                return buildChain(start, end, collisonUser);
        }

        return null;
    }

    /** Function used to search a level
     * We return the collision User if we find one
     */
    public User searchFriends(PathData start, PathData end)
    {
        /* Get the number of nodes in this level */
        int nrOfFriends = start.getToVisit().size();

        for (int i = 0; i < nrOfFriends; i++) {

            /* We take out the first node */
            Node node = start.getToVisit().poll();
            assert node != null;
            int userID = node.getUser().getID();
            /* We check if it has already been visited */
            if (end.getVisited().containsKey(userID))
                return node.getUser();
            /* We add all friends in the queue to visit them later */
            User user = node.getUser();
            ArrayList<User> friends = user.getFriends();
            for (User u : friends) {
                int friendID = u.getID();
                if (!start.getVisited().containsKey(friendID)) {
                    User friend = users.get(friendID);
                    Node next = new Node(friend, node);
                    start.getVisited().put(friendID, next);
                    start.getToVisit().add(next);
                }
            }
        }
        return null;
    }

    /** Function used to build the chain after we found our collision
     */
    static LinkedList<User> buildChain(PathData start, PathData end, User collisionUser)
    {
        Node startNode = start.getVisited().get(collisionUser.getID());
        Node endNode = end.getVisited().get(collisionUser.getID());

        LinkedList<User> firstHalf = startNode.buildPath(true);
        LinkedList<User> secondHalf = endNode.buildPath(false);

        /* We remove the collision User because it's in both lists */
        secondHalf.removeFirst();
        firstHalf.addAll(secondHalf);

        return firstHalf;
    }
}
