package com.sanvaad.Model.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Firebase.CommonMessage;
import com.sanvaad.Model.Firebase.Feedback;
import com.sanvaad.Model.Firebase.User;
@androidx.room.Database(entities = {User.class, CommonMessage.class, Feedback.class, Contact.class, Conversation.class}, version = 1, exportSchema = false)
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