package com.sanvaad.Model;

import com.google.audio.asr.TranscriptionResultUpdatePublisher;

public class TextData {
    String text;
    int type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public TextData(String text, int type) {
        this.text = text;
        this.type = type;
    }
}
