package gui.admin;

import javax.swing.*;
import java.awt.*;

public class AdminGui extends JFrame {
        public AdminGui() {
            JTabbedPane jtp = new JTabbedPane();// 选项卡

            JPanel jp1, jp2, jp3, jp4;

            jp1 = new JPanel();
            jp2 = new JPanel();
            jp3 = new JPanel();
            jp4 = new JPanel();

            // 将面板添加到选项卡窗格上
            jtp.add("修改个人信息", jp1);
            jtp.add("课程添加", jp2);
            jtp.add("课程修改", jp3);
            jtp.add("管理员授权", jp4);

            jp1

            this.add(jtp);
            this.setVisible(true);
            this.setLocationRelativeTo(null);
            this.setAlwaysOnTop(true);
            this.setResizable(false);
            setTitle("管理员");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to avoid memory leaks
            setLocationRelativeTo(null); // Center the window
        }
}