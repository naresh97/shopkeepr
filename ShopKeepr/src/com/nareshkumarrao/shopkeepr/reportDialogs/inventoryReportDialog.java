package com.nareshkumarrao.shopkeepr.reportDialogs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JButton;

import com.nareshkumarrao.shopkeepr.utils.inventoryReportCreator;
import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType.inventoryReport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JComboBox;

public class inventoryReportDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox startField;
	private JComboBox endField;

	public inventoryReportDialog() {
		final inventoryReport invRep = loaders.loadInventoryReport();
		List<String> repDates = invRep.dates;
		String[] str_repDates = repDates.toArray(new String[repDates.size()]);
		
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Inventory Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 265, 180);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStartDate = new JLabel("Start Date:");
		lblStartDate.setBounds(6, 12, 82, 16);
		contentPane.add(lblStartDate);
		
		JLabel lblEndDate = new JLabel("End Date:");
		lblEndDate.setBounds(6, 52, 67, 16);
		contentPane.add(lblEndDate);
		
		startField = new JComboBox();
		startField.setBounds(100, 6, 151, 28);
		startField.setModel(new DefaultComboBoxModel(str_repDates));
		startField.setSelectedIndex(repDates.size()-1);
		contentPane.add(startField);
		
		endField = new JComboBox();
		endField.setBounds(100, 46, 151, 28);
		endField.setModel(new DefaultComboBoxModel(str_repDates));
		endField.setSelectedIndex(repDates.size()-1);
		contentPane.add(endField);
		
		JLabel lblPleaseNoteDate = new JLabel("Please Note: Date format is yyyyMMdd.");
		lblPleaseNoteDate.setBounds(6, 80, 245, 21);
		contentPane.add(lblPleaseNoteDate);
		
		JButton btnGenerateReports = new JButton("Generate Report");
		btnGenerateReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				inventoryReportCreator.createReport(invRep, (String)startField.getSelectedItem(), (String)endField.getSelectedItem());
				setVisible(false);
				dispose();
			}
		});
		btnGenerateReports.setBounds(6, 113, 245, 29);
		contentPane.add(btnGenerateReports);
	}
}
