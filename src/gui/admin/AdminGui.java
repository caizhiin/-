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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import gui.student.AddStu;
import gui.teacher.AddTea;

public class AdminGui extends JFrame {
    private String cname;
    private String cno;
    private int credit;

    private int [] selectedRows;
    public static void main(String[] args) {
        new AdminGui("2022014021");
    }
        public AdminGui(String id) {
            JTabbedPane jtp = new JTabbedPane();// 选项卡

            JPanel jp1,jp2, jp21, jp3, jp4, jp5;

            jp1 = new JPanel();
            jp2 = new JPanel();
            jp3 = new JPanel();
            jp4 = new JPanel();
            jp5 = new JPanel();
            jp21 = new JPanel();
            
            // 将面板添加到选项卡窗格上
            jtp.add("修改个人信息", jp1);
            jtp.add("课程管理", jp2);
            jtp.add("学生管理", jp3);
            jtp.add("教师管理", jp4);
            jtp.add("管理员授权", jp5);


            //jp2课程管理面板实现
            JButton jb21 = new JButton("添加课程");
            JButton jb22 = new JButton("刷新");
            JButton jb23 = new JButton("删除");
            JButton jb24 = new JButton("提交修改");

            jp21.add(jb21);
            jp21.add(jb22);
            jp21.add(jb23);
            jp21.add(jb24);
            String[][] datas = {};
            String[] titles = { "课程号", "课程名" ,"学分"};
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
            });
            jb21.addActionListener(e -> {
                AddCourse adc = new AddCourse();
                cname = adc.getCname();
                cno = adc.getCno();
                credit = adc.getCredit();
            });
            table1.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    selectedRows = table1.getSelectedRows();
                }
            });
            jb23.addActionListener(e -> {
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    String strSQL = "DELETE FROM course WHERE cno = ?";
                    System.out.println("连接数据库成功");
                    int rowsDeleted = 0;
                    for (int i = 0; i < selectedRows.length; i++) {
                        PreparedStatement st = dbConn.prepareStatement(strSQL);
                        Object value = myModel.getValueAt(selectedRows[i],0);
                        st.setObject(1, value);
                        rowsDeleted = st.executeUpdate();
                    }
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(AdminGui.this, "删除成功了");
                    } else {
                        JOptionPane.showMessageDialog(AdminGui.this, "删除失败了，数据已删除请刷新");
                    }

                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(AdminGui.this, "未选择数据");
                }
            });
            jb24.addActionListener(e -> {
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    String strSQL = "UPDATE course SET cno = ?,cname = ?, credit = ? WHERE cno = ?";
                    System.out.println("连接数据库成功");
                    int rowChangeed = 0;
                    for (int i = 0; i < selectedRows.length; i++) {
                        PreparedStatement st = dbConn.prepareStatement(strSQL);
                        st.setObject(1, myModel.getValueAt(selectedRows[i],0));
                        st.setObject(2, myModel.getValueAt(selectedRows[i],1));
                        st.setObject(3, myModel.getValueAt(selectedRows[i],2));
                        st.setObject(4, myModel.getValueAt(selectedRows[i],0));
                        rowChangeed = st.executeUpdate();
                    }
                    if (rowChangeed > 0) {
                        JOptionPane.showMessageDialog(AdminGui.this, "修改成功");
                    } else {
                        JOptionPane.showMessageDialog(AdminGui.this, "请选择要修改的行");
                    }

                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    System.out.print("连接失败");
                }
            });
            
            //jp1修改个人信息面板实现
            try {
                String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                String user = "u1";
                String password = "123";
                Connection dbConn = DriverManager.getConnection(URL, user, password);
                System.out.println("连接数据库成功");
                String strSQL1 = "select * from login where id = ?";
                PreparedStatement st = dbConn.prepareStatement(strSQL1);
                st.setString(1,id);
                ResultSet rs = st.executeQuery();
                rs.next();

                jp1.setLayout(new GridLayout(5,1,5,5));
                JPanel jp11 = new JPanel();
                JPanel jp12 = new JPanel();
                JPanel jp13 = new JPanel();
                JTextField jt11 = new JTextField(10);
                jt11.setText(rs.getString(1));
                JTextField jt12 = new JTextField(10);
                jt12.setText(rs.getString(2));
                JLabel jl11 = new JLabel("账户：");
                JLabel jl12 = new JLabel("密码：");
                JButton jb11 = new JButton("提交");
                jb11.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Connection dbConn1 = DriverManager.getConnection(URL, user, password);
                            String strSQL1 = "UPDATE login SET id = ?,pw = ? WHERE id = ?";
                            PreparedStatement st1 = dbConn1.prepareStatement(strSQL1);
                            st1.setString(1,jt11.getText());
                            st1.setString(2,jt12.getText());
                            st1.setString(3,id);
                            st1.executeUpdate();
                            JOptionPane.showMessageDialog(AdminGui.this, "提交成功");
                            dbConn.close();

                        } catch (Exception ee) {
                            ee.printStackTrace();
                            JOptionPane.showMessageDialog(AdminGui.this, "数据不合法");
                        }
                    }
                });

                jp11.add(jl11);
                jp11.add(jt11);
                jp12.add(jl12);
                jp12.add(jt12);
                jp13.add(jb11);


                jp1.add(new JPanel());
                jp1.add(jp11);
                jp1.add(jp12);
                jp1.add(jp13);
                jp1.add(new JPanel());

                dbConn.close();

            } catch (Exception ee) {
                ee.printStackTrace();
                System.out.print("连接失败");
            }

            //jp3学生管理面板实现
            JButton jb31 = new JButton("添加学生");
            JButton jb32 = new JButton("刷新");
            JButton jb33 = new JButton("删除");
            JButton jb34 = new JButton("提交修改");

            JPanel jp31 = new JPanel();
            jp31.add(jb31);
            jp31.add(jb32);
            jp31.add(jb33);
            jp31.add(jb34);
            String[][] datas2 = {};
            String[] titles2 = { "学号", "姓名" ,"性别","年龄", "班级"};
            DefaultTableModel myModel2 = new DefaultTableModel(datas2, titles2);// myModel存放表格的数据
            JTable table2 = new JTable(myModel2);// 表格对象table的数据来源是myModel对象
            table2.setRowHeight(20);// 行高
            table2.preferredSize();
            TableColumnModel columnModel2 = table2.getColumnModel();
            // 假设我们要设置第一列的宽度为150像素
            columnModel2.getColumn(0).setPreferredWidth(100);
            // 也可以设置第二列的宽度
            columnModel2.getColumn(1).setPreferredWidth(100);
            columnModel2.getColumn(2).setPreferredWidth(100);
            table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            try {
                String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                String user = "u1";
                String password = "123";
                Connection dbConn = DriverManager.getConnection(URL, user, password);
                Statement st = dbConn.createStatement();
                System.out.println("连接数据库成功");
                String strSQL = "select * from dbo.student";
                ResultSet rs = st.executeQuery(strSQL);
                if (myModel2.getRowCount() != 0) {
                    int num = myModel2.getRowCount();
                    for (int i = num - 1; i >= 0; i--) {
                        myModel2.removeRow(i);
                    }
                }
                while (rs.next()) {
                    Vector<String> ve = new Vector<String>();
                    ve.addElement(rs.getString(1));
                    ve.addElement(rs.getString(2));
                    ve.addElement(rs.getString(3));
                    ve.addElement(rs.getString(4));
                    ve.addElement(rs.getString(5));
                    myModel2.addRow(ve); // 添加一行到模型结尾
                }
                dbConn.close();

            } catch (Exception ee) {
                ee.printStackTrace();
                System.out.print("连接失败");
            }
            table2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            BoxLayout layout3 = new BoxLayout(jp3, BoxLayout.Y_AXIS);
            jp3.setLayout(layout3);
            jp3.add(jp31);
            jp3.add(new JScrollPane(table2));


            jb32.addActionListener(e -> {
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    Statement st = dbConn.createStatement();
                    System.out.println("连接数据库成功");
                    String strSQL = "select * from dbo.student";
                    ResultSet rs = st.executeQuery(strSQL);
                    if (myModel2.getRowCount() != 0) {
                        int num = myModel2.getRowCount();
                        for (int i = num - 1; i >= 0; i--) {
                            myModel2.removeRow(i);
                        }
                    }
                    while (rs.next()) {
                        Vector<String> ve = new Vector<String>();
                        ve.addElement(rs.getString(1));
                        ve.addElement(rs.getString(2));
                        ve.addElement(rs.getString(3));
                        ve.addElement(rs.getString(4));
                        ve.addElement(rs.getString(5));
                        myModel2.addRow(ve); // 添加一行到模型结尾
                    }
                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    System.out.print("连接失败");
                }
            });
            jb31.addActionListener(e -> {
                new AddStu();
            });
            table2.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    selectedRows = table2.getSelectedRows();
                }
            });
            jb33.addActionListener(e -> {
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    String strSQL = "DELETE FROM student WHERE sno = ?";
                    System.out.println("连接数据库成功");
                    int rowsDeleted = 0;
                    for (int i = 0; i < selectedRows.length; i++) {
                        PreparedStatement st = dbConn.prepareStatement(strSQL);
                        Object value = myModel2.getValueAt(selectedRows[i],0);
                        st.setObject(1, value);
                        rowsDeleted = st.executeUpdate();
                    }
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(AdminGui.this, "删除成功了");
                    } else {
                        JOptionPane.showMessageDialog(AdminGui.this, "删除失败了，数据已删除请刷新");
                    }

                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(AdminGui.this, "未选择数据");
                }
            });
            jb34.addActionListener(e -> {
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    String strSQL = "UPDATE student SET sno = ?,sname = ?, ssex = ?, sage = ?, class = ? WHERE sno = ?";
                    System.out.println("连接数据库成功");
                    int rowChangeed = 0;
                    for (int i = 0; i < selectedRows.length; i++) {
                        PreparedStatement st = dbConn.prepareStatement(strSQL);
                        st.setObject(1, myModel2.getValueAt(selectedRows[i],0));
                        st.setObject(2, myModel2.getValueAt(selectedRows[i],1));
                        st.setObject(3, myModel2.getValueAt(selectedRows[i],2));
                        st.setObject(4, myModel2.getValueAt(selectedRows[i],3));
                        st.setObject(5, myModel2.getValueAt(selectedRows[i],4));
                        st.setObject(6, myModel2.getValueAt(selectedRows[i],0));
                        rowChangeed = st.executeUpdate();
                    }
                    if (rowChangeed > 0) {
                        JOptionPane.showMessageDialog(AdminGui.this, "修改成功");
                    } else {
                        JOptionPane.showMessageDialog(AdminGui.this, "请选择要修改的行");
                    }

                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    System.out.print("连接失败");
                }
            });



            //jp4管理老师面板实现
            JButton jb41 = new JButton("添加教师");
            JButton jb42 = new JButton("刷新");
            JButton jb43 = new JButton("删除");
            JButton jb44 = new JButton("提交修改");

            JPanel jp41 = new JPanel();
            jp41.add(jb41);
            jp41.add(jb42);
            jp41.add(jb43);
            jp41.add(jb44);
            String[][] datas3 = {};
            String[] titles3 = { "教师号", "姓名" ,"性别","年龄", "课程"};
            DefaultTableModel myModel3 = new DefaultTableModel(datas3, titles3);// myModel存放表格的数据
            JTable table3 = new JTable(myModel3);// 表格对象table的数据来源是myModel对象
            table3.setRowHeight(20);// 行高
            table3.preferredSize();
            TableColumnModel columnModel3 = table3.getColumnModel();
            // 假设我们要设置第一列的宽度为150像素
            columnModel3.getColumn(0).setPreferredWidth(100);
            // 也可以设置第二列的宽度
            columnModel3.getColumn(1).setPreferredWidth(100);
            columnModel3.getColumn(2).setPreferredWidth(100);
            columnModel3.getColumn(4).setPreferredWidth(150);
            table3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            try {
                String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                String user = "u1";
                String password = "123";
                Connection dbConn = DriverManager.getConnection(URL, user, password);
                Statement st = dbConn.createStatement();
                System.out.println("连接数据库成功");
                String strSQL = "select * from dbo.teacher";
                ResultSet rs = st.executeQuery(strSQL);
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
                    ve.addElement(rs.getString(5));
                    myModel3.addRow(ve); // 添加一行到模型结尾
                }
                dbConn.close();

            } catch (Exception ee) {
                ee.printStackTrace();
                System.out.print("连接失败");
            }
            table3.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            BoxLayout layout4 = new BoxLayout(jp4, BoxLayout.Y_AXIS);
            jp4.setLayout(layout4);
            jp4.add(jp41);
            jp4.add(new JScrollPane(table3));


            jb42.addActionListener(e -> {
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    Statement st = dbConn.createStatement();
                    System.out.println("连接数据库成功");
                    String strSQL = "select * from dbo.teacher";
                    ResultSet rs = st.executeQuery(strSQL);
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
                        ve.addElement(rs.getString(5));
                        myModel3.addRow(ve); // 添加一行到模型结尾
                    }
                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    System.out.print("连接失败");
                }
            });
            jb41.addActionListener(e -> {
                new AddTea();
            });
            table3.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    selectedRows = table3.getSelectedRows();
                }
            });
            jb43.addActionListener(e -> {
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    String strSQL = "DELETE FROM teacher WHERE tno = ?";
                    System.out.println("连接数据库成功");
                    int rowsDeleted = 0;
                    for (int i = 0; i < selectedRows.length; i++) {
                        PreparedStatement st = dbConn.prepareStatement(strSQL);
                        Object value = myModel3.getValueAt(selectedRows[i],0);
                        st.setObject(1, value);
                        rowsDeleted = st.executeUpdate();
                    }
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(AdminGui.this, "删除成功了");
                    } else {
                        JOptionPane.showMessageDialog(AdminGui.this, "删除失败了，数据已删除请刷新");
                    }

                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(AdminGui.this, "未选择数据");
                }
            });
            jb44.addActionListener(e -> {
                try {
                    String URL = "jdbc:sqlserver://localhost:1433;databaseName=Final_Disign";
                    String user = "u1";
                    String password = "123";
                    Connection dbConn = DriverManager.getConnection(URL, user, password);
                    String strSQL = "UPDATE teacher SET tno = ?,tname = ?, tsex = ?, tage = ?, class = ? WHERE tno = ?";
                    System.out.println("连接数据库成功");
                    int rowChangeed = 0;
                    for (int i = 0; i < selectedRows.length; i++) {
                        PreparedStatement st = dbConn.prepareStatement(strSQL);
                        st.setObject(1, myModel3.getValueAt(selectedRows[i],0));
                        st.setObject(2, myModel3.getValueAt(selectedRows[i],1));
                        st.setObject(3, myModel3.getValueAt(selectedRows[i],2));
                        st.setObject(4, myModel3.getValueAt(selectedRows[i],3));
                        st.setObject(5, myModel3.getValueAt(selectedRows[i],4));
                        st.setObject(6, myModel3.getValueAt(selectedRows[i],0));
                        rowChangeed = st.executeUpdate();
                    }
                    if (rowChangeed > 0) {
                        JOptionPane.showMessageDialog(AdminGui.this, "修改成功");
                    } else {
                        JOptionPane.showMessageDialog(AdminGui.this, "请选择要修改的行");
                    }

                    dbConn.close();

                } catch (Exception ee) {
                    ee.printStackTrace();
                    System.out.print("连接失败");
                }
            });


            //jp5管理员授权面板实现



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