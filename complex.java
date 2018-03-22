
class complex{
	public double real;
	public double imaginary;
	public complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	public complex(double d) {
		this.real = d;
		this.imaginary = 0;
	}
	public complex add(complex a) {
		complex tmp = new complex(0,0);
		tmp.real = a.real+ real;
		tmp.imaginary = a.imaginary + imaginary;
		if(tmp.real<0.1&&tmp.real>0)
			tmp.real=0;
		if(tmp.imaginary<0.1&&tmp.imaginary>0)
			tmp.imaginary=0;
		if(tmp.real>-0.1&&tmp.real<0)
			tmp.real=0;
		if(tmp.imaginary>-0.1&&tmp.imaginary<0)
			tmp.imaginary=0;
		return tmp;
	}
	
	public complex add(double a) {
		complex tmp = new complex(0,0);
		tmp.real  = a+real;
		tmp.imaginary = imaginary ;
		if(tmp.real<0.1&&tmp.real>0)
			tmp.real=0;
		if(tmp.imaginary<0.1&&tmp.imaginary>0)
			tmp.imaginary=0;
		if(tmp.real>-0.1&&tmp.real<0)
			tmp.real=0;
		if(tmp.imaginary>-0.1&&tmp.imaginary<0)
			tmp.imaginary=0;
		
		return tmp;
	}
	
	public complex multiply(complex a) {
		complex tmp = new complex(0,0);
		tmp.real  = a.real*this.real - a.imaginary*this.imaginary;
		tmp.imaginary = a.real*this.imaginary + a.imaginary*this.real;
		if(tmp.real<0.1&&tmp.real>0)
			tmp.real=0;
		if(tmp.imaginary<0.1&&tmp.imaginary>0)
			tmp.imaginary=0;
		if(tmp.real>-0.1&&tmp.real<0)
			tmp.real=0;
		if(tmp.imaginary>-0.1&&tmp.imaginary<0)
			tmp.imaginary=0;
		
		return tmp;
	}
	
	public complex multiply(double a) {
		complex tmp = new complex(0,0);
		tmp.real  = a*real;
		tmp.imaginary = a*imaginary ;
		if(tmp.real<0.1&&tmp.real>0)
			tmp.real=0;
		if(tmp.imaginary<0.1&&tmp.imaginary>0)
			tmp.imaginary=0;
		if(tmp.real>-0.1&&tmp.real<0)
			tmp.real=0;
		if(tmp.imaginary>-0.1&&tmp.imaginary<0)
			tmp.imaginary=0;
		
		return tmp;
	}
	
	public complex divide(double a) {
		real = real/a;
		imaginary = imaginary/a;
		if(real<0.1&&real>0)
			real=0;
		if(imaginary<0.1&&imaginary>0)
			imaginary=0;
		if(real>-0.1&&real<0)
			real=0;
		if(imaginary>-0.1&&imaginary<0)
			imaginary=0;
		
		return this;
	}
	
	public complex minus(complex a) {
		complex tmp = new complex(0,0);
		tmp.real = -a.real+ real;
		tmp.imaginary = -a.imaginary + imaginary;
		if(tmp.real<0.1&&tmp.real>0)
			tmp.real=0;
		if(tmp.imaginary<0.1&&tmp.imaginary>0)
			tmp.imaginary=0;
		if(tmp.real>-0.1&&tmp.real<0)
			tmp.real=0;
		if(tmp.imaginary>-0.1&&tmp.imaginary<0)
			tmp.imaginary=0;
		return tmp;
		
		
	}
	public double amplitude() {
		double d = Math.sqrt(this.real*this.real + this.imaginary*this.imaginary);
		if(d<0)
			d=d*-1;
		return d;
	}
}	


