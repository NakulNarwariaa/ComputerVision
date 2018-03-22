import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;

public class FindDistance {

	public static int findPixelDistance(BufferedImage image1,BufferedImage image2,BufferedImage pImage) {
		  
		if(image1.getWidth()!=image2.getWidth() || image1.getHeight()!=image2.getHeight())
			return 0;
		
		int xfinalpt=0, xinitialpt = 0, width = image1.getWidth(), height = image1.getHeight();
		Color red= new Color(255,0,0);
		
		int yinitial=image1.getWidth(); //just to plot initial point on image for pictorial demonstration
		int yfinal=image1.getWidth();
		for(int y=0;y<image1.getHeight();y++)
		{
			for(int x=width-1;x>=0;x--) { 
				
				if(image2.getRGB(x,y)!=pImage.getRGB(x, y)) //pixel in image 2 which is different then it's parent image i.e. background
				{
					if(xfinalpt<x) { //this verifies that the pixel we found is the farthest pixel in x direction in image2 that is not same as the background
					 yfinal=y;
						//Thus xfinal gives us the end point of car in image2
					xfinalpt=x;
					}
				}
				if(image1.getRGB(x,y)!=pImage.getRGB(x, y))	//pixel in image 2 which is different then it's parent image i.e. background
				{
					if(xinitialpt<x) {	//this verifies that the pixel we found is the farthest pixel in x direction in image1 that is not same as the background				
					xinitialpt=x;		//Thus xinitial gives us the end point of car in image1
					yinitial=y;
					}
				}
			}	
		}
		image1.setRGB(xinitialpt, yinitial, red.getRGB()); //just to demonstrate in image the points in consideration
		image2.setRGB(xfinalpt, yfinal, red.getRGB()); //not part of the algorithm that need to be implemented
		JFrame frame = new JFrame();
		JFrame frame2 = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(image1)));
		frame2.getContentPane().add(new JLabel(new ImageIcon(image2)));
		frame.pack();
		frame2.pack();
		frame.setVisible(true);
		frame2.setVisible(true);
		int tmp=0; //since we do not know the direction of the car, values of xfinal and xinitial may be swapped 
		if(xfinalpt>xinitialpt)
			tmp=xfinalpt- xinitialpt;
		else
			tmp=xinitialpt-xfinalpt;
		
		return tmp;
	}
	
	public static double calculateSpeed(int time, double pixelToDistance,int pixelDistance) {
		return (pixelDistance*pixelToDistance)/time;
	}
	
	public static void main(String args[]) throws IOException
	{
		BufferedImage image1 =inputImage();
		BufferedImage image2 = inputImage();
		BufferedImage parentImage = inputImage();
		
		//time is given in seconds and pixelto distance ratio is calculated in meters
		System.out.println("Speed of car is - "+calculateSpeed(5,0.1,findPixelDistance(image1,image2,parentImage))+" meters per second");
		
	}
	

	public static BufferedImage inputImage() throws IOException {
		System.out.println("Enter path of the input image");
		Scanner sc= new Scanner(System.in);
		String path = sc.next();
		BufferedImage input = ImageIO.read(new File(path));
        return input;
	}
	
}
