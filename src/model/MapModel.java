package model;
/**
 * This class is to record the map of one game. For example:
 */
import java.awt.*;
import java.util.*;
import java.util.List;

import controller.MoveRecord;
import view.game.BoxComponent;

import javax.swing.*;

/*边界处理和三种方块移动具体实现*/
public class MapModel {
    private int[][] matrix,initialmatrix;  // 二维数组表示棋盘，数值代表方块类型
    public boolean isValidMove(int row, int col, int width, int height, Direction direction, int matrix[][]){//判断移动是否合法
        int newRow=row+direction.getRow();
        int newCol=col+direction.getCol();
        int id=matrix[row][col];
        boolean isValid=true;
        System.out.printf("%d %d\n",width,height);
        if(newRow<0||newRow+height>matrix.length||newCol<0||newCol+width>matrix[0].length){
            isValid=false;
            System.out.printf("false1 %d %d %d %d\n",newRow,newCol,height,width);
        }else{
            for (int i=0;i<height;i++){
                for (int j=0;j<width;j++){
                    matrix[row+i][col+j]=0;
                }
            }
            for(int i=0;i<height;i++){
                for(int j=0;j<width;j++){
                    if(matrix[newRow+i][newCol+j]!=0){
//                    System.out.println("false");
                        isValid=false;
                        System.out.printf("false2\n");
                        for(int i1=0;i1<4;i1++){
            for(int j1=0;j1<4;j1++){
                System.out.printf("%d ",matrix[i1][j1]);
            }
            System.out.println();
        }
                        break;
                    }
                }
            }
            for (int i=0;i<height;i++){
                for (int j=0;j<width;j++){
                    matrix[row+i][col+j]=id;
                }
            }
        }

//        System.out.println("true");
        return isValid;
    }
    public void moveBlock(int row, int col, int width, int height, Direction direction, int matrix[][]) {//实际移动方块并更新矩阵
        int newRow=row+direction.getRow();
        int newCol=col+direction.getCol();
        int id=matrix[row][col];
        for (int i=0;i<height;i++){
            for (int j=0;j<width;j++){
                matrix[row+i][col+j]=0;
            }
        }
        for (int i=0;i<height;i++){
            for (int j=0;j<width;j++){
                matrix[newRow+i][newCol+j]=id;
            }
        }
        //测试用
//        for(int i=0;i<4;i++){
//            for(int j=0;j<4;j++){
//                System.out.printf("%d ",matrix[i][j]);
//            }
//            System.out.println();
//        }
    }

    // 新添加，用于更新棋盘矩阵
    public void updateMatrix(int[][] newMatrix) {
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                this.matrix[i][j]=newMatrix[i][j];
            }
        }
    }
    // 使用 A* 算法求解最短路径
    public List<MoveRecord> findShortestPath() {
        PriorityQueue<State> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();

        List<MoveRecord> initialMoves = new ArrayList<>();
        int initialH = heuristic(matrix);
        State initialState = new State(matrix, initialMoves, 0, initialH);
        openSet.offer(initialState);

        while (!openSet.isEmpty()) {
            State currentState = openSet.poll();
            // 检查是否达到目标状态
            if (isGoalState(currentState.board)) {
                return currentState.moves;
            }

            closedSet.add(boardToString(currentState.board));

            // 生成所有可能的移动
            for (BoxComponent box : currentState.getBoxes()) {
                int row=box.getRow(),col=box.getCol(),width=box.getWidth()/box.getGRID_SIZE(),height=box.getHeight()/box.getGRID_SIZE();

                        for (Direction direction : Direction.values()) {
                            if (isValidMove(row, col, width, height, direction,currentState.board)) {
                                int[][] newBoard = currentState.copyBoard(currentState.board);
                                moveBlock( row, col, width, height, direction,newBoard);
//                                System.out.printf("move from %d %d\n",row ,col);
//                                for(int i=0;i<4;i++){
//                                    for(int j=0;j<4;j++){
//                                        System.out.printf("%d ",newBoard[i][j]);
//                                    }
//                                    System.out.println();
//                                }
                                String newBoardString = boardToString(newBoard);
                                if (closedSet.contains(newBoardString)) {
                                    continue;
                                }

                                List<MoveRecord> newMoves = new ArrayList<>(currentState.moves);
                                MoveRecord newMove=new MoveRecord(row, col, width, height, direction);
                                newMoves.add(newMove);
                                int newG = currentState.g + 1;
                                int newH = heuristic(newBoard);
                                State newState = new State(newBoard, newMoves, newG, newH);

                                openSet.offer(newState);
                            }
                        }


            }
        }

        return null; // 未找到路径
    }

    // 启发式函数：曼哈顿距离
    private int heuristic(int[][] board) {
        int targetRow = 2;
        int targetCol = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 4) {
                    return Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return 0;
    }

    // 判断是否达到目标状态
    private boolean isGoalState(int[][] board) {
        // 假设曹操块在特定位置为目标状态
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 4) {
                    if (i == 2 && j == 1) {
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }
        return false;
    }

    // 获取方块的宽度
    private int getBlockWidth(int[][] board, int row, int col) {
        int id=board[row][col];
        switch (id) {
            case 1,3:
                return 1;
            case 2,4:
                return 2;
            default:
                return 0;
        }
    }

    // 获取方块的高度
    private int getBlockHeight(int[][] board, int row, int col) {
        int id=board[row][col];
        switch (id) {
            case 1,2:
                return 1;
            case 3,4:
                return 2;
            default:
                return 0;
        }
    }

    // 将棋盘矩阵转换为字符串，用于去重
    private String boardToString(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(cell);
            }
        }
        return sb.toString();
    }

    // 构造函数：初始化棋盘矩阵
    public MapModel(int[][] matrix,int[][] initialmatrix) {
        System.setProperty("javax.imageio.iiop.allowInvalidICCProfile", "true");
        //if(matrix.equals(new int[][]{{3, 0, 3, 3}, {3, 0, 3, 3}, {1, 1, 2, 2}, {1, 3, 4, 4}, {1, 3, 4, 4},}) || matrix.equals(new int[][]{{3, 3, 3, 3}, {3, 3, 3, 3}, {1, 4, 4, 1}, {1, 4, 4, 1}, {0, 2, 2, 0},}) || matrix.equals(new int[][]{{3, 4, 4, 3}, {3, 4, 4, 3}, {3, 0, 0, 3}, {3, 2, 2, 3}, {1, 1, 1, 1},}))
        this.matrix = matrix;
        this.initialmatrix=initialmatrix;
    //}else{
            //JFrame setFrame = new JFrame("请不要修改棋盘");
            //setFrame.setSize(300, 200);
            //setFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //setFrame.setLayout(new GridLayout(1, 1)); // 使用网格布局
        //}
    }
    public MapModel() {
        this.matrix = null;
    }

    // 获取棋盘宽度（列数）
    public int getWidth() {
        return matrix[0].length;
    }

    // 获取棋盘高度（行数）
    public int getHeight() {
        return matrix.length;
    }

    // 获取指定位置的方块类型ID
    public int getId(int row, int col) {
        return matrix[row][col];
    }

    // 获取完整棋盘矩阵（用于状态保存/加载）
    public int[][] getMatrix() {
        return matrix;
    }

    // 检查列坐标是否在棋盘范围内
    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    // 检查行坐标是否在棋盘范围内
    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }

    public int[][] getInitialmatrix() {
        return initialmatrix;
    }

    public void setInitialmatrix(int[][] initialmatrix) {
        this.initialmatrix = initialmatrix;
    }

    public void copymatrix(int[][] copymatrix,int[][] copiedmatrix){
        for(int i=0;i<copymatrix.length;i++){
            for(int j=0;j<copymatrix[i].length;j++){
                copymatrix[i][j]=copiedmatrix[i][j];
            }
        }
    }
}