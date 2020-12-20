package com.sanvaad.Model.Util;

import com.google.audio.asr.TranscriptionResultUpdatePublisher;

import java.util.Queue;

public class TextData {
    String text;
    boolean type;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFinal() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public TextData(String text, boolean type) {
        this.text = text;
        this.type = type;

    }
}