package com.sanvaad.Model.Speech;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.audio.CodecAndBitrate;
import com.google.audio.NetworkConnectionChecker;
import com.google.audio.asr.CloudSpeechSessionParams;
import com.google.audio.asr.CloudSpeechStreamObserverParams;
import com.google.audio.asr.RepeatingRecognitionSession;
import com.google.audio.asr.SafeTranscriptionResultFormatter;
import com.google.audio.asr.SpeechRecognitionModelOptions;
import com.google.audio.asr.TranscriptionResultFormatterOptions;
import com.google.audio.asr.TranscriptionResultUpdatePublisher;
import com.google.audio.asr.cloud.CloudSpeechSessionFactory;
import com.sanvaad.Model.TextData;
import com.sanvaad.R;

import static com.google.audio.asr.SpeechRecognitionModelOptions.SpecificModel.DICTATION_DEFAULT;
import static com.google.audio.asr.SpeechRecognitionModelOptions.SpecificModel.VIDEO;
import static com.google.audio.asr.TranscriptionResultFormatterOptions.TranscriptColoringStyle.NO_COLORING;

public class SpeechToText {

    public SpeechToText(Context context) {
        this.context = context;
        initLanguageLocale();
        constructRepeatingRecognitionSession();
    }

    Context context;

    TextView textView;



    private static final String TAG = "SPEECH TO TEXT";

    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private static final int MIC_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int MIC_CHANNEL_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private static final int MIC_SOURCE = MediaRecorder.AudioSource.VOICE_RECOGNITION;
    private static final int SAMPLE_RATE = 16000;
    private static final int CHUNK_SIZE_SAMPLES = 1280;
    private static final int BYTES_PER_SAMPLE = 2;
    private static final String SHARE_PREF_API_KEY = "api_key";

    private int currentLanguageCodePosition;
    private String currentLanguageCode;

    private AudioRecord audioRecord;
    private final byte[] buffer = new byte[BYTES_PER_SAMPLE * CHUNK_SIZE_SAMPLES];

    private RepeatingRecognitionSession recognizer;
    private NetworkConnectionChecker networkChecker;

    TextData textData;

    MutableLiveData<TextData> textDataMutableLiveData = new MutableLiveData<>();


    private final TranscriptionResultUpdatePublisher transcriptUpdater = new TranscriptionResultUpdatePublisher() {
        @Override
        public void onTranscriptionUpdate(Spanned formattedResult, UpdateType updateType) {
            setTextData(formattedResult.toString(),updateType);
            Log.w(TAG,"Running");
        }
    };

    private void setTextData(String text, TranscriptionResultUpdatePublisher.UpdateType updateType){
        boolean type=true;
         if(updateType==TranscriptionResultUpdatePublisher.UpdateType.TRANSCRIPT_FINALIZED){
             getTextData();
             textDataMutableLiveData.postValue(new TextData(text,type)); ///TODO put this on UI thread!!!
         }
    }


    public LiveData<TextData> getTextData(){
        if(textDataMutableLiveData==null){
            return new MutableLiveData<TextData>();
        }
        return textDataMutableLiveData;
    }

    private Runnable readMicData =
            () -> {
                if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                    return;
                }
                recognizer.init(CHUNK_SIZE_SAMPLES);
                while (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                    audioRecord.read(buffer, 0, CHUNK_SIZE_SAMPLES * BYTES_PER_SAMPLE);
                    recognizer.processAudioBytes(buffer);
                }
                recognizer.stop();
            };

    public void onStart() {
        //Request Permissions Here\

        startRecording();
    }


    public void onPause(){
        if (audioRecord != null) {
            audioRecord.stop();
        }
    }

    public void onResume(){
        if (audioRecord != null) {
            audioRecord.startRecording();
        }
    }


    public void onStop() {
        Log.w(TAG,"Stopped");
        if (audioRecord != null) {
            audioRecord.stop();
        }
    }


    public void onDestroy() {
        if (recognizer != null) {
            recognizer.unregisterCallback(transcriptUpdater);
            networkChecker.unregisterNetworkCallback();
        }
    }

    private void initLanguageLocale() {
        // The default locale is en-US.
        currentLanguageCode = "en-US";
        currentLanguageCodePosition = 22;
    }

    private void constructRepeatingRecognitionSession() {
        SpeechRecognitionModelOptions options =
                SpeechRecognitionModelOptions.newBuilder()
                        .setLocale(currentLanguageCode)
                        // As of 7/18/19, Cloud Speech's video model supports en-US only.
                        .setModel(currentLanguageCode.equals("en-US") ? VIDEO : DICTATION_DEFAULT)
                        .build();
        CloudSpeechSessionParams cloudParams =
                CloudSpeechSessionParams.newBuilder()
                        .setObserverParams(
                                CloudSpeechStreamObserverParams.newBuilder().setRejectUnstableHypotheses(false))
                        .setFilterProfanity(true)
                        .setEncoderParams(
                                CloudSpeechSessionParams.EncoderParams.newBuilder()
                                        .setEnableEncoder(true)
                                        .setAllowVbr(true)
                                        .setCodec(CodecAndBitrate.UNDEFINED))
                        .build();
        networkChecker = new NetworkConnectionChecker(context);
        networkChecker.registerNetworkCallback();

        // There are lots of options for formatting the text. These can be useful for debugging
        // and visualization, but it increases the effort of reading the transcripts.
        TranscriptionResultFormatterOptions formatterOptions =
                TranscriptionResultFormatterOptions.newBuilder()
                        .setTranscriptColoringStyle(NO_COLORING)
                        .build();
        RepeatingRecognitionSession.Builder recognizerBuilder =
                RepeatingRecognitionSession.newBuilder()
                        .setSpeechSessionFactory(new CloudSpeechSessionFactory(cloudParams, getApiKey()))
                        .setSampleRateHz(SAMPLE_RATE)
                        .setTranscriptionResultFormatter(new SafeTranscriptionResultFormatter(formatterOptions))
                        .setSpeechRecognitionModelOptions(options)
                        .setNetworkConnectionChecker(networkChecker);
        recognizer = recognizerBuilder.build();
        recognizer.registerCallback(transcriptUpdater, TranscriptionResultUpdatePublisher.ResultSource.MOST_RECENT_SEGMENT);
    }

    private void startRecording() {
        if (audioRecord == null) {
            audioRecord =
                    new AudioRecord(
                            MIC_SOURCE,
                            SAMPLE_RATE,
                            MIC_CHANNELS,
                            MIC_CHANNEL_ENCODING,
                            CHUNK_SIZE_SAMPLES * BYTES_PER_SAMPLE);
            Log.d(TAG, "startRecording: audioRecord Initialized");
        }
        if(audioRecord==null)
            return;
        audioRecord.startRecording();
        new Thread(readMicData).start();
    }

  //** Handles selecting language by spinner. *//*
    private void handleLanguageChanged(int itemPosition) {
        currentLanguageCodePosition = itemPosition;
       // currentLanguageCode = Resources.getResources().getStringArray(R.array.language_locales)[itemPosition];
    }

    private static String getApiKey() {
        return "";
    }
}
