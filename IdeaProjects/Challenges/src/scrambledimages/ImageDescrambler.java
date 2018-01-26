package scrambledimages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDescrambler {
    private static int i;
    private BufferedImage image;
    private int imageArr[][];
    private int newImageArr[][];
    ImageDescrambler(String imageName) {
        i++;
        try {
            File file = new File(imageName);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageArr = new int[image.getWidth()][image.getHeight()];
        newImageArr = new int[image.getWidth()][image.getHeight()];
        for (int i = 0; i < imageArr.length; i++)
            for (int j = 0; j < imageArr[0].length; j++)
                imageArr[j][i] = image.getRGB(i, j);
        sortArray();
        BufferedImage img = new BufferedImage(imageArr.length, imageArr[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < imageArr.length; i++)
            for (int j = 0; j < imageArr[0].length; j++)
                img.setRGB(i, j, newImageArr[j][i]);
        try {
            File newImage = new File("assets\\scrambledimages\\new" + i + ".png");
            ImageIO.write(img, "png", newImage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void sortArray() {
        for (int i = 0; i < imageArr.length; i++)
            newImageArr[i] = sort(imageArr[i], getRedPixel(imageArr[i]));
    }

    private int [] sort(int [] arr, int redPix) {
        int spacing = arr.length - 1 - redPix;
        int [] newArr = new int [arr.length];
        for(int i = 0; i < arr.length; i++)
            newArr[(i+spacing)%arr.length] = arr[i];
        return newArr;
    }

    private int getRedPixel(int [] arr) {
        int red = -1;
        for (int i = 0; i < arr.length; i++) {
            Color c = new Color(arr[i]);
            if (c.getRed() == 255 && c.getGreen() == 0 && c.getBlue() == 0) red = i;
        }
        return red;
    }
}