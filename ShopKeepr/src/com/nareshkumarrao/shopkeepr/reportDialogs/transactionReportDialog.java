package com.nareshkumarrao.shopkeepr.reportDialogs;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType.transactionReport;
import com.nareshkumarrao.shopkeepr.utils.transactionReportCreator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class transactionReportDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox dateField;

	public transactionReportDialog() {
		final transactionReport transRep = loaders.loadTransactionReport();
		List<String> transDates = transRep.dates;
		String[]str_transDates = transDates.toArray((String[]) new String[transDates.size()]);
		
		
		setAlwaysOnTop(true);
		setTitle("Transaction Report");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 344, 162);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblReportDate = new JLabel("Report Date :");
		lblReportDate.setBounds(6, 12, 92, 16);
		contentPane.add(lblReportDate);
		
		dateField = new JComboBox();
		dateField.setBounds(110, 6, 215, 28);
		dateField.setModel(new DefaultComboBoxModel(str_transDates));
		dateField.setSelectedIndex(transDates.size()-1);
		contentPane.add(dateField);
		
		JLabel lblPleaseNoteThe = new JLabel("Please Note: The format for the date is yyyyMMdd");
		lblPleaseNoteThe.setBounds(6, 68, 319, 16);
		contentPane.add(lblPleaseNoteThe);
		
		JLabel lblSession = new JLabel("Session :");
		lblSession.setBounds(6, 40, 61, 16);
		contentPane.add(lblSession);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(loaders.loadCFG()));
		comboBox.setBounds(110, 36, 215, 27);
		contentPane.add(comboBox);
		
		JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				transactionReportCreator.createReport(transRep, (String)dateField.getSelectedItem(), (String) comboBox.getSelectedItem());
				setVisible(false);
				dispose();
			}
		});
		btnGenerateReport.setBounds(6, 96, 319, 29);
		contentPane.add(btnGenerateReport);
	}
}
