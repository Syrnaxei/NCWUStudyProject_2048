package com.syrnaxei.terminal2048;

public class MergeLogic {
    private BoardControl board;

    public MergeLogic(BoardControl board){
        this.board = board;
    }

    //====================================merge方法======================================
    public void mergeRight() {
        int[][] data = board.getBoard();
        for (int row = 0; row < GameConfig.BoardSize; row++) {
            int[] currRow = data[row];

            // Step1: 先紧凑（非0数字向右靠，消除中间0）
            int[] compacted = compactRight(currRow);

            // Step2: 再合并（从右往左遍历，合并相邻相同数字）
            int[] merged = adjacentRight(compacted);

            // Step3: 替换原行
            data[row] = merged;
        }
        board.setBoard(data);
    }

    public void mergeLeft() {
        int[][] data = board.getBoard();
        for(int row = 0;row < GameConfig.BoardSize;row++){
            int[] currRow = data[row];
            int[] compacted = compactLeft(currRow);
            int[] merged = adjacentLeft(compacted);
            data[row] = merged;
        }
        board.setBoard(data);
    }

    public void mergeDown() {

    }

    public void mergeUp() {

    }
    //====================================adjacent方法===================================
    public int[] adjacentRight(int[] compactedRow) {
        int[] newRow = new int[GameConfig.BoardSize];
        int index = GameConfig.BoardSize - 1;
        int i = GameConfig.BoardSize - 1;

        while(i >= 0){
            int tempNum = compactedRow[i];
            if(tempNum == 0){
                i--;
                continue;
            }
            if(i - 1 >= 0 && tempNum == compactedRow[i - 1]){
                newRow[index] = tempNum * 2;
                index--;
                i -= 2;
            }else{
                newRow[index] = tempNum;
                index--;
                i--;
            }
        }

        return newRow;
    }

    public int[] adjacentLeft(int[] compactedRow){
        int[] newRow = new int[GameConfig.BoardSize];
        int index = 0;
        int i = 0;

        while(i < GameConfig.BoardSize){
            int tempNum = compactedRow[i];
            if(tempNum == 0){
                i++;
                continue;
            }
            if(i + 1 < GameConfig.BoardSize && tempNum == compactedRow[i + 1]){
                newRow[index] = tempNum * 2;
                index++;
                i += 2;
            }else{
                newRow[index] = tempNum;
                index++;
                i++;
            }
        }
        return newRow;
    }

    //====================================compact方法====================================
    public int[] compactRight(int[] row){
        int[] newRow = new int[GameConfig.BoardSize];
        int index = GameConfig.BoardSize - 1; // 从右侧开始填充
        for (int num : row) {
            if (num != 0) {
                newRow[index--] = num;
            }
        }
        return newRow;
    }

    public int[] compactLeft(int[] row){
        int[] newRow = new int[GameConfig.BoardSize];
        int index = 0;
        for(int num : row){
            if(num != 0){
                newRow[index++] = num;
            }
        }
        return newRow;
    }

    public int[] compactDown(int[] col) {
        int[] result = new int[GameConfig.BoardSize];
        int index = GameConfig.BoardSize - 1;
        for (int num : col) {
            if (num != 0) {
                result[index--] = num;
            }
        }
        return result;
    }

    public int[] compactUp(int[] col){
        int[] result = new int[GameConfig.BoardSize];
        int index = 0;
        for(int num : col){
            result[index++] = num;
        }
        return result;
    }
}
