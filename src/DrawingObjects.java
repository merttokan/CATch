import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DrawingObjects {

        private Image down;
        private Image up;
        private Image left;
        private Image right;
        private Image ghost;
        protected Image heart;
        protected Image fish;
        protected Image catIntro;

        public void imageLoader() { // resimlerin yüklenmesi
                down = new ImageIcon("src/CatDown.gif").getImage();
                up = new ImageIcon("src/CatUp.gif").getImage();
                left = new ImageIcon("src/CatLeft.gif").getImage();
                right = new ImageIcon("src/CatRight.gif").getImage();
                ghost = new ImageIcon("src/ghost.gif").getImage();
                heart = new ImageIcon("src/heart.png").getImage();
                fish = new ImageIcon("src/fish.png").getImage();
                catIntro = new ImageIcon("src/IntroCat.gif").getImage();

        }

        public void ghostDrawing(Graphics2D obj, int ghost_x, int ghost_y, ImageObserver im) {// Ghostun çizilmesi
                obj.drawImage(ghost, ghost_x, ghost_y, im);
        }

        public void catDrawing(Graphics2D obj, int req_dx, int req_dy, int cat_x, int cat_y, ImageObserver im) {
                if (req_dx == -1) {
                        obj.drawImage(left, cat_x + 1, cat_y + 1, im);
                } else if (req_dx == 1) {
                        obj.drawImage(right, cat_x + 1, cat_y + 1, im);
                } else if (req_dy == -1) {
                        obj.drawImage(up, cat_x + 1, cat_y + 1, im);
                } else {
                        obj.drawImage(down, cat_x + 1, cat_y + 1, im);
                }
        }// Kedinin hareketine göre o resmin çizilmesi
}
