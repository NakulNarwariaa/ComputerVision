
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FFT {
	static complex[][] imagedata;
	static complex[][] fourier;
	public static void main(String args[]) throws IOException {
		BufferedImage in = inputImage();
		int n=0;
		double a;
		if(in.getHeight()>in.getWidth())
			a= Math.log(in.getHeight())/Math.log(2);
		else
			a= Math.log(in.getHeight())/Math.log(2);
		if(a>(int)a)
			{
				a=(int)a +1;
				n=(int)Math.pow(2, a);
			}
			else 
				n=(int)Math.pow(2, a);
 		
		imagedata = new complex[n][n];
		for(int x=0;x<n;x++) {
			for(int y=0;y<n;y++) {
				try {
					imagedata[x][y] = new complex(in.getRaster().getSampleDouble(x, y, 0));
				}
				catch(Exception e) {
					imagedata[x][y]=new complex(0);
				}
			}
		}
		System.out.println("Enter Choice \n1.Butterworth Low Pass filter \n2.Gaussian High Pass Filter \n3.Averaging Filter");
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();
		switch(input) {
			case 1:
				System.out.println("Enter normalized cut-off Radius ");
				double radius1 = sc.nextDouble();
				System.out.println("Enter order of filter");
				double order = sc.nextDouble();
				display("Before Filter");
				applyButterworthLowPass(radius1, order);
				display("After Filter");
				break;
			case 2:
				System.out.println("Enter normalized cut-off Radius ");
				double radius2 = sc.nextDouble();
				display("Before Filter");
				applyGaussianHighPass(radius2);
				display("After Filter");
				break;
			case 3:
				displayAverage("Before Filter");
				applyAveragingFilter();
				displayAverage("After Filter");
				break;
			default:
				break;

		}
	}

	public static void displayAverage(String name) {
		int n = imagedata.length;
		BufferedImage inverse= new BufferedImage(n,n,BufferedImage.TYPE_BYTE_GRAY);
		for(int x=0;x<imagedata.length;x++) {
			for(int y=0;y<imagedata.length;y++) {
				inverse.getRaster().setSample(x,y, 0, imagedata[x][y].amplitude());
			}
		}
		JFrame frame = new JFrame(name);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(inverse)));
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public static void display(String name) {
		int n = imagedata.length;
		BufferedImage inverse= new BufferedImage(n,n,BufferedImage.TYPE_BYTE_GRAY);
		BufferedImage fourierdata= new BufferedImage(n,n,BufferedImage.TYPE_BYTE_GRAY);
		
		if(fourier==null)
		{
			twoD(1);
			
		}
		double max=0;
		double min = 255;
		
		for(int y=0;y<fourierdata.getHeight();y++) {
			for(int x=0;x<fourierdata.getWidth();x++) {
				double tmp=Math.log(fourier[x][y].amplitude()+1);
				if(tmp>max)
					max=tmp;
				if(tmp<min)
					min=tmp;
			}	
		}
		
		double scale = 255/(max-min);
		
		for(int x=0;x<imagedata.length;x++) {
			for(int y=0;y<imagedata.length;y++) {
				inverse.getRaster().setSample(x,y, 0, imagedata[x][y].amplitude());
				if(fourier!=null)
					fourierdata.getRaster().setSample(x,y, 0, Math.log(fourier[x][y].amplitude()+1)*scale);
				
			}
		}
		JFrame inverseframe = new JFrame("Spatial domain "+name);
		inverseframe.getContentPane().setLayout(new FlowLayout());
		inverseframe.getContentPane().add(new JLabel(new ImageIcon(inverse)));
		inverseframe.pack();
		inverseframe.setVisible(true);	
		
		JFrame forwardframe = new JFrame("frequency domain "+name);
		forwardframe.getContentPane().setLayout(new FlowLayout());
		forwardframe.getContentPane().add(new JLabel(new ImageIcon(fourierdata)));
		forwardframe.pack();
		forwardframe.setVisible(true);	
		
	}
	
	
    public static complex[] oneDTransform(complex[] in,int direction) {
        int n = in.length;

        if (n == 1) 
        	return new complex[] { in[0] };

        if (n % 2 != 0) 
        {
        	System.out.println("Image Dimension not in power of 2");
        	return in;
        }
        
        complex[] even = new complex[n/2];
        for (int k = 0; k < n/2; k++) {
            even[k] = in[2*k];
        }
        complex[] evenout = oneDTransform(even,direction);

        complex[] odd  = even;  // reuse the array
        for (int k = 0; k < n/2; k++) {
            odd[k] = in[2*k + 1];
        }
        complex[] oddout = oneDTransform(odd,direction);

        complex[] out = new complex[n];
        for (int k = 0; k < n/2; k++) {
       		double theta=2 * k * Math.PI / n;
        	if(direction==1)
        		theta = -1*theta;
                complex tmp = new complex(Math.cos(theta), Math.sin(theta));
                out[k]       = evenout[k].add(tmp.multiply(oddout[k]));
                out[k + n/2] = evenout[k].minus(tmp.multiply(oddout[k]));
        }
        return out;
    }

    public static void twoD(int direction) {

		if(direction == 0 && fourier==null)
		{
			System.out.println("No Fourier data found");
			return ;
		}
		if(direction == 1 && fourier==null)
		{
			fourier = new complex[imagedata.length][imagedata.length];
		}
		
		complex[][] data;
		if(direction==1)
			data = imagedata;
		else
			data= fourier;
		complex[][] out = new complex[data.length][data.length];
		for(int x=0;x<data.length;x++) {
			complex[] row = new complex[data.length];
			
			for(int y=0;y<data.length;y++) {
				if(direction==1)
				row[y] = data[x][y].multiply(Math.pow(-1, x+y));
				else
					row[y] = data[x][y];
			}
			row = oneDTransform(row, direction);
			for(int y=0;y<data.length;y++) {
				out[x][y]=row[y];
			}
		}
	
		for(int x=0;x<data.length;x++) {
			complex[] column = new complex[data.length];
			
			for(int y=0;y<data.length;y++) {
				column[y] = out[y][x];
			}
			
			column = oneDTransform(column, direction);
			for(int y=0;y<data.length;y++) {
				out[y][x]=column[y];
				out[y][x] = out[y][x].divide(out.length);
			}
		}
		if(direction==1) {
		for(int x=0;x<out.length;x++) {
			for(int y=0;y<out.length;y++) {
				fourier[x][y] = out[x][y];
			}
		}
		}
		else
		{
			for(int x=0;x<out.length;x++) {
				for(int y=0;y<out.length;y++) {
					imagedata[x][y] = out[x][y];
				}
			}
	
		}	
	}


	
	public static void applyButterworthLowPass(double cutoff,double order) {
		if(fourier==null)
			twoD(1);
		
		if(cutoff >1 || cutoff<0)
		{
			System.out.println("Invalid argument, Normalized cut-off radius between 0-1 is to be entered");
			return;
		}
		
		cutoff = cutoff*imagedata.length;
		int tmpx = fourier[0].length/2;
		int tmpy = fourier.length/2;
		for(int y=0;y<fourier.length;y++) {
			for(int x=0;x<fourier[0].length;x++) {
				double r= Math.sqrt((x-tmpx)*(x-tmpx)+(y-tmpy)*(y-tmpy));
				double tmp = 1/(1+Math.pow(r/cutoff, 2*order));
				fourier[x][y]=fourier[x][y].multiply(tmp);
				
			}
		}
		twoD(0);
		
	}
	
		
	public static void applyGaussianHighPass(double cutoff) {
		if(fourier==null)
			twoD(1);
		
		if(cutoff >1 || cutoff<0)
		{
			System.out.println("Invalid argument, Normalized cut-off radius between 0-1 is to be entered");
			return;
		}
		
		cutoff = cutoff*imagedata.length;
		int tmpx = fourier[0].length/2;
		int tmpy = fourier.length/2;
		for(int y=0;y<fourier.length;y++) {
			for(int x=0;x<fourier[0].length;x++) {
				double r= (x-tmpx)*(x-tmpx)+(y-tmpy)*(y-tmpy);
				double power= -r/(2*cutoff*cutoff);
				double tmp = 1-Math.pow(Math.E, power);
				fourier[x][y]=fourier[x][y].multiply(tmp);
				
			}
		}
		twoD(0);
		double max=0;
		double min=255;
		for(int y=0;y<fourier.length;y++) {
			for(int x=0;x<fourier[0].length;x++) {
				if(imagedata[x][y].amplitude()>max)
					max=imagedata[x][y].amplitude();
				if(imagedata[x][y].amplitude()<min)
					min = imagedata[x][y].amplitude();
			}
		}
		
	}
	
	public static void applyAveragingFilter() {
	
		
		for(int y=1;y<imagedata.length-1;y++) {
			for(int x=1;x<imagedata.length-1;x++) {
				double tmp = imagedata[x-1][y-1].real + imagedata[x][y-1].real + imagedata[x+1][y-1].real + imagedata[x-1][y].real + imagedata[x][y].real + imagedata[x+1][y].real + imagedata[x-1][y+1].real + imagedata[x][y+1].real + imagedata[x+1][y+1].real ;
				tmp = tmp/9;
				imagedata[x][y].real = tmp;
				
			}
		}
			
	}
	
	
	public static BufferedImage inputImage() throws IOException {
		System.out.println("Enter path of the input image");
		Scanner sc= new Scanner(System.in);
		String path = sc.next();
		BufferedImage input = ImageIO.read(new File(path));
	        return input;
	}
	
	
}
