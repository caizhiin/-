package gui.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddStu extends JFrame{
    private String sno = null;
    private String sname = null;
    private String ssex = null;
    private String clas = null;
    private int sage = 0;

    public static void main(String[] args) {
        new AddStu();
    }

    public AddStu() {

        JTextField jt1 = new JTextField(10);
        JTextField jt2 = new JTextField(10);
        JTextField jt3 = new JTextField(10);
        JTextField jt4 = new JTextField(10);
        JTextField jt5 = new JTextField(10);
        JLabel jl1 = new JLabel("学号");
        JLabel jl2 = new JLabel("姓名");
        JLabel jl3 = new JLabel("性别");
        JLabel jl4 = new JLabel("年龄");
        JLabel jl5 = new JLabel("班级");

        JButton jb1 = new JButton("提交");
        JButton jb2 = new JButton("取消");

        JPanel jp2 = new JPanel();
        JPanel jp1 = new JPanel();
        JPanel jp4 = new JPanel();
        JPanel jp5 = new JPanel();
        JPanel jp6 = new JPanel();
        JPanel jp3 = new JPanel();
        jp1.add(jl1);
        jp1.add(jt1);
        jp2.add(jl2);
        jp2.add(jt2);
        jp3.add(jb1);
        jp3.add(jb2);
        jp4.add(jl3);
        jp4.add(jt3);
        jp5.add(jl4);
        jp5.add(jt4);
        jp6.add(jl5);
        jp6.add(jt5);
        jp1.setLayout(new FlowLayout());
        jp2.setLayout(new FlowLayout());
        jp3.setLayout(new FlowLayout());
        jp4.setLayout(new FlowLayout());
        jp5.setLayout(new FlowLayout());
        jp6.setLayout(new FlowLayout());

        this.add(jp1);
        this.add(jp2);
        this.add(jp4);
        this.add(jp5);
        this.add(jp6);

        this.add(jp3);
        this.setSize(300,300);
        this.setLayout(new FlowLayout());
        this.setAlwaysOnTop(true);
        setVisible(true);
        setTitle("添加学生");
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
                try {
                    sno = jt1.getText();
                    sname = jt2.getText();
                    ssex = jt3.getText();
                    sage = Integer.parseInt(jt4.getText());
                    clas = jt5.getText();
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    String strSQL = "INSERT INTO student(sno, sname ,ssex,sage,class) VALUES (? ,? ,? ,? ,?)";
                    PreparedStatement st = dbConn.prepareStatement(strSQL);
                    st.setString(1, sno);
                    st.setString(2, sname);
                    st.setString(3, ssex);
                    st.setInt(4, sage);
                    st.setString(5, clas);
                    System.out.println("连接数据库成功");
                    int rowsAffected = st.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("新行已成功添加到数据库。");
                        JOptionPane.showMessageDialog(AddStu.this, "添加成功");
                    }
                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(AddStu.this, "添加失败，数据不符合要求");
                }
            }
        });


    }
}
