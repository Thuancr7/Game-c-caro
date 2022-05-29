package BaiCuoiKi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BanCo extends JPanel {
    private static final int N = 3;
    private static final int M = 3;

    private Image imageX;
    private Image imageO;
    private Chuot matran[][] = new Chuot[N][M];

    private void taomatran(){
        //Khoi tao ma tran
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                Chuot c = new Chuot();
                matran[i][j] = c;
            }
        }
    }
    public BanCo(){
        this.taomatran();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();

                //Tính toán (x,y) rơi vào ô nào trong bàn cờ để vẽ hình tùy ý
                for (int i = 0; i < N; i++){
                    for (int j = 0; j < M; j++){
                        Chuot c = matran[i][j];

                    }
                }
            }
        });

        try{
            imageX = ImageIO.read(getClass().getResource("X.png"));
            imageO = ImageIO.read(getClass().getResource("O.png"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void paint(Graphics g) {
        int w = getWidth() / 3;
        int h = getHeight() / 3;

        Graphics2D graphics2D = (Graphics2D) g;

        int m = 0;
        for (int i = 0; i < N; i++ ){
            for (int j = 0; j < M; j++ ) {
                int x = i * w;
                int y = j * h;

                //Cập nhật ma trận
                Chuot c = matran[i][j];
                c.setY(y);
                c.setX(x);
                c.setW(w);
                c.setH(h);

                Color color = m % 2 == 0 ? Color.GRAY : Color.LIGHT_GRAY;
                graphics2D.setColor(color);
                graphics2D.fillRect(x, y, w, h);

                //Image image = m % 2 == 0 ? imageX : imageO;
                //graphics2D.drawImage(image, x, y, w, h, this);
                m++;
            }
        }
    }
}
