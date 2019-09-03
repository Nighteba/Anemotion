package betapackage;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

public class MenuEventBeta extends Application {

   @Override
   public void start(Stage stage) {

       // Create MenuBar
       MenuBar menuBar = new MenuBar();

       // Create menus
       Menu fileMenu = new Menu("Fichier");
       Menu editMenu = new Menu("Editer");
       Menu helpMenu = new Menu("Aide");

       // Create MenuItems
       MenuItem newItem = new MenuItem("New");
       MenuItem openFileItem = new MenuItem("Open File");
       MenuItem exitItem = new MenuItem("Exit");
       
       
       
       openFileItem.setOnAction(new EventHandler<ActionEvent>() 
       {
    	   public void handle(ActionEvent event) 
    	   {
    		   FileChooser ouvreFichier = new FileChooser();
    		   ouvreFichier.setTitle("Ouvrir un fichier");
    		   ouvreFichier.showOpenDialog(stage);
    		   
    	   }
       });

       openFileItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

       // Set Accelerator for Exit MenuItem.
       exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));

       // When user click on the Exit item.
       exitItem.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
               System.exit(0);
           }
       });

       // Add menuItems to the Menus
       fileMenu.getItems().addAll(newItem, openFileItem, exitItem);

       // Add Menus to the MenuBar
       menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

       BorderPane root = new BorderPane();
       root.setTop(menuBar);
       Scene scene = new Scene(root, 800, 600);

       stage.setTitle("JavaFX Menu");
       stage.setScene(scene);
       stage.show();
   }

   public static void main(String[] args) {
       Application.launch(args);
   }

}