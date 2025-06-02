package model;

// 定义方向枚举，用于表示方块的移动方向
public enum Direction {
    // 左移：行不变，列减1
    LEFT(0, -1),
    // 上移：行减1，列不变
    UP(-1, 0),
    // 右移：行不变，列加1
    RIGHT(0, 1),
    // 下移：行加1，列不变
    DOWN(1, 0);

    private final int row;  // 行变化量
    private final int col;  // 列变化量

    // 构造函数：初始化行和列的变化量
    Direction(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // 获取行变化量
    public int getRow() {
        return row;
    }

    // 获取列变化量
    public int getCol() {
        return col;
    }
}