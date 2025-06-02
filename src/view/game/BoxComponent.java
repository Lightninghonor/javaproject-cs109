package view.game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

// 表示棋盘上的方块组件，可绘制不同颜色并支持选中状态
public class BoxComponent extends JComponent {
    private Color color;       // 方块颜色（用于区分类型）
    private int row;           // 方块在棋盘的行坐标
    private int col;           // 方块在棋盘的列坐标
    private boolean isSelected;// 是否被选中（高亮显示）
    private final int GRID_SIZE = 50;

    // 构造函数：初始化颜色和位置
    public BoxComponent(Color color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
        isSelected = false;
    }

    // 绘制方块：填充颜色并绘制边框
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());  // 填充方块颜色

        // 根据选中状态设置边框
        Border border;
        if (isSelected) {
            border = BorderFactory.createLineBorder(Color.red, 3);  // 红色粗边框表示选中
        } else {
            border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);  // 灰色细边框
        }
        this.setBorder(border);
    }

    // 设置选中状态并刷新显示
    public void setSelected(boolean selected) {
        isSelected = selected;
        this.repaint();  // 重新绘制以更新边框
    }

    // 获取/设置行坐标
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    // 获取/设置列坐标
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }
}
