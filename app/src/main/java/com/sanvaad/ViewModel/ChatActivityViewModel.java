package com.sanvaad.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Repository;
import com.sanvaad.Model.TextData;

import java.util.ArrayList;
import java.util.List;

public class ChatActivityViewModel extends AndroidViewModel {
    Repository repository;
    boolean triggerState = false;
    private final Conversation conversation;
    List<Message> messages = new ArrayList<>();
    List<Contact> participants = new ArrayList<>();
    User user;
    int index=0;

    MutableLiveData<List<Contact>> participantsLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Message>> getMessageMutableLiveData() {
        return messageMutableLiveData;
    }

    MutableLiveData<List<Message>> messageMutableLiveData = new MutableLiveData<>();

    public boolean isTextToSpeechToggle() {
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
        messageMutableLiveData.postValue(messages);
        handleSpeakerMessages();
    }

    Message speakerMessage;
    private void handleSpeakerMessages(){
        LiveData<TextData> textData = repository.getTextData();
        speakerMessage = new Message("",conversation);
        messages.add(0,speakerMessage);
        textData.observe(getApplication(), new Observer<TextData>() {
            @Override
            public void onChanged(TextData textData) {
                if(textData.isFinal()) {
                    speakerMessage=new Message("",conversation);
                    messages.add(0,speakerMessage);
                }
                    messages.get(0).setMessage(textData.getText());
            }
        });
    }

    public LiveData<List<Contact>> getParticipantsLiveData(){
        return participantsLiveData;
    }

    public void addParticipant(Contact contact){
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
        message.setContact(repository.getUserAsContact());
        messages.add(message);
        if(textToSpeechToggle)
            speakText(messageText);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        repository.saveMessages(messages);

    }
}
