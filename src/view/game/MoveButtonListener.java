package view.game;

import controller.GameController;
import model.Direction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveButtonListener implements ActionListener{
    private final GameController controller;
    private final Direction direction;
    public MoveButtonListener(GameController controller, Direction direction){
        this.controller=controller;
        this.direction=direction;
    }
    public void actionPerformed(ActionEvent e){// 实现ActionListener接口的actionPerformed方法
        BoxComponent selectedBox = controller.getView().getSelectedBox();
        if (selectedBox!=null){
            controller.doMove(selectedBox.getRow(),selectedBox.getCol(),selectedBox.getWidth()/controller.getView().getGRID_SIZE(),selectedBox.getHeight()/controller.getView().getGRID_SIZE(),direction);
        }
    }
}