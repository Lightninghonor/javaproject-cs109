package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends ListenerPanel1 {
    public List<BoxComponent> boxes;       // 存储所有方块组件
    private MapModel model;                 // 关联的棋盘模型
    private GameController controller;      // 关联的游戏控制器
    public JLabel stepLabel;               // 显示步数的标签
    private int steps;                      // 当前步数
    private final int GRID_SIZE = 50;       // 每个格子的大小（像素）
    private BoxComponent selectedBox;
    // 构造函数：初始化面板和棋盘
    public GamePanel(MapModel model) {
        System.setProperty("javax.imageio.iiop.allowInvalidICCProfile", "true");
        // 主动请求焦点
        boxes = new ArrayList<>();
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        // 设置面板大小：棋盘宽度*格子大小 + 边框
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.selectedBox = null;
        initialGame();  // 初始化游戏界面
        new Thread(() -> {
            setupKeyListener();
        }).start();
        setFocusable(true);          // 启用焦点
        requestFocusInWindow();
//        addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                System.out.println("焦点已获取");
//            }
//        });
    }

    // 新增方法，设置键盘监听器
    public void setupKeyListener() {
        new Thread(() -> {


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Direction direction = null;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        System.out.println("up");
                        direction = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        System.out.println("down");
                        direction = Direction.DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        System.out.println("left");
                        direction = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        System.out.println("right");
                        direction = Direction.RIGHT;
                        break;
                    case KeyEvent.VK_Z: // 假设 Z 键用于撤销
                        System.out.println("undo");
                        controller.undoMove();
                        return;
                }
                if (direction != null && selectedBox != null) {
                    controller.doMove(selectedBox.getRow(), selectedBox.getCol(),
                            selectedBox.getWidth() / GRID_SIZE,
                            selectedBox.getHeight() / GRID_SIZE, direction);
                }
            }
        });
        }).start();
    }

    public void updateBoxPosition(int row, int col, int width, int height, Direction direction) {
        for (BoxComponent box : boxes) {
            if (box.getRow() == row && box.getCol() == col) {
                box.setRow(row + direction.getRow());
                box.setCol(col + direction.getCol());
                box.setLocation(box.getCol() * GRID_SIZE + 2, box.getRow() * GRID_SIZE + 2);
                box.repaint();
                break;
            }
        }
    }

    public void initialGame() {
        steps = 0;
        // 复制棋盘矩阵（避免修改原始模型）
        int[][] map = new int[model.getHeight()][model.getWidth()];
        for (int i = 0; i < map.length; i++) {
            System.arraycopy(model.getMatrix()[i], 0, map[i], 0, model.getWidth());
        }

        // 遍历棋盘，创建不同大小和颜色的方块
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
                    this.add(box);
                    System.out.printf("%d %d\n",i,j);
                }
            }
        }
        this.repaint();
    }

    public void restoreState(int[][] boardState) {
        for(int i=0;i<model.getHeight();i++){
            for(int j=0;j<model.getWidth();j++){
                model.getMatrix()[i][j]=boardState[i][j];
            }
        }
        this.removeAll();
        this.boxes.clear();
        initialGame();
    }
    // 绘制面板背景和边框
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());  // 灰色背景
        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 2);
        this.setBorder(border);  // 深色边框
    }
    // 处理鼠标点击事件：选中方块
    @Override
    public void doMouseClick(Point point) {
        Component component = this.getComponentAt(point);
        if (component instanceof BoxComponent clickedComponent) {
            // 切换选中状态：取消前一个选中，选中当前或取消
            if (selectedBox == null) {
                selectedBox = clickedComponent;
                selectedBox.setSelected(true);
            } else if (selectedBox != clickedComponent) {
                selectedBox.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBox = clickedComponent;
            } else {
                clickedComponent.setSelected(false);
                selectedBox = null;
            }
        }
    }
    // 处理方向键移动（右）
    @Override
    public void doMoveRight() {
        System.out.println("Click VK_RIGHT");
        if (selectedBox != null) {
            // 调用控制器处理移动
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), selectedBox.getWidth(), selectedBox.getHeight(), Direction.RIGHT)) {
                afterMove();  // 移动后更新步数
            }
        }
    }
    // 类似处理左、上、下移动（代码结构与doMoveRight一致，省略重复注释）
    public void doMoveLeft() {
        System.out.println("Click VK_LEFT");
        if (selectedBox != null) {
            // 调用控制器处理移动
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), selectedBox.getWidth(), selectedBox.getHeight(), Direction.LEFT)) {
                afterMove();  // 移动后更新步数
            }
        }
    }

    public void doMoveUp() {
        System.out.println("Click VK_UP");
        if (selectedBox != null) {
            // 调用控制器处理移动
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), selectedBox.getWidth(), selectedBox.getHeight(), Direction.UP)) {
                afterMove();  // 移动后更新步数
            }
        }
    }

    public void doMoveDown() {
        System.out.println("Click VK_DOWN");
        if (selectedBox != null) {
            // 调用控制器处理移动
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), selectedBox.getWidth(), selectedBox.getHeight(), Direction.DOWN)) {
                afterMove();  // 移动后更新步数
            }
        }
    }
    // 移动后更新步数显示
    public void afterMove() {
//        steps++; // 直接操作内部变量
//        stepLabel.setText("Step: " + steps);
        controller.checkVictoryCondition();
    }

    // 设置步数标签（由GameFrame注入）
    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }

    // 获取选中的方块
    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public MapModel getMapModel() {
        return model;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getSteps(){
        return steps;
    }
}