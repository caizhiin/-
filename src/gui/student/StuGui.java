package gui.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.util.Vector;

import javax.swing.table.TableColumnModel;


public class StuGui extends JFrame {

    private int [] selectedRows;
    public static void main(String[] args) {
        new StuGui("2022014003");
    }
    public StuGui(String id) {
        JTabbedPane jtp = new JTabbedPane();// 选项卡

        JPanel jp1,jp2, jp21, jp3,jp31;

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

        jp21 = new JPanel();
        jp31 =new JPanel();

        // 将面板添加到选项卡窗格上
        jtp.add("修改个人信息", jp1);
        jtp.add("选课查询", jp2);
        jtp.add("成绩查询", jp3);



        //jp2选课查询面板实现
        JButton jb22 = new JButton("刷新");

        jp21.add(jb22);
        String[][] datas = {};
        String[] titles = { "课程号", "课程名" ,"教师"};
        DefaultTableModel myModel = new DefaultTableModel(datas, titles);// myModel存放表格的数据
        JTable table1 = new JTable(myModel);// 表格对象table的数据来源是myModel对象
        table1.setRowHeight(20);// 行高
        table1.preferredSize();
        TableColumnModel columnModel = table1.getColumnModel();
        // 假设我们要设置第一列的宽度为150像素
        columnModel.getColumn(0).setPreferredWidth(150);
        // 也可以设置第二列的宽度
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
            String user = "u1";
            String password = "123";
            Connection dbConn = DriverManager.getConnection(URL, user, password);
            System.out.println("连接数据库成功");
            String strSQL = "select course.cno,course.cname,teacher.tname\n" +
                    "from dbo.sc left join teacher on sc.cno = teacher.class left join course on sc.cno = course.cno\n" +
                    "where sc.sno = ?";
            PreparedStatement st = dbConn.prepareStatement(strSQL);
            st.setString(1,id);
            ResultSet rs = st.executeQuery();
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
        table1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        BoxLayout layout2 = new BoxLayout(jp2, BoxLayout.Y_AXIS);
        jp2.setLayout(layout2);
        jp2.add(jp21);
        jp2.add(new JScrollPane(table1));


        jb22.addActionListener(e -> {
            try {
                String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                String user = "u1";
                String password = "123";
                Connection dbConn = DriverManager.getConnection(URL, user, password);
                System.out.println("连接数据库成功");
                String strSQL = "select course.cno,course.cname,teacher.tname\n" +
                        "from dbo.sc left join teacher on sc.cno = teacher.class left join course on sc.cno = course.cno\n" +
                        "where sc.sno = ?";
                PreparedStatement st = dbConn.prepareStatement(strSQL);
                st.setString(1,id);
                ResultSet rs = st.executeQuery();
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
        });
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRows = table1.getSelectedRows();
            }
        });

        //jp1修改个人信息面板实现
        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
            String user = "u1";
            String password = "123";
            Connection dbConn = DriverManager.getConnection(URL, user, password);
            System.out.println("连接数据库成功");
            String strSQL = "select * from login where id = ?";
            String strSQL1 = "select * from student where sno = ?";
            PreparedStatement st1 = dbConn.prepareStatement(strSQL1);
            PreparedStatement st = dbConn.prepareStatement(strSQL);
            st.setString(1,id);
            st1.setString(1,id);
            ResultSet rs = st.executeQuery();
            ResultSet rs1 = st1.executeQuery();
            rs.next();
            rs1.next();

            jp1.setLayout(new GridLayout(7,1,5,5));
            JPanel jp11 = new JPanel();
            JPanel jp12 = new JPanel();
            JPanel jp13 = new JPanel();
            JPanel jp14 = new JPanel();
            JPanel jp15 = new JPanel();
            JPanel jp16 = new JPanel();
            JPanel jp17 = new JPanel();
            JTextField jt11 = new JTextField(10);
            jt11.setText(rs1.getString(1));
            JTextField jt12 = new JTextField(10);
            jt12.setText(rs1.getString(2));
            JTextField jt13 = new JTextField(10);
            jt13.setText(rs1.getString(3));
            JTextField jt14 = new JTextField(10);
            jt14.setText(rs1.getString(4));
            JTextField jt15 = new JTextField(10);
            jt15.setText(rs1.getString(5));
            JTextField jt16 = new JTextField(10);
            jt16.setText(rs.getString(2));
            JLabel jl11 = new JLabel("账号：");
            JLabel jl12 = new JLabel("姓名：");
            JLabel jl13 = new JLabel("性别：");
            JLabel jl14 = new JLabel("年龄：");
            JLabel jl15 = new JLabel("班级：");
            JLabel jl16 = new JLabel("密码：");
            JButton jb11 = new JButton("提交");
            jb11.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Connection dbConn1 = DriverManager.getConnection(URL, user, password);
                        String strSQL1 = "UPDATE login SET id = ?,pw = ? WHERE id = ?";
                        String strSQL2 = "UPDATE student SET sno = ?,sname = ?,ssex = ?, sage = ?,class = ? WHERE id = ?";
                        PreparedStatement st1 = dbConn1.prepareStatement(strSQL1);
                        PreparedStatement st2 = dbConn1.prepareStatement(strSQL2);
                        st1.setString(1,jt11.getText());
                        st1.setString(2,jt16.getText());
                        st1.setString(3,id);
                        st1.executeUpdate();
                        st2.setString(1,jt11.getText());
                        st2.setString(2,jt12.getText());
                        st2.setString(3,jt13.getText());
                        st2.setString(4,jt14.getText());
                        st2.setString(5,jt15.getText());
                        st2.executeUpdate();
                        JOptionPane.showMessageDialog(StuGui.this, "提交成功");
                        dbConn.close();

                    } catch (Exception ee) {
                        ee.printStackTrace();
                        JOptionPane.showMessageDialog(StuGui.this, "数据不合法");
                    }
                }
            });

            jp11.add(jl11);
            jp11.add(jt11);
            jp12.add(jl12);
            jp12.add(jt12);
            jp13.add(jl13);
            jp13.add(jt13);
            jp14.add(jl14);
            jp14.add(jt14);
            jp15.add(jl15);
            jp15.add(jt15);
            jp16.add(jl16);
            jp16.add(jt16);
            jp17.add(jb11);

            jp1.add(jp11);
            jp1.add(jp12);
            jp1.add(jp13);
            jp1.add(jp14);
            jp1.add(jp15);
            jp1.add(jp16);
            jp1.add(jp17);

            dbConn.close();

        } catch (Exception ee) {
            ee.printStackTrace();
            System.out.print("连接失败");
        }

        //jp3学生管理面板实现
        JButton jb32 = new JButton("刷新");

        jp31.add(jb32);
        String[][] datas3 = {};
        String[] titles3 = { "课程号", "课程名" ,"成绩","获得学分"};
        DefaultTableModel myModel3 = new DefaultTableModel(datas3, titles3);// myModel存放表格的数据
        JTable table3 = new JTable(myModel3);// 表格对象table的数据来源是myModel对象
        table3.setRowHeight(20);// 行高
        table3.preferredSize();
        TableColumnModel columnModel3 = table3.getColumnModel();
        // 假设我们要设置第一列的宽度为150像素
        columnModel3.getColumn(0).setPreferredWidth(150);
        // 也可以设置第二列的宽度
        columnModel3.getColumn(1).setPreferredWidth(150);
        columnModel3.getColumn(2).setPreferredWidth(150);
        table3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
            String user = "u1";
            String password = "123";
            Connection dbConn = DriverManager.getConnection(URL, user, password);
            System.out.println("连接数据库成功");
            String strSQL = "select course.cno,course.cname,sc.grade,sc.getcredit\n" +
                    "from dbo.sc left join course on sc.cno = course.cno\n" +
                    "where sc.sno = ?";
            PreparedStatement st = dbConn.prepareStatement(strSQL);
            st.setString(1,id);
            ResultSet rs = st.executeQuery();
            if (myModel3.getRowCount() != 0) {
                int num = myModel3.getRowCount();
                for (int i = num - 1; i >= 0; i--) {
                    myModel3.removeRow(i);
                }
            }
            while (rs.next()) {
                Vector<String> ve = new Vector<String>();
                ve.addElement(rs.getString(1));
                ve.addElement(rs.getString(2));
                ve.addElement(rs.getString(3));
                ve.addElement(rs.getString(4));
                myModel3.addRow(ve); // 添加一行到模型结尾
            }
            dbConn.close();

        } catch (Exception ee) {
            ee.printStackTrace();
            System.out.print("连接失败");
        }
        table3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        BoxLayout layout3 = new BoxLayout(jp3, BoxLayout.Y_AXIS);
        jp3.setLayout(layout3);
        jp3.add(jp31);
        jp3.add(new JScrollPane(table3));


        jb32.addActionListener(e -> {
            try {
                String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                String user = "u1";
                String password = "123";
                Connection dbConn = DriverManager.getConnection(URL, user, password);
                System.out.println("连接数据库成功");
                String strSQL = "select course.cno,course.cname,sc.grade,sc.getcredit" +
                        "from dbo.sc left join course on sc.cno = course.cno" +
                        "where sc.sno = ?";
                PreparedStatement st = dbConn.prepareStatement(strSQL);
                st.setString(1,id);
                ResultSet rs = st.executeQuery();
                if (myModel3.getRowCount() != 0) {
                    int num = myModel3.getRowCount();
                    for (int i = num - 1; i >= 0; i--) {
                        myModel3.removeRow(i);
                    }
                }
                while (rs.next()) {
                    Vector<String> ve = new Vector<String>();
                    ve.addElement(rs.getString(1));
                    ve.addElement(rs.getString(2));
                    ve.addElement(rs.getString(3));
                    ve.addElement(rs.getString(4));
                    myModel3.addRow(ve); // 添加一行到模型结尾
                }
                dbConn.close();

            } catch (Exception ee) {
                ee.printStackTrace();
                System.out.print("连接失败");
            }
        });
        table1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRows = table1.getSelectedRows();
            }
        });




        this.add(jtp);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setResizable(false);

        setTitle("学生");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Use DISPOSE_ON_CLOSE to avoid memory leaks
        setLocationRelativeTo(null); // Center the window
    }
}