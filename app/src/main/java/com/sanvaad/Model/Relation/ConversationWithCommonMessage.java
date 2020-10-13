package com.sanvaad.Model.Relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Conversation;

import java.util.List;

public class ConversationWithCommonMessage {
    @Embedded
    Conversation conversation;

    @Relation(
            parentColumn = "convoID",
            entityColumn = "convoID"
    )
    List<CommonMessage> commonmessages;

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public List<CommonMessage> getCommonmessages() {
        return commonmessages;
    }

    public void setCommonmessages(List<CommonMessage> commonmessages) {
        this.commonmessages = commonmessages;
    }
}
