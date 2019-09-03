package betapackage;
import javax.swing.JPanel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Gebruiker
 * Inspiration : https://docs.oracle.com/javase/8/javafx/media-tutorial/playercontrol.htm
 *
 */
public class ControleElementBeta extends BorderPane
{
	private MediaPlayer lecteurMedia;
    private MediaView vueMedia;
    private boolean boucle = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duree;
    private Slider sliderTemps, sliderVolume;
    private Label playTime;
    private HBox barreMedia;
    private Text subtitres;
    
    /**
     *
     * @param mp
     */
    public ControleElementBeta(final MediaPlayer lectMed) 
    {
        this.lecteurMedia = lectMed;
        setStyle("-fx-background-color: #bfc2c7;");
        vueMedia = new MediaView(lectMed);
        vueMedia.setPreserveRatio(true);
        Pane mvPane = new Pane() {                };
        mvPane.getChildren().add(vueMedia);
        //contenant.add(comp); Eeeet non ca passe pas
        mvPane.setStyle("-fx-background-color: black;"); 
        setCenter(mvPane);
        barreMedia = new HBox();
        barreMedia.setAlignment(Pos.CENTER);
        barreMedia.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(barreMedia, Pos.CENTER);
 
        final Button boutonPlayPause  = new Button(">");
        boutonPlayPause.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Status status = lectMed.getStatus();
         
                if (status == Status.UNKNOWN  || status == Status.HALTED)
                {
                   return;
                }
         
                  if ( status == Status.PAUSED || status == Status.READY || status == Status.STOPPED)
                  {
                     // rewind si a la fin
                     if (atEndOfMedia) {
                        lectMed.seek(lectMed.getStartTime());
                        atEndOfMedia = false;
                     }
                     lectMed.play();
                     } else {
                       lectMed.pause();
                     }
                 }
           });
        
        lectMed.currentTimeProperty().addListener(new InvalidationListener() 
        {
            public void invalidated(Observable ov) {
                updateValeurs();
            }
        });
 
        lectMed.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    lectMed.pause();
                    stopRequested = false;
                } else {
                    boutonPlayPause.setText("||");
                }
            }
        });
 
        lectMed.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                boutonPlayPause.setText(">");
                subtitres = new Text();
        		subtitres.setText("Suspendu : Oil Painting tips");
        		subtitres.setFill(Color.WHITE); 
        	    subtitres.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50)); 
        	    subtitres.setX(50); 
        	    subtitres.setY(130);
        	    
            }
        });
 
        lectMed.setOnReady(new Runnable() {
            public void run() {
                duree = lectMed.getMedia().getDuration();
                updateValeurs();
            }
        });
 
        lectMed.setCycleCount(boucle ? MediaPlayer.INDEFINITE : 1);
        lectMed.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!boucle) {
                    boutonPlayPause.setText(">");
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
       });
        barreMedia.getChildren().add(boutonPlayPause);
        setBottom(barreMedia); 
     // Espace
        Label spacer = new Label("   ");
        barreMedia.getChildren().add(spacer);
         
        //label temporel
        Label timeLabel = new Label("Temps : ");
        barreMedia.getChildren().add(timeLabel);
         
        // Ajout du slider temporel
        sliderTemps = new Slider();
        HBox.setHgrow(sliderTemps,Priority.ALWAYS);
        sliderTemps.setMinWidth(50);
        sliderTemps.setMaxWidth(Double.MAX_VALUE);
        
        sliderTemps.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (sliderTemps.isValueChanging()) {
               // multiply duration by percentage calculated by slider position
                  lectMed.seek(duree.multiply(sliderTemps.getValue() / 100.0));
               }
            }
        });
        barreMedia.getChildren().add(sliderTemps);

        // Add Play label
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        barreMedia.getChildren().add(playTime);
         
        // Add the volume label
        Label volumeLabel = new Label("Volume : ");
        barreMedia.getChildren().add(volumeLabel);
         
        // Add Volume slider
        sliderVolume = new Slider();        
        sliderVolume.setPrefWidth(70);
        sliderVolume.setMaxWidth(Region.USE_PREF_SIZE);
        sliderVolume.setMinWidth(30);
        
        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
               if (sliderVolume.isValueChanging()) {
                   lectMed.setVolume(sliderVolume.getValue() / 100.0);
               }
            }
        });
         
        barreMedia.getChildren().add(sliderVolume);
    	
     }
    /**
     * 
     */
    protected void updateValeurs() 
    {
    	  if (playTime != null && sliderTemps != null && sliderVolume != null) {
    	     Platform.runLater(new Runnable() {
    	        public void run() {
    	          Duration currentTime = lecteurMedia.getCurrentTime();
    	          playTime.setText(tempsFormat(currentTime, duree));
    	          sliderTemps.setDisable(duree.isUnknown());
    	          if (!sliderTemps.isDisabled() 
    	            && duree.greaterThan(Duration.ZERO) 
    	            && !sliderTemps.isValueChanging()) {
    	              sliderTemps.setValue(currentTime.divide(duree).toMillis()
    	                  * 100.0);	//caster en Double plus tard.
    	          }
    	          if (!sliderVolume.isValueChanging()) {
    	            sliderVolume.setValue((int)Math.round(lecteurMedia.getVolume() * 100));
    	          }
    	        }
    	     });
    	  }
    	}
    private static String tempsFormat(Duration elapsed, Duration duree) 
    {
    	   int intElapsed = (int)Math.floor(elapsed.toSeconds());
    	   int elapsedHours = intElapsed / (60 * 60);
    	   if (elapsedHours > 0) {
    	       intElapsed -= elapsedHours * 60 * 60;
    	   }
    	   int elapsedMinutes = intElapsed / 60;
    	   int elapsedSecondes = intElapsed - elapsedHours * 60 * 60 
    	                           - elapsedMinutes * 60;
    	 
    	   if (duree.greaterThan(Duration.ZERO)) {
    	      int intduree = (int)Math.floor(duree.toSeconds());
    	      int dureeHeures = intduree / (60 * 60);
    	      if (dureeHeures > 0) {
    	         intduree -= dureeHeures * 60 * 60;
    	      }
    	      int dureeMinutes = intduree / 60;
    	      int dureeSecondes = intduree - dureeHeures * 60 * 60 - 
    	          dureeMinutes * 60;
    	      if (dureeHeures > 0) {
    	         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
    	            elapsedHours, elapsedMinutes, elapsedSecondes,
    	            dureeHeures, dureeMinutes, dureeSecondes);
    	      } else {
    	          return String.format("%02d:%02d/%02d:%02d",
    	            elapsedMinutes, elapsedSecondes,dureeMinutes, 
    	                dureeSecondes);
    	      }
    	      } else {
    	          if (elapsedHours > 0) {
    	             return String.format("%d:%02d:%02d", elapsedHours, 
    	                    elapsedMinutes, elapsedSecondes);
    	            } else {
    	                return String.format("%02d:%02d",elapsedMinutes, 
    	                    elapsedSecondes);
    	            }
    	        }
    	    }
    /**
     * Ajout de sous titres blancs en gras par-dessus une video.
     * @param sub
     */
    public void addSoustitresBlancs(String sub)
    {
    	Text subtitres = new Text();
		subtitres.setText(sub);
		subtitres.setFill(Color.WHITE); 
	    subtitres.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50)); 
	    subtitres.setX(50); 
	    subtitres.setY(130);
	    
    	
    }
    /**
     * 
     * @param primaryStage
     */
    public void start(Stage primaryStage) 
    {
    	
    	primaryStage.setTitle("Lecteur media");
        Group root = new Group();
        Scene scene = new Scene(root, 900, 650);

        lecteurMedia.setOnPaused(new Runnable()
        {
        	@Override
        	public void run() 
        	{
        	    addSoustitresBlancs("Suspendu : Oil Painting tips");
        	    
        		root.getChildren().add(subtitres);
        	    
        	}
        	});
       
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    } 
    
    
 
}
