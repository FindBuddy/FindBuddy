package com.findbuddy.findbuddy.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhidhar on 2/21/15.
 */
public class UserList<User> extends ArrayList<User> implements Serializable, List<User> {
    private static final long serialVersionUID = 23425345L;

}
