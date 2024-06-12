package gui.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.util.Vector;

public class AdminGui extends JFrame {
    private String cname;
    private String cno;

    private int credit;
    public static void main(String[] args) {
        new AdminGui();
    }
        public AdminGui() {
            JTabbedPane jtp = new JTabbedPane();// 选项卡

            JPanel jp1, jp2, jp3, jp4, jp5;

            jp1 = new JPanel();
            jp2 = new JPanel();
            jp3 = new JPanel();
            jp4 = new JPanel();
            jp5 = new JPanel();

            // 将面板添加到选项卡窗格上
            jtp.add("修改个人信息", jp1);
            jtp.add("课程添加", jp2);
            jtp.add("课程修改", jp3);
            jtp.add("管理员授权", jp4);

            JButton jb1 = new JButton("添加课程");
            JButton jb2 = new JButton("刷新");

            jp5.add(jb1);
            jp5.add(jb2);
            String[][] datas = {};
            String[] titles = { "课程号", "课程名" ,"学分"};
            DefaultTableModel myModel = new DefaultTableModel(datas, titles);// myModel存放表格的数据
            JTable table1 = new JTable(myModel);// 表格对象table的数据来源是myModel对象
            table1.setRowHeight(20);// 行高
            table1.preferredSize();
            table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    Statement st = dbConn.createStatement();
                    System.out.println("连接数据库成功");
                    String strSQL = "select * from dbo.course";
                    ResultSet rs = st.executeQuery(strSQL);
                    if (myModel.getRowCount() != 0) {
                        int num = myModel.getRowCount();
                        for (int i = num - 1; i >= 0; i--) {
                            myModel.removeRow(i);
                        }
                    }
                    while (rs.next()) {
                        Vector<String> ve = new Vector<String>();
                        ve.addElement(rs.getString(1));
                        ve.addElement(rs.getString(2));
                        ve.addElement(rs.getString(3));
                        myModel.addRow(ve); // 添加一行到模型结尾
                    }
                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    System.out.print("连接失败");
                }
            BoxLayout layout = new BoxLayout(jp2, BoxLayout.Y_AXIS);
                jp2.setLayout(layout);
            jp2.add(jp5);
            jp2.add(new JScrollPane(table1));

            this.add(jtp);
            this.setVisible(true);
            this.setLocationRelativeTo(null);
            this.setAlwaysOnTop(true);
            this.setResizable(false);

            setTitle("管理员");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to avoid memory leaks
            setLocationRelativeTo(null); // Center the window

            jb2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                        String user = "u1";
                        String password = "123";
                        Connection dbConn = DriverManager.getConnection(URL, user, password);
                        Statement st = dbConn.createStatement();
                        System.out.println("连接数据库成功");
                        JOptionPane.showMessageDialog(AdminGui.this, "刷新成功");
                        String strSQL = "select * from dbo.course";
                        ResultSet rs = st.executeQuery(strSQL);
                        if (myModel.getRowCount() != 0) {
                            int num = myModel.getRowCount();
                            for (int i = num - 1; i >= 0; i--) {
                                myModel.removeRow(i);
                            }
                        }
                        while (rs.next()) {
                            Vector<String> ve = new Vector<String>();
                            ve.addElement(rs.getString(1));
                            ve.addElement(rs.getString(2));
                            ve.addElement(rs.getString(3));
                            myModel.addRow(ve); // 添加一行到模型结尾
                        }
                        dbConn.close();

                    } catch (Exception ee) {
                        ee.printStackTrace();
                        System.out.print("连接失败");
                    }
                }
            });
            jb1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddCourse adc = new AddCourse();
                    cname = adc.getCname();
                    cno = adc.getCno();
                    credit = adc.getCredit();
                }
            });
        }
}