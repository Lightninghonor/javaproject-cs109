package view.game;

import view.user.User;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GameSaveData implements Serializable {
    private static final long serialVersionUID = 1L;
    private int[][] boardState;
    private int steps;
    private User user;

    public int getTime() {
        return time;
    }

    private int time;
    private String checksum;

    public GameSaveData(int[][] boardState, int steps,int time, User user) {
        this.boardState = deepCopy(boardState);
        this.steps = steps;
        this.user = user;
        this.time = time;
        this.checksum = computeChecksum();
    }

    // 新增方法：验证文件完整性
    public boolean dataIsValid() {
        return this.checksum.equals(computeChecksum());
    }

    // 优化校验和计算方法（确保计算内容一致）
    private String computeChecksum() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(user.getUsername().getBytes(StandardCharsets.UTF_8));
            digest.update((byte) steps);
            for (int[] row : boardState) {
                for (int cell : row) {
                    digest.update((byte) cell);
                }
            }
            byte[] hash = digest.digest();
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not supported", e);
        }
    }

    // 辅助方法：字节转十六进制字符串
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    // 深拷贝二维数组（防止引用共享）
    private int[][] deepCopy(int[][] original) {
        if (original == null) return null;
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    public boolean isValid() {
        return this.checksum.equals(computeChecksum());
    }

    public int[][] getBoardState() {
        return boardState;
    }

    public int getSteps() {
        return steps;
    }

    public User getUser() {
        return user;
    }

    public String getChecksum() {
        return checksum;
    }
}
