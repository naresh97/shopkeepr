package com.nareshkumarrao.shopkeepr.stockDialogs;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import javax.swing.DefaultComboBoxModel;

import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType;
import com.nareshkumarrao.shopkeepr.utils.srType.inventoryReport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class adjustStock extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField qttField;

	public adjustStock(final srType.inventory inv) {
		setResizable(false);

		setTitle("Adjust Stock");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 475, 207);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(6, 12, 61, 16);
		contentPane.add(lblName);

		JLabel lblPrice = new JLabel("Price: ");
		lblPrice.setBounds(6, 52, 61, 16);
		contentPane.add(lblPrice);

		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setBounds(6, 92, 61, 16);
		contentPane.add(lblQuantity);

		nameField = new JTextField();
		nameField.setBounds(79, 6, 134, 28);
		contentPane.add(nameField);
		nameField.setColumns(10);

		priceField = new JTextField();
		priceField.setBounds(79, 46, 134, 28);
		contentPane.add(priceField);
		priceField.setColumns(10);

		qttField = new JTextField();
		qttField.setBounds(79, 86, 134, 28);
		contentPane.add(qttField);
		qttField.setColumns(10);

		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = (String) comboBox.getSelectedItem();
				if (inv.exists(id)) {
					nameField.setText(inv.getName(id));
					priceField.setText(inv.getPrice(id).toPlainString());
					qttField.setText(Integer.toString(inv.getQuantity(id)));
				} else {
					nameField.setText("ITEM_NAME");
					priceField.setText("0.00");
					qttField.setText("0");
				}
				System.out.println(id);
			}
		});
		comboBox.setModel(new DefaultComboBoxModel((String[]) inv.invID
				.toArray(new String[inv.invID.size()])));
		comboBox.setEditable(true);
		comboBox.setBounds(298, 6, 146, 27);
		contentPane.add(comboBox);

		JLabel lblBarcode = new JLabel("Barcode:");
		lblBarcode.setBounds(225, 12, 61, 16);
		contentPane.add(lblBarcode);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (inv.exists((String) comboBox.getSelectedItem())) {
					inv.modify((String) comboBox.getSelectedItem(),
							nameField.getText(),
							Integer.parseInt(qttField.getText()),
							new BigDecimal(priceField.getText()));
					loaders.saveInventory(inv);
					
					//Save to Report
					inventoryReport invRep = loaders.loadInventoryReport();
					invRep.addInvToLog(inv);
					loaders.saveInventoryReport(invRep);
				}
			}
		});
		btnApply.setBounds(198, 126, 117, 29);
		contentPane.add(btnApply);

		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		btnCancel.setBounds(327, 126, 117, 29);
		contentPane.add(btnCancel);

		JButton btnNewButton = new JButton("Delete Item");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inv.removeItem((String)comboBox.getSelectedItem());
				comboBox.setModel(new DefaultComboBoxModel(
						(String[]) inv.invID.toArray(new String[inv.invID
								.size()])));
				loaders.saveInventory(inv);
				//Save to Report
				inventoryReport invRep = loaders.loadInventoryReport();
				invRep.addInvToLog(inv);
				loaders.saveInventoryReport(invRep);
				nameField.setText("");
				priceField.setText("");
				qttField.setText("");
			}
		});
		btnNewButton.setBounds(16, 126, 170, 29);
		contentPane.add(btnNewButton);

		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!inv.exists((String) comboBox.getSelectedItem())) {
					inv.addItem(comboBox.getSelectedItem().toString().toUpperCase(),
							nameField.getText(),
							Integer.parseInt(qttField.getText()),
							new BigDecimal(priceField.getText()));
					comboBox.setModel(new DefaultComboBoxModel(
							(String[]) inv.invID.toArray(new String[inv.invID
									.size()])));
					loaders.saveInventory(inv);
					//Save to Report
					inventoryReport invRep = loaders.loadInventoryReport();
					invRep.addInvToLog(inv);
					loaders.saveInventoryReport(invRep);
					nameField.setText("");
					priceField.setText("");
					qttField.setText("");
				}
			}
		});
		btnAddItem.setBounds(225, 39, 219, 29);
		contentPane.add(btnAddItem);
	}
}
