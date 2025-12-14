package com.syrnaxei.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.Objects;

public class InfoGUI extends JFrame {
    // 信息窗口需要的资源
    ImageIcon ncwuLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/logo_ncwu.png")));
    Image githubLogo = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/github-mark.png"))).getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH);
    Image ncwuEmblem = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/2048/ncwu_emblem.png"))).getImage().getScaledInstance(65,65,Image.SCALE_SMOOTH);


    // 构造方法：接收主窗口（GameGUI）和图标
    public InfoGUI(GameGUI mainFrame, ImageIcon infoIcon) {
        initializeInfoWindow(mainFrame, infoIcon);
    }


    private void initializeInfoWindow(GameGUI mainFrame, ImageIcon infoIcon) {
        setTitle("About this program");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(infoIcon.getImage());
        setSize(new Dimension(500,600));
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setLayout(new BorderLayout());

        //=====  top area of the info window  =====
        JPanel ncwuPicPanel = new JPanel(new BorderLayout());
        ncwuPicPanel.setBackground(new Color(100,160,205));
        ncwuPicPanel.setPreferredSize(new Dimension(500,100)); //the high of top-part
        //Ncwu picture part
        JLabel ncwuPicLabel = new JLabel();
        ncwuPicLabel.setIcon(ncwuLogo);

        ncwuPicPanel.add(ncwuPicLabel,BorderLayout.CENTER);
        add(ncwuPicPanel,BorderLayout.NORTH);

        //=====  mid-area of the info window  =====
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setPreferredSize(new Dimension(500,400)); //the high of mid-part

        //text Part
        JTextArea textArea = new JTextArea(
                "    Final Assignment for Java Course (3rd Semester)\n" +
                        "    North China University of Water Resources and Electric Power\n\n" +
                        "    Project Name: Classic 2048 Game (GUI Version)\n" +
                        "    Project Description: A 2048 game implemented with Java Swing\n\n" +
                        "    Special thanks to Liew for his assistance in GUI interface design\n\n" +
                        "                        Development Team:       Java Study Group\n" +
                        "                        Completion Time: December 13, 2025 22:34"
        );
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setLineWrap(true); //自动换行
        textArea.setWrapStyleWord(true); //单词边界换行
        textArea.setEditable(false);
        textArea.setBackground(new Color(180, 200, 215));

        textPanel.add(textArea,BorderLayout.CENTER);
        add(textPanel,BorderLayout.CENTER);

        //===== end area of the info window  =====
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,60,20));
        linkPanel.setBackground(new Color(100,160,205));
        linkPanel.setPreferredSize(new Dimension(500,100));

        JLabel link1 = createLinkLabel(githubLogo,"https://github.com/Syrnaxei/NCWUStudyProject_2048");
        JLabel link2 = createLinkLabel(ncwuEmblem,"https://www.ncwu.edu.cn/");

        linkPanel.add(link1);
        linkPanel.add(link2);

        add(linkPanel,BorderLayout.SOUTH);


        // 关闭信息窗口后，主窗口重新获取焦点
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mainFrame.requestFocusInWindow();
                mainFrame.setFocusable(true);
                mainFrame.toFront();
            }
        });

        setVisible(true);
    }


    // 创建带链接的Label（从原 GameGUI 中迁移）
    private JLabel createLinkLabel(Image image,String url) {
        JLabel label = new JLabel();

        label.setIcon(new ImageIcon(image));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(InfoGUI.this, "Failed to open link: " + ex.getMessage());
                }
            }
        });

        return label;
    }
}