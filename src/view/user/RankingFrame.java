package view.user;

import view.FrameUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingFrame extends JFrame {
    public RankingFrame() {
        setTitle("Ranking");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout()); // 使用 BorderLayout 布局

// 创建表格模型
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Rank");
        tableModel.addColumn("Username");
        tableModel.addColumn("Steps");
        tableModel.addColumn("Time");

// 读取排名数据文件
        List<PlayerRanking> rankings = loadRankings("ranking_data.ser");

// 按照时间排序
        Collections.sort(rankings, (r1, r2) -> Integer.compare(r1.getTime(), r2.getTime()));

        //测试用
        for (PlayerRanking ranking : rankings) {
            System.out.println("Ranking: " + ranking.getUsername() + ", Steps: " + ranking.getSteps() + ", Time: " + ranking.getTime());
        }



// 只取前五名
        int maxRows = Math.min(5, rankings.size());
        for (int i = 0; i < maxRows; i++) {
            PlayerRanking ranking = rankings.get(i);
            tableModel.addRow(new Object[] {
                    (i + 1), // 排名
                    ranking.getUsername(),
                    ranking.getSteps(),
                    ranking.getTime()
            });
            System.out.println("Added to table: " + ranking.getUsername() + ", Steps: " + ranking.getSteps() + ", Time: " + ranking.getTime());
        }

        // 创建表格并设置样式
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);

// 创建滚动面板
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER); // 确保滚动面板添加到窗口中心

// 返回按钮
        JButton returnBtn = FrameUtil.createButton(this, "Return", new Point(250, 350), 100, 40);
        JPanel buttonPanel = new JPanel(); // 创建一个面板用于放置按钮
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(returnBtn);
        add(buttonPanel, BorderLayout.SOUTH); // 将按钮面板添加到窗口南部

        returnBtn.addActionListener(e -> {
            setVisible(false);
            // 显示登录界面的逻辑（根据你的应用结构调整）
        });

        // 设置窗口位置并显示
        setLocationRelativeTo(null);
        //validate(); // 验证布局
        repaint();  // 重绘窗口
        setVisible(true);
    }

    private List<PlayerRanking> loadRankings(String filePath) {
        File file = new File(filePath);

        // 检查文件是否存在
        if (!file.exists()) {
            System.out.println("File not found: " + filePath);
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                List<PlayerRanking> rankings = new ArrayList<>();
                for (Object item : (List<?>) obj) {
                    if (item instanceof PlayerRanking) {
                        rankings.add((PlayerRanking) item);
                    }
                }
                return rankings;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading rankings: " + e.getMessage());
        }

        return new ArrayList<>();
    }}