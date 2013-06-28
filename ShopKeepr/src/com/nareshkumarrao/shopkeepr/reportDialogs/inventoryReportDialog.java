package com.nareshkumarrao.shopkeepr.reportDialogs;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.nareshkumarrao.shopkeepr.utils.inventoryReportCreator;
import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType.inventoryReport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class inventoryReportDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField startField;
	private JTextField endField;

	public inventoryReportDialog() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		
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
		
		startField = new JTextField();
		startField.setBounds(100, 6, 151, 28);
		contentPane.add(startField);
		startField.setColumns(10);
		startField.setText(date);
		
		endField = new JTextField();
		endField.setBounds(100, 46, 151, 28);
		contentPane.add(endField);
		endField.setColumns(10);
		endField.setText(date);
		
		JLabel lblPleaseNoteDate = new JLabel("Please Note: Date format is yyyyMMdd.");
		lblPleaseNoteDate.setBounds(6, 80, 245, 21);
		contentPane.add(lblPleaseNoteDate);
		
		JButton btnGenerateReports = new JButton("Generate Report");
		btnGenerateReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inventoryReport invRep = loaders.loadInventoryReport();
				inventoryReportCreator.createReport(invRep, startField.getText(), endField.getText());
				setVisible(false);
				dispose();
			}
		});
		btnGenerateReports.setBounds(6, 113, 245, 29);
		contentPane.add(btnGenerateReports);
	}
}
