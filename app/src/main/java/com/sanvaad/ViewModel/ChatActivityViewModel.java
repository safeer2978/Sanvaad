package com.sanvaad.ViewModel;

import android.app.Application;
import android.graphics.Color;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Repository;
import com.sanvaad.Model.TextData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChatActivityViewModel extends AndroidViewModel {
    Repository repository;

    boolean triggerState = true;
    private final Conversation conversation;
    List<Message> messages = new ArrayList<>();
    List<Contact> participants = new ArrayList<>();

    ConstraintLayout constraintLayout;


    User user;
    int index=0;

    MutableLiveData<List<Contact>> participantsLiveData = new MutableLiveData<>();

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



    public ChatActivityViewModel(Application application) {
        super(application);
        this.repository = Repository.getInstance(application);
        conversation = new Conversation(repository.getUser());
        participantsLiveData.postValue(participants);
        messagesLiveData.postValue(messages);
        handleSpeakerMessages();
    }

    Message speakerMessage;
    private void handleSpeakerMessages(){
        LiveData<TextData> textDataLiveData = repository.getTextData();
        speakerMessage = new Message("Listening to Speaker...",conversation);
        messages.add(0,speakerMessage);
        /*LiveData somthing = Transformations.map(textDataLiveData, textData -> {
            if(textdata.isFinal()) {
                speakerMessage=new Message("",conversation);
                messages.add(0,speakerMessage);
            }
            messages.get(0).setMessage(textData.getText());

        });*/
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
    }


    public void triggerListening(){
        repository.triggerListening(triggerState);
        triggerState = !triggerState;
    }

    public void speakText(String text){
        repository.speakText(text);
    }
    public boolean getTriggerListeningState(){
        return triggerState;
    }


    public void assignContactToMessage(Message message, Contact contact){
        int i=0;
        for(Message m:messages){
            if(m.getID()==message.getID())
                break;
            i++;
        }
        messages.get(i).setContact(contact);
    }

    /** DUMMY DATA //TODO Change this later
     * */
    public List<Contact> getContactList(){
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Safeer"));
        contacts.add(new Contact("Priyanshu"));
        contacts.add(new Contact("Nachi"));
        contacts.add(new Contact("Ritvik"));
        contacts.add(new Contact("Daryl"));
        contacts.add(new Contact("Shreyum"));
        contacts.add(new Contact("Daksh"));
        contacts.add(new Contact("Madhul"));
        contacts.add(new Contact("Karthik"));
        contacts.add(new Contact("Usman"));
        return contacts;

    }

    /** DUMMY DATA //TODO Change this later
     * */
    public List<CommonMessage> getCommonMessageList(){
        List<CommonMessage> commonMessages = new ArrayList<>();
        commonMessages.add(new CommonMessage("Hello There",repository.getUser()));
        commonMessages.add(new CommonMessage("Alright",repository.getUser()));
        commonMessages.add(new CommonMessage("Thank You",repository.getUser()));
        commonMessages.add(new CommonMessage("Sorry!",repository.getUser()));
        commonMessages.add(new CommonMessage("Please!",repository.getUser()));
        commonMessages.add(new CommonMessage("You're Welcome",repository.getUser()));
        commonMessages.add(new CommonMessage("Could you please speak near the phone",repository.getUser()));
        return commonMessages;
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
        repository.saveMessages(messages);

    }




    Map<Contact,Integer> colorMap = new HashMap<>();

    public int getColorInteger(Contact contact) {
        if(colorMap.get(contact)==null){
        Random rnd = new Random();
        int newColor = Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        colorMap.put(contact,newColor);
        }
        return colorMap.get(contact);
    }
}
