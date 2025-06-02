package view.game;

import controller.GameController;
import controller.MoveRecord;
import model.Direction;
import model.MapModel;
import view.FrameUtil;
import view.login.LoginFrame;
import view.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JButton saveBtn;
    private JButton returnBtn;
    private JLabel stepLabel;
    private GamePanel gamePanel;
    private User currentUser;
    private LoginFrame loginFrame;
    private Timer timer;
    private JLabel timerLabel;
    private JButton upButton, downButton, leftButton, rightButton, undoButton,solveButton;
    private boolean isTimerRunning = false;
    public int seconds = 0;
    public long startTime;
    public String mode;
    private int timeLimit = 300;

    public GameFrame(int width, int height, MapModel mapModel,String mode) {
        this.setTitle("华容道1.2");
        this.setLayout(null);
        this.setSize(width, height);
        this.currentUser = new User();
        this.mode =mode;
        this.gamePanel = new GamePanel(mapModel);
        this.gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, this,mapModel);

        setupButtons(); // 设置按钮
        setupButtonListeners(); // 设置按钮监听器

        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("serif", Font.ITALIC, 22));
        timerLabel.setBounds(gamePanel.getWidth() + 80, 30, 180, 50); // 将计时器标签放在按钮上方
        this.add(timerLabel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 140), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 80, 280), 80, 50);
        this.returnBtn = FrameUtil.createButton(this, "Return", new Point(gamePanel.getWidth() + 80, 350), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Step: 0", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        this.solveButton = FrameUtil.createButton(this, "Solve", new Point(gamePanel.getWidth() + 80, 420), 80, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();
        });

        this.loadBtn.addActionListener(e -> {
            if (currentUser != null && !currentUser.isGuest()) {
                loadGame(); // 加载游戏
            } else {
                JOptionPane.showMessageDialog(this, "Guest users cannot load saved games.", "Guest Mode", JOptionPane.INFORMATION_MESSAGE);
            }
            gamePanel.requestFocusInWindow();
        });

        this.saveBtn.addActionListener(e -> {
            if (currentUser != null && !currentUser.isGuest()) {
                saveGame(); // 保存游戏
            } else {
                // 弹窗提示游客用户无法保存
                JOptionPane.showMessageDialog(this, "Guest users cannot save games.", "无法保存", JOptionPane.INFORMATION_MESSAGE);
            }
            gamePanel.requestFocusInWindow();
        });

        // Return按钮的事件监听器
        this.returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 隐藏当前游戏界面，显示登录界面
                setVisible(false);
                LoginFrame loginFrame = new LoginFrame(580, 660);
                loginFrame.setVisible(true);
                gamePanel.requestFocusInWindow();
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 固定保存路径为 D:\IdeaProjects
        String basePath = "D:\\IdeaProjects";
        File baseDir = new File(basePath);
        if (!baseDir.exists()) {
            baseDir.mkdirs(); // 自动创建目录
        }
        gamePanel.requestFocusInWindow();
//        gamePanel.setupKeyListener();
    }

    public GameFrame(int width, int height, MapModel mapModel,int timeLimit) {
        this.setTitle("华容道1.2");
        this.setLayout(null);
        this.setSize(width, height);
        this.currentUser = new User();
        this.mode ="timing";
        this.gamePanel = new GamePanel(mapModel);
        this.gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, this,mapModel);
        this.timeLimit = timeLimit;
        setupButtons(); // 设置按钮
        setupButtonListeners(); // 设置按钮监听器

        timerLabel = new JLabel("Time: "+timeLimit+"s");
        timerLabel.setFont(new Font("serif", Font.ITALIC, 22));
        timerLabel.setBounds(gamePanel.getWidth() + 80, 30, 180, 50); // 将计时器标签放在按钮上方
        this.add(timerLabel);

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 140), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 80, 280), 80, 50);
        this.returnBtn = FrameUtil.createButton(this, "Return", new Point(gamePanel.getWidth() + 80, 350), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Step: 0", new Font("serif", Font.ITALIC, 22), new Point(gamePanel.getWidth() + 80, 70), 180, 50);
        this.solveButton = FrameUtil.createButton(this, "Solve", new Point(gamePanel.getWidth() + 80, 420), 80, 50);
        gamePanel.setStepLabel(stepLabel);

        this.restartBtn.addActionListener(e -> {
            controller.restartGame();
            gamePanel.requestFocusInWindow();
        });

        this.loadBtn.addActionListener(e -> {
            if (currentUser != null && !currentUser.isGuest()) {
                loadGame(); // 加载游戏
            } else {
                JOptionPane.showMessageDialog(this, "Guest users cannot load saved games.", "Guest Mode", JOptionPane.INFORMATION_MESSAGE);
            }
            gamePanel.requestFocusInWindow();
        });

        this.saveBtn.addActionListener(e -> {
            if (currentUser != null && !currentUser.isGuest()) {
                saveGame(); // 保存游戏
            } else {
                // 弹窗提示游客用户无法保存
                JOptionPane.showMessageDialog(this, "Guest users cannot save games.", "无法保存", JOptionPane.INFORMATION_MESSAGE);
            }
            gamePanel.requestFocusInWindow();
        });

        // Return按钮的事件监听器
        this.returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 隐藏当前游戏界面，显示登录界面
                setVisible(false);
                LoginFrame loginFrame = new LoginFrame(580, 660);
                loginFrame.setVisible(true);
                gamePanel.requestFocusInWindow();
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // 固定保存路径为 D:\IdeaProjects
        String basePath = "D:\\IdeaProjects";
        File baseDir = new File(basePath);
        if (!baseDir.exists()) {
            baseDir.mkdirs(); // 自动创建目录
        }
        gamePanel.requestFocusInWindow();
//        gamePanel.setupKeyListener();
    }


    private void setupButtons() {
        upButton = new JButton("Up");
        downButton = new JButton("Down");
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");
        undoButton = new JButton("Undo");
        solveButton= new JButton("Solve");

        // 调整了按钮位置
        upButton.setBounds(gamePanel.getWidth() + 220, 100, 80, 30);
        downButton.setBounds(gamePanel.getWidth() + 220, 140, 80, 30);
        leftButton.setBounds(gamePanel.getWidth() + 220, 180, 80, 30);
        rightButton.setBounds(gamePanel.getWidth() + 220, 220, 80, 30);
        undoButton.setBounds(gamePanel.getWidth() + 220, 260, 80, 30);
        solveButton.setBounds(gamePanel.getWidth() + 220, 320, 80, 30);

        add(upButton);
        add(downButton);
        add(leftButton);
        add(rightButton);
        add(undoButton);
        add(solveButton);
    }

    private void setupButtonListeners() {
        upButton.addActionListener(new MoveButtonListener(Direction.UP));
        downButton.addActionListener(new MoveButtonListener(Direction.DOWN));
        leftButton.addActionListener(new MoveButtonListener(Direction.LEFT));
        rightButton.addActionListener(new MoveButtonListener(Direction.RIGHT));
        undoButton.addActionListener(e -> controller.undoMove());
        solveButton.addActionListener(e -> solveGame());

    }

    private void solveGame() {
        java.util.List<MoveRecord> shortestPath = controller.getView().getMapModel().findShortestPath();
        if (shortestPath != null) {
            StringBuilder message = new StringBuilder("最短路径步骤：\n");
            for (MoveRecord move : shortestPath) {
                message.append("从 (").append(move.getRow()).append(", ").append(move.getCol()).append(") 向 ").append(move.getDirection()).append(" 移动\n");
            }
            JOptionPane.showMessageDialog(this, message.toString(), "最短路径", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "未找到最短路径", "求解失败", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 修改后的保存方法（使用绝对路径）
    public void saveGame() {
        if (currentUser == null || currentUser.isGuest()) {
            showErrorDialog("Guest Mode", "Guest users cannot save games.");
            return;
        }

        String username = currentUser.getUsername();
        String fullPath = "D:\\IdeaProjects\\" + username + ".dat";
        File file = new File(fullPath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            int currentSteps = Integer.parseInt(stepLabel.getText().split(": ")[1]);
            GameSaveData saveData = new GameSaveData(
                    gamePanel.getMapModel().getMatrix(),
                    currentSteps,
                    currentUser
            );
            oos.writeObject(saveData);
            JOptionPane.showMessageDialog(this, "Game saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            showErrorDialog("Save Error", "Invalid step count format.");
        } catch (IOException e) {
            showErrorDialog("Save Error", "Failed to save game:\n" + e.getMessage());
        }
        for(int i=0;i<gamePanel.getMapModel().getHeight();i++){
            for(int j=0;j<gamePanel.getMapModel().getWidth();j++){
                System.out.printf("%d ",gamePanel.getMapModel().getMatrix()[i][j]);
            }
            System.out.println("save");}

    }

    // 修改后的加载方法，使用绝对路径
    public void loadGame() {
        if (currentUser == null || currentUser.isGuest()) {
            showErrorDialog("Guest Mode", "Guest users cannot load saved games.");
            return;
        }

        String username = currentUser.getUsername();
        String fullPath = "D:\\IdeaProjects\\" + username + ".dat";
        File file = new File(fullPath);

        if (!file.exists()) {
            showErrorDialog("File Not Found", "No saved game found at:\n" + fullPath);
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            GameSaveData saveData = (GameSaveData) ois.readObject();

            if (!saveData.dataIsValid()) {
                showErrorDialog("File Corrupted", "File integrity check failed:\n" + fullPath);
                return;
            }

            // 同步棋盘状态
            gamePanel.restoreState(saveData.getBoardState());

            // 同步内部步数状态
            gamePanel.setSteps(saveData.getSteps());
            stepLabel.setText("Step: " + saveData.getSteps());
            JOptionPane.showMessageDialog(this, "Game loaded successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    System.out.printf("%d ",saveData.getBoardState()[i][j]);
                }
                System.out.println("\n");}
        } catch (FileNotFoundException e) {
            showErrorDialog("File Not Found", "Unexpected error: File not found during load.\n" + e.getMessage());
        } catch (IOException e) {
            showErrorDialog("Wrong", "Error reading file:\n" + "Have been modified");
        } catch (ClassNotFoundException e) {
            showErrorDialog("Class Not Found", "Error loading game data:\n" + e.getMessage());
        } catch (Exception e) {
            showErrorDialog("Unexpected Error", "Unexpected error occurred:\n" + e.getMessage());
        }
    }

    // 通用错误提示方法
    private void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }


    // 新增方法：供GameController调用以启动计时器
    public void startTimer() {
        if (mode != "timing") {
            if (!isTimerRunning) {
                startTime = System.currentTimeMillis();
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                        seconds = (int) elapsedTime;
                        timerLabel.setText("Time: " + elapsedTime + "s");
                        if (elapsedTime % 60 == 0) {
                            saveGame();
                        }
                    }
                });
                timer.start();
                isTimerRunning = true;
            }
        } else {//timing mode
            if (!isTimerRunning) {
                startTime = System.currentTimeMillis();
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                        seconds = (int) elapsedTime;
                        int secondsLeft = timeLimit - (int)elapsedTime;
                        timerLabel.setText("Time: " + secondsLeft + "s");
                        if (elapsedTime % 60 == 0) {
                            saveGame();
                        }
                        if(secondsLeft == 0){
                            stopTimer();
                            showErrorDialog("YOU FAILED","TIME IS UP");
                            setVisible(false);
                            LoginFrame loginFrame = new LoginFrame(580, 660);
                            loginFrame.setVisible(true);
                        }
                    }
                });
                timer.start();
                isTimerRunning = true;
            }
        }
    }
    // 新增方法：供GameController调用以重置计时器
    public void resetTimer() {
        isTimerRunning = false;
        if(timer != null){timer.stop();
        timerLabel.setText("Time: 0s");}
        else {
            timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
            }
        });
        }
    }

    public void stopTimer() {
        timer.stop();
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

//    public User getCurrentUser() {
//        return currentUser;
//    }
//
//    public LoginFrame getLoginFrame() {
//        return loginFrame;
//    }
//
//    public void setLoginFrame(LoginFrame loginFrame) {
//        this.loginFrame = loginFrame;
//    }

    private class MoveButtonListener implements ActionListener {
        private final Direction direction;

        MoveButtonListener(Direction direction) {
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gamePanel.getSelectedBox() != null) {
                if (controller.doMove(
                        gamePanel.getSelectedBox().getRow(),
                        gamePanel.getSelectedBox().getCol(),
                        gamePanel.getSelectedBox().getWidth() / gamePanel.getGRID_SIZE(),
                        gamePanel.getSelectedBox().getHeight() / gamePanel.getGRID_SIZE(),
                        direction
                )) {
                    gamePanel.afterMove();  // 移动成功后更新步数
                }
            }
        }
    }
}