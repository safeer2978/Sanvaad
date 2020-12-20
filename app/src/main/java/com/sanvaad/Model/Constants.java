package com.sanvaad.Model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constants {

    public static final long ADMIN_ID = 1;

    public static final String DUMMY_CONTACT_IMAGE_LINK = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Google_Contacts_icon.svg/1200px-Google_Contacts_icon.svg.png";
    public static final long USER_ID = 0 ;
    public static final String SHARED_PREF = "SANVAAD_PREFERENCES";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final int TAB_CHAT = 0;
    public static final int TAB_CONTACTS = 1;
    public static final int TAB_PROFILE = 2;
    public static final int CHAT_VIEWMODEL = 1;
    public static final int HOME_VIEWMODEL = 0;

    public static final List<Integer> colorSet = new ArrayList<>();
    public static final String GENDER = "GENDER";

    static {
        colorSet.add(Color.parseColor("#FFCD95"));
        colorSet.add(Color.parseColor("#EECB3F"));
        colorSet.add(Color.parseColor("#FF3B5D"));
        colorSet.add(Color.parseColor("#45CE88"));
        colorSet.add(Color.parseColor("#FFFFFF"));
        colorSet.add(Color.parseColor("#91EBEF"));
    }
}
