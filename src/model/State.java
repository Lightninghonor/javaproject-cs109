package model;

import controller.MoveRecord;
import view.game.BoxComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class State implements Comparable<State> {
    int[][] board;
    private List<BoxComponent> boxes;
    List<MoveRecord> moves;
    private final int GRID_SIZE = 50;
    int g; // 从起始状态到当前状态的实际代价
    int h; // 从当前状态到目标状态的启发式估计代价
    int f; // f = g + h
    State(int[][] board, List<MoveRecord> moves, int g, int h) {
        this.board = copyBoard(board);
        this.moves = new ArrayList<>(moves);
        this.boxes = new ArrayList<>();
        int[][] map=copyBoard(board);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                BoxComponent box = null;
                int blockId = map[i][j];
                switch (blockId) {
                    case 1:  // 1x1士兵块（橙色）
                        box = new BoxComponent(Color.ORANGE, i, j);
                        box.setSize(GRID_SIZE, GRID_SIZE);
                        map[i][j] = 0;
                        break;
                    case 2:  // 1x2横向块（粉色）
                        box = new BoxComponent(Color.PINK, i, j);
                        box.setSize(GRID_SIZE * 2, GRID_SIZE);
                        map[i][j] = 0;
                        map[i][j + 1] = 0;
                        break;
                    case 3:  // 2x1纵向块（蓝色，关羽块）
                        box = new BoxComponent(Color.BLUE, i, j);
                        box.setSize(GRID_SIZE, GRID_SIZE * 2);
                        map[i][j] = 0;
                        map[i + 1][j] = 0;
                        break;
                    case 4:  // 2x2曹操块（绿色）
                        box = new BoxComponent(Color.GREEN, i, j);
                        box.setSize(GRID_SIZE * 2, GRID_SIZE * 2);
                        map[i][j] = 0;
                        map[i + 1][j] = 0;
                        map[i][j + 1] = 0;
                        map[i + 1][j + 1] = 0;
                        break;
                }
                if (box != null) {
                    // 设置方块位置并添加到面板
                    box.setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                    boxes.add(box);
                }
            }
        }
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    // 复制棋盘矩阵
    public int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[0].length);
        }
        return newBoard;
    }
    public int compareTo(State other) {
        return Integer.compare(this.f, other.f);
    }

    public List<BoxComponent> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<BoxComponent> boxes) {
        this.boxes = boxes;
    }
}
