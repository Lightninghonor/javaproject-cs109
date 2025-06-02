package view.user;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderboardFrame extends JFrame {
    private UserManager userManager;

    public LeaderboardFrame(UserManager userManager) {
        this.userManager = userManager;
        setTitle("ranking");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

//        add(createTitleLabel("简单模式"));
//        add(createRankingPanel("easy"));
//
//        add(createTitleLabel("中等模式"));
//        add(createRankingPanel("mid"));
//
//        add(createTitleLabel("困难模式"));
//        add(createRankingPanel("hard"));
//
//        setLocationRelativeTo(null);
//    }

//    private JLabel createTitleLabel(String text) {
//        JLabel label = new JLabel(text, SwingConstants.CENTER);
//        label.setFont(new Font("Arial", Font.BOLD, 20));
//        return label;
//    }
//
//    private JPanel createRankingPanel(String mode) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//
//        Map<String, User.ModeRecord> rankings = userManager.getLeaderboard(mode);
//        int rank = 1;
//        for (Map.Entry<String, User.ModeRecord> entry : sortByScore(rankings)) {
//            JPanel entryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//            entryPanel.add(new JLabel(rank + ". " + entry.getKey()));
//            entryPanel.add(new JLabel("时间：" + entry.getValue().bestTime + "s"));
//            entryPanel.add(new JLabel("步数：" + entry.getValue().bestSteps));
//            panel.add(entryPanel);
//            rank++;
//            if (rank > 10) break; // 显示前10名
//        }
//        return panel;
//    }
//
//    private Map<String, User.ModeRecord> sortByScore(Map<String, User.ModeRecord> map) {
//        return map.entrySet().stream()
//                .sorted((e1, e2) -> {
//                    int cmp = Integer.compare(e1.getValue().bestTime, e2.getValue().bestTime);
//                    if (cmp == 0) cmp = Integer.compare(e1.getValue().bestSteps, e2.getValue().bestSteps);
//                    return cmp;
//                })
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        Map.Entry::getValue,
//                        (e1, e2) -> e1,
//                        LinkedHashMap::new
//                ));
//    }
}}