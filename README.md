Computer Vision Operations : 

EdgeDetection.java : 
  Edge Detection is one of the important operations in image processing which finds the edges in a given image. This class performs the   same operation by using a 3x3 sobel convolutional mask.

Equalization.java : 
  Equalization is performed to enhance the image contrast by distributing equal pixels to all color bins. This class perform so by         histogram equalization on colored RGB images by converting it to YCbCr color space.

EqualizationGreyscale.java : 
  This java class performs equalization on greyscale images without converting it to YCbCr color space.
 
FFT.java : 
  This java class computes the fourier transform of the input image and performs various frequency domain operations on it such as           butterworth lowpass filter and gaussian highpass filter.        
  
FindDsitance.java : 
  This java class attempts to find the relative distance of a car in 2 images, provided with 1 background image. Consequently, it also     computes the speed of the car in pictures using time difference in images.
  
JPEGCompression.java : 
  This java class demonstrates the first phase of jpeg compression algorithm for image compression.
  
Quantization.java : 
  This java class reduces the number of colors used in an image by reducing the number of bits per color channel. Number of allowed bits   per color channel is given by user.
  
Resampling.java : 
  This java class enlarges the input image with preserving maximum image quality. Concept used to perform image enlargement is Bilinear       interpolation.
  
SaltAndPepper.java : 
  This java class adds salt and pepper noise to the input image and uses median filter to show the efficiency of median filter on same     type   of noise. Median filter almost completely removes salt and pepper noise.
  
Complex.java : 
  This java class defines various operations such as additon, subtraction, multiplication and division of complex numbers.
  
QuantizationFinal.java : 
   This java class performs quantization by defining color masks that acts as a filter for color bits.
   
