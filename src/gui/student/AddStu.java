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
    private Object ssex = null;
    private String clas = null;
    private int sage = 0;

    public static void main(String[] args) {
        new AddStu();
    }

    public AddStu() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Insets insets = new Insets(10, 0, 10, 0);


        JLabel jl1 = new JLabel("学号:");
        c.gridy = 0;
        c.gridx = 1;
        c.insets = insets;
        this.add(jl1,c);
        JLabel jl2 = new JLabel("姓名:");
        c.gridy = 1;
        c.gridx = 1;
        c.insets = insets;
        this.add(jl2,c);
        JLabel jl3 = new JLabel("性别:");
        c.gridy = 2;
        c.gridx = 1;
        c.insets = insets;
        this.add(jl3,c);
        JLabel jl4 = new JLabel("年龄:");
        c.gridy = 3;
        c.gridx = 1;
        c.insets = insets;
        this.add(jl4,c);
        JLabel jl5 = new JLabel("班级:");
        c.gridy = 4;
        c.gridx = 1;
        c.insets = insets;
        this.add(jl5,c);

        JTextField jt1 = new JTextField(10);
        c.gridy = 0;
        c.gridx = 2;
        c.fill = GridBagConstraints.NONE; // 不填充单元格
        c.anchor = GridBagConstraints.LINE_START; // 左对齐
        this.add(jt1,c);
        JTextField jt2 = new JTextField(10);
        c.gridy = 1;
        c.gridx = 2;
        this.add(jt2,c);
        JComboBox<String> jc3 = new JComboBox<String>();
        jc3.addItem("男");
        jc3.addItem("女");
        c.gridy = 2;
        c.gridx = 2;
        this.add(jc3,c);
        JTextField jt4 = new JTextField(10);
        c.gridy = 3;
        c.gridx = 2;
        this.add(jt4,c);
        JTextField jt5 = new JTextField(10);
        c.gridy = 4;
        c.gridx = 2;
        this.add(jt5,c);

        JButton jb1 = new JButton("提交");
        c.gridy = 5;
        c.gridx = 1;
        this.add(jb1,c);
        JButton jb2 = new JButton("取消");
        c.gridy = 5;
        c.gridx = 2;
        c.anchor = GridBagConstraints.CENTER;
        this.add(jb2,c);



        this.setSize(300,300);
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
                    checkSno(sno);
                    sname = jt2.getText();
                    ssex = jc3.getSelectedItem();
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
                    st.setObject(3, ssex);
                    st.setInt(4, sage);
                    st.setString(5, clas);
                    String strSQL1 = "INSERT INTO login (id, pw ,sf) VALUES (? ,? ,? )";
                    PreparedStatement st1 = dbConn.prepareStatement(strSQL1);
                    st1.setString(1, sno);
                    st1.setString(2, "123");
                    st1.setInt(3,2);
                    System.out.println("连接数据库成功");
                    int rowsAffected = st.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("新行已成功添加到数据库。");
                        JOptionPane.showMessageDialog(AddStu.this, "添加成功");
                    }
                    dbConn.close();

                } catch (CheckWrong ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(AddStu.this, "添加失败，学号应为十位数字");
                }catch (Exception ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(AddStu.this, "添加失败，数据不符合要求");
                }
            }
        });
    }
    class CheckWrong extends Exception{
    }
    public void checkSno(String sno ) throws CheckWrong {
        if(sno.length()!=10)throw new CheckWrong();
    }
}
