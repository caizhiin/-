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

            jp1.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            Insets insets = new Insets(10, 0, 10, 0);

            JLabel jl11 = new JLabel("账号：");
            c.gridy = 0;
            c.gridx = 1;
            c.insets = insets;
            jp1.add(jl11,c);
            JLabel jl12 = new JLabel("姓名：");
            c.gridy = 1;
            c.gridx = 1;
            jp1.add(jl12,c);
            JLabel jl13 = new JLabel("性别：");
            c.gridy = 2;
            c.gridx = 1;
            jp1.add(jl13,c);
            JLabel jl14 = new JLabel("年龄：");
            c.gridy = 3;
            c.gridx = 1;
            jp1.add(jl14,c);
            JLabel jl15 = new JLabel("班级：");
            c.gridy = 4;
            c.gridx = 1;
            jp1.add(jl15,c);
            JLabel jl16 = new JLabel("密码：");
            c.gridy = 5;
            c.gridx = 1;
            jp1.add(jl16,c);
            JLabel jl17 = new JLabel("确认密码：");
            c.gridy = 6;
            c.gridx = 1;
            jp1.add(jl17,c);

            JLabel jl111 = new JLabel();
            jl111.setText(rs1.getString(1));
            c.gridy = 0;
            c.gridx = 2;
            c.fill = GridBagConstraints.NONE; // 不填充单元格
            c.anchor = GridBagConstraints.LINE_START; // 左对齐
            jp1.add(jl111,c);
            JTextField jt12 = new JTextField(10);
            jt12.setText(rs1.getString(2));
            c.gridy = 1;
            c.gridx = 2;
            jp1.add(jt12,c);
            JComboBox<String> jc13 = new JComboBox<String>();
            jc13.addItem("男");
            jc13.addItem("女");
            jc13.setSelectedItem(rs1.getString(3));
            c.gridy = 2;
            c.gridx = 2;
            jp1.add(jc13,c);
            JTextField jt14 = new JTextField(10);
            jt14.setText(rs1.getString(4));
            c.gridy = 3;
            c.gridx = 2;
            jp1.add(jt14,c);
            JTextField jt15 = new JTextField(10);
            jt15.setText(rs1.getString(5));
            c.gridy = 4;
            c.gridx = 2;
            jp1.add(jt15,c);
            JPasswordField jpw16 = new JPasswordField(10);
            jpw16.setText(rs.getString(2));
            c.gridy = 5;
            c.gridx = 2;
            jp1.add(jpw16,c);
            JPasswordField jpw17 = new JPasswordField(10);
            jpw17.setText(rs.getString(2));
            c.gridy = 6;
            c.gridx = 2;
            jp1.add(jpw17,c);

            JButton jb11 = new JButton("提交");
            c.gridy = 7;
            c.gridx = 2;
            jp1.add(jb11,c);

            jb11.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        comparePw(jpw16.getText(),jpw17.getText());
                        Connection dbConn1 = DriverManager.getConnection(URL, user, password);
                        String strSQL1 = "UPDATE login SET pw = ? WHERE id = ?";
                        String strSQL2 = "UPDATE student SET sname = ?,ssex = ?, sage = ?,class = ? WHERE sno = ?";
                        PreparedStatement st1 = dbConn1.prepareStatement(strSQL1);
                        PreparedStatement st2 = dbConn1.prepareStatement(strSQL2);
                        st1.setString(1,jpw16.getText());
                        st1.setString(2,id);
                        st1.executeUpdate();
                        st2.setString(1,jt12.getText());
                        st2.setObject(2,jc13.getSelectedItem());
                        st2.setString(3,jt14.getText());
                        st2.setString(4,jt15.getText());
                        st2.setString(5,id);
                        st2.executeUpdate();
                        JOptionPane.showMessageDialog(StuGui.this, "提交成功");
                        dbConn.close();

                    }catch(NotSheSame ee) {
                        JOptionPane.showMessageDialog(StuGui.this, "两次输入的密码不一致");
                    }catch (Exception ee) {
                        ee.printStackTrace();
                        JOptionPane.showMessageDialog(StuGui.this, "数据不合法");
                    }
                }
            });


            dbConn.close();

        } catch (Exception ee) {
            ee.printStackTrace();
            System.out.print("连接失败");
        }

        //jp3成绩查询面板实现
        JButton jb32 = new JButton("刷新");

        jp31.add(jb32);
        String[][] datas3 = {};
        String[] titles3 = { "课程号", "课程名" ,"成绩"};
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
            String strSQL = "select course.cno,course.cname,grade.grade\n" +
                    "from dbo.sc left join course on sc.cno = course.cno left join grade on (sc.sno = grade.sno and sc.cno = grade.cno) \n" +
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
                String strSQL = "select course.cno,course.cname,grade.grade\n" +
                        "from dbo.sc left join course on sc.cno = course.cno left join grade on (sc.sno = grade.sno and sc.cno = grade.cno) \n" +
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
    class NotSheSame extends Exception{
    }
    public void comparePw(String pw1, String pw2 ) throws NotSheSame{
        if(!pw1.equals(pw2))throw new NotSheSame();
    }
}