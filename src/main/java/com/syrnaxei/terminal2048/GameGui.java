package com.syrnaxei.terminal2048;

import javax.swing.*;
import java.awt.*;

public class GameGui extends JFrame {
    public GameGui() {
        JLabel ncwuLogo = new JLabel();
        ncwuLogo.setText("test");
        ncwuLogo.setHorizontalAlignment(JLabel.CENTER); // 文字/图片居中
        ncwuLogo.setVerticalAlignment(JLabel.CENTER);
        ncwuLogo.setIcon(new ImageIcon("/images/2048/NCWU_Logo.png"));


        setTitle("2048 Game");
        setSize(960, 540);
        Container contentPane = getContentPane();
        contentPane.setBackground(new Color(129,216,207));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(ncwuLogo);
        setResizable(false);
        setVisible(true);

    }
}
