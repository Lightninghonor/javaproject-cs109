package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GameFrame;
import view.game.GamePanel;
import view.user.PlayerRanking;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
//    private final MapModel model;
    private Stack<MoveRecord> moveStack;
    public boolean isStart;
    private final GameFrame frame; // 新增GameFrame引用

    public GameController(GamePanel view, GameFrame frame,MapModel model) {
        this.view = view;
//        this.model = model;
        this.frame = frame;
        view.setController(this);
        this.moveStack = new Stack<>();
        this.isStart = false;
    }

    // 处理方块移动逻辑
    public boolean doMove(int row, int col, int width, int height, Direction direction) {
        if (view.getMapModel().isValidMove(row, col, width, height, direction, view.getMapModel().getMatrix())) {
            view.getMapModel().moveBlock(row, col, width, height, direction, view.getMapModel().getMatrix());
            moveStack.push(new MoveRecord(row, col, width, height, direction));
            //首次移动后启动计时器
            if (!isStart) {
                frame.startTimer();
                isStart = true;
                frame.startTime = System.currentTimeMillis();
            }
            view.updateBoxPosition(row, col, width, height, direction);
            //测试用
//            for(int i=0;i<4;i++){
//                for(int j=0;j<4;j++){
//                    System.out.printf("%d ",view.getMapModel().getMatrix()[i][j]);
//                }
//                System.out.println();}
            view.setSteps(view.getSteps() + 1);
            view.stepLabel.setText("Step: " + view.getSteps());
            return true;
        }
        return false;
    }

    public void undoMove() {//撤销
        if (!moveStack.isEmpty()) {
            MoveRecord lastMove = moveStack.pop();
            Direction reverseDirection = getReverseDirection(lastMove.direction);
            view.getMapModel().moveBlock(lastMove.row + lastMove.direction.getRow(),
                    lastMove.col + lastMove.direction.getCol(),
                    lastMove.width, lastMove.height, reverseDirection, view.getMapModel().getMatrix());

            view.updateBoxPosition(lastMove.row + lastMove.direction.getRow(),
                    lastMove.col + lastMove.direction.getCol(),
                    lastMove.width, lastMove.height, reverseDirection);
            if(view.getSteps() != 0){
            view.setSteps(view.getSteps() - 1);
            view.stepLabel.setText("Step: " + view.getSteps());
            }
        }
    }

    private Direction getReverseDirection(Direction direction) {//反向
        switch (direction) {
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            default:
                throw new IllegalArgumentException("Unknown direction");
        }
    }

    public GamePanel getView() {
        return view;
    }

//    public MapModel getModel() {
//        return model;
//    }

    public boolean getIsStart(){
        return isStart;
    }

    // 检查胜利条件并展示胜利信息，同时停止计时器
    public void checkVictoryCondition() {
        boolean caoCaoAtExit = false;
        for (BoxComponent box : view.boxes) {
            if (box.getColor() == Color.GREEN) {
                int row = box.getRow();
                int col = box.getCol();
                // 修改胜利条件判断逻辑
                if (row==3&&col==1) {
                    caoCaoAtExit = true;
                    break;
                }
            }
        }
        if (caoCaoAtExit) {
            JOptionPane.showMessageDialog(view, "Victory!\nMoves: " + view.getSteps() + "\nTime: " + frame.seconds + "s", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.stopTimer();  // 停止计时
            if(frame.mode == "ranking"){
                savePlayerRanking(frame.getName(), view.getSteps(), frame.seconds);
                System.out.println("rank");
            }
        }
    }
    // 重新开始游戏时重置计时器的代码
    public void restartGame() {
        view.removeAll();
        view.boxes.clear();
        view.setSteps(0);
        view.getMapModel().copymatrix(view.getMapModel().getMatrix(), view.getMapModel().getInitialmatrix());
        view.initialGame();
        isStart = false;
        frame.resetTimer();
        view.stepLabel.setText("Step: 0");
    }

    // 在胜利时保存数据到文件
    public void savePlayerRanking(String username, int steps, int time) {
        String filePath = "ranking_data.ser"; // 排名数据文件路径
        PlayerRanking newRanking = new PlayerRanking(username, steps, time);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath, true))) {
            oos.writeObject(newRanking);
            System.out.println("Player ranking saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
