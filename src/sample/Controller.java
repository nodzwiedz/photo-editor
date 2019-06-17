package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;

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
    BufferedImage dstbimg;                                  //ten na ktorym dokonujemy rozmycia, wyostrzenia itd.
    BufferedImage dstbimg2;                                 //potrzebny nam do filtracji
    Image img=null;
    ImageView imgView;
    String filename,filename4;
    String filename2 = "src\\bufer.jpg";
    String filename3 = "src\\bufer.png";
    boolean rozszerzenie;                                    //false = .jpg     true = .png


    public void openNew() throws IOException {                          //otworz nowy plik
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(chooser);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filename=chooser.getSelectedFile().toString();
        }

        ORbimg = ImageIO.read(new File(filename));
        int width = ORbimg.getWidth();
        int height = ORbimg.getHeight();
        int rgb;
        bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);        //robimy typ INT_RGB
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rgb = ORbimg.getRGB(i, j);
                bimg.setRGB(i, j, rgb);
            }
        }
        dstbimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);        //robimy typ INT_RGB
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rgb = ORbimg.getRGB(i, j);
                dstbimg.setRGB(i, j, rgb);
            }
        }
        dstbimg2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);        //robimy typ INT_RGB
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rgb = ORbimg.getRGB(i, j);
                dstbimg2.setRGB(i, j, rgb);
            }
        }
        int dl_src = filename.length();
        if( filename.charAt(dl_src-2)=='n') rozszerzenie = true;                    //sprawdzenie rozszerzenia
            else rozszerzenie = false;

        if(rozszerzenie)    ImageIO.write(dstbimg, "png", new File(filename));
            else ImageIO.write(dstbimg, "jpg", new File(filename));
        img = new Image(new File(filename).toURI().toString());
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);

    }




    public void save() throws IOException {                                                 //zapisz

        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(chooser);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            filename4=chooser.getSelectedFile().toString();
        }
        int dl_src = filename4.length();
        if( filename4.charAt(dl_src-2)=='n') rozszerzenie = true;                    //sprawdzenie rozszerzenia
        else rozszerzenie = false;

        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename4));
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename4));
        }

    }

    public void cofnij() throws IOException {                                                 //cofniecie wszystkich zmian
        dstbimg = bimg;
        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }

        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);

        rozmycieSlider.setValue(0);
        jasnoscSlider.setValue(0);
        ostroscSlider.setValue(0);
        czerwonySlider.setValue(0);
        zielonySlider.setValue(0);
        czerwonySlider.setValue(0);

    }

    public void turn90() throws IOException {                           //obrot 90
        int width = dstbimg.getHeight();
        int height = dstbimg.getWidth();
        BufferedImage bimg90 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        BufferedImage turned_bimg2 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        int rgb=0;

            for (int i = 0; i < width; i++) {               //wysokosc starego
                for (int j = 0; j < height; j++) {          //szerokosc starego
                    rgb = dstbimg.getRGB(j, i);                //i = height, j=width starego
                    bimg90.setRGB((width - i - 1), j, rgb);
                    rgb = bimg.getRGB(j,i);
                    turned_bimg2.setRGB((width-i-1),j,rgb);
                }
            }

        dstbimg = bimg90;
            bimg = turned_bimg2;
        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }

        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
    }

    public void turn180() throws IOException {                          //obrot 180
        int width = dstbimg.getWidth();
        int height = dstbimg.getHeight();
        BufferedImage turned_bimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        BufferedImage turned_bimg2 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        int rgb=0;
        for(int i=0; i<width;i++){
            for(int j=0; j<height;j++){
                rgb = dstbimg.getRGB(i,j);
                turned_bimg.setRGB((width-i-1),(height-j-1),rgb);
                rgb = bimg.getRGB(i,j);
                turned_bimg2.setRGB((width-i-1),(height-j-1),rgb);
            }
        }
        dstbimg = turned_bimg;
        bimg = turned_bimg2;
        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);

    }

    public void dragDone1() throws IOException {                                           //rozmycie

        double rozmycie = (rozmycieSlider.getValue()) +1.0;
        int rozm = (int)rozmycie;
        float wymiar_matrixa = (float)(rozm*rozm);
        float wartosc_matrix = (float)(1.0/wymiar_matrixa);
        float[] matrix = new float[(rozm*rozm)];

        for(int i=0; i<(rozm*rozm);i++)
            matrix[i] = wartosc_matrix;                     //ustawiamy wartosci macierzy filtrujacej

        Kernel kernel = new Kernel(rozm,rozm,matrix);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(bimg,dstbimg);                                                           //filtrujemy

        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
    }

    public void dragDone2() throws IOException {                                           //jasnosc
        double jasnosc = jasnoscSlider.getValue();
        int bright = (int) jasnosc;
        int width = dstbimg.getWidth();
        int height = dstbimg.getHeight();
        int red,green,blue,rgb;
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                rgb = dstbimg.getRGB(i,j);
                red = (rgb >> 16) & 0xFF;
                green = (rgb >> 8) & 0xFF;
                blue = rgb & 0xFF;
                red += bright;
                green += bright;
                blue += bright;
                if(red < 0) red = 0;
                if( red > 255) red = 255;
                if(green < 0) green = 0;
                if( green > 255) green = 255;
                if(blue < 0) blue = 0;
                if( blue > 255) blue = 255;
                rgb = ((red&0x0ff)<<16)|((green&0x0ff)<<8)|(blue&0x0ff);
                dstbimg.setRGB(i,j,rgb);
            }
        }
        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
    }

    public void dragDone3() throws IOException {                                            //ostrosc
        double double_ostrosc = (ostroscSlider.getValue()) + 1.0;
        float ostrosc = (float) double_ostrosc;
        float ostrosc_sasiad = ((ostrosc-1.0f)/4.0f);                                       //liczymy wartosci elementow macierzy

        float[] SHARPEN3x3 = {
                0.0f, -ostrosc_sasiad, 0.0f,
                -ostrosc_sasiad, ostrosc, -ostrosc_sasiad,
                0.0f, -ostrosc_sasiad, 0.0f};
        Kernel kernel = new Kernel(3,3,SHARPEN3x3);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(bimg,dstbimg);

        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
    }

    public void dragDone4() throws IOException {                                           //czerwony


        double czerwony = czerwonySlider.getValue();
        int r = (int) czerwony;
        int width,height;
        int red,green,blue,rgb;

            width =  dstbimg.getWidth();
            height = dstbimg.getHeight();
            for(int i=0; i<width; i++){
                for(int j=0; j< height; j++){
                    rgb = bimg.getRGB(i,j);
                    red = (rgb >> 16) & 0xFF;
                    green = (rgb >> 8) & 0xFF;
                    blue = rgb & 0xFF;
                    red += r;
                    if(red < 0) red = 0;                    //przekroczenie wartosci
                    if( red > 255) red = 255;
                    rgb = ((red&0x0ff)<<16)|((green&0x0ff)<<8)|(blue&0x0ff);

                    dstbimg.setRGB(i,j,rgb);
                }
            }

        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);


    }

    public void dragDone5() throws IOException {                                           //zielony

        double zielony = zielonySlider.getValue();
        int g = (int) zielony;
        int width,height;
        int red,green,blue,rgb;

        width =  dstbimg.getWidth();
        height = dstbimg.getHeight();
        for(int i=0; i<width; i++){
            for(int j=0; j< height; j++){
                rgb = bimg.getRGB(i,j);
                red = (rgb >> 16) & 0xFF;
                green = (rgb >> 8) & 0xFF;
                blue = rgb & 0xFF;
                green += g;
                if(green < 0) green = 0;                    //przekroczenie wartosci
                if( green > 255) green = 255;
                rgb = ((red&0x0ff)<<16)|((green&0x0ff)<<8)|(blue&0x0ff);

                dstbimg.setRGB(i,j,rgb);
            }
        }

        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
    }

    public void dragDone6() throws IOException {                                           //niebieski

        double niebieski = niebieskiSlider.getValue();
        int b = (int) niebieski;
        int width,height;
        int red,green,blue,rgb;

        width =  dstbimg.getWidth();
        height = dstbimg.getHeight();
        for(int i=0; i<width; i++){
            for(int j=0; j< height; j++){
                rgb = bimg.getRGB(i,j);
                red = (rgb >> 16) & 0xFF;
                green = (rgb >> 8) & 0xFF;
                blue = rgb & 0xFF;
                blue += b;
                if(blue < 0) blue = 0;                    //przekroczenie wartosci
                if( blue > 255) blue = 255;
                rgb = ((red&0x0ff)<<16)|((green&0x0ff)<<8)|(blue&0x0ff);

                dstbimg.setRGB(i,j,rgb);
            }
        }

        if(rozszerzenie){
            ImageIO.write(dstbimg, "png", new File(filename3));
            img = new Image(new File(filename3).toURI().toString());
        }
        else {
            ImageIO.write(dstbimg, "jpg", new File(filename2));
            img = new Image(new File(filename2).toURI().toString());
        }
        imgView = new ImageView(img);
        zdjecie.getChildren().clear();
        zdjecie.getChildren().add(imgView);
    }
}

