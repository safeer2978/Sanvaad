package com.sanvaad.Model.Relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Message;

import java.util.List;

public class MessageConversation {
    @Embedded
    Conversation conversation;

    @Relation(
            parentColumn = "convID",
            entityColumn = "ID"
    )
    List<Message> messages;

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
