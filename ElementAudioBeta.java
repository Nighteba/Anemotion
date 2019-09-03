package betapackage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;
/**
 * Classe representant tout element audio utilise par l'app.
 * @author JLM
 *
 */
public class ElementAudioBeta extends Application 
{
	//fichier audio exemple
	private String pathname = "C:\\Users\\Gebruiker\\Music\\JLMusic\\OST\\Seiken Densetsu 3 OST - 35 - Innocent Water.mp3";
	private final int MID = 50;

	private Button boutonPlayPause,boutonStop;
    private CategoryAxis axeX;
	private NumberAxis axeY;
	private XYChart.Series<String,Number>  series1,series2;
	private XYChart.Data[] series1Data,series2Data;


	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    /**
     * @pre Le String du fichier devrait être non vide. (ça vaut mieux)
     * @param nomFichier
     */
    public void setPath(String nomFichier) 
    {
    	pathname = nomFichier;
    	
    }
    
    /**
     * 
     * @return pathname , le chemin du fichier.
     */
    public String getPath() 
    {
    	return pathname;
    	
    }
    
    /**
     * Fonction scindant un media audio.
     * (seulement le mp4 pour l'instant)
     * 
     */
    public void scindeAudio(String nomFichier,int tempsDepartSecondes,int tempsCoupureSecondes)
    {
	    	
         try 
         {
        	 Runtime.getRuntime().exec("ffmpeg -i "+ nomFichier +".mp3 -ss "+ tempsDepartSecondes+ " -t 600 "+nomFichier+"Coupe.mp3"); 
         } 
        catch(Exception e) 
        { 
            System.out.println("Erreur : " + e.getMessage()); 
            e.printStackTrace(); 
        } 
	    	
    }
    
    /**
     * Fusion de deux fichiers (ici 2 mp3, les autres types seront supportes plus tard)
     * @param fichier1
     * @param fichier2
     */
    public void fusionneAudio(File fichier1, File fichier2) 
    {
    	String nomFichier1 = fichier1.getName();
    	String nomFichier2 = fichier1.getName();
    	//Appel a FFMPEG en utilisant un codec intermediaire
    	try 
    	{
    		Runtime.getRuntime().exec("ffmpeg -i \"concat: "+nomFichier1+".mp3| "+ nomFichier2 + "file2.mp3\" -acodec copy output.mp3");
    	}
    	catch(IOException e) 
    	{
    		System.out.println("Erreur : " + e.getMessage()); 
            e.printStackTrace(); 
    		
    	}
    	
    }
    

    @Override
    public void start(Stage primaryStage) 
    {

        StackPane root = new StackPane();
   
        Line[] lines = new Line[128];
        for (int i = 0; i < 128; i++) 
        {
            Line line = new Line(5 + i*3, MID, 5 + i*3, MID);
            lines[i] = line;
            root.getChildren().add(line);
        }
        final File fichier = new File(pathname); 
        final Media media = new Media(fichier.toURI().toString()); 
        final MediaPlayer lecteurMedia = new MediaPlayer(media); 
        
        lecteurMedia.setAudioSpectrumListener(new AudioSpectrumListener() {
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
                System.out.print(timestamp + " " + magnitudes[6]);
                for (int i = 0; i < Math.max(128, magnitudes.length); i++) {
                    lines[i].setEndY(MID - magnitudes[i] + lecteurMedia.getAudioSpectrumThreshold());
                }
            }
        });
        //int spectreAudio = lecteurMedia.getAudioSpectrumThreshold();
        
        
        axeX = new CategoryAxis();
        axeY = new NumberAxis(-50,50,10);
        
        final BarChart<String,Number>  chartBarres = new BarChart<>(axeX,axeY);
        chartBarres.setLegendVisible(false);
        chartBarres.setAnimated(false);
        chartBarres.setBarGap(0);
        chartBarres.setCategoryGap(0);
        chartBarres.setVerticalGridLinesVisible(false);
        chartBarres.setHorizontalGridLinesVisible(false);
        chartBarres.setHorizontalZeroLineVisible(false);
        chartBarres.setVerticalZeroLineVisible(false);
        chartBarres.setMaxSize(900, 400);
        chartBarres.setMinSize(900, 400);
          
              axeY.setTickLabelFormatter(new NumberAxis.DefaultFormatter(axeY,null,"dB"));
              axeY.setTickLabelFill(Color.TRANSPARENT);
              axeY.setTickLabelFill(Color.TRANSPARENT);

              

        
        series1 = new XYChart.Series<>();
        series2 = new XYChart.Series<>();

        series1Data = new XYChart.Data[128];
        series2Data = new XYChart.Data[128];
        series1.setName("Series Neg");
        series2.setName("Series Pos");
             
        for (int i=0; i<series1Data.length; i++) 
        {
            series1Data[i] = new XYChart.Data<>( Integer.toString(i+1),50);
            series1.getData().add(series1Data[i]);
        }
         chartBarres.getData().add(series1);

         for (int i=0; i<series2Data.length; i++) 
         {
                    series2Data[i] = new XYChart.Data<>( Integer.toString(i+1),50);
                    series2.getData().add(series2Data[i]);
         }
         chartBarres.getData().add(series2);
        
        
        lecteurMedia.setAudioSpectrumListener((double d, double d1, float[] magnitudes , float[] phases) -> {

            for(int i=0;i<magnitudes.length;i++){

                    series1Data[i].setYValue((phases[i]+60)); //Series du haut

                    series2Data[i].setYValue(-(phases[i]+60));//Series du bas
            }

          });
        
        
        
        MediaView mediaView = new MediaView(lecteurMedia);  

        mediaView.setFitWidth(400);
      	mediaView.setFitHeight(500);
      	mediaView.setSmooth(true);

        root.getChildren().add(mediaView);

        Scene scene = new Scene(root, 750, 300);
        
		boutonPlayPause = new Button("Play");

		//Gestion evenementielle pour le bouton Play/Pause
				// Le bouton n'update pas sa value au 1er event de pause, a verif.
		boutonPlayPause.setOnAction(new EventHandler <ActionEvent>()
				{
		            public void handle(ActionEvent event)
		            {
		            	if (lecteurMedia.getStatus() == Status.PLAYING)
		            	{
		            		lecteurMedia.pause();
		            		boutonPlayPause.setText("Play");

		            		
		            	}
		            	else
		            	{
		            		lecteurMedia.play();
		            		boutonPlayPause.setText("Pause");
		            	}
		            }
		        });

      		
      	boutonStop = new Button("Stop");
      	boutonStop.setOnAction(new EventHandler <ActionEvent>()
      		{
                  public void handle(ActionEvent event)
                  {
                  	lecteurMedia.stop();
                  }
              });

              //Creation de la Horizontal Box, siege des boutons de controle

      		HBox controlBox = new HBox(5, boutonPlayPause, boutonStop);
      		
    		root.getChildren().add(controlBox);

      		
      		root.setStyle("-fx-padding: 10;" +
      						"-fx-border-style: solid inside;" +
      						"-fx-border-width: 2;" +
      						"-fx-border-insets: 5;" +
      						"-fx-border-radius: 5;" +
      						"-fx-border-color: #29a329;");
        primaryStage.setScene(scene);
        primaryStage.show();
        


        ControleElementBeta mediaControl = new ControleElementBeta(lecteurMedia);
        scene.setRoot(mediaControl);

       lecteurMedia.play();

    }	//fin start()
}		//fin ElementAudio