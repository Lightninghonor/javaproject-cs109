package controller;
import model.Direction;

/*honor记录用以撤销*/
public class MoveRecord {
    int row;
    int col;
    int width;
    int height;
    Direction direction;

    public MoveRecord(int row, int col, int width, int height, Direction direction) {
        this.row = row;
        this.col = col;
        this.width = width;
        this.height = height;
        this.direction = direction;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
