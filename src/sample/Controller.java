package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import static javax.imageio.ImageIO.write;


public class Controller {

    @FXML
    private Slider rozmycieSlider;
    @FXML
    private Slider kontrastSlider;
    @FXML
    private Slider ostroscSlider;
    @FXML
    private AnchorPane zdjecie;
    @FXML
    private Slider zielony;
    @FXML
    private Slider czerwony;
    @FXML
    private Slider niebieski;

    BufferedImage bimg=null;
    Image img=null;
    ImageView imgView;
    int moment_obrotu=0;

    public void openNew() throws IOException {                          //otworz nowy plik

        bimg = ImageIO.read(new File("src\\ola.jpg"));
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

        switch(moment_obrotu) {
            case 0: {
                for (int i = 0; i < width; i++) {               //wysokosc starego
                    for (int j = 0; j < height; j++) {          //szerokosc starego
                        rgb = bimg.getRGB(j, i);                //i = height, j=width starego
                        bimg90.setRGB(i, height - j - 1, rgb);
                    }
                }
                moment_obrotu = 1;
                break;
            }

            case 1: {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        rgb = bimg.getRGB(i, j);
                        bimg90.setRGB(i, height - j - 1, rgb);
                    }
                }
                moment_obrotu = 2;
                break;
            }

            case 2: {
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        rgb = bimg.getRGB(j, i);
                        bimg90.setRGB(i, j, rgb);
                    }
                }
                moment_obrotu = 3;
                break;
            }
            case 3:

                break;
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

    public void dragDone1() {                                           //rozmycie

        double rozmycie = rozmycieSlider.getValue();
        /*
        int r = (int)rozmycie;
        Graphics g = bimg.createGraphics();
        g.drawLine(r,r,100+r,100+r);
        g.drawString("penis", 100,200);
        g.dispose();

        try {
            ImageIO.write(bimg, "jpg", new File("src\\ola2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = new Image(new File("src\\ola2.jpg").toURI().toString());
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
        */

    }

    public void dragDone2() {                                           //kontrast
    }

    public void dragDone3(){                                            //ostrosc
        Double ostrosc = rozmycieSlider.getValue();
    }




}

