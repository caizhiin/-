package gui.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddCourse extends JFrame{
    private String cno = null;
    private String cname = null;
    private int credit = 0;

    public static void main(String[] args) {
        new AddCourse();
    }

    public String getCno() {
        return cno;
    }

    public String getCname() {
        return cname;
    }

    public int getCredit() {
        return credit;
    }
    public AddCourse() {

        JTextField jt1 = new JTextField(10);
        JTextField jt2 = new JTextField(10);
        JTextField jt3 = new JTextField(10);
        JLabel jl1 = new JLabel("课程号");
        JLabel jl2 = new JLabel("课程名");
        JLabel jl3 = new JLabel("学  分");
        JButton jb1 = new JButton("提交");
        JButton jb2 = new JButton("取消");

        JPanel jp2 = new JPanel();
        JPanel jp1 = new JPanel();
        JPanel jp4 = new JPanel();
        JPanel jp3 = new JPanel();
        jp1.add(jl1);
        jp1.add(jt1);
        jp2.add(jl2);
        jp2.add(jt2);
        jp3.add(jb1);
        jp3.add(jb2);
        jp4.add(jl3);
        jp4.add(jt3);
        jp1.setLayout(new FlowLayout());
        jp2.setLayout(new FlowLayout());
        jp3.setLayout(new FlowLayout());
        jp4.setLayout(new FlowLayout());

        this.add(jp1);
        this.add(jp2);
        this.add(jp4);
        this.add(jp3);
        this.setLayout(new FlowLayout());
        this.setAlwaysOnTop(true);
        setVisible(true);
        setTitle("添加课程");
        setSize(270, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to avoid memory leaks
        setLocationRelativeTo(null); // Center the window

        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cno = jt1.getText();
                cname = jt2.getText();
                credit = Integer.parseInt(jt3.getText());
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    String strSQL = "INSERT INTO course(cno, cname ,credit) VALUES (?, ? ,?)";
                    PreparedStatement st = dbConn.prepareStatement(strSQL);
                    st.setString(1, cno);
                    st.setString(2, cname);
                    st.setInt(3, credit);
                    System.out.println("连接数据库成功");
                    int rowsAffected = st.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("新行已成功添加到数据库。");
                        JOptionPane.showMessageDialog(AddCourse.this, "添加成功");
                    }
                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(AddCourse.this, "添加失败，数据不符合要求");
                    System.out.print("连接失败");
                }
            }
        });


    }
}
