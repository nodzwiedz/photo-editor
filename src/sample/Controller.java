package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;

import static javax.imageio.ImageIO.write;


public class Controller {

    @FXML
    private Slider rozmycieSlider;
    @FXML
    private Slider jasnoscSlider;
    @FXML
    private Slider ostroscSlider;
    @FXML
    private AnchorPane zdjecie;
    @FXML
    private Slider zielonySlider;
    @FXML
    private Slider czerwonySlider;
    @FXML
    private Slider niebieskiSlider;

    BufferedImage ORbimg;
    BufferedImage bimg;
    Image img=null;
    ImageView imgView;

    public void openNew() throws IOException {                          //otworz nowy plik

        ORbimg = ImageIO.read(new File("src\\ola.jpg"));
        int width = ORbimg.getWidth();
        int height = ORbimg.getHeight();
        int rgb;
        bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);        //robimy typ INT_RGB
        for(int i=0; i < width;i++){
            for(int j=0; j<height;j++){
                rgb = ORbimg.getRGB(i,j);
                bimg.setRGB(i,j,rgb);
            }
        }
        img = new Image("ola.jpg");
        imgView = new ImageView(img);
        zdjecie.getChildren().add(imgView);

    }




    public void save(){                                                 //zapisz

    }

    public void turn90() throws IOException {                           //obrot 90
        int width = bimg.getHeight();
        int height = bimg.getWidth();
        BufferedImage bimg90 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        int rgb=0;

        for (int i = 0; i < width; i++) {               //wysokosc starego
            for (int j = 0; j < height; j++) {          //szerokosc starego
                rgb = bimg.getRGB(j, i);                //i = height, j=width starego
                bimg90.setRGB(width-i-1, j, rgb);
            }
        }

        bimg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        bimg = bimg90;
        ImageIO.write(bimg, "jpg", new File("src\\ola2.jpg"));
        img = new Image(new File("src\\ola2.jpg").toURI().toString());
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
    }

    public void turn180() throws IOException {                          //obrot 180

        BufferedImage turned_bimg = new BufferedImage(bimg.getWidth(),bimg.getHeight(),BufferedImage.TYPE_INT_RGB);
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        int rgb=0;

        for(int i=0; i<width;i++){
            for(int j=0; j<height;j++){
                rgb = bimg.getRGB(i,j);
                turned_bimg.setRGB((width-i-1),(height-j-1),rgb);
            }
        }
        bimg = turned_bimg;
        ImageIO.write(bimg, "jpg", new File("src\\ola2.jpg"));
        img = new Image(new File("src\\ola2.jpg").toURI().toString());
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);

    }

    public void dragDone1() throws IOException {                                           //rozmycie

        double rozmycie = rozmycieSlider.getValue();
        int rozm = (int)rozmycie;
        float wymiar_matrixa = (float)(rozm*rozm);
        float wartosc_matrix = (float)(1.0/wymiar_matrixa);
        float[] matrix = new float[(rozm*rozm)];

        for(int i=0; i<(rozm*rozm);i++)
            matrix[i] = wartosc_matrix;                     //ustawiamy wartosci macierzy filtrujacej

        int width = bimg.getWidth();
        int height = bimg.getHeight();
        BufferedImage dstbimg = new BufferedImage(width ,height ,BufferedImage.TYPE_INT_RGB);
        Kernel kernel = new Kernel(rozm,rozm,matrix);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(bimg,dstbimg);                                                           //filtrujemy

        ImageIO.write(dstbimg, "jpg", new File("src\\ola2.jpg"));
        img = new Image(new File("src\\ola2.jpg").toURI().toString());
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);

    }

    public void dragDone2() {                                           //jasnosc
    }

    public void dragDone3() throws IOException {                                            //ostrosc
        double double_ostrosc = ostroscSlider.getValue();
        float ostrosc = (float) double_ostrosc;
        float ostrosc_sasiad = ((ostrosc-1.0f)/4.0f);                                       //liczymy wartosci elementow macierzy

        float[] SHARPEN3x3 = {
                0.0f, -ostrosc_sasiad, 0.0f,
                -ostrosc_sasiad, ostrosc, -ostrosc_sasiad,
                0.0f, -ostrosc_sasiad, 0.0f};
        int width = bimg.getWidth();
        int height = bimg.getHeight();
        BufferedImage dstbimg = new
                BufferedImage(width ,height ,BufferedImage.TYPE_INT_RGB);
        Kernel kernel = new Kernel(3,3,SHARPEN3x3);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(bimg,dstbimg);

        ImageIO.write(dstbimg, "jpg", new File("src\\ola2.jpg"));
        img = new Image(new File("src\\ola2.jpg").toURI().toString());
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
    }




}

