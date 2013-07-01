package com.nareshkumarrao.shopkeepr.stockDialogs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;

import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType.supplierList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class supplierDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public supplierDialog() {
		final supplierList suppList = loaders.loadSupplierList();
		
		setTitle("Suppliers");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 346, 118);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setBounds(197, 10, 121, 20);
		contentPane.add(comboBox);
		
		String[] str_suppList = suppList.suppliers.toArray(new String[suppList.suppliers.size()]);
		comboBox.setModel(new DefaultComboBoxModel(str_suppList));
		
		JLabel lblSupplier = new JLabel("Supplier:");
		lblSupplier.setBounds(141, 13, 46, 14);
		contentPane.add(lblSupplier);
		
		JButton btnAddSupplier = new JButton("Add Supplier");
		btnAddSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=(String)comboBox.getSelectedItem();
				int ind = suppList.suppliers.indexOf(id);
				if(ind<0)
				{
					suppList.addSupplier(id);
					loaders.saveSupplierList(suppList);
					String[] str_suppList = suppList.suppliers.toArray(new String[suppList.suppliers.size()]);
					comboBox.setModel(new DefaultComboBoxModel(str_suppList));
				}
			}
		});
		btnAddSupplier.setBounds(10, 10, 121, 23);
		contentPane.add(btnAddSupplier);
		
		JButton btnRemoveSupplier = new JButton("Remove Supplier");
		btnRemoveSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id=(String)comboBox.getSelectedItem();
				int ind = suppList.suppliers.indexOf(id);
				if(ind>-1)
				{
					suppList.removeSupplier(id);
					loaders.saveSupplierList(suppList);
					String[] str_suppList = suppList.suppliers.toArray(new String[suppList.suppliers.size()]);
					comboBox.setModel(new DefaultComboBoxModel(str_suppList));
				}
			}
		});
		btnRemoveSupplier.setBounds(10, 44, 121, 23);
		contentPane.add(btnRemoveSupplier);
	}
}
