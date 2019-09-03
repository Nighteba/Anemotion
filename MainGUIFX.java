package betapackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainGUIFX extends Application 
{
	Scene scene, sceneAccordeon;
	MenuBar menuBar;
	//JFXPanel labelCanvas, labelAudio, labelVideo;
	Label labelCanvas,labelAudio,labelVideo;
	File fichier;
	String cheminMedia = "C:\\Users\\Gebruiker\\Documents\\Ephec\\3TI\\TFE\\Anemotion\\IMG\\java1470a.gif";
	ElementVideoBeta lecteurVideo;
	
	public MainGUIFX() 
	{
			
	}
	
	public void setChemin(String chemin) 
	{
		cheminMedia = chemin;
		
	}
	public String getChemin() 
	{
		return cheminMedia;
		
	}
	
	public void start(Stage primaryStage) 
	{

		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #19232e");


		scene = new Scene(root,900,600);
		//scene.setFill();
	       // Create MenuBar
	       menuBar = new MenuBar();

	       // Espace artificiel pour eviter les bugs evenementiels avec l'accordeon...
	       Menu fileMenu = new Menu("       ");
	       Menu editMenu = new Menu("     ");
	       Menu helpMenu = new Menu("    ");
	       
	       //Creation de menus
	       Menu fichierMenu = new Menu("Fichier");
	       Menu editionMenu = new Menu("Edition");
	       Menu aideMenu = new Menu("Aide");
	       Menu canevasMenu = new Menu("Canevas");
	       Menu audioMenu = new Menu("Audio");
	       Menu videoMenu = new Menu("Video");
	       Menu animationMenu = new Menu("Animation");

	       
	       // Create MenuItems
	       MenuItem nouveauItem = new MenuItem("Nouveau");
	       
	       nouveauItem.setOnAction(new EventHandler<ActionEvent>() 
	       {
	    	   public void handle(ActionEvent event) 
	    	   {
	    		   labelCanvas = new Label();
	    		   Image image = new Image(getClass().getResourceAsStream(cheminMedia));
	    		   labelCanvas.setGraphic(new ImageView(image));
	    		   labelCanvas.setTextFill(Color.web("#0076a3"));
	    	   }
	       });
	       MenuItem ouvreFichierItem = new MenuItem("Ouvrir un fichier...");
	       MenuItem quitterItem = new MenuItem("Quitter");
	       
	       
	       ouvreFichierItem.setOnAction(new EventHandler<ActionEvent>() 
	       {
	    	   public void handle(ActionEvent event) 
	    	   {
	    		   FileChooser ouvreFichier = new FileChooser();
	    		   ouvreFichier.setTitle("Ouvrir un fichier");
	    		   ouvreFichier.showOpenDialog(primaryStage);
	    		   
	    		   if (fichier != null) 
	    		   { 
	    			  
	                    try 
	                    { 
	                    	/*final FileChooser fileChooser = new FileChooser();
	                        FileChooser.ExtensionFilter mp4Filter =
	                                new FileChooser.ExtensionFilter("Fichiers MP4 (*." + "mp4" + ")", "*.");
	                        fileChooser.getExtensionFilters().add(mp4Filter);
	                        fileChooser.setTitle("Choisir une video : ");
	                       fileChooser.showOpenDialog(primaryStage);*/
	                    	/*
	                        lecteurVideo = new ElementVideoBeta(fichier.toURI().toString());
	                        lecteurVideo.setMedia(fichier.toURI().toString());
	                        lecteurVideo.start(primaryStage);	    */       //remplace toute la fenetre,et c'est pas ca qu'on veut. 
								//Faut un emplacement interne au stage.
	                         
	                    } 
	                    catch (Exception e1) { 
	                        e1.printStackTrace(); 
	                    } 
	                }
	    	   }
	       });
	       
	       /*
	        * 
        // Connecting the above three 
        file.getItems().add(open); // it would connect open with file 
        menu.getMenus().add(file); 
  
        // Adding functionality to switch to different videos 
        fileChooser = new FileChooser(); 
        open.setOnAction(new EventHandler<ActionEvent>(){ 
            public void handle(ActionEvent e) 
            { 
                // Pausing the video while switching 
                player.player.pause(); 
                File file = fileChooser.showOpenDialog(primaryStage); 
  
                // Choosing the file to play 
                if (file != null) { 
                    try { 
                        player = new Player(file.toURI().toURL().toExternalForm()); 
                        Scene scene = new Scene(player, 720, 535, Color.BLACK); 
                        primaryStage.setScene(scene); 
                    } 
                    catch (MalformedURLException e1) { 
                        e1.printStackTrace(); 
                    } 
                } 
            }  */

	       ouvreFichierItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

	       // Accelerateur pour le MenuItem Quitter.
	       quitterItem.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));

	       // Clic sur Quitter.
	       quitterItem.setOnAction(new EventHandler<ActionEvent>() 
	       {

	           @Override
	           public void handle(ActionEvent event) 
	           {
	               System.exit(0);
	           }
	       });

	       //fileMenu.getItems().addAll(newItem, openFileItem, quitterItem);
	       
	       fichierMenu.getItems().addAll(nouveauItem, ouvreFichierItem, quitterItem);

  MenuItem ouvreCanevasItem = new MenuItem("Ouvrir un canevas...");
	       
  		ouvreCanevasItem.setOnAction(new EventHandler<ActionEvent>() 
	       {

	           @Override
	           public void handle(ActionEvent event) 
	           	{
	        	   PaintPanelBeta ppb = new PaintPanelBeta();		//Ferme aussi toutes les fenetres
					ppb.main(null);
	           	}
	        });
	       canevasMenu.getItems().addAll(ouvreCanevasItem);

	       MenuItem ouvreVideoItem = new MenuItem("Ouvrir une video...");
	       
	       ouvreVideoItem.setOnAction(new EventHandler<ActionEvent>() 
	       {

	           @Override
	           public void handle(ActionEvent event) 
	           	{
	        	   ElementVideoBeta video = new ElementVideoBeta();
	        	   video.start(primaryStage);	           //remplace toute la fenetre,et c'est pas ca qu'on veut. 
	        	   											//Faut un emplacement interne au stage.
	           	}
	        });
	       videoMenu.getItems().addAll(ouvreVideoItem);
	       
 MenuItem ouvreAudioItem = new MenuItem("Ouvrir un son...");
	       
	       ouvreAudioItem.setOnAction(new EventHandler<ActionEvent>() 
	       {

	           @Override
	           public void handle(ActionEvent event) 
	           	{
	        	   ElementAudioBeta audio = new ElementAudioBeta();
	        	   audio.start(primaryStage);	           //remplace toute la fenetre,et c'est pas ca qu'on veut. 
	        	   											//Faut un emplacement interne au stage.
	           	}
	        });
	       audioMenu.getItems().addAll(ouvreAudioItem);
	       
	       MenuItem effetVideoItem = new MenuItem("Transformation video...");
	       
	       effetVideoItem.setOnAction(new EventHandler<ActionEvent>() 
	       {

	           @Override
	           public void handle(ActionEvent event) 
	           	{
	        	   demoEffetVideo videoDemo = new demoEffetVideo();
	        	   videoDemo.start(primaryStage);
	           	}
	        });
	       videoMenu.getItems().addAll(effetVideoItem);
	       
MenuItem ouvreAnimationItem = new MenuItem("Ouvrir une animation...");
	       
	       ouvreAnimationItem.setOnAction(new EventHandler<ActionEvent>() 
	       {

	           @Override
	           public void handle(ActionEvent event) 
	           	{
	               AnimationBeta anim = new AnimationBeta();
	        	   	           //remplace toute la fenetre,et c'est pas ca qu'on veut. 
	        	   											//Faut un emplacement interne au stage.
	           	}
	        });
	       animationMenu.getItems().addAll(ouvreAnimationItem);
	       
	       menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu,fichierMenu, editionMenu, aideMenu,canevasMenu,audioMenu,videoMenu,animationMenu);

	       root.setTop(menuBar);
	       
	       TitledPane titledPaneGauche = new TitledPane();
	       titledPaneGauche.setText("Outils de canevas");
	       VBox contenuSousMenu1  = new VBox();
	       contenuSousMenu1.getChildren().add(new Label("Brush"));
	       contenuSousMenu1.getChildren().add(new Label("Eraser"));
	       contenuSousMenu1.getChildren().add(new Label("Layer"));
	       contenuSousMenu1.getChildren().add(new Label("Timeline"));
	       contenuSousMenu1.getChildren().add(new Label("Selection"));
	       titledPaneGauche.setContent(contenuSousMenu1);
	       
	       TitledPane titledPaneGauche2 = new TitledPane();
	       titledPaneGauche2.setText("Outils d'Animation");
	       VBox contenuSousMenu2 = new VBox();
	       contenuSousMenu2.getChildren().add(new Label("Brush"));
	       contenuSousMenu2.getChildren().add(new Label("Eraser"));
	       contenuSousMenu2.getChildren().add(new Label("Nouvelle Frame"));
	       contenuSousMenu2.getChildren().add(new Label("Nouvelle Timeline"));
	       contenuSousMenu2.getChildren().add(new Label("Selection"));
	       titledPaneGauche2.setContent(contenuSousMenu2);
	       titledPaneGauche2.setExpanded(false);

	       
	       TitledPane titledPaneGauche3 = new TitledPane();
	       titledPaneGauche3.setText("Outils d'Audio");
	       VBox contenuSousMenu3 = new VBox();
	       contenuSousMenu3.getChildren().add(new Label("Demarrer"));
	       contenuSousMenu3.getChildren().add(new Label("Stopper"));
	       contenuSousMenu3.getChildren().add(new Label("Nouveau son"));
	       contenuSousMenu3.getChildren().add(new Label("Nouvelle Timeline"));
	       contenuSousMenu3.getChildren().add(new Label("Selection"));
	       titledPaneGauche3.setContent(contenuSousMenu3);
	       titledPaneGauche3.setExpanded(false);
	       
	       Accordion menuAccordeon= new Accordion();      
	       menuAccordeon.getPanes().addAll(titledPaneGauche,titledPaneGauche2,titledPaneGauche3);
	     
	       VBox vboxLateraleGauche = new VBox();
	       vboxLateraleGauche.setPadding(new Insets(50,10,10,10));
	       vboxLateraleGauche.getChildren().addAll(titledPaneGauche,titledPaneGauche2,titledPaneGauche3);
	       
	       primaryStage.setTitle("Anemotion");
	       primaryStage.setScene(scene);
	       root.getChildren().add(vboxLateraleGauche);
	       

	       
	       primaryStage.show();
	       
		
	}
	
	public static void main(String[] args) 
	{
		Application.launch(args);
		
	}
}