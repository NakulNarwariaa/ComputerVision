# Computer Vision Operations:
This repository provides manual implementation of many fundamental Computer Vision principles/operations. **All the operations are well optimized to be performed in low time- complexity, and can be performed by invoking methods names provided for each class with image input.** For demonstration purposes, each class contains a main method that shows how to invoke the implemented methods and results are displayed on java frames.

# Operations and Usage:
## EdgeDetection: 
Edge Detection is one of the important operations in image processing which finds the edges in a given image. This class performs the same operation by using a 3x3 sobel convolutional mask. To use this operation, invoke the finalEdge function on a grayscale image with the input paramter as a bufferedImage. If edges on a colored image is to be obtained, turnGrayS() can be used to obtain a grayscale version of the image first.

## Equalization: 
Equalization is performed to enhance the image contrast by distributing equal pixels to all color bins. This class perform so by histogram equalization on colored RGB images by converting it to YCbCr color space. To perform this operation on any image, just invoke the equalizeImage() with an bufferedImage as an input parameter. 

## Equalization Greyscale: 
This java class performs equalization on greyscale images without converting it to YCbCr color space. To perform this operation on any grayscale image, just invoke the equalizeImage() with an bufferedImage as an input parameter. 
 
## Fast Fourier Transform: 
This java class computes the fourier transform of the input image and performs various frequency domain operations on it such as butterworth lowpass filter and gaussian highpass filter. The java class contains an implementation of Fast Fourier Transform instead of conventional Fourier Transform, which is considerably faster. The main method in this class demonstrates how to convert image data to complex representation and apply few frequency domain filters implemented in the class itself, using Fourier transform.       
  
## Find Dsitance: 
This java class attempts to find the relative distance of a car in 2 images, provided with 1 background image. Consequently, it also computes the speed of the car in pictures using time difference in images.
  
## JPEGCompression: 
This java class demonstrates the first phase of jpeg compression algorithm for image compression.
  
## Quantization: 
This java class reduces the number of colors used in an image by reducing the number of bits per color channel. Number of allowed bits per color channel is given by user. To perform this operation on any image, just invoke the quantizeImage() with an bufferedImage and number of channels for colors as an input parameter. 
  
## Resampling: 
This java class enlarges the input image with preserving maximum image quality. Concept used to perform image enlargement is Bilinear interpolation. To perform this operation on any image, just invoke the createImageWithText() with an bufferedImage and scale to which the image is to be enlarged as an input parameter. 
  
## SaltAndPepper: 
This java class adds salt and pepper noise to the input image and uses median filter to show the efficiency of median filter on same type of noise. Median filter almost completely removes salt and pepper noise.
  
## Complex: 
  This java class defines various operations such as additon, subtraction, multiplication and division of complex numbers.
  
## QuantizationFinal: 
This java class performs quantization by defining color masks that acts as a filter for color bits. To perform this operation on any image, just invoke the quantizeImageColor() with an bufferedImage and appropriate mask shown in the class parameters as an input parameter.
   
