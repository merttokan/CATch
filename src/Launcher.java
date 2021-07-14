import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class Launcher extends JFrame {
    public Launcher() {
        add(new Data()); // JPanel'e çizilenleri konteynır olarak ekleme
    }

    public static void main(String[] args) {
        Launcher cat = new Launcher();
        cat.setVisible(true);
        cat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cat.setTitle("CATch");
        cat.setSize(735, 800);// Jframe ile pencere oluşturma
        cat.setLocationRelativeTo(null);
        cat.setResizable(true);
        ImageIcon image = new ImageIcon("C:/Users/okanm/Desktop/TED UNIVERSITY/3.Semester Tedu/CMPE_211/CAT/logo.png");
        cat.setIconImage(image.getImage());
    }

}
