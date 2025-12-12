package com.syrnaxei.game2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class GameGUI extends JFrame {
    private BoardControl boardControl;
    private MergeLogic mergeLogic;
    private TilePanel[][] tilePanels;
    private JLabel scoreLabel;
    ImageIcon gameIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/icon/game_icon.png")));
    
    public GameGUI(BoardControl boardControl, MergeLogic mergeLogic) {
        this.boardControl = boardControl;
        this.mergeLogic = mergeLogic;
        initializeUI();
        setupKeyListener();
        refreshBoard(); // 显示初始方块
    }
    
    private void initializeUI() {
        setTitle("2048 Game");
        setIconImage(gameIcon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 创建顶部面板（分数）
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(scoreLabel);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // 创建游戏网格面板
        JPanel gridPanel = new JPanel(new GridLayout(GameConfig.BOARD_SIZE, GameConfig.BOARD_SIZE, 5, 5));
        gridPanel.setBackground(new Color(187, 173, 160));
        gridPanel.setPreferredSize(new Dimension(400, 400));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 初始化方块面板数组
        tilePanels = new TilePanel[GameConfig.BOARD_SIZE][GameConfig.BOARD_SIZE];
        
        // 创建每个方格
        for (int i = 0; i < GameConfig.BOARD_SIZE; i++) {
            for (int j = 0; j < GameConfig.BOARD_SIZE; j++) {
                tilePanels[i][j] = new TilePanel();
                gridPanel.add(tilePanels[i][j]);
            }
        }
        
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        
        // 创建底部面板（说明）
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel instructionLabel = new JLabel("Use WASD or Arrow Keys to move tiles");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        bottomPanel.add(instructionLabel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved = false;
                int[][] boardBefore = copyBoard(boardControl.getBoard());
                
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        mergeLogic.mergeUp();
                        moved = !boardsEqual(boardBefore, boardControl.getBoard());
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        mergeLogic.mergeDown();
                        moved = !boardsEqual(boardBefore, boardControl.getBoard());
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        mergeLogic.mergeLeft();
                        moved = !boardsEqual(boardBefore, boardControl.getBoard());
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        mergeLogic.mergeRight();
                        moved = !boardsEqual(boardBefore, boardControl.getBoard());
                        break;
                }
                
                if (moved) {
                    boardControl.addNumber();
                    refreshBoard();
                    checkGameOver();
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }
    
    // 复制棋盘用于比较
    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }
    
    // 比较两个棋盘是否相同
    private boolean boardsEqual(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void refreshBoard() {
        int[][] board = boardControl.getBoard();
        int score = boardControl.getScore();
        
        // 更新分数显示
        SwingUtilities.invokeLater(() -> {
            scoreLabel.setText("Score: " + score);
            
            // 更新方格显示
            for (int i = 0; i < GameConfig.BOARD_SIZE; i++) {
                for (int j = 0; j < GameConfig.BOARD_SIZE; j++) {
                    tilePanels[i][j].setValue(board[i][j]);
                }
            }
        });
    }
    
    private void checkGameOver() {
        if (boardControl.isGameOver()) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + boardControl.getScore());
            });
        }
    }
    
    // 自定义方块面板类
    private static class TilePanel extends JPanel {
        private JLabel valueLabel;
        
        public TilePanel() {
            initializeTile();
        }
        
        private void initializeTile() {
            setPreferredSize(new Dimension(90, 90));
            setBackground(new Color(205, 193, 180)); // 空方块背景色
            setLayout(new BorderLayout());
            
            valueLabel = new JLabel("", SwingConstants.CENTER);
            valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
            add(valueLabel, BorderLayout.CENTER);
            
            setBorder(BorderFactory.createRaisedBevelBorder());
        }
        
        public void setValue(int value) {
            if (value == 0) {
                valueLabel.setText("");
                setBackground(new Color(205, 193, 180)); // 空方块背景色
            } else {
                valueLabel.setText(String.valueOf(value));
                setColorByValue(value);
            }
        }
        
        private void setColorByValue(int value) {
            switch (value) {
                case 2:
                    setBackground(new Color(238, 228, 218));
                    valueLabel.setForeground(Color.BLACK);
                    break;
                case 4:
                    setBackground(new Color(237, 224, 200));
                    valueLabel.setForeground(Color.BLACK);
                    break;
                case 8:
                    setBackground(new Color(242, 177, 121));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 16:
                    setBackground(new Color(245, 149, 99));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 32:
                    setBackground(new Color(246, 124, 95));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 64:
                    setBackground(new Color(246, 94, 59));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 128:
                    setBackground(new Color(237, 207, 114));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 256:
                    setBackground(new Color(237, 204, 97));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 512:
                    setBackground(new Color(237, 200, 80));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 1024:
                    setBackground(new Color(237, 197, 63));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                case 2048:
                    setBackground(new Color(237, 194, 46));
                    valueLabel.setForeground(Color.WHITE);
                    break;
                default:
                    setBackground(new Color(60, 58, 51));
                    valueLabel.setForeground(Color.WHITE);
                    break;
            }
        }
    }
}