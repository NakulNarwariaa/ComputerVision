
public class JPEGCompression {

	public static void main(String []args) {
		
		double[][] data = {{56,45,51,66,70,61,64,73},{63,59,66,90,109,85,69,72},{62,59,68,103,144,104,66,73},{63,58,71,132,134,106,70,69},{65,61,68,114,116,82,68,70},{79,65,60,67,77,68,58,75},{85,71,54,59,55,61,65,73},{87,79,69,58,65,66,78,94}};
		data = multipleOfEight(data);		
		int tmp = (data[0].length/8)*(data.length/8);
		int[][] n = new int[tmp][];
		int index=0;
			for(int y=0;y<data.length/8;y++) {
				for(int x=0;x<data[0].length/8;x++) {
					double[][] sample = new double[8][8];
					for(int i=0;i<8;i++ ) {
						for(int j=0;j<8;j++) {
							sample[i][j] = data[i + y*8][j + x*8];
						}	
					}
					n[index]= compute(sample);
				}
			}


		for(int i=0;i<n.length;i++)
		{
			System.out.println("1D sequence for "+i+" block");

			for(int j=0;j<n[i].length;j++)	
				System.out.print(n[i][j]+" ");

		}
	}
	
	
	public static int[] compute(double[][] data) {

		double[][] dataa= new double[8][8];
		for(int i=0;i<data.length;i++ ) {
			for(int j=0;j<data[0].length;j++) {
				dataa[i][j]= data[i][j]-128;
			}
		}
		dataa = dct(dataa);
		dataa = quantize(dataa);
			
		double[] d = oneDReorder(dataa);
		int[] n = new int[d.length];
		for(int i=0;i<d.length;i++)
			n[i] = (int)d[i];
	
		return n;

	}
	
	 
	
	
	
	public static double[] oneDReorder(double[][] data) {
		int direction = 0;
		double[] oneD= new double[64];
		int index=0;
		int x=0, y = 0;
		while(x<8 || y<8) {
			if(x==7&&y==7) {
				oneD[index]=data[x][y];
				break;
			}
			
			if(direction==1) {
				if(x<0 || y<0 || x>7 || y>7) {
					
					if(index>35) {
						x=x-1;
						y=y+1;
						y=y+1;
						direction=0;
						
					}
					else {
						x=x-1;
						y=y+1;
						x=x+1;
						direction=0;
					}
				}
				else {
					oneD[index]=data[x][y];
					index++;
					x=x+1;
					y=y-1;
				}
			}
			if (direction == 0){
				if(x<0 || y<0 || x>7 || y>7) {

					if(index>35) {
						x=x+1;
						y=y-1;
						x=x+1;
						direction=1;
					}
					else {
						x=x+1;
						y=y-1;
						y=y+1;
						direction=1;
				}
				}
				else {
					oneD[index]=data[x][y];
					index++;
					x=x-1;
					y=y+1;
				}
			}
		}
		return oneD;
	}
	
	public static double[][] multipleOfEight(double[][] imageData){
		int height = imageData.length;
		int width = imageData[0].length;
		if(height%8==0&&width%8==0)
		{
			return imageData;
		}
		if(height%8!=0) {
			height = height/8 + 8;
		}
		if(width%8!=0) {
			width = width/8 + 8;
		}
		
		double[][] data = new double[height][width];
		for(int i=0;i<height;i++ ) {
			for(int j=0;j<width;j++) {
				if(i<imageData.length && j<imageData[0].length) {
					data[i][j] = imageData[i][j];
				}
			}
		}
		return data;
	}

	
	 //DCT
    public static double[][] dct(double[][] data)
    {
	int i,j,u,v;
	double val;
	double temp[][] = new double[8][8]; 
	//outer
	for(u=0;u<8;u++)
	{
	    for(v=0;v<8;v++)
	    {
		val = 0;
		//inner
		for(i=0;i<8;i++)
		{
		    for(j=0;j<8;j++)
		    {
		       val = val + (data[i][j]*Math.cos(Math.PI*(2*i+1)*u/16)*Math.cos(Math.PI*(2*j+1)*v/16)); 
		    }
		}           
		val = val / Math.sqrt(8); //for a

		if(u!=0)
		{
			val = val* Math.sqrt(2);
		}

		val = val / Math.sqrt(8); //for b

		if(v!=0)
		{
		    val = val * Math.sqrt(2);
		}


		temp[u][v] = val;
	    }
	}

	return temp;
    }
     
   
    public static double[][] quantize(double [][]data)
    {
       double qt[][] = {{16,11,10,16,24,40,51,61},{12,12,14,19,26,58,60,55},{14,13,16,24,40,57,69,56},{14,17,22,29,51,87,80,62},{18,22,37,56,68,109,103,77},{24,35,55,64,81,104,113,92},{49,64,78,87,103,121,120,101},{72,92,95,98,112,110,103,99}};
       for(int i=0;i<8;i++)
          for(int j=0;j<8;j++){
		  data[i][j]=data[i][j]/qt[i][j];
	  	  data[i][j] = Math.round(data[i][j]);
       	  }
      return data; 
    }


	
}
