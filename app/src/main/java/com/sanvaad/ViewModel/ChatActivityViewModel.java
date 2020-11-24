package com.sanvaad.ViewModel;

import android.app.Application;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChatActivityViewModel extends AndroidViewModel implements CommonParticipantsViewModel {
    Repository repository;

    boolean triggerState = false;
    private final Conversation conversation;
    List<Message> messages = new ArrayList<>();
    List<Contact> participants = new ArrayList<>();
    messageListener listener;

    public void setListener(messageListener listener) {
        this.listener = listener;
    }

    User user;
    int index=0;

    MutableLiveData<List<Contact>> participantsLiveData = new MutableLiveData<>();


    public ChatActivityViewModel(Application application) {
        super(application);
        this.repository = Repository.getInstance(application);
        conversation = new Conversation(repository.getUser());
        conversation.setConvoID(34);    ///TODO FIx this
        conversation.setTitle("Conversation");
        participantsLiveData.postValue(participants);
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

    public void speakText(String text){
        repository.speakText(text);
    }
    public boolean getTriggerListeningState(){
        return triggerState;
    }


    public void assignContactToMessage(int pos, Contact contact){
      /*  int i=0;
        for(Message m:messages){
            if(m.getID()==message.getID())
                break;
            i++;
        }*/
        Log.w("CHAT_VIEWMODEL","Message found at:"+pos);

        messages.get(pos).setContact(contact);
    }

    /** DUMMY DATA //TODO Change this later
     * */
    public List<Contact> getContactList(){
        /*List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Safeer",2));
        contacts.add(new Contact("Priyanshu",3));
        contacts.add(new Contact("Nachi",4));
        contacts.add(new Contact("Ritvik",5));
        contacts.add(new Contact("Daryl",6));
        contacts.add(new Contact("Shreyum",7));
        contacts.add(new Contact("Daksh",8));
        contacts.add(new Contact("Madhul",9));
        contacts.add(new Contact("Karthik",10));
        contacts.add(new Contact("Usman",11));
        return contacts;*/

        return repository.getContactList();
    }

    /** DUMMY DATA //TODO Change this later
     * */
    public List<CommonMessage> getCommonMessageList(){
/*        List<CommonMessage> commonMessages = new ArrayList<>();
        commonMessages.add(new CommonMessage("Hello There",repository.getUser()));
        commonMessages.add(new CommonMessage("Alright",repository.getUser()));
        commonMessages.add(new CommonMessage("Thank You",repository.getUser()));
        commonMessages.add(new CommonMessage("Sorry!",repository.getUser()));
        commonMessages.add(new CommonMessage("Please!",repository.getUser()));
        commonMessages.add(new CommonMessage("You're Welcome",repository.getUser()));
        commonMessages.add(new CommonMessage("Could you please speak near the phone",repository.getUser()));
        return commonMessages;*/

        return repository.getCommonMessages(user);
    }


    public void onUserText(String messageText){
        Message message = new Message(messageText,conversation);
        //message.setContact(repository.getUserAsContact());
        Contact contact = new Contact();
        contact.setId(Constants.USER_ID);
        message.setContact(contact);
        message.setMessage(messageText);
        messages.add(message);
        if(textToSpeechToggle)
            speakText(messageText);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void end(){
        repository.endConversation(messages,conversation);
        //repository.saveMessages(messages);
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
