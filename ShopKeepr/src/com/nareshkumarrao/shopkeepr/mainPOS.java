package com.nareshkumarrao.shopkeepr;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.KeyStroke;

import com.nareshkumarrao.shopkeepr.confirmationDialogs.alert;
import com.nareshkumarrao.shopkeepr.confirmationDialogs.confirmPurchase;
import com.nareshkumarrao.shopkeepr.reportDialogs.inventoryReportDialog;
import com.nareshkumarrao.shopkeepr.reportDialogs.transactionReportDialog;
import com.nareshkumarrao.shopkeepr.stockDialogs.adjustStock;
import com.nareshkumarrao.shopkeepr.stockDialogs.receiveStock;
import com.nareshkumarrao.shopkeepr.stockDialogs.supplierDialog;
import com.nareshkumarrao.shopkeepr.utils.barcodeGenerator;
import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType;
import com.nareshkumarrao.shopkeepr.utils.srType.transactionReport;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class mainPOS extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel posPane;
	private JTextField idField;
	private JTextField qttField;

	private srType.transactionList translist;
	private srType.transaction trans;
	private srType.inventory inv;

	public mainPOS(final String session) {

		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		inv = loaders.loadInventory();
		trans = new srType.transaction(session);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		
		transactionReport transRep = loaders.loadTransactionReport();
		if(transRep.transListExists(date))
		{
			translist = transRep.getTransList(date);
		}else{
			translist = new srType.transactionList();
		}

		setResizable(false);
		setTitle("Shopkeepr - " + session);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 686, 444);
		posPane = new JPanel();
		posPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(posPane);
		posPane.setLayout(null);

		final JLabel priceLabel = new JLabel("RM0.00");
		priceLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 60));
		priceLabel.setBounds(19, 24, 628, 84);
		posPane.add(priceLabel);

		JLabel sessionLabel = new JLabel("Session: " + session);
		sessionLabel.setBounds(29, 17, 173, 16);
		posPane.add(sessionLabel);

		final JTextPane transTPanel = new JTextPane();
		transTPanel.setEditable(false);
		transTPanel.setBounds(29, 120, 623, 226);
		posPane.add(transTPanel);

		JLabel lblItemCode = new JLabel("Item Code:");
		lblItemCode.setBounds(39, 358, 75, 16);
		posPane.add(lblItemCode);

		idField = new JTextField();
		idField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String id = idField.getText();
				if (id.length() < 1) {
					return;
				}
				if (inv.exists(id)) {
					idField.setText("");
					trans.addItems(id, Integer.parseInt(qttField.getText()),
							inv);
					transTPanel.setText(trans.generateTransactionText(inv));
					priceLabel.setText("RM" + trans.totalPrice);
				}else{
					new alert("No such item").setVisible(true);
				}
			}
		});
		idField.setBounds(126, 352, 183, 28);
		idField.requestFocus();
		posPane.add(idField);
		idField.setColumns(10);

		JLabel lblQuantity = new JLabel("Quantity: ");
		lblQuantity.setBounds(321, 358, 68, 16);
		posPane.add(lblQuantity);

		qttField = new JTextField();
		qttField.setText("1");
		qttField.setBounds(401, 352, 40, 28);
		posPane.add(qttField);
		qttField.setColumns(10);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		mnFile.add(mntmExit);

		JMenu mnInventory = new JMenu("Inventory");
		menuBar.add(mnInventory);
		
		JMenu mnStockAdjustments = new JMenu("Stock Adjustments");
		mnInventory.add(mnStockAdjustments);
				
						JMenuItem mntmAdjustStock = new JMenuItem("Adjust Stock");
						mnStockAdjustments.add(mntmAdjustStock);
						mntmAdjustStock.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								adjustStock dialog = new adjustStock(inv);
								dialog.setVisible(true);
							}
						});
		
				JMenuItem mntmReceiveStock = new JMenuItem("Receive Stock");
				mnStockAdjustments.add(mntmReceiveStock);
				
				JMenuItem mntmSuppliers = new JMenuItem("Suppliers");
				mntmSuppliers.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new supplierDialog().setVisible(true);
					}
				});
				mnInventory.add(mntmSuppliers);
				mntmReceiveStock.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						receiveStock dialog = new receiveStock(inv);
						dialog.setVisible(true);
					}
				});

		JMenu mnTransaction = new JMenu("Transaction");
		menuBar.add(mnTransaction);

		JMenuItem mntmConfirmPurchase = new JMenuItem("Confirm Purchase");
		mntmConfirmPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmPurchase dialog = new confirmPurchase(trans, translist,
						inv, transTPanel, priceLabel);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				trans = new srType.transaction(session);
			}
		});
		mntmConfirmPurchase.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F3, 0));
		mnTransaction.add(mntmConfirmPurchase);

		JMenuItem mntmCancelPurchase = new JMenuItem("Cancel Purchase");
		mntmCancelPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trans = new srType.transaction(session);
				transTPanel.setText("");
				priceLabel.setText("RM0.00");
			}
		});
		mntmCancelPurchase.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F7, 0));
		mnTransaction.add(mntmCancelPurchase);
		
		JMenu mnReports = new JMenu("Reports");
		menuBar.add(mnReports);
		
		JMenu mnInventory_1 = new JMenu("Inventory");
		mnReports.add(mnInventory_1);
		
		JMenuItem mntmInventoryReport = new JMenuItem("Inventory Report");
		mnInventory_1.add(mntmInventoryReport);
		
		JMenuItem mntmBarcodeGenerator = new JMenuItem("Barcode Generator");
		mntmBarcodeGenerator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				barcodeGenerator.generate(inv);
			}
		});
		mnInventory_1.add(mntmBarcodeGenerator);
		mntmInventoryReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new inventoryReportDialog().setVisible(true);
			}
		});
		
		JMenu mnTransactions = new JMenu("Transactions");
		mnReports.add(mnTransactions);
		
		JMenuItem mntmTransactionReport = new JMenuItem("Transaction Report");
		mnTransactions.add(mntmTransactionReport);
		
		JMenuItem mntmTransactionViewer = new JMenuItem("Transaction Viewer");
		mntmTransactionViewer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new transactionViewer(inv).setVisible(true);
			}
		});
		mnTransactions.add(mntmTransactionViewer);
		mntmTransactionReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new transactionReportDialog().setVisible(true);
			}
		});
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new aboutDialog().setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);

		// ---------------

	}
}
