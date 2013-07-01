package com.nareshkumarrao.shopkeepr.stockDialogs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.nareshkumarrao.shopkeepr.confirmationDialogs.alert;
import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType.inventory;
import com.nareshkumarrao.shopkeepr.utils.srType.inventoryReport;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class receiveStock extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JTextField receivField;
	private JTextField oldstockField;
	private JTextField textField;

	public receiveStock(final inventory inv) {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Receive Stock");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 277, 226);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBarcode = new JLabel("Barcode:");
		lblBarcode.setBounds(6, 12, 61, 16);
		contentPane.add(lblBarcode);
		
		idField = new JTextField();
		idField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(inv.exists(idField.getText()))
				{
					String id = idField.getText();
					oldstockField.setText(Integer.toString(inv.getQuantity(id)));
					receivField.setText("0");
				}else if(idField.getText().matches(""))
					{
						//nth
					}else{
					
					new alert("No such item").setVisible(true);
				}
			}
		});
		idField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inv.exists(idField.getText()))
				{
					String id = idField.getText();
					oldstockField.setText(Integer.toString(inv.getQuantity(id)));
					receivField.setText("0");
				}else{
					new alert("No such item").setVisible(true);
					idField.requestFocus();
				}
			}
		});
		idField.setBounds(132, 6, 134, 28);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JLabel lblReceiveAmount = new JLabel("Receive Amount:");
		lblReceiveAmount.setBounds(6, 52, 110, 16);
		contentPane.add(lblReceiveAmount);
		
		receivField = new JTextField();
		receivField.setBounds(132, 46, 134, 28);
		contentPane.add(receivField);
		receivField.setColumns(10);
		
		JLabel lblCurrentStock = new JLabel("Current Stock: ");
		lblCurrentStock.setBounds(6, 85, 110, 16);
		contentPane.add(lblCurrentStock);
		
		oldstockField = new JTextField();
		oldstockField.setEnabled(false);
		oldstockField.setEditable(false);
		oldstockField.setBounds(132, 79, 134, 28);
		contentPane.add(oldstockField);
		oldstockField.setColumns(10);
		
		JButton btnApplyChange = new JButton("Apply Change");
		btnApplyChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rec = Integer.parseInt(receivField.getText());
				String id = idField.getText();
				if(rec>0 && inv.exists(id))
				{
					inv.addQuantity(rec, id);
					loaders.saveInventory(inv);
					oldstockField.setText(Integer.toString(inv.getQuantity(id)));
					receivField.setText("0");
					
					//Save to Report
					inventoryReport invRep = loaders.loadInventoryReport();
					invRep.addInvToLog(inv);
					loaders.saveInventoryReport(invRep);
				}
			}
		});
		btnApplyChange.setBounds(6, 158, 117, 29);
		contentPane.add(btnApplyChange);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnClose.setBounds(132, 158, 134, 29);
		contentPane.add(btnClose);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setBounds(132, 118, 134, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblSupplier = new JLabel("Supplier:");
		lblSupplier.setBounds(6, 125, 46, 14);
		contentPane.add(lblSupplier);
	}
}
