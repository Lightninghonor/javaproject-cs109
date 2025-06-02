package view.login;

import model.MapModel;
import utils.ImageLoader;
import view.FrameUtil;
import view.game.GameFrame;
import view.user.RankingFrame;
import view.user.User;
import view.user.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField username;
    private JPasswordField password;
    private JButton submitBtn;
    private JButton resetBtn;
    private JButton registerBtn;
    private JButton guestBtn;
    private JButton setBtn;
    private JButton rankingBtn;
    private GameFrame gameFrame;
    private UserManager userManager;
    private int width;
    private int height;
    public int timeLimit = 300;


    public LoginFrame(int width, int height) {
        this.setTitle("Login Frame");
        setContentPane(new BackgroundPanel());
        this.setLayout(null);
        this.setSize(width, height);
        this.width = width;
        this.height =height;
        this.userManager = new UserManager("users.dat");

        //注册界面
        //账号
        JLabel userLabel = FrameUtil.createJLabel(this, new Point((int) (0.5 * getWidth()-150), getHeight()-410), 70, 40, "Username:");
        // 密码
        JLabel passLabel = FrameUtil.createJLabel(this, new Point((int) (0.5 * getWidth()-150), getHeight()-360), 70, 40, "Password:");
        username = FrameUtil.createJTextField(this, new Point((int) (0.5 * getWidth()-50), getHeight()-410), 220, 40);
        password = new JPasswordField();
        password.setSize(220, 40);
        password.setLocation((int) (0.5 * getWidth()-50), getHeight()-360);
        this.add(password);

        //提交
        submitBtn = FrameUtil.createButton(this, "Confirm", new Point((int) (0.5 * getWidth()-200), getHeight()-310), 150, 60);
        //重新提交
        resetBtn = FrameUtil.createButton(this, "Reset", new Point((int) (0.5 * getWidth()+ 20), getHeight()-310),150, 60);
        // 注册
        registerBtn = FrameUtil.createButton(this, "Register", new Point((int) (0.5 * getWidth()-200), getHeight()-210), 150, 60);
        // 游客模式
        guestBtn = FrameUtil.createButton(this, "Guest", new Point((int) (0.5 * getWidth()+20), getHeight()-210), 150, 60);
        setBtn = FrameUtil.createButton(this, "set", new Point((int) (0.5 * getWidth()-200), getHeight()-110), 150, 60);
        rankingBtn = FrameUtil.createButton(this, "ranking", new Point((int) (0.5 * getWidth()+20), getHeight()-110), 150, 60);
        //接收按钮行为
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());
                boolean loginResult = userManager.loginUser(usernameText, passwordText);
                if (loginResult&&(!(usernameText.isEmpty()&&(passwordText.isEmpty())))&&(!(usernameText.isBlank()||passwordText.isBlank()))) {
                    System.out.println(usernameText + "" );
                        // 隐藏当前登录窗口
                        setVisible(false);
                        // 创建难度选择窗口
                        JFrame difficultyFrame = new JFrame("选择难度");
                        difficultyFrame.setSize(300, 200);
                        difficultyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        difficultyFrame.setLayout(new GridLayout(5, 1));
                        // 创建难度按钮
                        JButton easyBtn = new JButton("easy");
                        JButton midBtn = new JButton("mid");
                        JButton hardBtn = new JButton("hard");
                        JButton rankBtn = new JButton("ranking");
                        JButton timingBtn = new JButton("timing");

                    // 将按钮添加到窗口
                    difficultyFrame.add(easyBtn);
                    difficultyFrame.add(midBtn);
                    difficultyFrame.add(hardBtn);
                    difficultyFrame.add(rankBtn);
                    difficultyFrame.add(timingBtn);
                    // 显示难度选择窗口
                    difficultyFrame.setLocationRelativeTo(null);  // 居中显示
                    difficultyFrame.setVisible(true);
                    // 添加按钮事件监听
                    easyBtn.addActionListener(e1 -> {
                        User user = userManager.getUser(username.getText());
                        user.setGuest(false);
                        //level = "easy";
                        MapModel mapModel = new MapModel(new int[][]{
                    {2, 2, 3, 3},
                    {1, 1, 3, 3},
                    {1, 0, 4, 4},
                    {1, 0, 4, 4},

            },new int[][]{
                    {2, 2, 3, 3},
                    {1, 1, 3, 3},
                    {1, 0, 4, 4},
                    {1, 0, 4, 4},

                    });
                    GameFrame gameFrame = new GameFrame(600, 450, mapModel,"easy");
                        gameFrame.setUser(user); // 直接使用已更新的User对象
                        gameFrame.setVisible(true);
                        difficultyFrame.dispose();
                    });

                    midBtn.addActionListener(e1 -> {
                        User user = userManager.getUser(username.getText());
                        user.setGuest(false);
                        MapModel mapModel = new MapModel(new int[][]{
                                {1, 4, 4, 3},
                                {1, 4, 4, 3},
                                {1, 0, 0, 1},
                                {1, 0, 0, 1},

                        },new int[][]{
                                {1, 4, 4, 3},
                                {1, 4, 4, 3},
                                {1, 0, 0, 1},
                                {1, 0, 0, 1},

                        });
                        GameFrame gameFrame = new GameFrame(600, 450, mapModel,"mid");
                        gameFrame.setUser(user); // 直接使用已更新的User对象
                        gameFrame.setVisible(true);
                        difficultyFrame.dispose();
                    });

                    hardBtn.addActionListener(e1 -> {
                        User user = userManager.getUser(username.getText());
                        user.setGuest(false);
                        MapModel mapModel = new MapModel(new int[][]{
                                {3, 4, 4, 3},
                                {3, 4, 4, 3},
                                {3, 0, 0, 3},
                                {3, 2, 2, 3},
                                {1, 1, 1, 1},

                        },new int[][]{
                                {3, 4, 4, 3},
                                {3, 4, 4, 3},
                                {3, 0, 0, 3},
                                {3, 2, 2, 3},
                                {1, 1, 1, 1},

                        });
                        GameFrame gameFrame = new GameFrame(600, 450, mapModel,"hard");
                        gameFrame.setUser(user); // 直接使用已更新的User对象
                        gameFrame.setVisible(true);
                        difficultyFrame.dispose();
                    });
                    rankBtn.addActionListener(e1 -> {
                        User user = userManager.getUser(username.getText());
                        user.setGuest(false);
                        MapModel mapModel = new MapModel(new int[][]{
                                {2, 2, 3, 3},
                                {1, 1, 3, 3},
                                {1, 0, 4, 4},
                                {1, 0, 4, 4},

                        },new int[][]{
                                {2, 2, 3, 3},
                                {1, 1, 3, 3},
                                {1, 0, 4, 4},
                                {1, 0, 4, 4},

                        });
                        GameFrame gameFrame = new GameFrame(600, 450, mapModel,"ranking");
                        gameFrame.setUser(user); // 直接使用已更新的User对象
                        gameFrame.setVisible(true);
                        difficultyFrame.dispose();
                    });
                    timingBtn.addActionListener(e1 -> {
                        User user = userManager.getUser(username.getText());
                        user.setGuest(false);
                        MapModel mapModel = new MapModel(new int[][]{
                                {3, 4, 4, 3},
                                {3, 4, 4, 3},
                                {3, 0, 0, 3},
                                {3, 2, 2, 3},
                                {1, 1, 1, 1},

                        },new int[][]{
                                {3, 4, 4, 3},
                                {3, 4, 4, 3},
                                {3, 0, 0, 3},
                                {3, 2, 2, 3},
                                {1, 1, 1, 1},

                        });
                        GameFrame gameFrame = new GameFrame(600, 450, mapModel,timeLimit);
                        gameFrame.setUser(user); // 直接使用已更新的User对象
                        gameFrame.setVisible(true);
                        difficultyFrame.dispose();
                    });
                    if (gameFrame != null) {
                        gameFrame.setUser(new User(usernameText, passwordText)); // 设置当前用户
                        gameFrame.setVisible(true);
                        setVisible(false);
                    }
                } else {
                    // 登录失败
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.setText("");
                password.setText("");
            }
        });
        // 注册按钮
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());
                boolean registerResult = userManager.registerUser(usernameText, passwordText);
                if (registerResult&&(!(usernameText.isBlank()||passwordText.isBlank()))) {
                    //成功
                    JOptionPane.showMessageDialog(LoginFrame.this, "Registration successful.", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                    username.setText("");
                    password.setText("");
                }else if(usernameText.isBlank()||passwordText.isBlank()){
                    JOptionPane.showMessageDialog(LoginFrame.this, "Username and Passwords cannot be blank", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    // 失败
                    JOptionPane.showMessageDialog(LoginFrame.this, "Username already exists.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        guestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MapModel mapModel = new MapModel(new int[][]{
                        {2, 2, 3, 3},
                        {1, 1, 3, 3},
                        {1, 0, 4, 4},
                        {1, 0, 4, 4},

                },new int[][]{
                        {2, 2, 3, 3},
                        {1, 1, 3, 3},
                        {1, 0, 4, 4},
                        {1, 0, 4, 4},

                });
                GameFrame gameFrame = new GameFrame(600, 450, mapModel,"easy");
                gameFrame.setVisible(true);
                setGameFrame(gameFrame);
                    gameFrame.setUser(new User()); // Set guest user
                    gameFrame.setVisible(true);
                    setVisible(false);
                }
        });

        rankingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RankingFrame rankingFrame = new RankingFrame();
                rankingFrame.setVisible(true);
            }
        });

        setBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame setFrame = new JFrame("设置");
                setFrame.setSize(300, 200);
                setFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setFrame.setLayout(new GridLayout(3, 1)); // 使用网格布局

                // 第一行：TimeLimit 标签和三个按钮
                JPanel timeLimitPanel = new JPanel();
                timeLimitPanel.setLayout(new FlowLayout());
                JLabel timeLimitLabel = new JLabel("TimeLimit:");
                JButton btn1 = new JButton("120s");
                JButton btn2 = new JButton("180s");
                JButton btn3 = new JButton("300s");
                timeLimitPanel.add(timeLimitLabel);
                timeLimitPanel.add(btn1);
                timeLimitPanel.add(btn2);
                timeLimitPanel.add(btn3);
                setFrame.add(timeLimitPanel);

                // 第二行：音效选项
                JPanel soundPanel = new JPanel();
                soundPanel.setLayout(new FlowLayout());
                JLabel soundLabel = new JLabel("Sound:");
                JRadioButton soundOn = new JRadioButton("On");
                JRadioButton soundOff = new JRadioButton("Off");
                ButtonGroup soundGroup = new ButtonGroup(); // 用于单选按钮组
                soundGroup.add(soundOn);
                soundGroup.add(soundOff);
                soundPanel.add(soundLabel);
                soundPanel.add(soundOn);
                soundPanel.add(soundOff);
                setFrame.add(soundPanel);

                // 应用按钮
                JPanel applyPanel = new JPanel();
                JButton applyBtn = new JButton("Apply");
                applyPanel.add(applyBtn);
                setFrame.add(applyPanel);

                // 按钮事件监听
                btn1.addActionListener(e1 -> timeLimit = 120);
                btn2.addActionListener(e1 -> timeLimit = 180);
                btn3.addActionListener(e1 -> timeLimit = 300);
                applyBtn.addActionListener(e1 -> {
                    String soundStatus = soundOn.isSelected() ? "On" : "Off";
                    setFrame.dispose(); // 关闭设置窗口
                });

                // 设置窗口位置并显示
                setFrame.setLocationRelativeTo(null);
                setFrame.setVisible(true);
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    // 新增的背景面板类
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // 绘制背景图片，自动适配面板大小
            g.drawImage(ImageLoader.bg, 0, 0, width, height, this);
        }}
}