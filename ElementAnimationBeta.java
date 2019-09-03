package betapackage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import javafx.scene.image.Image;


/**
 * 
 * @author Gebruiker
 *
 */
public class ElementAnimationBeta 
{
	Thread threadAnimation;
	ArrayList<ImageIcon> listImages;//Buffered Image pour des questions de performance (BMP,PNG,GIF,JPG supportes pour l'instant,
						//les autres comme .PSD ou .SVG sont des ameliorations futures)

	ArrayList<ImageIcon> listIcones;
	
	JLabel labelPrincipal, labelIcones;
	

	/**
	 * Ajoute un nouveau frame en fin de liste.
	 */
	public void newFrame() 
	{
		ImageIcon nouvelleImage = new ImageIcon();
		listImages.add(nouvelleImage);
		//Ajout d'un canvas ou image en + en fin de ligne,
		
	}

	
	/**
	 * Si aucun frame n'est present, en ajoute un. Ajoute un nouveau frame en fin de liste sinon.
	 */
	public void ajouteFrame(int indice) 
	{
		ImageIcon nouvelleImage = new ImageIcon();
		//Ajout d'un canvas ou image en + en fin de ligne,
		if(listImages.size() == 0) 
		{
			newFrame();
			
		}
		else
		{
			listImages.add(indice,nouvelleImage);			
		}
		
	}
	/**
	 * 
	 */
	public void retireFrame(int indice ) 
	{
		
	}
	
	/**
	 * 
	 */
	public void gotoFrame(int indice) 
	{
		
		
	}

	/**
	 *Classe interne pour animer les images 
	 */
	  class Animate extends Thread{

	    public void run(){//begin run method
	      try{
	      	//The following code will continue to
	      	// loop until the animation thread is
	      	// interrupted by the mouseExited
	      	// method.
	        while(true){
	          //affiche several images in succession.
	          affiche(1,500);
	          affiche(0,500);
	          affiche(2,500);
	          affiche(0,500);
	        }//end while loop
	      }catch(Exception ex){
	        if(ex instanceof InterruptedException)
	        {
	      
	        }else
	        {
	          System.out.println(ex);
	          System.exit(1);//terminate program
	        }//end else
	      }//end catch
	    }//end run
	    //-----------------------------------------//
	    
	    //This method affiches an image and sleeps
	    // for a prescribed period of time.  It
	    // terminates and throws an
	    // InterruptedException when interrupted
	    // by the mouseExited method.
	    void affiche(int indiceImage,int delai) 
	                    throws InterruptedException{
	      //Select and affiche an image.
	      labelPrincipal.setIcon(listImages.get(indiceImage));

	      labelIcones.setIcon(listIcones.get(indiceImage));
	      labelPrincipal.repaint();
	      //Check interrupt status.  If interrupted
	      // while not asleep, force animation to
	      // terminate.
	      if(Thread.currentThread().interrupted())
	        throw(new InterruptedException());
	      //Delay specified number of msec.
	      //Terminate animation automatically if
	      // interrupted while asleep.
	      Thread.currentThread().sleep(delai);
	    }//fin affiche()
	    
	    public void afficheFrame(int indice) throws InterruptedException
	    {
	    	affiche(indice,100);
	    	labelPrincipal.repaint();
	    	
	    }
	  }//fin nested class Animate

}		//fin ElementAnimationBeta
