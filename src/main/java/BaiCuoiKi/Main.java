package BaiCuoiKi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static int sec = 0;
    private static Timer timer = new Timer();
    private static JLabel time;
    private static JButton start;
    private static BanCo banCo;

    public static void main(String[] args) {
        banCo = new BanCo();

        banCo.setEndGameListener(new EndGameListener() {
            @Override
            public void end(String player, int st) {
                if(st == BanCo.ST_WIN){
                    JOptionPane.showMessageDialog(null, "Người chơi " + player + " thắng");
                    stopGame();
                }else if(st == BanCo.ST_DRAW){
                    JOptionPane.showMessageDialog(null, "Hòa rồi");
                    stopGame();
                }
            }
        });

        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);

        banCo.setPreferredSize(new Dimension(600, 600));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 12);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(flowLayout);

        start = new JButton("Bắt đầu");
        time = new JLabel("00:00");
        bottomPanel.add(time);
        bottomPanel.add(start);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (start.getText().equals("Bắt đầu")){
                    startGame();
                }else{
                    stopGame();
                }
            }
        });

        jPanel.add(banCo);
        jPanel.add(bottomPanel);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame JF = new JFrame("Game cờ ca rô");
        JF.setSize(600, 600);
        JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JF.setResizable(true);
        JF.add(jPanel);

        int x = (int) (dimension.getWidth() / 2 - (JF.getWidth() / 2));
        int y = (int) (dimension.getHeight() / 2 - (JF.getHeight() / 2));
        JF.setLocation(x, y);

        JF.pack();
        //show flame
        JF.setVisible(true);
    }
    private static void startGame(){
        int choice = JOptionPane.showConfirmDialog(null, "Người chơi chọn X đi trước đúng không?", "Chọn người đi trước", JOptionPane.YES_NO_OPTION);
        String nguoiChoi = (choice == 0) ? Chuot.X_VALUE : Chuot.O_VALUE;
        banCo.reset();
        banCo.setNguoiChoi(nguoiChoi);

        sec = 0;
        time.setText("00:00");
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sec++;
                String value = (sec / 60) + " : " + (sec % 60);
                time.setText(value);
            }
        }, 1000, 1000);

        start.setText("Chơi lại");
    }
    private static void stopGame(){
        start.setText("Bắt đầu");

        sec = 0;
        time.setText("00:00");
        timer.cancel();
        timer = new Timer();

        banCo.reset();

    }
}
