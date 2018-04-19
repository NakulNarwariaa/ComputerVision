import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.lang.management.PlatformManagedObject;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
//For GreyScale Images Only

public class EqualizationGreyscale {
	
	static double [] probability= new double[256];
	static double [] cumu_prob = new double[256];
	static int max_pixel_count=0;
		
	public static void main(String args[]) throws IOException {
		
		BufferedImage buffer = inputImage();
		int []output = equalizeHistogram(buffer); //equalized histogram
		BufferedImage bi = equalizeImage(buffer); //equalized image
		//for displaying output images on a frame
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(buffer)));
		frame.getContentPane().add(new JLabel(new ImageIcon(bi)));
		frame.pack();
		frame.setVisible(true);
		
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
	
	public static BufferedImage equalizeImage(BufferedImage bi) throws IOException{
	
		BufferedImage newImage = new BufferedImage(bi.getWidth(),bi.getHeight(),bi.getType());
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

		return newImage;
	}
	
	public static boolean saveAsJPG(BufferedImage b,String name) throws IOException  {	
       		File outputfile = new File(name+".png");
	        RenderedImage rendImage = b;
	        return ImageIO.write(rendImage, "png", outputfile);
		
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

