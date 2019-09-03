package betapackage;

//Composants AWT
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Color;
//Composants Swing
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenu;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import betapackage.PaintPanelBeta.Tool;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
//import ElementDrawingCanvas.SimplePaintPanel;
import javafx.scene.media.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.*;

//import inspackage.PaintPanel.MenuHandler;
//import inspackage.PaintPanel.Tool;

import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
/**  ***************************************************************************
 * 
 * @author JLM
 *
  ******************************************************************************/

public class MainGUIBeta extends JPanel {
	
	//Composants Swing
	private JFrame fenetre;
	private JFileChooser selecteurFichier;
	private JMenuItem item_1;
	private JScrollPane scrollPaneCanvas;		//Contiendra tous les calques, permettra de naviguer avec des toolbars flechees & zoom/dezoom
	private JLayeredPane paneCalque;			//Contiendra tous les calques, juxtaposes, avec des opacites variables, Onion skin,etc
	private JLabel calque;
	
	private JToolBar toolBarDessous;
	private JButton btnPlay,btnStop;
	
    //Composants JavaFX
    private JFXPanel panelFXVideo,panelMenuBar,panelCanvas,panelAudio;
	private MenuBar barredemenu;
	private HBox barreMedia;
	private Label timeLabel;
    private Slider sliderTemps, sliderVolume;
    
	//private IMediaReader imr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUIBeta window = new MainGUIBeta();
					window.fenetre.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUIBeta() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fenetre = new JFrame();
		Color frameColor = Color.decode("#19232e"); // utiliser W3C pour les valeurs en hex.
		fenetre.getContentPane().setBackground(frameColor);
		fenetre.setBounds(90, 30, 1000, 700);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      fenetre.setTitle("Anemotion Beta");  

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(frameColor);
		fenetre.getContentPane().add(layeredPane, BorderLayout.CENTER);
		
		//MenuBar Swing
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(frameColor);
		menuBar.setBounds(10, 0, 1300, 40);
		fenetre.getContentPane().add(menuBar);
  
		JMenu menuFichier = new JMenu("Fichier");
		menuFichier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menuFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		//ActionListener listener = new ActionListener();
		JMenuItem item;
		item = new JMenuItem("Ouvrir un fichier...");
		item.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		
		//item.addActionListener(listener);
		menuFichier.add(item);
		
		item = new JMenuItem("Enregistrer sous...");
		item.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		menuFichier.add(item);
		
		item = new JMenuItem("Quitter");
		item.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
		menuFichier.add(item);

		menuFichier.setForeground(Color.LIGHT_GRAY);
		menuFichier.setBackground(Color.GRAY);
		menuBar.add(menuFichier);

		JMenu menuEdition = new JMenu("Edition");
		menuEdition.setForeground(Color.LIGHT_GRAY);
		menuEdition.setBackground(Color.GRAY);
		menuBar.add(menuEdition);
		
		item = new JMenuItem("Retour arriere");
		item.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		menuEdition.add(item);
		
		JMenuItem mntmRetourEnAvant = new JMenuItem("Retour avant");
		mntmRetourEnAvant.setAccelerator(KeyStroke.getKeyStroke("ctrl Y"));
		menuEdition.add(mntmRetourEnAvant);

		JMenu mnCanvas = new JMenu("Canevas");
		mnCanvas.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnCanvas);
		

		
		
		JMenu mnAudio = new JMenu("Audio");
		mnAudio.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnAudio);
		item = new JMenuItem("Nouveau son...");
		item.addActionListener(new ActionListener()
		{
			
				public void actionPerformed(ActionEvent e)
				{
					ElementAudioBeta.main(null);
				}
			
			});
		mnAudio.add(item);

		JMenu mnVideo = new JMenu("Video");
		mnVideo.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnVideo);
				
		JMenu mnCalque = new JMenu("Calque");
		mnCalque.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnCalque);
		
		
		item = new JMenuItem("Nouveau Calque...");
		mnCalque.add(item);
		
		JMenu mnSelection = new JMenu("Selection");
		mnSelection.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnSelection);

		JMenu mnFenetre = new JMenu("Fenetre");
		mnFenetre.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnFenetre);

		JMenu mnAide = new JMenu("Aide");
		mnAide.setForeground(Color.LIGHT_GRAY);
		menuBar.add(mnAide);
		
		//MenuBar JavaFX
		panelMenuBar = new JFXPanel();
		fenetre.add(panelMenuBar);
		
		
		
		JScrollPane scrollPaneCanvas = new JScrollPane();
		scrollPaneCanvas.setBounds(110, 65, 1200, 510);
		fenetre.getContentPane().add(scrollPaneCanvas);
		scrollPaneCanvas.setBackground(Color.DARK_GRAY);
		scrollPaneCanvas.setVisible(false);

		// La scrollpane devrait se creer au moment de la creation d'un "New" truc. En
		// fait je pense à assigner un canvas a chaque
		// New et chacun des scrollpane sont des layers de paneCalques
		//JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setViewportView(scrollPane);
		//scrollPane.setBackground(Color.DARK_GRAY);
		//scrollPane.setVisible(true);
		
		// Mettre une listView horizontale contenant chaque frame d'une animation (dur
		// dur pour faire de meme avec une video,etant donne la possible difference de
		// FPS

		Color toolBarColor = Color.decode("#627284");
		Color toolBarButton = Color.decode("#98a5b3");
		
		paneCalque = new JLayeredPane();
		paneCalque.setVisible(true);
		//paneCalque.setBounds(110, 65, 1200, 510);
		//fenetre.getContentPane().add(paneCalque,BorderLayout.CENTER);
		paneCalque.setBounds(20, 20, 1200, 500);
		scrollPaneCanvas.add(paneCalque);
		paneCalque.setBackground(Color.WHITE);
		JScrollPane scrollPaneLateraleGauche = new JScrollPane();
		scrollPaneLateraleGauche.setBounds(10, 70, 87, 516);
		fenetre.getContentPane().add(scrollPaneLateraleGauche);
		JToolBar toolBarLateraleGauche = new JToolBar();
		toolBarLateraleGauche.setOrientation(SwingConstants.VERTICAL);
		scrollPaneLateraleGauche.setViewportView(toolBarLateraleGauche);
		toolBarLateraleGauche.setBackground(toolBarColor);
		
		JButton btnBrush = new JButton("Brush");
		btnBrush.setBackground(toolBarButton);
		btnBrush.setToolTipText("Outil pinceau / Brush Tool");
		toolBarLateraleGauche.add(btnBrush);
		
		JButton btnErase = new JButton("Erase");
		btnErase.setBackground(toolBarButton);
		toolBarLateraleGauche.add(btnErase);
		
		JButton btnNewLayer = new JButton("New Layer");
		btnNewLayer.setBackground(toolBarButton);
		toolBarLateraleGauche.add(btnNewLayer);
		btnNewLayer.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e) {
				
				/*
			      if (scrollPaneCanvas.isVisible()) 
			      {
			    	  calque = new JLabel();		//Calque est amene a devenir un object Canvas
			    	  calque.setBounds(20, 20, 1200, 500);
			    	  calque.setBackground(Color.WHITE);
			          calque.setVisible(true);
			    	  
			      }*/
				//DrawingCanvas dc = new DrawingCanvas();		Ferme aussi toutes les fenetres
				//dc.main(null);
		        scrollPaneCanvas.setVisible(true);

				PaintPanelBeta ppb = new PaintPanelBeta();		//Ferme aussi toutes les fenetres
				ppb.main(null);
			      
			}
			
		});
		
		JButton btnNewTimeline = new JButton("New TL");
		btnNewTimeline.setBackground(toolBarButton);
		toolBarLateraleGauche.add(btnNewTimeline);
		btnNewTimeline.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e) {
				
					ThumbnailTimelineBeta.main(null);		//Fonctionne, mais est bloquant. 
					//MainGUI freeze a son ouverture. (impossible donc d'en ouvrir +ieurs a la fois.
					try 
					{
						ThumbnailTimelineBeta timeline = new ThumbnailTimelineBeta();
					} catch (MalformedURLException e1) 
					{
						e1.printStackTrace();
					}
					//fenetre.getContentPane().add(timeline);	Component cast required

				
			}
			
		});
		
		
		JScrollPane scrollPanetoolBar = new JScrollPane();
		scrollPanetoolBar.setBounds(116, 614, 1200, 40);
		fenetre.getContentPane().add(scrollPanetoolBar);

		// Toolbar contenant les controles de base pour >, || ,O, |<< ou >>| une
		// animation,video,audio.
		//Ajouter une ControleMedia.mediabar instead
		JToolBar toolBarDessous = new JToolBar();
		toolBarDessous.setBackground(toolBarColor);
		scrollPanetoolBar.setViewportView(toolBarDessous);
		toolBarDessous.setBounds(167, 591, 215, 26);
		
		JButton btnPlay = new JButton(">");
		btnPlay.setForeground(Color.DARK_GRAY);
		btnPlay.setBackground(toolBarButton);

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//lecteurMedia.play(); //Demarrer TOUS les lecteurs Media & lancer l'animation des calques
				
			}
		});
		toolBarDessous.add(btnPlay);

		
		JButton btnStop = new JButton("Stop");
		btnStop.setForeground(Color.DARK_GRAY);
		btnStop.setBackground(toolBarButton);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//lecteurMedia.stop(); //Arreter TOUS les lecteurs Media & lancer l'animation des calques

			}
			
		});
		toolBarDessous.add(btnStop);

		
		JSlider sliderOpacite = new JSlider();
		sliderOpacite.setBounds(116, 581, 200, 26);
		sliderOpacite.setValue(100);
		//sliderOpacite.setPaintTicks(true); fonctionne, mais le rendu devient bizarre par manque de place.
		fenetre.getContentPane().add(sliderOpacite);
		
		
		JLabel lblOpacite = new JLabel("Opacite");
		lblOpacite.setForeground(Color.GRAY);
		lblOpacite.setBounds(326, 581, 46, 22);
		 

		fenetre.getContentPane().add(lblOpacite);
		
		JSlider sliderTailleBrush = new JSlider();
		sliderTailleBrush.setBounds(382, 581, 200, 26);
		sliderTailleBrush.setValue(10);
		//sliderTailleBrush.setPaintTicks(true); fonctionne, mais le rendu devient bizarre par manque de place.
		fenetre.getContentPane().add(sliderTailleBrush);
		
		JLabel lblTailleBrush = new JLabel("Taille brush");
		lblTailleBrush.setForeground(Color.GRAY);
		//lblTailleBrush.setBounds(594, 581, 73, 22);
		lblTailleBrush.setBounds(614, 581, 73, 22);
		fenetre.getContentPane().add(lblTailleBrush);

		item_1 = new JMenuItem("Nouveau Canevas...");
		item_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			      scrollPaneCanvas.setVisible(true);
			      
			}
		});
		
		mnCanvas.add(item_1);
		
		item = new JMenuItem("Nouvelle video...");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//ElementVideo.main(null);	
				//A l'ajout de la video, ajouter une ControleElement.mediaBar a l'emplacement de 
				//ajouteBarreMedia(); 
				//fenetre.getContentPane().
		        scrollPaneCanvas.setVisible(true);
		        ElementVideoBeta.main(null);//faire comme ca genere un element dans une fenetre, 
		        //et non le canvas video... Par contre le canvas est visible et la video s'affiche
		        
		        //Ajouter un JFileChooser pour la video
		        ajoutePanelVideoSwing();		//foire
				
			}
		});
		mnVideo.add(item);
		
		

	}	//fin initialize()
	
	public void nouveauCanevas() 
	{
		/*ElementDrawingCanvas content = new ElementDrawingCanvas();
	      fenetre.setContentPane(content);
	      fenetre.setSize(800,780);
	      fenetre.setLocation(100,100);
	      fenetre.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );*/
	      //layeredPane.revalidate();
	      //layeredPane.repaint();
	      scrollPaneCanvas.setVisible(true); 
		
	}
	/**
	 * 
	 */
	public void ajouteBarreMedia() 
	{
		barreMedia = new HBox();
		barreMedia.setAlignment(Pos.CENTER);
        barreMedia.setPadding(new Insets(5, 10, 5, 10));
        timeLabel = new Label("Temps : ");
        barreMedia.getChildren().add(timeLabel);
        
        sliderTemps = new Slider();
        HBox.setHgrow(sliderTemps,Priority.ALWAYS);
        sliderTemps.setMinWidth(50);
        sliderTemps.setMaxWidth(Double.MAX_VALUE);
        
        //sliderTemps.valueProperty().addListener(new InvalidationListener() {
          //  public void invalidated(Observable ov) {
            //   if (sliderTemps.isValueChanging()) {
               // multiply duration by percentage calculated by slider position
              //    mp.seek(duration.multiply(sliderTemps.getValue() / 100.0));
               //}
            //}
        //});
        barreMedia.getChildren().add(sliderTemps);
        //toolBarDessous.add(barreMedia);
        btnPlay  = new JButton(">");
        toolBarDessous.add(btnPlay);
        //barreMedia.getChildren().add(boutonPlayPause);

	}
	private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }
	private static Scene createScene()
	{
        Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root);		//souci conflit Swing JFX
        Text  text  =  new  Text();
        
        text.setX(40);
        text.setY(100);
        text.setFont(new Font(25));
        text.setText("Welcome JavaFX!");

        root.getChildren().add(text);

        return (scene);
    }
	public void ajoutePanelVideoSwing() 
	{
		panelFXVideo = new JFXPanel();
		panelFXVideo.setBounds(110, 65, 1200, 510);
		panelFXVideo.add(scrollPaneCanvas);
		panelFXVideo.setBackground(Color.DARK_GRAY);
        fenetre.add(panelFXVideo);
        panelFXVideo.setVisible(true);
		ElementVideoBeta.main(null);
	}
	
	/**
	 * Opens an image file selected by the user.  If the image is read
	 * successfully, it replaces the image in the off-screen canvas.
	 * (The new image is scaled to fit the canvas size exactly.)
	 */
	private void ouvrirFichierSwing() {
		if (selecteurFichier == null)
			selecteurFichier = new JFileChooser();
		selecteurFichier.setDialogTitle("Selectionnez le fichier a ouvrir");
		selecteurFichier.setSelectedFile(null);  // No file is initially selected.
		int option = selecteurFichier.showOpenDialog(this);
		if (option != JFileChooser.APPROVE_OPTION)
			return;  // User canceled or clicked the dialog's close box.
		File selectedFile = selecteurFichier.getSelectedFile();
		FileInputStream stream;
		try {
			stream = new FileInputStream(selectedFile);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Erreur, l'ouverture du fichier n'a pas eu lieu :\n" + e);
			return;
		}
		try {
			BufferedImage image = ImageIO.read(stream);
			
			//Aussi verifier les extensions avec image.toURI().toString()); et contains() 

			if (image == null)
				throw new Exception("File does not contain a recognized image format.");
			//saveUndoImage();
			//Graphics g = OSC.createGraphics();
			//g.drawImage(image,0,0,OSC.getWidth(),OSC.getHeight(),null);
			//g.dispose();
			repaint();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Sorry, but an error occurred while trying to read the image:\n" + e);
		}   
	}
	/**
	 * This nested class defines the ActionListener that responds when the
	 * user selects a command from one of the menus.  It is used in the
	 * getMenuBar() method.
	 */
	private class MenuHandler implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			String commande = evt.getActionCommand();
			switch(commande)
			{
			case "Ouvrir un fichier image...":
					ouvrirFichierSwing();
					break;
				case "Ouvrir un fichier son...":
				break;
				case "Ouvrir un fichier video...":
				break;
				
			}	//fin switch(command)
			/*if (command.equals("Ouvrir un fichier image..."))
			{
				ouvrirFichierSwing();
			}
			if (commande.equals("Custom Drawing Color...")) {
				Color newColor = JColorChooser.showDialog(PaintPanelBeta.this, 
						"Select Drawing Color", currentStrokeColor);
				if (newColor != null)
					currentStrokeColor = newColor;
			}
			else if (command.equals("Custom Fill Color...")) {
				Color newColor = JColorChooser.showDialog(PaintPanelBeta.this, 
						"Select Fill Color", currentFillColor);
				if (newColor != null)
					currentFillColor = newColor;
			}
			else if (command.equals("Clear to Color...")) {
				Color newColor = JColorChooser.showDialog(PaintPanelBeta.this, 
						"Select Background Color", fillColor);
				if (newColor != null) {
					fillColor = newColor;
					saveUndoImage();
					Graphics osg = OSC.createGraphics();
					osg.setColor(fillColor);
					osg.fillRect(0,0,OSC.getWidth(),OSC.getHeight());
					osg.dispose();
					PaintPanelBeta.this.repaint();
				}
			}
			else if (command.startsWith("Thickness = "))
				lineWidth = Integer.parseInt(command.substring(12));
			else if (command.equals("Draw With Black"))
				currentStrokeColor = Color.BLACK;
			else if (command.equals("Draw With White"))
				currentStrokeColor = Color.WHITE;
			else if (command.equals("Draw With Red"))
				currentStrokeColor = Color.RED;
			else if (command.equals("Draw With Green"))
				currentStrokeColor = Color.GREEN;
			else if (command.equals("Draw With Blue"))
				currentStrokeColor = Color.BLUE;
			else if (command.equals("Draw With Yellow"))
				currentStrokeColor = Color.YELLOW;
			else if (command.equals("Fill With Black"))
				currentFillColor = Color.BLACK;
			else if (command.equals("Fill With White"))
				currentFillColor = Color.WHITE;
			else if (command.equals("Fill With Red"))
				currentFillColor = Color.RED;
			else if (command.equals("Fill With Green"))
				currentFillColor = Color.GREEN;
			else if (command.equals("Fill With Blue"))
				currentFillColor = Color.BLUE;
			else if (command.equals("Fill With Yellow"))
				currentFillColor = Color.YELLOW;
			else if (command.equals("Curve"))
				currentTool = Tool.CURVE;
			else if (command.equals("Line"))
				currentTool = Tool.LINE;
			else if (command.equals("Rectangle"))
				currentTool = Tool.RECT;
			else if (command.equals("Oval"))
				currentTool = Tool.OVAL;
			else if (command.equals("Filled Rectangle"))
				currentTool = Tool.FILLED_RECT;
			else if (command.equals("Filled Oval"))
				currentTool = Tool.FILLED_OVAL;
			else if (command.equals("Stroked Filled Rectangle"))
				currentTool = Tool.STROKED_FILLED_RECT;
			else if (command.equals("Stroked Filled Oval"))
				currentTool = Tool.STROKED_FILLED_OVAL;
			else if (command.equals("Smudge"))
				currentTool = Tool.SMUDGE;
			else if (command.equals("Erase"))
				currentTool = Tool.ERASE;
			else if (commande.equals("Open Image File...")) 
				ouvrirFichier();
			else if (command.equals("Save PNG File..."))
				doSaveFile("PNG");
			else if (command.equals("Save JPEG File..."))
				doSaveFile("JPEG");
			else if (command.equals("Quit"))
				System.exit(0);
			else if (command.equals("Undo")) {
				BufferedImage temp = OSC;
				OSC = imageSavedForUndo;
				imageSavedForUndo = temp;
				repaint();
			}*/
		}
	} // fin classe MenuHandler


}

