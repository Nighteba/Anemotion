package betapackage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
/**
 * 
 * @author Gebruiker
 * 
 * Unstable env
 *
 *Trouver un moyen de resize() les differentes images d'une listView pour un affichage dynamique des differentes cellules.
 */
public class ThumbnailTimelineBeta extends Application {
	
	ArrayList<Image> listeImages;
	ArrayList<ImageIcon> listeIcones;
	
	
	public ThumbnailTimelineBeta() throws MalformedURLException{}
	
	/**
	 * 
	 * @param listeImages (Placeholder : faire une liste ou Arraylist d'ImageIcon/canvas ensuite
	 * @throws MalformedURLException
	 */
	public ThumbnailTimelineBeta(ImageIcon[] listeIcones) throws MalformedURLException
	{
		for(ImageIcon image : listeIcones) 
		{
			String path1 = "C:\\Users\\Gebruiker\\Pictures\\Insp\\pixart\\91879-the-lion-king-snes-screenshot-nice-cut-scene.gif";
			final File fichier = new File(path1); 
			java.net.URL url = fichier.toURI().toURL();
			Image img000  = new Image(url.toString());
			addImage(img000);
			
		}
		
	}
	
	public void addImage(Image imageAajouter) 
	{
		listeImages.add(imageAajouter);
		
	}
	
	public void addIcone(ImageIcon iconeAajouter) 
	{

		listeIcones.add(iconeAajouter);
		
	}
	/**
	 * 
	 * @param listeImages	(Placeholder : faire une liste ou Arraylist d'images/canvas ensuite
	 * @throws MalformedURLException
	 */
	public ThumbnailTimelineBeta(Image[] listeImages) throws MalformedURLException
	{
		
		for(Image image : listeImages) 
		{
			String path1 = "C:\\Users\\Gebruiker\\Pictures\\Insp\\pixart\\91879-the-lion-king-snes-screenshot-nice-cut-scene.gif";
			final File fichier = new File(path1); 
			java.net.URL url = fichier.toURI().toURL();
			Image img000  = new Image(url.toString());
			
		}
	}
    
	
	String path1 = "C:\\Users\\Gebruiker\\Pictures\\Insp\\pixart\\91879-the-lion-king-snes-screenshot-nice-cut-scene.gif";
	final File fichier = new File(path1); 
	java.net.URL url = fichier.toURI().toURL();
	private final Image img000  = new Image(url.toString());
	
	
	String path2 = "C:\\Users\\Gebruiker\\Pictures\\Insp\\pixart\\1541627131041 (1).gif";
	final File fichier1 = new File(path2); 
	java.net.URL url1 = fichier1.toURI().toURL();
	private final Image img001  = new Image(url1.toString());
	
	
	String path3 = "C:\\Users\\Gebruiker\\Pictures\\Insp\\pixart\\1547859312626.gif";
	final File fichier2 = new File(path3); 
	java.net.URL url2 = fichier2.toURI().toURL();
	private final Image img002  = new Image(url2.toString());
	
	
	/*String path4 = "C:\\Users\\Gebruiker\\Pictures\\Insp\\pixart\\1527279876374.jpg";
	final File fichier3 = new File(path1); 
	java.net.URL url3 = fichier3.toURI().toURL();
	private final Image img003  = new Image(url3.toString());
	 */
	//private final Image img000  = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/img0_logo_64x64.png");
	//private final Image img002  = new Image("http://antaki.ca/bloom/img/windows_64x64.png");
	private final Image img003 = new Image("https://upload.wikimedia.org/wikipedia/commons/f/f1/Ruby_logo_64x64.png");

    private Image[] listOfImages = {img000, img001, img002, img003};
   
    //Mettre une arraylist a la place, ou on peut ajouter/retirer des elements (idealement des icones stackpane, selon leur position)
    @Override
    public void start(Stage primaryStage) throws Exception {

        ListView<String> listView = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList (
                "img0", "img1", "img2", "img3");
        listView.setItems(items);
        listView.setMaxHeight(350);
        listView.setMinWidth(700);

        listView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if(name.equals("img0"))
                        imageView.setImage(listOfImages[0]);
                    else if(name.equals("img1"))
                        imageView.setImage(listOfImages[1]);
                    else if(name.equals("img2"))
                        imageView.setImage(listOfImages[2]);
                    else if(name.equals("img3"))
                        imageView.setImage(listOfImages[3]);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
        HBox box = new HBox(listView);
        box.setAlignment(Pos.CENTER);
        listView.setOrientation(Orientation.HORIZONTAL);

        Scene scene = new Scene(box, 450, 250);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}