
package jdbc;


import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class WinDB extends JFrame implements ActionListener {
    public static void main(String[] args) {           new WinDB().setVisible(true);    }    

    //Container represents inner part of the window excluding title and buttons of miax/min size
    private final Container cnt;
    
    private static final String CONNECTION_STR = "jdbc:oracle:thin:hr/oracle@localhost:1521/XE";
    //classes of Layout are used for automatical placement of visual component
    private BorderLayout mainLayout = new BorderLayout();

    private JLabel sqlLab = new JLabel("SQL :");
    private JTextField sqlField = new JTextField(50);
    
    //JPanel works as a container for other visual components
    private JPanel sqlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JButton sqlBut = new JButton("execute");
    private JButton sqlBut1 = new JButton("FistUp Word");
    
    //a special visual component  JTable to show table-like data
    private JTable tab = new JTable( new String[ ][ ]{ 
        {"Row1_1","Row1_2"}, {"Row1_1","Row1_2"} },	//some data of table
            new String[ ]{"Column1", "Column2"} 		//array with column headers
    );
    private JScrollPane scrPane = new JScrollPane(tab); //to allow scrolling of table's date  put  JTable  in   JScrollPane
    
    public WinDB( ) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        cnt = getContentPane( );
        //use inside Container layout manager of BorderLayout kind
        cnt.setLayout(mainLayout);        
        setBounds(20,20,  800, 500);//size and placement of the window
        
        //on top of the window place JPanel (another container)
        cnt.add(sqlPanel, BorderLayout.NORTH);
        sqlPanel.add(sqlLab);   //add to JPanel label with text 
        sqlPanel.add(sqlField);
        sqlPanel.add(sqlBut);
        sqlPanel.add(sqlBut1);
        sqlBut.addActionListener(this); //this class listens to button click 
        sqlBut1.addActionListener(this);
        
        cnt.add(scrPane, BorderLayout.CENTER);  //place JTable with 2-dim data array in center of window
        sqlField.setText("SELECT * FROM Books");//write some text into text field
        setExtendedState(MAXIMIZED_BOTH);       //maximize the window
    }
    
    private ArrayList<Object[ ]> executeSQL(String sql){
        ArrayList<Object[ ]> data  = new ArrayList<>();	//this arrayList is returned by the method
        try
        (   //all closeable resources put into try-with-resourse 
          Connection   con = DriverManager.getConnection(CONNECTION_STR);
          Statement stm = con.createStatement();
          ResultSet rs = stm.executeQuery(sql);
        )
        {
            //rs.getMetaData() returns a ResultSet with description of 
            //data (metadata), not results of query
            //Using metadata it's possible to get info about all fields (name, type, length)
            final int colCount = rs.getMetaData().getColumnCount();
            
            Object[ ] titles = new Object[colCount];
            //read all names of fields in the query
            for(int c = 0; c < colCount; c++)
                titles[c] = rs.getMetaData().getColumnName(c+1);
            data.add(titles);   //add all field names into results of method
            
            //read results of query and add them into results of method
            while( rs.next( ) ){
                Object[] row = new Object[colCount];
                for(int i = 0; i < colCount; i++){	//read all fields of table row one-by-one
                    row[i] = rs.getObject(i + 1);
                }
                data.add(row);// add filled Object array to results of method
            }
            return data;
        }
        catch(SQLException e){   //exception handling         
            Color prevCol = sqlField.getBackground();
            sqlField.setBackground(Color.red);
            JOptionPane.showMessageDialog(this, "Exception = " + e.getMessage());
            System.out.println("Exception = " + e);
            sqlField.setBackground(prevCol);
            
            return null;    //if an error happened return null
        }
    }	// executeSQL

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == sqlBut1){
            sqlField.setText("select substr(name, 1, instr(name, ' ',1,1) ) firWord, name from books  where substr(name, 1, instr(name, ' ',1,1) )  = upper(substr(name, 1, instr(name, ' ',1,1) ) )");
            src = sqlBut;
        }
        
        if (src == sqlBut){
            ArrayList<Object[]> arl = executeSQL(sqlField.getText());
            if (arl == null)    //check result, if null do not show anything in table
                return;
            
            //remove row with names of fields and save it in a special array
            Object [] titles = arl.remove(0);
            //read all data from ArrayList and copy them into 2-dim array "rows"
            //Array rows is used to fill JTable. 
            //To fill JTable a special class  DefaultTableModel is used
            Object[][] rows =  new Object[arl.size()][titles.length];
            int i = 0;
            for(Object[ ] row : arl){
                rows[i++] = row;
            }
            //fill the JTable instance
            //After this method data from rows with headers from titles are 
            //shown in JTable
            tab.setModel(new DefaultTableModel(rows, titles));
        }
    }	// actionPerformed

}//class