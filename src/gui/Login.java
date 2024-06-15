package gui;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import gui.admin.*;
import gui.student.StuGui;
import gui.teacher.TeaGui;


public class Login extends JFrame{

    public String followid;
    public Login(String name){
        super(name);
        JPanel jp1 = new JPanel();
        JTextField logname = new JTextField(10);
        JLabel jl1 = new JLabel("用户名:");
        jp1.add(jl1);
        jp1.add(logname);
        jp1.setLayout(new FlowLayout());

        JPanel jp2 = new JPanel();
        JLabel jl2 = new JLabel("密   码:");
        JPasswordField password = new JPasswordField(10);
        jp2.add(jl2);
        jp2.add(password);
        jp2.setLayout(new FlowLayout());

        JButton login = new JButton("登录");
        JButton cancel = new JButton("取消");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sno = logname.getText();
                char []pw = password.getPassword();
                String passwordStr = new String(pw);
                if (!sno.isEmpty()) { // 确保输入不为空
                    String url = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    try (Connection connection = DriverManager.getConnection(url, user, password);
                         Statement statement = connection.createStatement();
                         ResultSet resultSet = statement.executeQuery("SELECT * FROM login")) {
                         Boolean flag = true;
                        while (resultSet.next()&& flag ) {
                            // 假设表有两列：id和name
                            String id = resultSet.getString("id");
                            String pas = resultSet.getString("pw");
                            if(sno.equals(id)&& pas.equals(passwordStr) ){
                                followid = id;
                                flag = false;
                                int sf = resultSet.getInt("sf");
                                switch (sf){
                                    case 1: AdminGui adminFrame = new AdminGui(Login.this.getId());
                                        adminFrame.setVisible(true);  dispose();break;

                                    case 2: StuGui stuFrame = new StuGui(Login.this.getId());
                                        stuFrame.setVisible(true);  dispose();break;

                                    case 3:
                                        TeaGui teaFrame = new TeaGui(Login.this.getId());
                                        teaFrame.setVisible(true); dispose();break;

                                }
                            }

                        }
                        if(flag){
                            JOptionPane.showMessageDialog(Login.this, "账号或密码错误", "Message", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (SQLException f) {
                        f.printStackTrace();
                    }
                }
            }
        });
        cancel.addActionListener(e -> dispose());
        JPanel jp3 = new JPanel();
        jp3.add(login);
        jp3.add(cancel);

        this.setLayout(new FlowLayout());
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.setSize(300, 160);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    public String getId(){
        return followid;
    }
}