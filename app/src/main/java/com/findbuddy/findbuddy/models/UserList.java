package com.findbuddy.findbuddy.models;

import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhidhar on 2/21/15.
 */
public class UserList<P> extends ArrayList<P> implements Serializable, List<P> {
    private static final long serialVersionUID = 23425345L;

}
