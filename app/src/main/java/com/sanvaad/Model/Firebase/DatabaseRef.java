package com.sanvaad.Model.Firebase;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanvaad.Model.Database.Database;
import com.sanvaad.Model.Database.RoomDao;

import java.util.List;


public class DatabaseRef {
    RoomDao Dao;
    DatabaseReference databaseUsers;

    public DatabaseRef(Application application) {
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        Dao = Database.getDatabase(application).dao();

    }

    private void createUser(User user,String token){
        if (){
            com.sanvaad.Model.Entity.User roomUser=new com.sanvaad.Model.Entity.User(user.getName(),user.getEmail(),user.getAge(),user.getPhone(),user.getStatus());
            Dao.insertUser(roomUser);
            databaseUsers.child(String.valueOf(user.getUserID())).child(user);
        }




    }
}
