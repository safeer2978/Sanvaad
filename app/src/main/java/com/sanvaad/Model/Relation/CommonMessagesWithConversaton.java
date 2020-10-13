package com.sanvaad.Model.Relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Conversation;

import java.util.List;

public class CommonMessagesWithConversaton {
    @Embedded
    CommonMessage commonMessage;

    @Relation(
            parentColumn = "messageID",
            entityColumn = "messageID"
    )
    List<Conversation> converations;

    public CommonMessage getCommonMessage() {
        return commonMessage;
    }

    public void setCommonMessage(CommonMessage commonMessage) {
        this.commonMessage = commonMessage;
    }

    public List<Conversation> getConverations() {
        return converations;
    }

    public void setConverations(List<Conversation> converations) {
        this.converations = converations;
    }
}
