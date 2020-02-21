import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


class Ates {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Oyun extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(1, this); //topu hareket ettirmek için timer

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        gecen_sure += 5;
        g.drawImage(backgroundImage,0, 0,1350 , 750,this);
        // g.setColor(Color.red);

        // g.fillOval(topX, 0, 60, 60);
        g.drawImage(enemyImage, topX, 0, enemyImage.getWidth(), enemyImage.getHeight(), this);
        g.drawImage(image, uzayGemisiX, 600, image.getWidth(), image.getHeight(), this);

        for (Ates ates : atesler) {
            if (ates.getY() < 0) {
                atesler.remove(ates);
            }
        }

        // g.setColor(Color.BLUE);

        for (Ates ates : atesler) {
            // g.fillRoundRect(ates.getX(), ates.getY(), 20, 35, 20, 20);
            g.drawImage(laserImage, ates.getX(), ates.getY(), 20, 35, this);
        }

        if (kontrolEt()) {
            timer.stop(); //oyunu durduruyor eğer topu vurduysak
            String message = "Kazandınız...\n" +
                    "Harcanan Ateş : " + harcanan_ates +
                    "\nGeçen Süre :" + (gecen_sure / 300.0) + "saniye";
            JOptionPane.showMessageDialog(this, message);
            System.exit(0);
        }

    }


    @Override
    public void repaint() {
        super.repaint();
    }

    public Oyun() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzayGemisi.png")));
            enemyImage = ImageIO.read(new FileImageInputStream(new File("ufoRed.png")));
            laserImage = ImageIO.read(new FileImageInputStream(new File("laserBlue04.png")));
            backgroundImage = ImageIO.read(new FileImageInputStream(new File("purple.png")));
        } catch (IOException ex) {
            Logger.getLogger(Oyun.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);

        timer.start();

    }

    private int gecen_sure = 0;
    private int harcanan_ates = 0;

    private BufferedImage image;
    private BufferedImage enemyImage;
    private BufferedImage laserImage;
    private BufferedImage backgroundImage;

    private ArrayList<Ates> atesler = new ArrayList<Ates>();

    private int atesdirY = 2;

    private int topX = 600;

    private int topdirX = 5;

    private int uzayGemisiX = 600;

    private int dirUzayX = 20;

    public boolean kontrolEt() {
        for (Ates ates : atesler) {

            if (new Rectangle(ates.getX(), ates.getY(), 13, 37).intersects(new Rectangle(topX, 0, 85, 85))) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ates ates : atesler) {

            ates.setY(ates.getY() - atesdirY);
        }

        topX += topdirX;

        if (topX >= 1275) { //465 inci ğikseli geçmemesi için
            topdirX = -topdirX; //eğer geçerse eksi döner buda onu tam ters yöne götürür
        }
        if (topX <= 0) { // 0 pixeli geçip ekran dışına çıkmaması için
            topdirX = -topdirX;
        }
        repaint(); //her seferinde topu baştan boyuyor

    }


    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();// sağa ve sola bastığında değer döndürür

        if (c == KeyEvent.VK_LEFT) {
            if (uzayGemisiX <= 0) {
                uzayGemisiX = 0;
            } else {
                uzayGemisiX -= dirUzayX;
            }


        } else if (c == KeyEvent.VK_RIGHT) {
            if (uzayGemisiX >= 1220) {
                uzayGemisiX = 1220;
            } else {
                uzayGemisiX += dirUzayX;
            }
        } else if (c == KeyEvent.VK_CONTROL) {
            atesler.add(new Ates(uzayGemisiX + 45, 570));
            harcanan_ates++;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}