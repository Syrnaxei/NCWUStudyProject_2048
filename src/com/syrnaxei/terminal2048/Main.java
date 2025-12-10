package com.syrnaxei.terminal2048;

import com.syrnaxei.terminal2048.view.Map;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // 在事件调度线程中启动GUI应用
        SwingUtilities.invokeLater(() -> {
            //==================================  棋盘初始化  ==================================
            BoardControl board = new BoardControl();
            MergeLogic mergeLogic = new MergeLogic(board);  //逻辑类需要操控棋盘，传棋盘对象进去
            board.createBoard();

            // 创建并显示游戏窗口
            Map gameMap = new Map(board, mergeLogic);
            gameMap.setVisible(true);
        });
    }
}