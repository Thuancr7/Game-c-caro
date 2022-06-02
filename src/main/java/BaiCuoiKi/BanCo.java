package BaiCuoiKi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class BanCo extends JPanel {
    private static final int N = 24;
    private static final int M = 24;

    public static final int ST_DRAW = 0;
    public static final int ST_WIN = 1;
    public static final int ST_NORMAL = 2;

    private EndGameListener endGameListener;
    private Image imageX;
    private Image imageO;
    private Chuot[][] matran = new Chuot[N][M];
    private String nguoiChoi = Chuot.EMPTY_VALUE;

    public BanCo(String player){
        this();
        this.nguoiChoi = player;
    }

    public BanCo(){
        this.taomatran();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();

                if (nguoiChoi.equals(Chuot.EMPTY_VALUE)){
                    return;
                }

                //Tính toán (x,y) rơi vào ô nào trong bàn cờ để vẽ hình tùy ý
                for (int i = 0; i < N; i++){
                    for (int j = 0; j < M; j++){
                        Chuot c = matran[i][j];

                        int cXStart = c.getX();
                        int cYStart = c.getY();

                        int cXEnd = cXStart + c.getW();
                        int cYEnd = cYStart + c.getH();

                        if (x >= cXStart && x <= cXEnd && y >= cYStart && y <= cYEnd){
                            if (c.getValue().equals(Chuot.EMPTY_VALUE)){
                                c.setValue(nguoiChoi);
                                repaint();
                                int result = checkWin(nguoiChoi, i, j);
                                if(endGameListener != null){
                                    endGameListener.end(nguoiChoi, result);
                                }

                                if(result == ST_NORMAL){
                                // Đảo người chơi
                                nguoiChoi = nguoiChoi.equals(Chuot.O_VALUE) ? Chuot.X_VALUE : Chuot.O_VALUE;
                                }
                            }
                        }
                    }
                }
            }
        });

        try{
            imageX = ImageIO.read(new File("D:\\Java\\CoCaro\\src\\main\\java\\BaiCuoiKi\\x.png"));
            imageO = ImageIO.read(new File("D:\\Java\\CoCaro\\src\\main\\java\\BaiCuoiKi\\o.png"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void taomatran(){
        //Khoi tao ma tran
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                Chuot c = new Chuot();
                matran[i][j] = c;
            }
        }
    }
    public void setEndGameListener(EndGameListener endGameListener) {
        this.endGameListener = endGameListener;
    }

    public void reset(){
        this.taomatran();
        this.setNguoiChoi(Chuot.EMPTY_VALUE);
        repaint();
    }

    public void setNguoiChoi(String nguoiChoi) {
        this.nguoiChoi = nguoiChoi;
    }

    public String getNguoiChoi() {
        return nguoiChoi;
    }
    public int checkWin(String player, int i, int j){
        int count = 0;
        for(int col = 0; col < M; col++){
        Chuot cell = matran[i][col];
        if (cell.getValue().equals(nguoiChoi)) {
            count++;
            if(count == 5){
                System.out.println("Ngang");
                return ST_WIN;
            }
        }else {
            count = 0;
        }
    }
    count = 0;
        for(int row = 0; row < N; row++){
        Chuot cell = matran[row][j];
        if (cell.getValue().equals(nguoiChoi)) {
            count++;
            if(count == 5){
                System.out.println("Dọc");
                return ST_WIN;
            }
        }else {
            count = 0;
        }
    }
    int min = Math.min(i, j);
    int TopI = i - min;
    int TopJ = j - min;
    count = 0;

        for(;TopI < N && TopJ < M; TopI++, TopJ++){
        Chuot cell = matran[TopI][TopJ];
        if (cell.getValue().equals(nguoiChoi)) {
            count++;
            if(count == 5){
                System.out.println("Chéo trái");
                return ST_WIN;
            }
        }else {
            count = 0;
        }
    }
    min = Math.min(i, j);
    TopI = i - min;
    TopJ = j + min;
    count = 0;

        if(TopJ >= M){
        int du = TopJ - (M - 1);
        TopI = TopI + du;
        TopJ = M - 1;
    }

        for(;TopI < N && TopJ >= 0; TopI++, TopJ--){
        Chuot cell = matran[TopI][TopJ];
        if (cell.getValue().equals(nguoiChoi)) {
            count++;
            if(count == 5){
                System.out.println("Chéo phải");
                return ST_WIN;
            }
        }else {
            count = 0;
        }
    }

        if(this.isFull()){
        return ST_DRAW;
    }

        return ST_NORMAL;
}

    private boolean isFull(){
        int number = N * M;

        int k = 0;
        for(int i = 0 ; i < N; i++){
            for(int j = 0 ; j < M; j++){
                Chuot c = matran[i][j];
                if(!c.getValue().equals(Chuot.EMPTY_VALUE)){
                    k++;
                }
            }
        }

        return k == number;
    }

    @Override
    public void paint(Graphics g) {
        int w = getWidth() / M;
        int h = getHeight() / N;

        Graphics2D graphics2D = (Graphics2D) g;

        for (int i = 0; i < N; i++ ){
            int m = i;
            for (int j = 0; j < M; j++ ) {
                int x = j * w;
                int y = i * h;

                //Cập nhật ma trận
                Chuot c = matran[i][j];
                c.setY(y);
                c.setX(x);
                c.setW(w);
                c.setH(h);

                Color color = m % 2 == 0 ? Color.GRAY : Color.LIGHT_GRAY;
                graphics2D.setColor(color);
                graphics2D.fillRect(x, y, w, h);

                if (c.getValue().equals(Chuot.X_VALUE)){
                    Image image = imageX;
                    graphics2D.drawImage(image, x, y, w, h, this);

                }else if (c.getValue().equals(Chuot.O_VALUE)){
                    Image image = imageO;
                    graphics2D.drawImage(image, x, y, w, h, this);
                }

                m++;
            }
        }
    }
}
