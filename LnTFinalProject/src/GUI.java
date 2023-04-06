import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.net.MalformedURLException;
import java.net.URL;

public class GUI implements ActionListener, KeyListener{

	JFrame frame;
	JTextField idField;
	JTextField nameField;
	JTextField priceField;
	JTextField stockField;
	JButton btnAdd;
	JButton btnDelete;
	JButton btnUpdate;
	JButton btnClear;
	JTable table;
	DefaultTableModel model;
	
	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI() {
		Connect();
		run();
		viewTable();
	}
	
	Connection con;
	Statement s;
	PreparedStatement pst;
	ResultSet rs;
	 
	public void Connect() {
	        
		String url = "jdbc:mysql://localhost:3306/pudding";
		String username = "root";
		String password = "";
		
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection(url, username, password);
	        s = con.createStatement();
				
	    } catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				
		} catch (SQLException ex) {
				ex.printStackTrace();
		}
	}
	
	public void viewTable() {
		
		while(table.getRowCount() != 0) {
			model.removeRow(table.getRowCount() - 1);
		}
		
		try {
			rs = s.executeQuery("SELECT * FROM Menu");
			while(rs.next()) {
				Object[] row = {
						rs.getString("id"), 
						rs.getString("name"), 
						rs.getInt("price"), 
						rs.getInt("stock")
						};
				
				model.addRow(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void run() {
		
		// ICON
		ImageIcon icon = new ImageIcon();
		try {
			icon = new ImageIcon(new URL("https://cdn-icons-png.flaticon.com/512/333/333322.png"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		// FRAME
		frame = new JFrame();
		frame.setResizable(false);
		frame.setIconImage(icon.getImage());
		frame.setTitle("Pudding Database");
		frame.setBounds(100, 100, 990, 500);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(239, 241, 249));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		// INNER COMPONENTS
		JPanel topPanel = new JPanel();
		topPanel.setBackground(new Color(240, 240, 240));
		topPanel.setPreferredSize(new Dimension(0, 80));
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(null);
		
		JLabel header = new JLabel("PT Pudding Database");
		header.setBounds(62, 0, 420, 80);
		header.setBackground(new Color(240, 240, 240));
		header.setForeground(Color.BLACK);
		header.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 40));
		header.setHorizontalAlignment(SwingConstants.LEFT);
		topPanel.add(header);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(240, 240, 240));
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);
		
		JLabel panelTitle = new JLabel("Menu Editor");
		panelTitle.setBounds(60, 24, 85, 16);
		centerPanel.add(panelTitle);
		
		JPanel editorPanel = new JPanel();
		editorPanel.setFocusable(false);
		editorPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		editorPanel.setBackground(new Color(240, 240, 240));
		editorPanel.setBounds(60, 43, 372, 202);
		centerPanel.add(editorPanel);
		editorPanel.setLayout(null);
		
		JLabel IdLabel = new JLabel("Menu ID:");
		IdLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		IdLabel.setBounds(26, 33, 86, 25);
		editorPanel.add(IdLabel);
		
		JLabel nameLabel = new JLabel("Menu Name:");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameLabel.setBounds(26, 70, 86, 25);
		editorPanel.add(nameLabel);
		
		JLabel priceLabel = new JLabel("Menu Price:");
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		priceLabel.setBounds(26, 107, 86, 25);
		editorPanel.add(priceLabel);
		
		JLabel stockLabel = new JLabel("Menu Stock:");
		stockLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		stockLabel.setBounds(26, 144, 86, 25);
		editorPanel.add(stockLabel);
		
		idField = new JTextField();
		idField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		idField.setBorder(new BevelBorder(BevelBorder.LOWERED));
		idField.setBounds(116, 33, 223, 25);
		editorPanel.add(idField);
		idField.setColumns(10);
		idField.addKeyListener(this);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameField.setBorder(new BevelBorder(BevelBorder.LOWERED));
		nameField.setBounds(116, 70, 223, 25);
		editorPanel.add(nameField);
		nameField.setColumns(10);
		
		priceField = new JTextField();
		priceField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		priceField.setBorder(new BevelBorder(BevelBorder.LOWERED));
		priceField.setBounds(116, 107, 223, 25);
		editorPanel.add(priceField);
		priceField.setColumns(10);
		
		stockField = new JTextField();
		stockField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		stockField.setBorder(new BevelBorder(BevelBorder.LOWERED));
		stockField.setBounds(116, 144, 223, 25);
		editorPanel.add(stockField);
		stockField.setColumns(10);
		
		btnAdd = new JButton("ADD");
		btnAdd.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdd.setFocusable(false);
		btnAdd.setBackground(UIManager.getColor("Button.background"));
		btnAdd.setBounds(60, 266, 85, 44);
		btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdd.addActionListener(this);
		centerPanel.add(btnAdd);
		
		btnUpdate = new JButton("UPDATE");
		btnUpdate.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setFocusable(false);
		btnUpdate.setBackground(UIManager.getColor("Button.background"));
		btnUpdate.setBounds(156, 266, 85, 44);
		btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnUpdate.addActionListener(this);
		btnUpdate.setEnabled(false);
		centerPanel.add(btnUpdate);
		
		btnDelete = new JButton("DELETE");
		btnDelete.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDelete.setFocusable(false);
		btnDelete.setBackground(UIManager.getColor("Button.background"));
		btnDelete.setBounds(251, 266, 85, 44);
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDelete.addActionListener(this);
		btnDelete.setEnabled(false);
		centerPanel.add(btnDelete);
		
		btnClear = new JButton("CLEAR");
		btnClear.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setBackground(UIManager.getColor("Button.background"));
		btnClear.setFocusable(false);
		btnClear.setBounds(347, 266, 85, 44);
		btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnClear.addActionListener(this);
		centerPanel.add(btnClear);
		
		// TABLE
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(470, 0, 462, 342);
		centerPanel.add(scrollPane);
		
		model = new DefaultTableModel();
		table = new JTable(model);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Price");
		model.addColumn("Stock");
		scrollPane.setViewportView(table);
		
	}
	
	// METHODS

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAdd) {
			if (idField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nothing to add...");
			} else {
				add();
			}
		}
		if (e.getSource() == btnUpdate) {
			update();
		}
		if (e.getSource() == btnDelete) {
			delete();
		}
		if (e.getSource() == btnClear) {
			clear();
		}
	}
	
	public void add() {
		
		String menuID, menuName, menuPrice, menuStock;
		
		menuID = idField.getText();
		menuName = nameField.getText();
		menuPrice = priceField.getText();
		menuStock = stockField.getText();
		
		if (menuID.matches("PD-" + "[0-9][0-9][0-9]")) {
			
			try {
				pst = con.prepareStatement("insert into Menu values(?,?,?,?)");
				pst.setString(1, menuID);
				pst.setString(2, menuName);
				pst.setString(3, menuPrice);
				pst.setString(4, menuStock);
				pst.executeUpdate();
				viewTable();
				JOptionPane.showMessageDialog(null, "Menu added!");
				
				idField.setText("");
				nameField.setText("");
				priceField.setText("");
				stockField.setText("");
				idField.requestFocus();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "Wrong Menu ID format! (PD-XXX)");
		}
	}
	
	public void update() {
		String menuID, menuName, menuPrice, menuStock;
		
		menuID = idField.getText();
		menuName = nameField.getText();
		menuPrice = priceField.getText();
		menuStock = stockField.getText();
		
		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to update " + menuName + "?", "Warning", JOptionPane.YES_NO_OPTION);
		
		if (result == 0) {
			try {
				pst = con.prepareStatement("UPDATE Menu set price = ?, stock = ? where id = ?");
				pst.setString(1, menuPrice);
				pst.setString(2, menuStock);
				pst.setString(3, menuID);
				pst.executeUpdate();
				viewTable();
				JOptionPane.showMessageDialog(null, "Menu updated!");
				
				idField.setText("");
				nameField.setText("");
				priceField.setText("");
				stockField.setText("");
				nameField.setEditable(true);
				idField.requestFocus();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void delete() {
		
		String menuID, menuName;
		menuID = idField.getText();
		menuName = nameField.getText();
		
		int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + menuName + "?", "Warning", JOptionPane.YES_NO_OPTION);
			
		if (result == 0) {
			try {
				pst = con.prepareStatement("DELETE FROM Menu WHERE id = ?");
				pst.setString(1, menuID);
				pst.executeUpdate();
				viewTable();
				JOptionPane.showMessageDialog(null, "Menu deleted!");
					
				idField.setText("");
				nameField.setText("");
				priceField.setText("");
				stockField.setText("");
				nameField.setEditable(true);
				idField.requestFocus();
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void clear() {
		idField.setText("");
		nameField.setText("");
		priceField.setText("");
		stockField.setText("");
		idField.requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		try {
			
			String id = idField.getText();
			
			pst = con.prepareStatement("SELECT name, price, stock FROM Menu WHERE id = ?");
			pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
            	btnAdd.setEnabled(false);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
            	String name = rs.getString("name");
            	String price = rs.getString("price");
                String stock = rs.getString("stock");
                
                nameField.setText(name);
                priceField.setText(price);
                stockField.setText(stock);
                nameField.setEditable(false);
                
                
			} else {
				btnAdd.setEnabled(true);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				
				if (!nameField.isEditable()) {
					nameField.setText("");
					priceField.setText("");
					stockField.setText("");
				}
				nameField.setEditable(true);
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
}
