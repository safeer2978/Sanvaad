package com.sanvaad.Model.Speech;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.Log;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.TextToSpeechSettings;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import com.sanvaad.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class TextToSpeech {



    private static final String TAG = "TEXT_TO_SPEECH:";

    String languageCode="en-IN";
    String voiceName="en-IN-Standard-C";

    TextToSpeechClient textToSpeechClient = null;
    public static final List<String> SCOPE =
            Collections.singletonList("https://www.googleapis.com/auth/cloud-platform");

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public void setVoiceName(String voiceName){
        this.voiceName=voiceName;
    }

    public TextToSpeech() throws IOException {
        final InputStream stream = Resources.getSystem().openRawResource(R.raw.credentials);
        final GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                .createScoped(SCOPE);
        FixedCredentialsProvider credentialsProvider = FixedCredentialsProvider.create(credentials);
        TextToSpeechSettings speechSettings = TextToSpeechSettings
                .newBuilder()
                .setCredentialsProvider(credentialsProvider)
                .build();
        Log.w(TAG,"REached:"+51);
        textToSpeechClient = TextToSpeechClient.create(speechSettings);
    }

    public void playText(String text){
        if(textToSpeechClient==null){
            Log.w(TAG,"client not ready yet");
            return;
        }
        SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
        VoiceSelectionParams voice =
                VoiceSelectionParams.newBuilder()
                        .setLanguageCode(languageCode)
                        .setName(voiceName)
                        .build();
        AudioConfig audioConfig =
                AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

        Log.w(TAG,textToSpeechClient.getSettings().toString());
        SynthesizeSpeechResponse response =
                textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

        ByteString audioContents = response.getAudioContent();

        // Write the response to the output file.
        try  {
            File tempMp3 = File.createTempFile("tempFile", "mp3");
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(audioContents.toByteArray());
            fos.close();
            // In case you run into issues with threading consider new instance like:
            MediaPlayer mediaPlayer = new MediaPlayer();
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            mediaPlayer.start();
    } catch (IOException ex) {
            String s = ex.toString();
            ex.printStackTrace();
        }

    }



}
