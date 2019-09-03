package betapackage;

	/*File Animate04.java
	Revised 01/07/03

	Illustrates frame animation.  Stick figure
	dances when user point to the image with the
	mouse.  Stick figure stops dancing when mouse 
	pointer exits the image.

	Also illustrates:
	  Event-driven programming
	  Multi-threaded programming
	  Ordinary inner classes
	  Anonymous inner classes
	  Image icons

	Tested using SDK 1.4.1 under Win 2000.
	************************************************/

/**
 * Source : https://www.developer.com/java/other/article.php/1587091/Fun-with-Java-Frame-Animation.htm
 */
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;

	public class Animate04Beta extends JFrame{
	  Thread animate;//Store ref to animation thread
	  ImageIcon images[] = {//An array of images
	  	new ImageIcon("C:\\Users\\Gebruiker\\Documents\\Ephec\\3TI\\TFE\\Anemotion\\IMG\\java1470a.gif"),
	    new ImageIcon("C:\\Users\\Gebruiker\\Documents\\Ephec\\3TI\\TFE\\Anemotion\\IMG\\java1470b.gif"),
	    new ImageIcon("C:\\Users\\Gebruiker\\Documents\\Ephec\\3TI\\TFE\\Anemotion\\IMG\\java1470c.gif")};
	  JLabel label = new JLabel(images[0]);
	  
	  ThumbnailTimelineBeta ligneDuTemps;
	  

	  JLabel labelligneDuTemps = new JLabel(images[0]);
	  
	  //-------------------------------------------//
	  public Animate04Beta(){//constructor

	    getContentPane().add(label);
	    //labelligneDuTemps.setVerticalAlignment(150);
	    getContentPane().add(labelligneDuTemps);
	    
	    //Utiliser un bouton pour lancer l'anim, comme ElementVdeo & ElementAudio
	    //Use an anonymous inner class to register a
	    // mouse listener
	    getContentPane().addMouseListener(
	      new MouseAdapter(){
	        public void mouseEntered(MouseEvent e){
	          //Get a new animation thread and start
	          // the animation on it.
	          animate = new Animate();
	          animate.start();
	        }//end mouseEntered

	        public void mouseExited(MouseEvent e){
	          //Terminate the animation.    
	          animate.interrupt();
	          //Let the thread die a natural death.
	          // Then make it eligible for garbage
	          // collection.
	          while (animate.isAlive()){}//loop;
	          animate = null;
	          //Restore default image.
	          label.setIcon(images[0]);
	          label.repaint();
	          
	          labelligneDuTemps.setIcon(images[0]);
	          labelligneDuTemps.repaint();

	        }//end MouseExited
	      }//end new MouseAdapter
	    );//end addMouseListener()
	    //End definition of anonymous inner class

	    setDefaultCloseOperation(EXIT_ON_CLOSE);    
	    setTitle("Copyright 2003, R.G.Baldwin");
	    setSize(500,400);
	    setVisible(true);

	  }//end constructor
	  //-------------------------------------------//

	  public static void main(String[] args){
	    new Animate04Beta();
	  }//end main
	  //-------------------------------------------//

	  //Ordinary inner class to animate the image
	  class Animate extends Thread{

	    public void run(){//begin run method
	      try{
	      	//The following code will continue to
	      	// loop until the animation thread is
	      	// interrupted by the mouseExited
	      	// method.
	    	  
	    	  /*ligneDuTemps = new ThumbnailTimelineBeta(images);
	    	  for(int i = 0; i < images.length ; i++) 
	    	  {
		    	 ligneDuTemps.addIcone(images[i]);
	    		  
	    	  }*/

	        while(true)
	        {
	          //Display several images in succession.
	          display(1,500);
	          display(0,500);
	          display(2,500);
	          display(0,500);
	        }//end while loop
	      }catch(Exception ex){
	        if(ex instanceof InterruptedException){
	          //Do nothing. This exception is
	          // expected on mouseExited.
	        }else{//Unexpected exception occurred.
	          System.out.println(ex);
	          System.exit(1);//terminate program
	        }//end else
	      }//end catch
	    }//end run
	    //-----------------------------------------//
	    
	    //This method displays an image and sleeps
	    // for a prescribed period of time.  It
	    // terminates and throws an
	    // InterruptedException when interrupted
	    // by the mouseExited method.
	    void display(int image,int delai) 
	                    throws InterruptedException{
	      //Select and display an image.
	      label.setIcon(images[image]);
	      label.repaint();
	      //labelligneDuTemps.setIcon(ligneDuTemps.listeIcones<0>());
	      labelligneDuTemps.setIcon(images[image]);
	      labelligneDuTemps.repaint();
	      //Check interrupt status.  If interrupted
	      // while not asleep, force animation to
	      // terminate.
	      if(Thread.currentThread().interrupted())
	        throw(new InterruptedException());
	      //delai specified number of msec.
	      //Terminate animation automatically if
	      // interrupted while asleep.
	      Thread.currentThread().sleep(delai);
	    }//end display method
	    //-----------------------------------------//
	  }//end inner class named Animate

	}//end class Animate04