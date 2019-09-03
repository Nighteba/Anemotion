package betapackage;

import java.io.File;

import javafx.scene.Parent;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public abstract class AudioVisualizer {
    private MediaPlayer audioMediaPlayer;
    private AudioSpectrumListener audioSpectrumListener;
    private String AUDIO_PATH = "C:\\Users\\Gebruiker\\Music\\JLMusic\\OST\\Seiken Densetsu 3 OST - 35 - Innocent Water.mp3";


    public AudioVisualizer(File file) {
        AUDIO_PATH = file.toURI().toString();
        audioSpectrumListener = (double timestamp, double duration, float[] magnitudes, float[] phases) -> {
            updateMagnitude(magnitudes);
        };
    }

    public abstract Parent createContent();
    public abstract void updateMagnitude(float[] magnitudes);

    public MediaPlayer getAudioMediaPlayer() {
        if (audioMediaPlayer == null) {
            Media audioMedia = new Media(AUDIO_PATH);
            audioMediaPlayer = new MediaPlayer(audioMedia);
            audioMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        return audioMediaPlayer;
    }

    private void startAudio() {
        try {
        getAudioMediaPlayer().setAudioSpectrumListener(
            audioSpectrumListener);
        getAudioMediaPlayer().play();
        } catch(Exception e) {System.out.println(e); }
    }

    public void play() 
    {
        this.startAudio();
    }
}