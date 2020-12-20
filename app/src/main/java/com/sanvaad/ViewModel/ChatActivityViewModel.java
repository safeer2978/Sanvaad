package com.sanvaad.ViewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.sanvaad.Model.Util.Constants;
import com.sanvaad.Model.UserData.db.Entity.CommonMessage;
import com.sanvaad.Model.UserData.db.Entity.Contact;
import com.sanvaad.Model.UserData.db.Entity.Conversation;
import com.sanvaad.Model.UserData.db.Entity.Message;
import com.sanvaad.Model.UserData.db.Entity.User;
import com.sanvaad.Model.Repository;
import com.sanvaad.Model.Util.TextData;
import com.sanvaad.View.Chat.Listener.messageListener;
import com.sanvaad.ViewModel.Interfaces.CommonParticipantsViewModel;
import com.sanvaad.ViewModel.Interfaces.ContactsViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ChatActivityViewModel extends AndroidViewModel implements CommonParticipantsViewModel, ContactsViewModel {

    final int TIMEOUT= 75000;
    String TAG= "CHAT_ACTIVITY_VIEW_MODEL";
    Repository repository;
    Message speakerMessage;
    boolean triggerState = false;
    private static Conversation conversation;
    List<Message> messages;
    List<Contact> participants = new ArrayList<>();
    messageListener listener;

    MutableLiveData<Boolean> listeningStateLiveData = new MutableLiveData<>();
    MutableLiveData<List<Contact>> participantsLiveData = new MutableLiveData<>();
    MutableLiveData<List<Message>> messagesLiveData = new MutableLiveData<>();

    Runnable timeout;
    User user;
    final Handler handler = new Handler(Looper.getMainLooper());
    private boolean textToSpeechToggle=false;

    public ChatActivityViewModel(Application application) {
        super(application);
        this.repository = Repository.getInstance(application);

        setupConversation();
        participantsLiveData.postValue(participants);
        listeningStateLiveData.setValue(triggerState);
        messagesLiveData.postValue(messages);
        user = repository.getUser();
    }

    private void setupConversation(){
        messages = new ArrayList<>();
        conversation = new Conversation(FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getUid());
        conversation.setConvoID(Calendar.getInstance().getTimeInMillis());
        Date date = new Date(conversation.getConvoID());
        conversation.setTitle("Conversation at "+date.getHours()+":"+date.getMinutes());
    }


    public void handleSpeakerMessages(TextData textData){
        speakerMessage = new Message("Listening to Speaker...", conversation);
        messages.add(speakerMessage);
        if(!textData.getText().equals("")) {
            messagesLiveData.postValue(messages);
            messages.get(messages.size() - 1).setMessage(textData.getText());
            messagesLiveData.postValue(messages);


            handler.removeCallbacks(timeout);
            handler.removeCallbacksAndMessages(null);
            Log.d(TAG, "handleSpeakerMessages: Old Callback Removed");
            timeout = new Runnable() {
                @Override
                public void run() {
                    stopApi();
                }
            };
            handler.postDelayed(timeout, TIMEOUT);
            Log.d(TAG, "handleSpeakerMessages: New Callback Added");
        }
    }

    public void onUserText(String messageText){
        Message message = new Message(messageText,conversation);
        //message.setContact(repository.getUserAsContact());
        message.setContactID(Constants.USER_ID);
        message.setMessage(messageText);
        messages.add(message);
        messagesLiveData.postValue(messages);
        listener.refreshMessage();

        if(textToSpeechToggle) //TODO fix performance issue, make this async
            speakText(messageText);
        listener.hideCommonMessageScreen();
    }


    public boolean getTextToSpeechToggle() {
        return textToSpeechToggle;
    }

    public void setTextToSpeechToggle() {
        textToSpeechToggle = !textToSpeechToggle;
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
        handler.removeCallbacks(timeout);
        handler.removeCallbacksAndMessages(null);
        timeout = new Runnable() {
            @Override
            public void run() {
                stopApi();
            }
        };
        handler.postDelayed(timeout,TIMEOUT);
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





    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void saveConversation(){
        repository.endConversation(messages,conversation);

        //repository.saveMessages(messages);
    }

    public void stopApi(){
        Log.d(TAG, "handleSpeakerMessages: stop called");
        triggerState=false;
        listeningStateLiveData.setValue(triggerState);
        repository.stopListening();
        handler.removeCallbacksAndMessages(null);
        /*Message message = new Message();
        message.setMessageDate(Calendar.getInstance().getTimeInMillis());
        message.setContactID(Constants.USER_ID);
        if(triggerState){
            message.setMessage("Recording has Started!");
        }else{
            message.setMessage("Recording has stopped!");
        }
        messages.add(message);*/
        listener.refreshMessage();
    }

    public void endConversation(){
        stopApi();
        setupConversation();
    }

    public MutableLiveData<Boolean> getListeningStateLiveData() {
        return listeningStateLiveData;
    }
    public MutableLiveData<List<Message>> getMessagesLiveData() {
        return messagesLiveData;
    }
    public LiveData<TextData> getTextLiveData(){
        return repository.getTextData();
    }

    public void setListener(messageListener listener) {
        this.listener = listener;
    }

    Map<Contact,Integer> colorMap = new HashMap<>();

    @Override
    public int getColorInteger(Contact contact) {
        if(colorMap.get(contact)==null){
        Random rnd = new Random();
        int newColor = Constants.colorSet.get(rnd.nextInt(5));
        while(colorMap.size()<5 && colorMap.containsValue(newColor))
            newColor = Constants.colorSet.get(rnd.nextInt(5));
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

    @Override
    public LiveData<List<Contact>> getContacts(String uid) {
        return repository.getContactLiveData(uid);
    }

    @Override
    public void saveContact(Contact contact) {
        repository.saveContact(contact);
    }

    @Override
    public void updateContact(Contact contact) {

    }

    @Override
    public void deleteContact(Contact contact) {

    }

    @Override
    public int parent() {
        return Constants.CHAT_VIEWMODEL;
    }

}
