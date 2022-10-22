import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUser {

    private JPanel Main;
    private JTextField txFname;
    private JTextField txLname;
    private JTextField txage;
    private JTextField txEmail;
    private JTextField txActive;
    private JButton saveButton;
    private JButton getusersButton;


    Connection con;
    PreparedStatement pst;

    public static void main(String[] args) {
        JFrame frame = new JFrame("AddUser");
        frame.setContentPane(new AddUser().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public AddUser() {

        DBConnect();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String fname = txFname.getText();
                String lname = txLname.getText();
                int age = Integer.parseInt(txage.getText());
                String email = txEmail.getText();
                boolean active = Boolean.parseBoolean(txActive.getText());

                try {
                    PreparedStatement pst = con.prepareStatement("insert into user(fname,lname,age,email,active)value(?,?,?,?,?)");
                    pst.setString(1, fname);
                    pst.setString(2, lname);
                    pst.setInt(3, age);
                    pst.setString(4, email);
                    pst.setBoolean(5, active);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Recorde Successfully added");
                }
                catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Something happened! Error");
                    ex.printStackTrace();
                }
                txFname.setText("");
                txLname.setText("");
                txage.setText("");
                txEmail.setText("");
                txActive.setText("");

                txFname.requestFocus();

            }
        });


        getusersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pst = con.prepareStatement("select * from user");
                    var result = pst.executeQuery();

                    while (result.next()) {
                        int id = result.getInt("id");
                        String fname = result.getString("fname");
                        String lname = result.getString("lname");
                        int age = Integer.parseInt(result.getString("age"));
                        String email = result.getString("email");
                        boolean active = Boolean.parseBoolean(result.getString("active"));
                        System.out.println(id + " - " + fname+ " - " +lname+ " - " +age+ " - " +email+ " - " +active);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void DBConnect() {
        try {

            String ConUrl = "localhost:3306";
            String userName = "root";
            String password = " ";
            String dataBase = "enterprice";

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+ConUrl+"/"+dataBase, userName, password);

            System.out.println("Success");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    }
