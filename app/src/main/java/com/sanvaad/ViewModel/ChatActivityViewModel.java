package com.sanvaad.ViewModel;

import android.app.Application;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sanvaad.CommonParticipantsViewModel;
import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Repository;
import com.sanvaad.Model.TextData;
import com.sanvaad.messageListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChatActivityViewModel extends AndroidViewModel implements CommonParticipantsViewModel {

    String TAG= "CHAT_ACTIVITY_VIEW_MODEL";
    Repository repository;

    boolean triggerState = false;
    private final Conversation conversation;
    List<Message> messages;
    List<Contact> participants = new ArrayList<>();
    messageListener listener;

    final Handler handler = new Handler(Looper.getMainLooper());

    public void setListener(messageListener listener) {
        this.listener = listener;
    }

    User user;
    int index=0;

    MutableLiveData<List<Contact>> participantsLiveData = new MutableLiveData<>();

Runnable timeout;

    public MutableLiveData<Boolean> getListeningStateLiveData() {
        return listeningStateLiveData;
    }

    MutableLiveData<Boolean> listeningStateLiveData = new MutableLiveData<>();

    public ChatActivityViewModel(Application application) {
        super(application);
        messages= new ArrayList<>();
        this.repository = Repository.getInstance(application);
        conversation = new Conversation(repository.getUser());
        //D simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        conversation.setConvoID(Calendar.getInstance().getTimeInMillis());
        Date date = new Date(conversation.getConvoID());
        Calendar calendar = Calendar.getInstance();
        conversation.setTitle("Conversation at "+date.getHours()+":"+date.getMinutes());
        participantsLiveData.postValue(participants);

        listeningStateLiveData.setValue(triggerState);

        messagesLiveData.postValue(messages);
        user = repository.getUser();

    }


    public MutableLiveData<List<Message>> getMessagesLiveData() {
        return messagesLiveData;
    }

    MutableLiveData<List<Message>> messagesLiveData = new MutableLiveData<>();

    public boolean getTextToSpeechToggle() {
        return textToSpeechToggle;
    }

    public void setTextToSpeechToggle() {
        textToSpeechToggle = !textToSpeechToggle;
    }

    private boolean textToSpeechToggle=false;

    public LiveData<TextData> getTextLiveData(){
    return repository.getTextData();
}

    Message speakerMessage;
    public void handleSpeakerMessages(TextData textData){
        speakerMessage = new Message("Listening to Speaker...",conversation);

        messages.add(speakerMessage);
            /*if(textData.isFinal()) {
                speakerMessage=new Message("",conversation);
                messages.add(0,speakerMessage);
            }*/
        messages.get(messages.size()-1).setMessage(textData.getText());
/*        textDataLiveData.observe(this, new Observer<TextData>() {
            @Override
            public void onChanged(TextData textData) {

            }
        });*/

        handler.removeCallbacks(timeout);
        Log.d(TAG, "handleSpeakerMessages: Old Callback Removed");
        timeout = new Runnable() {
            @Override
            public void run() {
                stopApi();
            }
        };
        handler.postDelayed(timeout,10000);
        Log.d(TAG, "handleSpeakerMessages: New Callback Added");


    }

    public LiveData<List<Contact>> getParticipantsLiveData(){
        return participantsLiveData;
    }

    public void addParticipant(Contact contact){
        if(participants.contains(contact))
            return;
        this.participants.add(contact);
        listener.hideContactScreen();
    }


    public void triggerListening(){
        repository.triggerListening(triggerState);
        triggerState = !triggerState;
        listeningStateLiveData.setValue(triggerState);
        Log.d(TAG, "handleSpeakerMessages: triggerstate:"+triggerState);
        Message message = new Message();
        message.setMessageDate(Calendar.getInstance().getTimeInMillis());
        message.setContactID(Constants.USER_ID);
        if(triggerState){
            message.setMessage("Recording has Started!");
        }else{
            message.setMessage("Recording has stopped!");
        }
        messages.add(message);
        listener.refreshMessage();
        timeout = new Runnable() {
            @Override
            public void run() {
                stopApi();
            }
        };
        handler.postDelayed(timeout,10000);
        Log.d(TAG, "handleSpeakerMessages: Callback Added");
    }

    public void refreshMessageRV(){
        listener.refreshMessage();
    }

    public void speakText(String text){
        repository.speakText(text);
    }
    public boolean getTriggerListeningState(){
        return triggerState;
    }


    public void assignContactToMessage(int pos, Contact contact){
        Log.w("CHAT_VIEWMODEL","Message found at:"+pos);

        messages.get(pos).setContact(contact);
    }

    public List<Contact> getContactList(){
        return repository.getContactList();
    }
    public List<CommonMessage> getCommonMessageList(){
        return repository.getCommonMessages(user);
    }


    public void onUserText(String messageText){
        Message message = new Message(messageText,conversation);
        //message.setContact(repository.getUserAsContact());
        message.setContactID(Constants.USER_ID);
        message.setMessage(messageText);
        messages.add(message);
        listener.refreshMessage();
        if(textToSpeechToggle) //TODO fix performance issue, make this async
            speakText(messageText);
        listener.hideCommonMessageScreen();

    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void end(){
        repository.endConversation(messages,conversation);
        //repository.saveMessages(messages);
    }

    public void stopApi(){
        Log.d(TAG, "handleSpeakerMessages: stop called");
        triggerState=false;
        listeningStateLiveData.setValue(triggerState);
        repository.stopListening();

        Message message = new Message();
        message.setMessageDate(Calendar.getInstance().getTimeInMillis());
        message.setContactID(Constants.USER_ID);
        if(triggerState){
            message.setMessage("Recording has Started!");
        }else{
            message.setMessage("Recording has stopped!");
        }
        messages.add(message);
        listener.refreshMessage();
    }


    Map<Contact,Integer> colorMap = new HashMap<>();

    @Override
    public int getColorInteger(Contact contact) {
        if(colorMap.get(contact)==null){
        Random rnd = new Random();
        int newColor = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        colorMap.put(contact,newColor);
        }
        return colorMap.get(contact);
    }

    public List<Contact> getParticipantsList() {
        return participants;
    }

    public void updateMessageBubble() {
        listener.refreshMessage();
    }


}
