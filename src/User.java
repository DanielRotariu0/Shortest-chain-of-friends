package com.company;

import java.util.ArrayList;

/** Represents a user in our social network
 * We identify the user in our social network using his ID
 * Each user has a list of friends
 */
public class User {
    private final int ID;
    private final String name;
    private final ArrayList<User> friends = new ArrayList<User>();

    public User(int id, String name)
    {
        this.ID = id;
        this.name = name;
    }
    public void addFriend(User u)
    {
        friends.add(u);
    }
    public int getID()
    {
        return ID;
    }
    public String getName()
    {
        return name;
    }
    public ArrayList<User> getFriends()
    {
        return friends;
    }
}
