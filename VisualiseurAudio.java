package betapackage;

import javafx.scene.Scene;
import javafx.scene.media.AudioSpectrumListener;

public class VisualiseurAudio
{
	public VisualiseurAudio() 
	{
		audioSpectrumListener = (double timestamp, double duration, 
			    float[] magnitudes, float[] phases) -> {
			        line.updateMagnitude(magnitudes); };
			    
		
	}

	private AudioSpectrumListener audioSpectrumListener;

	@Override
	public void start(Stage primaryStage) throws IOException
	{

    AudioVisualizer line = new AudioLine(new File("testing.mp3"));

    //Lines below is directly copied from the constructor of AudioVisualizer object
    audioSpectrumListener = (double timestamp, double duration, 
    float[] magnitudes, float[] phases) -> {
        line.updateMagnitude(magnitudes); };
    //-----------------------------------------

    //Lines below is directly copied from the play() method from AudioVisualizer
    line.getAudioMediaPlayer().setAudioSpectrumListener(
    audioSpectrumListener);
    line.getAudioMediaPlayer().play(); 
    //-----------------------------------------

    primaryStage.setScene(new Scene(line.createContent()));
    primaryStage.show();
	}

}
