package com.sanvaad.View;
// nachiket
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Feedback;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.UserDataStore;
import com.sanvaad.R;

import java.util.Calendar;
import java.util.List;
import java.util.TooManyListenersException;

public class MainActivity extends AppCompatActivity {
    UserDataStore userDataStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDataStore = new UserDataStore(getApplication());

/*        User user= new User();
        user.setEmail("334");
        user.setName("Safeer");
        user.setAge(4);
        user.setStatus("M");
        user.setUserID(9999);
        user.setPhoneNo(323232);
        userDataStore.createUser(user,"as");*/
/*        User user = new User();
        user.setName("Admin");*/
/*        userDataStore.createUser(user,"");*/
        //userDataStore.createCommonmessage(new CommonMessage(user.getUserID(),0,"Hello", Calendar.getInstance().getTimeInMillis()));
        //userDataStore.createFeedback(new Feedback(0,"Hi", Calendar.getInstance().getTimeInMillis()));

        //userDataStore.createConversation(new Conversation(Calendar.getInstance().getTimeInMillis(),1));
        //userDataStore.createMessage(new Message("Message1",1,1));





    }

    public void onClick(View view){
        //userDataStore.updateAdminMessages();

        /*userDataStore.getAdminCommonMessage().observe(this, new Observer<List<CommonMessage>>() {
            @Override
            public void onChanged(List<CommonMessage> commonMessages) {
                System.out.println("TEST"+commonMessages);
            }
        });*/
    }
}