import view.login.LoginFrame;

import javax.swing.*;

public class Main {
    //test
    public static void main(String[] args) {
        System.setProperty("javax.imageio.iiop.allowInvalidICCProfile", "true");
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(580, 660);
            loginFrame.setVisible(true);
        });
    }
}
