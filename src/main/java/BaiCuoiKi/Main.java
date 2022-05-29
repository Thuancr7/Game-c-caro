package BaiCuoiKi;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);

        BanCo banCo = new BanCo();
        banCo.setPreferredSize(new Dimension(300, 300));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 12);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(flowLayout);

        JButton start = new JButton("Bắt đầu");
        JLabel time = new JLabel("00:00");
        bottomPanel.add(time);
        bottomPanel.add(start);


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

}
