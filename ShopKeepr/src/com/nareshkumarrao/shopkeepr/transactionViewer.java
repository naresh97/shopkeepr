package com.nareshkumarrao.shopkeepr;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType.inventory;
import com.nareshkumarrao.shopkeepr.utils.srType.transaction;
import com.nareshkumarrao.shopkeepr.utils.srType.transactionList;
import com.nareshkumarrao.shopkeepr.utils.srType.transactionReport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextPane;
import java.awt.Font;

public class transactionViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField codeField;

	public transactionViewer(final inventory inv) {
		final transactionReport transRep = loaders.loadTransactionReport();
		
		setTitle("Transaction Viewer");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 535, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTransactionCode = new JLabel("Transaction Code:");
		lblTransactionCode.setBounds(10, 11, 95, 14);
		contentPane.add(lblTransactionCode);
		
		final JTextPane textPane = new JTextPane();
		textPane.setBounds(20, 36, 499, 244);
		contentPane.add(textPane);
		
		final JLabel lblRm = new JLabel("RM0.00");
		lblRm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRm.setBounds(10, 291, 130, 22);
		contentPane.add(lblRm);
		
		codeField = new JTextField();
		codeField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long millis = Long.parseLong(codeField.getText());
				Date date = new Date(millis);
				DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				String transDate = dateFormat.format(date);
				
				int itemIndex = transRep.dates.indexOf(transDate);
				if(itemIndex>-1)
				{
					transactionList transList = transRep.getTransList(transDate);
					transaction trans = transList.getTrans(millis);
					
					textPane.setText(trans.generateTransactionText(inv));
					lblRm.setText("RM"+trans.totalPrice);
				}
			}
		});
		codeField.setBounds(115, 8, 186, 20);
		contentPane.add(codeField);
		codeField.setColumns(10);
		
		
	}
}
