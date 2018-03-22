//import java.awt.Graphics;

//import java.awt.Image;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//BILINEAR INTERPOLATION IMAGE ENLARGEMENT 

public class Resampling extends JPanel {


public static BufferedImage createImageWithText(int k, BufferedImage buffer) throws IOException{

	 BufferedImage newImage = new BufferedImage(k*buffer.getWidth()-k+1, k*buffer.getHeight()-k+1, buffer.getType());
	 int x=0, y=0;
	 float x_y1, x_y, x1 = 0.0f, y1 = 0.0f,x2 = (float)newImage.getWidth(), y2 = (float)newImage.getHeight();
	 for (y = 0; y < newImage.getHeight(); y++)    //Finding new pixel values between rows of original pixels.
     	{  
		if(y%k!=0)
			continue;
        for (x = 0; x < newImage.getWidth(); x++)
        	{
            if(x%k==0)
            	{
                	x1=x;
                    x2=x+k;
                }
            for(int b=0;b<3;b++)
            {
            if(x+1>=newImage.getWidth()-1) //handling pixels with only one neighbour by copying neighbour's pixel value
                {
                x_y1 = (((x2 - x)/(x2 - x1))*fixRGB(buffer,x/k,y/k,b) + ((x - x1)/(x2 - x1))*fixRGB(buffer,x/k,y/k,b));
                }
            else
                {	
                x_y1 = (((x2 - x)/(x2 - x1))*fixRGB(buffer,x/k,y/k,b) + ((x - x1)/(x2 - x1))*fixRGB(buffer,(x/k+1),y/k,b));
                }
          newImage.getRaster().setSample(x, y, b, x_y1);;
        	}
        	}
        }
        
	 for (y = 0; y < newImage.getHeight(); y++)        //Finding pixel value between columns with the help of previously found row pixels.
        	{  
            for (x = 0; x < newImage.getWidth(); x++)
            	{
                if(y%k==0) //since original pixels need to be kept intact, original column pixels are to be escaped by this condition.
                	{
                    y1=y;
                    y2=y+k;
                    break;
                    }
                for(int b=0;b<3;b++)
                {
                if(y+1>=newImage.getHeight()-1)    //handling pixels with only one neighbour by copying neighbour's pixel value
                	x_y = ((y2 - y)/(y2 - y1))*fixRGB(newImage,x, (int) y1, b) + ((y - y1)/(y2 - y1))*fixRGB(newImage,x, (int) y1,b);
                else
                	x_y = ((y2 - y)/(y2 - y1))*fixRGB(newImage,x, (int) y1,b) + ((y - y1)/(y2 - y1))*fixRGB(newImage,x, (int)y2,b);
                newImage.getRaster().setSample(x, y, b, x_y);
                }
            	}
           } 
       return newImage;

   }

	public static int fixRGB(BufferedImage b,int x, int y,int band) {
		
		int value =b.getRaster().getSample(x, y, band);
		
		return value;
	}

   public static void main(String[] args) throws Throwable {
      JFrame frame = new JFrame();
      frame.getContentPane().setLayout(new FlowLayout());
      BufferedImage buffer = inputImage();
      Scanner sc= new Scanner(System.in);
      System.out.println("Enter New Scale of the Image (>1) ");
      int scale = sc.nextInt();
      frame.getContentPane().add(new JLabel(new ImageIcon(buffer)));
      frame.getContentPane().add(new JLabel(new ImageIcon(createImageWithText(scale,buffer))));
      frame.setVisible(true);
   }
   
   public static BufferedImage inputImage() throws IOException {
		System.out.println("Enter path of the input image");
		Scanner sc= new Scanner(System.in);
		String path = sc.next();
		BufferedImage input = ImageIO.read(new File(path));
      return input;
	}


}