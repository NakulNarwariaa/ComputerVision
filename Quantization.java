import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


//QUANTIZATION USING LOOK-UP TABLES

public class Quantization {
	public static void main(String args[]) {
		Scanner sc= new Scanner(System.in);
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		try {
			BufferedImage original = inputImage();
			System.out.println("Enter No of bits per channel");

			int channel = sc.nextInt();
			frame.getContentPane().add(new JLabel(new ImageIcon(original)));
			frame.getContentPane().add(new JLabel(new ImageIcon( quantizeImage(original,channel))));
			frame.setVisible(true);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static BufferedImage quantizeImage(BufferedImage image, int num) {
		int level = 8-num;
		float val = 255.0f/(255>>level);
		byte[] tableData = new byte[256];
		for(int i =0;i<256;i++) {
			tableData[i] = (byte) Math.round(val*(i>>level));
		}	
		LookupOp lookup = new LookupOp(new ByteLookupTable(0,tableData),null);
		BufferedImage redImage = getRedColorOfImage(image);
		BufferedImage blueImage = getBlueColorOfImage(image);
		BufferedImage greenImage = getGreenColorOfImage(image);
		
		//filter on separate color bands
		redImage = lookup.filter(redImage,null);
		greenImage = lookup.filter(greenImage,null);
		blueImage = lookup.filter(blueImage,null);

		
		BufferedImage result = mergeResults(redImage,blueImage,greenImage);
		
		return result;
			
		
	}
	

	private static BufferedImage mergeResults(BufferedImage redImage, BufferedImage blueImage,
			BufferedImage greenImage) {
		BufferedImage output = new BufferedImage(redImage.getWidth(),redImage.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
		int newColor;
		Color newc;
		int green;
		int red;
		int blue;
		for(int i=0;i<redImage.getHeight();i++) {
			for(int j=0;j<redImage.getWidth();j++) {
				red = redImage.getRaster().getSample(j, i, 2);
				green = greenImage.getRaster().getSample(j, i, 1);
				blue = blueImage.getRaster().getSample(j, i, 0);
				output.getRaster().setSample(j, i, 0, red);
				output.getRaster().setSample(j, i, 1, green);
				output.getRaster().setSample(j, i, 2, blue);
			}
		}
		return output;

	}

	private static BufferedImage getGreenColorOfImage(BufferedImage newImage) {
		BufferedImage image =copyImage(newImage);
		
		int newColor;
		Color newc;
		int green;
		for(int i=0;i<image.getHeight();i++) {
			for(int j=0;j<image.getWidth();j++) {
				green = image.getRaster().getSample(j, i, 1);
				newc = new Color(0,green,0);
				newColor = newc.getRGB();
				image.setRGB(j, i,newColor);
			}
		}
		return image;
	}

	private static BufferedImage getBlueColorOfImage(BufferedImage newImage) {
		BufferedImage image = copyImage(newImage);
		
		int newColor;
		Color newc;
		int blue;
		for(int i=0;i<image.getHeight();i++) {
			for(int j=0;j<image.getWidth();j++) {
				blue = image.getRaster().getSample(j, i, 2);
				newc = new Color(0,0,blue);
				newColor = newc.getRGB();
				image.setRGB(j, i,newColor);
			}
		}
		return image;
	}

	private static BufferedImage getRedColorOfImage(BufferedImage newImage) {
		BufferedImage image = copyImage(newImage);
		int newColor;
		Color newc;
		int red;
		for(int i=0;i<image.getHeight();i++) {
			for(int j=0;j<image.getWidth();j++) {
				red = image.getRaster().getSample(j, i, 0);
				newc = new Color(red,0,0);
				newColor = newc.getRGB();
				image.setRGB(j, i,newColor);
			}
		}
		return image;
	}

	private static BufferedImage copyImage(BufferedImage img) {
		if(img==null)
			System.out.println("yes");
		BufferedImage out = new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
		for(int i=0;i<out.getHeight();i++) {
			for(int j=0;j<out.getWidth();j++) {
				
				out.setRGB(j, i,img.getRGB(j, i));
			}
		}
				
		return out;
	}

	public static BufferedImage inputImage() throws IOException {
		System.out.println("Enter path of the input image");
		Scanner sc= new Scanner(System.in);
		String path = sc.next();
		BufferedImage input = ImageIO.read(new File(path));
       		return input;
	}
}
