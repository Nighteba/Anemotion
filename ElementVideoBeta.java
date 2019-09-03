package betapackage;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ElementVideoBeta extends Application
{
	//fichier exemple
		//private String cheminMedia = "C:/Users/Gebruiker/Videos/Rebirth.mp4";
		//exemple 2
		private String cheminMedia = "C:/Users/Gebruiker/Videos/OIL PAINTING TIPS  The Mind of an Artist 4.mp4";
		private Button boutonPlayPause,boutonStop;  
	    private Rectangle barreControles;
	    private Node nodeSoustitres;
	    private String soustitres;
		ProgressBar progress = new ProgressBar();  
		Label labelVideo;
	    
		private double currentHeight,currentWidth;
		
		/**
		 * Constructeur avec video choisie par defaut. (Pour les tests)
		 */
		public ElementVideoBeta() 
		{
			 cheminMedia = "C:/Users/Gebruiker/Videos/OIL PAINTING TIPS  The Mind of an Artist 4.mp4";
			
		}
		
		/**
		 * Constructeur avec video choisie par l'user.
		 * @param externalForm
		 */
	    public ElementVideoBeta(String externalForm) 
	    {
			cheminMedia = externalForm;
		}
	    /**
	     * 
	     */
	    public void setMedia(String mediaURL) 
	    {
	    	cheminMedia = mediaURL;
	    	
	    }
		/**
	     * 
	     * @param args
	     */
	    public static void main(String[] args) {
	        Application.launch(args);
	    }
	    
	    /**
	     * 
	     * @param nomFichier
	     */
	    public void setChemin(String nomFichier) 
	    {
	    	cheminMedia = nomFichier;
	    	
	    }
	    
	    public void addVideoPanel(ScrollPane canevasContenant) 
	    {
	    	//canevasContenant.add();
	    	
	    }
	    
	    /**
	     * Fonction scindant une video.
	     * (seulement le mp4 pour l'instant)
	     * 
	     */
	    public void scindeVideo(String nomFichier,int tempsDepartSecondes,int tempsCoupureSecondes)
	    {
		    	
	         try 
	         {
	        	 Runtime.getRuntime().exec("ffmpeg -i "+ nomFichier +".mp4 -ss "+ tempsDepartSecondes+ " -t 600 first-10-min.mp4"); 
	        } 
	        catch(Exception e) 
	        { 
	            System.out.println("Erreur : " + e.getMessage()); 
	            e.printStackTrace(); 
	        } 
		    	
	    }
	    
	    /**
	     * Fusion de deux fichiers (ici 2 mp4, les autres types seront supportes plus tard)
	     * @param fichier1
	     * @param fichier2
	     */
	    public void fusionneVideo(File fichier1, File fichier2) 
	    {
	    	String nomFichier1 = fichier1.getName();
	    	String nomFichier2 = fichier1.getName();
	    	//Appel a FFMPEG en utilisant un codec intermediaire
	    	try {
	    	 Runtime.getRuntime().exec("ffmpeg -i "+ nomFichier1 + ".mp4 -c copy -bsf:v h264_mp4toannexb -f mpegts intermediate1.ts");
	    	 Runtime.getRuntime().exec("ffmpeg -i " + nomFichier2 + ".mp4 -c copy -bsf:v h264_mp4toannexb -f mpegts intermediate2.ts");
	    	 Runtime.getRuntime().exec("ffmpeg -i concat:intermediate1.ts|intermediate2.ts -c copy -bsf:a aac_adtstoasc "+ nomFichier1 + nomFichier2 + ".mp4");
	    	}
	    	catch(IOException e) 
	    	{
	    		System.out.println("Erreur : " + e.getMessage()); 
	            e.printStackTrace(); 
	    		
	    	}
	    	
	    }
	    
	    /**
	     * 
	     * @return
	     */
	    public String getChemin() 
	    {
	    	return cheminMedia;
	    	
	    }
	    
	    private VBox zoneSoustitree(final Scene scene) {
	    	// create message area
	    	final VBox messageArea = new VBox(3);
	    	messageArea.setTranslateY(30);
	    	messageArea.translateXProperty().bind(scene.widthProperty().subtract(152) );
	    	messageArea.setTranslateY(20);
	    	String nodeSoustitre = "Oil Painting Tips";
	    	Node nodeSoustitres = TextBuilder.create()
	    	.stroke(Color.WHITE)
	    	.fill(Color.YELLOW)
	    	.font(new Font(15))
	    	.build();
	    	messageArea.getChildren().add(nodeSoustitres);
	    	return messageArea;
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
	    @Override
	    public void start(Stage primaryStage) {

	        StackPane root = new StackPane();
	   
	        final File fichier = new File(cheminMedia); 
	        final Media media = new Media(fichier.toURI().toString()); 
	       	
	        final MediaPlayer lecteurMedia = new MediaPlayer(media); 
	        
	        
	        MediaView vueMedia = new MediaView(lecteurMedia);  
	        
	        currentHeight = primaryStage.getHeight();
	        
	        currentWidth = primaryStage.getWidth();
	        
	        //vueMedia.setFitWidth(800);
	          vueMedia.setFitHeight(currentHeight);      
			//vueMedia.setFitHeight(700);
	        vueMedia.setFitWidth(currentWidth);
			vueMedia.setSmooth(true);
			
			
			
			
			DropShadow dropshadow = new DropShadow();
			dropshadow.setOffsetY(5.0);
			dropshadow.setOffsetX(5.0);
			dropshadow.setColor(Color.GREY);
			vueMedia.setEffect(dropshadow);


	        root.getChildren().add(vueMedia);
	        
	        Scene scene = new Scene(root, 900, 650);
	        
	       //labelVideo.add(vueMedia);
	        	
	        barreControles = new Rectangle(200,30,Color.DARKGREY);
	        barreControles.setOpacity(0.5);
	        
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

			HBox controles = new HBox(5, boutonPlayPause, boutonStop);
			root.getChildren().add(controles);
			
			DoubleProperty largeurVueMedia = vueMedia.fitWidthProperty();
			DoubleProperty hauteurVueMedia = vueMedia.fitHeightProperty();
			largeurVueMedia.bind(Bindings.selectDouble(vueMedia.sceneProperty(), "width"));
			hauteurVueMedia.bind(Bindings.selectDouble(vueMedia.sceneProperty(), "height"));
			vueMedia.setPreserveRatio(true);
			

	        primaryStage.setScene(scene);
	        
	        primaryStage.show();
	        
	        
	        /* Version 1 
	         * final MediaPlayer videoPlayer = new MediaPlayer(
	    		new Media(new File(videoPath).toURI().toString()));
		MediaView mv = new MediaView(videoPlayer);
		

		setScene(new Scene(new Group(mv), size.getWidth(), size.getHeight()));
		
		//fin Version 1
		*/

	        //Version 2
	        ControleElementBeta controleMedia = new ControleElementBeta(lecteurMedia);
	        
	        /*final VBox soustitre = zoneSoustitree(scene);
	        
	        media.getMarkers().put("Oil Painting Tips", Duration.millis(1000.0));

	        lecteurMedia.setOnMarker(new EventHandler<MediaMarkerEvent> (){
	        	public void handle(MediaMarkerEvent event){
	        	((Labeled) nodeSoustitres).setText(event.getMarker().getKey());
	        	}
	        	});
	        root.getChildren().add(soustitre);*/
	        
	        scene.setRoot(controleMedia);
	        
	        lecteurMedia.setOnPaused(new Runnable()
	        {
	        	@Override
	        	public void run() 
	        	{
	        		//addSoustitresBlancs(cheminMedia);
	        		Text subtitres = new Text();
	    			subtitres.setText(cheminMedia);
	    			subtitres.setFill(Color.WHITE); 
	    		    subtitres.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50)); 
	    		    subtitres.setX(50); 
	    		    subtitres.setY(130);
	    		    
	        	    root.getChildren().add(subtitres);
	        	    
	        	}
	        	});
	        
	       lecteurMedia.play();
	       //Fin Version 2
	       

	    }	//fin methode start()
}	//fin de classe