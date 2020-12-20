package com.sanvaad.Model.UserData.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sanvaad.Model.UserData.db.Entity.Contact;
import com.sanvaad.Model.UserData.db.Entity.Conversation;
import com.sanvaad.Model.UserData.db.Entity.CommonMessage;
import com.sanvaad.Model.UserData.db.Entity.Feedback;
import com.sanvaad.Model.UserData.db.Entity.Message;
import com.sanvaad.Model.UserData.db.Entity.User;


@androidx.room.Database(entities = {Message.class,User.class, CommonMessage.class, Feedback.class, Contact.class, Conversation.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract RoomDao dao();

    private static volatile Database INSTANCE;

    public static Database getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (Database.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class,
                            "database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}