import javax.swing.*;
import java.awt.*;


class OyunEkrani extends JFrame {

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) {

        OyunEkrani ekran = new OyunEkrani("Uzay Oyunu");

        ekran.setResizable(false);
        ekran.setFocusable(false); //j frmae den focusu çektik
        ekran.setExtendedState(JFrame.MAXIMIZED_BOTH); //full screen
        ekran.setUndecorated(true); // üstteki barı kaldırır
        ekran.setSize(1350, 750); //ekran boyutunu ayarlar
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //ekran.setIconImage(createImage("/uzayOyunuIcon.png").getImage());


        Oyun oyun = new Oyun(); //yeni objje oluşturmak için

        oyun.requestFocus();

        oyun.addKeyListener(oyun); //klavyeden işlemleri almayı sağlar

        oyun.setFocusable(true);  //daha demin çektiğimiz focusu j panel e verdik
        oyun.setFocusTraversalKeysEnabled(false); //klavye işlemlerini gerçekleştirmek için

        ekran.add(oyun); //oyunu ekrana ekledik
        ekran.setVisible(true); //ekranda oyunu göstermeye yarar

    }


   /* public static ImageIcon createImage(String path) {
        return new ImageIcon(java.awt.Toolkit.getDefaultToolkit().getClass().getResource(path));
    } */
}
