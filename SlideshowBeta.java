package betapackage;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * https://1bestcsharp.blogspot.com/2015/04/JAVA-How-To-Create-A-Slideshow-In-Java-Swing-Using-NetBeans.html
 * @author Gebruiker
 *
 */

public class SlideshowBeta extends JFrame{
    JLabel pic;
    Timer tm;
    int x = 0;
    //Images Path In Array
    String[] list = {
                      "C:\\Users\\Gebruiker\\Documents\\Ephec\\3TI\\TFE\\Anemotion\\IMG\\java1470a.gif",//0
                      "C:\\Users\\Gebruiker\\Documents\\Ephec\\3TI\\TFE\\Anemotion\\IMG\\java1470b.gif",//1
                      "C:/Users/samsng/Desktop/jv3.jpg",//2
                      "C:/Users/samsng/Desktop/jv4.jpg",//3
                      "C:/Users/samsng/Desktop/jv5.png",//4
                      "C:/Users/samsng/Desktop/jv6.jpg",//5
                      "C:/Users/samsng/Desktop/jv7.jpg"//6
                    };
    
    public SlideshowBeta(){
        super("Java SlideShow");
        pic = new JLabel();
        pic.setBounds(40, 30, 700, 300);

        //Call The Function SetImageSize
        SetImageSize(6);
               //set a timer
        tm = new Timer(500,new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SetImageSize(x);
                x += 1;
                if(x >= list.length )
                    x = 0; 
            }
        });
        add(pic);
        tm.start();
        setLayout(null);
        setSize(800, 400);
        getContentPane().setBackground(Color.decode("#bdb67b"));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    //create a function to resize the image 
    public void SetImageSize(int i){
        ImageIcon icon = new ImageIcon(list[i]);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(pic.getWidth(), pic.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        pic.setIcon(newImc);
    }

public static void main(String[] args){ 
      
    new SlideshowBeta();
}
}
