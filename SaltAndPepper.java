import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SaltAndPepper {

	//Adding salt and pepper noise and removing that noise via median filter 
	
	
	public static void main(String args[]) {

		JFrame salted = new JFrame("Image with salt and pepper noise");
		JFrame median = new JFrame("Noise removed with median filter");
		salted.getContentPane().setLayout(new FlowLayout());
		median.getContentPane().setLayout(new FlowLayout());

		try {
			salted.setSize(800, 800);
			median.setSize(800, 800);

			BufferedImage original =inputImage();

			BufferedImage newImage = addSaltAndPepper(original);
			BufferedImage afterFilter = medianFilter(newImage);

			salted.getContentPane().add(new JLabel(new ImageIcon(newImage)));
			median.getContentPane().add(new JLabel(new ImageIcon(afterFilter)));

			salted.setVisible(true);
			median.setVisible(true);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static BufferedImage addSaltAndPepper(BufferedImage img) {
		BufferedImage image = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
		for(int y=0;y<image.getHeight();y++) {
			for(int x=0;x<image.getWidth();x++) {
				image.getRaster().setSample(x,y,0,img.getRaster().getSample(x, y, 0));
				image.getRaster().setSample(x,y,1,img.getRaster().getSample(x, y, 1));
				image.getRaster().setSample(x,y,2,img.getRaster().getSample(x, y, 2));
			}
		}
		
		Random rand = new Random();
		 
		int tmp = (image.getHeight()*image.getWidth())*5/100;
		
		for(int i=1;i<tmp;i++) {
			 int x= rand.nextInt((image.getWidth()-2-1)+1)+1;
			 int y= rand.nextInt((image.getHeight()-2-1)+1)+1;
			
			 if(i%2==0)
				 {
				 image.getRaster().setSample(x, y, 0, 0);
				 image.getRaster().setSample(x, y, 1, 0);
				 image.getRaster().setSample(x, y, 2, 0);
				 }
			 else
				 {
				 image.getRaster().setSample(x, y, 0, 255);
				 image.getRaster().setSample(x, y, 1, 255);
				 image.getRaster().setSample(x, y, 2, 255);
				 }
		 }
	return image;	
	}

	
	public static BufferedImage medianFilter(BufferedImage img) {
		
		BufferedImage image = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
		for(int y=0;y<image.getHeight();y++) {
			for(int x=0;x<image.getWidth();x++) {
				image.getRaster().setSample(x,y,0,img.getRaster().getSample(x, y, 0));
				image.getRaster().setSample(x,y,1,img.getRaster().getSample(x, y, 1));
				image.getRaster().setSample(x,y,2,img.getRaster().getSample(x, y, 2));
			}
		}
		for(int y=1;y<image.getHeight()-1;y++) {
			for(int x=1;x<image.getWidth()-1;x++) {
				int []arr=new int[9];
				arr=image.getRaster().getSamples(x-1, y-1, 3, 3, 0, arr);
				arr=sort(arr);		
				image.getRaster().setSample(x, y, 0, arr[4]);
				arr=image.getRaster().getSamples(x-1, y-1, 3, 3, 1, arr);
				arr=sort(arr);		
				image.getRaster().setSample(x, y, 1, arr[4]);
				arr=image.getRaster().getSamples(x-1, y-1, 3, 3, 2, arr);
				arr=sort(arr);		
				image.getRaster().setSample(x, y, 2, arr[4]);
				
			}
		}		
		return image;
	}
	
	
	public static int[] sort(int []arr){
		
		for(int i=0;i<arr.length;i++) {
			for(int j=arr.length-1;j>0;j--) {
				if(arr[j]<arr[j-1])
				{
					int tmp=arr[j];
					arr[j]=arr[j-1];
					arr[j-1]=tmp;
				}
			}
		}
		return arr;
	}
	

	public static BufferedImage inputImage() throws IOException {
		System.out.println("Enter path of the input image");
		Scanner sc= new Scanner(System.in);
		String path = sc.next();
		BufferedImage input = ImageIO.read(new File(path));
       		return input;
	}
	
}
