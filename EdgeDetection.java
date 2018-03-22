import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class EdgeDetection {
	
	public static float scale = 0.2507345739f; 
	
	public static void main(String args[])
	{
		JFrame originalImage = new JFrame("Original Image ");
		JFrame xgrad = new JFrame("gradient in x direction ");
		JFrame ygrad = new JFrame("gradinent in y direction ");
		JFrame edges = new JFrame("Edges");
        
		originalImage.setSize(800, 800);
		xgrad.setSize(800, 800);
		ygrad.setSize(800, 800);
		edges.setSize(800, 800);

		originalImage.getContentPane().setLayout(new FlowLayout());
		xgrad.getContentPane().setLayout(new FlowLayout());
        ygrad.getContentPane().setLayout(new FlowLayout());
        edges.getContentPane().setLayout(new FlowLayout());
        
        
        try {
        BufferedImage original = inputImage();
        BufferedImage gray = turnGrayS(original);    
        BufferedImage gradx = gradientX(gray);
        BufferedImage grady= gradientY(gray);
        BufferedImage edge = finalEdge(original);
                
        originalImage.getContentPane().add(new JLabel(new ImageIcon(original)));
        edges.getContentPane().add(new JLabel(new ImageIcon(edge)));
        ygrad.getContentPane().add(new JLabel(new ImageIcon(grady)));
        xgrad.getContentPane().add(new JLabel(new ImageIcon(gradx)));
      
        edges.setVisible(true);
        } 
        catch (Exception e) {
        	e.printStackTrace();
        }
       

		
	}
	
	public static BufferedImage gradientX(BufferedImage image) {
		BufferedImage output = new BufferedImage(image.getWidth(),image.getHeight(), image.getType());
		int [][]gx = {{-1,0,1},{-2,0,2},{-1,0,1}};
		for(int y=1;y<image.getHeight()-1;y++) {
			for(int x=1;x<image.getWidth()-1;x++) {
				float tmp = (image.getRaster().getSample(x-1,y-1,0)*gx[0][0])+(image.getRaster().getSample(x,y-1,0)*gx[0][1])+(image.getRaster().getSample(x+1,y-1,0)*gx[0][2])+(image.getRaster().getSample(x-1,y,0)*gx[1][0])+(image.getRaster().getSample(x,y,0)*gx[1][1])+(image.getRaster().getSample(x+1,y,0)*gx[1][2])+(image.getRaster().getSample(x-1,y+1,0)*gx[2][0])+(image.getRaster().getSample(x,y+1,0)*gx[2][1])+(image.getRaster().getSample(x+1,y+1,0)*gx[2][2]); 
				if(tmp<0)
					tmp=tmp*-1;
			output.getRaster().setSample(x, y, 0,(int)(tmp*scale));
			}
		}
		return output;
	}
	
	public static BufferedImage gradientY(BufferedImage image) {
		BufferedImage output = new BufferedImage(image.getWidth(),image.getHeight(), image.getType());
		int [][]gy = {{-1,-2,-1},{0,0,0},{1,2,1}};
		for(int y=1;y<image.getHeight()-1;y++) {
			for(int x=1;x<image.getWidth()-1;x++) {
				float tmp = (image.getRaster().getSample(x-1,y-1,0)*gy[0][0])+(image.getRaster().getSample(x,y-1,0)*gy[0][1])+(image.getRaster().getSample(x+1,y-1,0)*gy[0][2])+(image.getRaster().getSample(x-1,y,0)*gy[1][0])+(image.getRaster().getSample(x,y,0)*gy[1][1])+(image.getRaster().getSample(x+1,y,0)*gy[1][2])+(image.getRaster().getSample(x-1,y+1,0)*gy[2][0])+(image.getRaster().getSample(x,y+1,0)*gy[2][1])+(image.getRaster().getSample(x+1,y+1,0)*gy[2][2]); 
				if(tmp<0)
					tmp=tmp*-1;
				output.getRaster().setSample(x, y, 0,(int)(tmp*scale));
				
			}
		}
		return output;
	}
	
	public static BufferedImage finalEdge(BufferedImage image) {
		image = turnGrayS(image);
		
		BufferedImage gradx= gradientX(image);
		BufferedImage grady= gradientY(image);
		
		BufferedImage output = new BufferedImage(image.getWidth(),image.getHeight(),image.getType());
		for(int y=1;y<image.getHeight()-1;y++) {
			for(int x=1;x<image.getWidth()-1;x++) {
				double pixelValue = Math.sqrt(((gradx.getRaster().getSample(x, y,0)*gradx.getRaster().getSample(x, y,0))+(grady.getRaster().getSample(x, y,0)*grady.getRaster().getSample(x, y,0))));  
				output.getRaster().setSample(x, y, 0,(int)pixelValue);
			}
		}
		
		return output;
	}
	
	
	
	public static BufferedImage turnGrayS(BufferedImage img) {
		BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
		for (int x = 0; x < img.getWidth(); ++x)
			    for (int y = 0; y < img.getHeight(); ++y)
			    {
			    	int rgb = img.getRGB(x, y);
			        int r = (rgb >> 16) & 0xFF;
			        int g = (rgb >> 8) & 0xFF;
			        int b = (rgb & 0xFF);

			        int grayLevel = (r + g + b) / 3;
			        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
					out.getRaster().setSample(x, y, 0,gray);

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
