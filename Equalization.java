import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Equalization {
	
	static double [] probability= new double[256];
	static double [] cumu_prob = new double[256];
	static int max_pixel_count=0;

	
	public static void main(String args[]) throws IOException {
		BufferedImage buffer =inputImage();
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(buffer)));
		

		BufferedImage bi = equalizeImage(buffer);
		
		frame.getContentPane().add(new JLabel(new ImageIcon(bi)));
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	public static BufferedImage equalizeImage(BufferedImage bi){
		BufferedImage yCbCrImage = getYCbCrImage(bi);
		BufferedImage newImage = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
		equalizeHistogram(yCbCrImage);
		for(int y=0;y<bi.getHeight();y++) {
			for(int x=0;x<bi.getWidth();x++) {
				int rgb = bi.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);
				Color newc = new Color((int) cumu_prob[r],(int) cumu_prob[g], (int) cumu_prob[b]);
				int newColor = newc.getRGB();
				newImage.setRGB(x, y,newColor);
				
			}
		}
		BufferedImage output = getRGBImage(yCbCrImage);
		return output;
	
	}
	
	
	public static int[] equalizeHistogram( BufferedImage b) {
		int total = b.getHeight()*b.getWidth();
		int[] input = createHistogram(b);
		for(int i=0;i<input.length;i++)
		{		
			probability[i] = (double)input[i]/total;
			if(i==0)
				cumu_prob[i] = (double)probability[i];
			else
				cumu_prob[i]= probability[i] + cumu_prob[i-1];
		}
			
		int [] output = new int[256];
		
		for(int i=0;i<256;i++) {
			cumu_prob[i] = cumu_prob[i]*255;
			if(cumu_prob[i] - (int)cumu_prob[i]>=0.5)
				cumu_prob[i] = cumu_prob[i] +0.5;			
		}
			for(int i=0;i<256;i++)
				output[(int)cumu_prob[i]] = output[(int)cumu_prob[i]]+ input[i];
			return output;
	}
	

	private static BufferedImage getRGBImage(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
		for(int y=0;y<image.getHeight();y++) {
			for(int x=0;x<image.getWidth();x++) {
				double yy = image.getRaster().getSample(x, y, 0);
				double cb = image.getRaster().getSample(x, y, 1);
				double cr = image.getRaster().getSample(x, y, 2);
				double r = yy + 1.403*(cr-128); //min =-179.584 max = 433.181
				double g = yy -0.344*(cb-128) -0.714*(cr-128); // min = -134.366 max = 390.424
				double b = yy + 1.773*(cb-128); //min  = -226.944 max = 480.171
				
				//Scaling
//				r = (255 -(0))*(r-(-179.584))/(433.181+179.584) - 0;
//				g = (255 -(0))*(g-(-134.366))/(390.424 - (-134.366)) - 0;
//				b = (255 -(0))*(b-(-226.944))/(480.171 - (-226.944)) - 0;
		
				
				newImage.getRaster().setSample(x, y, 0, r);
				newImage.getRaster().setSample(x, y, 1, g);
				newImage.getRaster().setSample(x, y, 2, b);
				
			}
			
		}
		
		
		return newImage;
	}



	private static BufferedImage getYCbCrImage(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
		for(int y=0;y<image.getHeight();y++) {
			for(int x=0;x<image.getWidth();x++) {
				double r = image.getRaster().getSample(x, y, 0);
				double g = image.getRaster().getSample(x, y, 1);
				double b = image.getRaster().getSample(x, y, 2);
				double yy = r*0.299 + 0.587*g + 0.114*b; //min = 0 max =255 
				double cb = 0.5*b -0.169*r -0.331*g +127.5; // min = 0.5 max = 255.5
				double cr = 0.5*r -0.419*g - 0.081*b + 127.5; //min= 0.5 max=255.5
				newImage.getRaster().setSample(x, y, 0, yy);
				newImage.getRaster().setSample(x, y, 1, cb);
				newImage.getRaster().setSample(x, y, 2, cr);
				
			}
			
		}

		
		return newImage;
	}

	public static int[] createHistogram(BufferedImage b ) {
		int[] output = new int[256];
		
		for(int y=0;y<b.getHeight();y++) {
			for(int x=0;x<b.getWidth();x++) {
				int rgb = b.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				output[r]++;
				if(output[r]>max_pixel_count)
					max_pixel_count=output[r];
			}
			
		}
		
		return output;
	}


	public static BufferedImage inputImage() throws IOException {
		System.out.println("Enter path of the input image");
		Scanner sc= new Scanner(System.in);
		String path = sc.next();
		BufferedImage input = ImageIO.read(new File(path));
        return input;
	}
}
