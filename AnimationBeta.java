package betapackage;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

public final class AnimationBeta extends JFrame implements ActionListener
{

    private int slideIndex = 0;
    // Contient les chemins.
    File fichiers[];
    //Timer pour le delai
    Timer timer;
    //Image sur le panel.
    private final JPanel imagePanel = new JPanel();
    // all other buttons.
    private final JPanel buttonPanel = new JPanel();
    //This is the picture.
    private JLabel lblPicture = new JLabel();

    private final JButton btnAjout = new JButton("Nouvelle(s) frame(s)");
    private final JButton btnStart = new JButton("|>");
    private final JButton btnPrecedent = new JButton("<-");
    private final JButton btnSuivant = new JButton("->");
    private final JButton btnClear = new JButton("Clear");
    private final JLabel lblDelai = new JLabel("Delai");
    private final JButton btnStop = new JButton("||");
    // Formatted text field to only accept ints, explained below.
    private JFormattedTextField txtDelai = new JFormattedTextField(1);

    /**
     * Constructeur.
     */
    AnimationBeta() 
    {
        setTitle("Animation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        SetUpPanels();
        setResizable(false);
        btnAjout.addActionListener(this);
        btnStart.addActionListener(this);
        btnPrecedent.addActionListener(this);
        btnSuivant.addActionListener(this);
        btnStop.addActionListener(this);
        btnClear.addActionListener(this);
        pack();
        setVisible(true);
    }
    /**
     * 
     */
    public void SetUpTextField()
    {
        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        numberFormatter.setValueClass(Long.class);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setMinimum(0l);

        txtDelai = new JFormattedTextField(numberFormatter);
    }
    
    /**
     * 
     */
    public void SetUpPanels() 
    {
        imagePanel.add(lblPicture);
        buttonPanel.add(btnAjout);
        buttonPanel.add(btnStart);
        buttonPanel.add(btnPrecedent);
        buttonPanel.add(btnSuivant);
        buttonPanel.add(lblDelai);
        buttonPanel.add(txtDelai);
        buttonPanel.add(btnStop);
        buttonPanel.add(btnClear);
        add(imagePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Extraction des images d'un fichier .gif anime en une sequence de fichiers .png 
     */
    /*public void extraireFramesGif() 
    {
    	ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
    	ImageInputStream in = ImageIO.createImageInputStream(gifFile);
    	reader.setInput(in);
    	for (int i = 0, count = reader.getNumImages(true); i < count; i++)
    	{
    	    BufferedImage image = reader.read(i);
    	    ImageIO.write(image, "PNG", new File("output" + i + ".png"));
    	}
    	
    }*/

    @Override
    public void actionPerformed(ActionEvent e) 
    {

        if (e.getSource() == btnAjout)
        {
            //reset du label
            lblPicture.setIcon(null);
            JFileChooser selectFichier = new JFileChooser();
            selectFichier.setMultiSelectionEnabled(true);
            
            //Bug sur les .gif
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG ,BMP,PNG & GIF Images", "jpg", "gif", "png","bmp");
            selectFichier.setFileFilter(filter);
            //Rather than create an integer and compare in an if statement I decided it would be better to call the open dialog and 
            // based on what it returns to compare that to JFileChoosers approve option.
            if (selectFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
            {
                // initalize the array to the fichiers.
                fichiers = selectFichier.getSelectedFiles();
            }// If the start slide show button is pressed.
        } 

        else if (e.getSource() == btnPrecedent) 
        {
        	timer.stop();
            // set the slide to the start.
            if (fichiers == null) 
                slideIndex = 0;
             else if(slideIndex -1 == -1) 
            {
                slideIndex--;
            }
            if(fichiers[slideIndex - 2] != null)
            {
            	
            	ImageIcon iiPrecedente = new ImageIcon(fichiers[slideIndex - 2].getAbsolutePath());
                Image imgPrecedente = iiPrecedente.getImage();
                Image newimg = imgPrecedente.getScaledInstance(400, 500, java.awt.Image.SCALE_SMOOTH);
                iiPrecedente = new ImageIcon(newimg);
                lblPicture.setIcon(iiPrecedente);
                
                slideIndex++;
                pack();
            }
        }
        else if (e.getSource() == btnSuivant) 
        {
        	timer.stop();
                // set the slide to the start.
                if (fichiers == null) 
                  slideIndex = 0;
                 else if(slideIndex -1 == -1) 
                {
                    slideIndex++;
    			}
                if(fichiers[slideIndex] != null)
                {
                	ImageIcon iiSuivante = new ImageIcon(fichiers[slideIndex].getAbsolutePath());
                    Image imgSuivante = iiSuivante.getImage();
                    Image newimg = imgSuivante.getScaledInstance(400, 500, java.awt.Image.SCALE_SMOOTH);
                    iiSuivante = new ImageIcon(newimg);
                    lblPicture.setIcon(iiSuivante);
                    
                    slideIndex--;
                    pack();
                }
                
            }
        else if (e.getSource() == btnStart) 
        {
            // set the slide to the start.
            if (fichiers == null) {
                slideIndex = 0;
            } else if(slideIndex -1 == -1) {
                slideIndex = fichiers.length;
            }

            // Call the timer to cause the delay desired.
            timer = new Timer((Integer.parseInt(txtDelai.getText())), null);
            timer.addActionListener(this);
            timer.start();
            // if the timer is activated.
        }
        else if (e.getSource() == timer) {
            // Si il y a des fichiers:
            if (fichiers == null) {

            } else if (slideIndex - 1 == -1) {
                slideIndex = fichiers.length;
            } else {
                ImageIcon ii = new ImageIcon(fichiers[slideIndex - 1].getAbsolutePath());
                Image img = ii.getImage();
                Image newimg = img.getScaledInstance(400, 500, java.awt.Image.SCALE_SMOOTH);
                ii = new ImageIcon(newimg);
                lblPicture.setIcon(ii);

                slideIndex--;
                pack();
            }

            
        }
        else if (e.getSource() == btnStop) 
        {
            timer.stop();
        }
        else if (e.getSource() == btnClear) 
        {
        	timer.stop();
            lblPicture.setIcon(null);
        }
                //switch :
                //case(e.getSource() == btnPrecedent)
              //case(e.getSource() == btnPrecedent) 
              //case(e.getSource() == btnStart) 
              //case(e.getSource() == btnStop) 
              //case(e.getSource() == btnClear) 
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        AnimationBeta anim = new AnimationBeta();
    }
}