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

//QUANTIZATION USING COLOR FILTERING MASKS
 

 class quantizationFinal extends JPanel{

    public static final int OnebitPerChannel = 0xff808080; // 1 bit per channel
    public static final int TwobitPerChannel = 0xffc0c0c0; // 2 bits per channel
    public static final int ThreebitPerChannel = 0xffe0e0e0; // 3 bits per channel
    public static final int FourbitPerChannel = 0xfff0f0f0; // 4 bits per channel
    public static final int FivebitPerChannel = 0xfff8f8f8; //5 bit per channel
    public static final int SixbitPerChannel = 0xfffcfcfc; // 6 bit per channel
    public static final int SevenbitPerChannel = 0xfffefefe; //7 bit per channel
    

    public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		JFrame frame = new JFrame();
                frame.getContentPane().setLayout(new FlowLayout());
                try {
			BufferedImage original =inputImage();
			System.out.println("Enter No.of bits per channel of color (range - 1 to 7)");
			int input = sc.nextInt();
			switch(input) {
			    case 1:
				frame.getContentPane().add(new JLabel(new ImageIcon( quantizeImageColor(OnebitPerChannel,original))));
				break;
			    case 2:
				frame.getContentPane().add(new JLabel(new ImageIcon( quantizeImageColor(TwobitPerChannel,original))));
			       break;
			    case 3:
				frame.getContentPane().add(new JLabel(new ImageIcon( quantizeImageColor(ThreebitPerChannel,original))));
			       break;
			    case 4:
				frame.getContentPane().add(new JLabel(new ImageIcon( quantizeImageColor(FourbitPerChannel,original))));
			       break;
			    case 5:
				frame.getContentPane().add(new JLabel(new ImageIcon( quantizeImageColor(FivebitPerChannel,original))));
				break;
			    case 6:
				frame.getContentPane().add(new JLabel(new ImageIcon( quantizeImageColor(SixbitPerChannel,original))));
				break;
			    case 7:
				frame.getContentPane().add(new JLabel(new ImageIcon( quantizeImageColor(SevenbitPerChannel,original))));
				break;

			}
			frame.setVisible(true);
                } 
                catch (Exception e) {
                	e.printStackTrace();
                }
                
    }
    
    public static BufferedImage quantizeImageColor(int mask, BufferedImage original) {
        int w = original.getWidth();
        int h = original.getHeight();
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for(int x=0; x< w; x++){
            for(int y=0; y< h; y++){
                // Apply mask to original value and save it in result image
                result.setRGB(x,y, original.getRGB(x, y) & mask);
            }
        }
        return result;
    }


	public static BufferedImage inputImage() throws IOException {
		System.out.println("Enter path of the input image");
		Scanner sc= new Scanner(System.in);
		String path = sc.next();
		BufferedImage input = ImageIO.read(new File(path));
        	return input;
	}
 }

