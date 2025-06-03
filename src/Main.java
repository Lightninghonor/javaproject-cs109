import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    //test
    public static void main(String[] args) {
        System.setProperty("javax.imageio.iiop.allowInvalidICCProfile", "true");
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(580, 660);
            loginFrame.setVisible(true);
//            MapModel mapModel = new MapModel(new int[][]{
//                    {2, 2, 3, 3},
//                    {1, 1, 3, 3},
//                    {1, 0, 4, 4},
//                    {1, 0, 4, 4},
//
//            },new int[][]{
//                    {2, 2, 3, 3},
//                    {1, 1, 3, 3},
//                    {1, 0, 4, 4},
//                    {1, 0, 4, 4},
//
//            });
//            GameFrame gameFrame = new GameFrame(600, 450, mapModel, loginFrame);
//            gameFrame.setVisible(false);
//            loginFrame.setGameFrame(gameFrame);
        });
    }
}
