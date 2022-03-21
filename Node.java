package com.company;

import java.util.LinkedList;

/** This class represents the path as we are searching
 * We store the User and the previous Node we visited in this path
 */
public class Node {
    private final User user;
    private final Node previous;

    public Node(User user, Node previous)
    {
        this.user = user;
        this.previous = previous;
    }

    public User getUser()
    {
        return user;
    }

    /** Function used to build the path once we have a collision
     * We use a flag to build the path in the correct order using both halves.
     */
    public LinkedList<User> buildPath(boolean reverse)
    {
        LinkedList<User> path = new LinkedList<User>();
        Node node = this;
        while (node != null)
        {
            if (!reverse)
                path.addLast(node.user);
            else
                path.addFirst(node.user);
            node = node.previous;
        }
        return path;
    }
}
