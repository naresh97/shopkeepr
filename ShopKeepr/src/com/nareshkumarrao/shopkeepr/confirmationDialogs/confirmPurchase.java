package com.nareshkumarrao.shopkeepr.confirmationDialogs;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import com.nareshkumarrao.shopkeepr.utils.loaders;
import com.nareshkumarrao.shopkeepr.utils.srType;
import com.nareshkumarrao.shopkeepr.utils.srType.inventoryReport;
import com.nareshkumarrao.shopkeepr.utils.srType.transactionReport;

import java.awt.Font;


public class confirmPurchase extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public confirmPurchase(final srType.transaction trans, final srType.transactionList translist, final srType.inventory inv, final JTextPane transTPanel, final JLabel priceLabel) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Confirm Purchase");
		setBounds(100, 100, 325, 114);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblConfirmPurchaseOf = new JLabel("Confirm purchase of:");
			lblConfirmPurchaseOf.setBounds(6, 24, 133, 16);
			contentPanel.add(lblConfirmPurchaseOf);
		}
		{
			JLabel lblRm = new JLabel("RM"+trans.totalPrice);
			lblRm.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
			lblRm.setBounds(151, 6, 293, 42);
			contentPanel.add(lblRm);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						trans.confirmPurchase(inv);
						loaders.saveInventory(inv);
						
						//Save to Report
						inventoryReport invRep = loaders.loadInventoryReport();
						invRep.addInvToLog(inv);
						loaders.saveInventoryReport(invRep);
						
						translist.addTrans(trans);
						
						transactionReport transRep = loaders.loadTransactionReport();
						transRep.addTransList(translist);
						loaders.saveTransactionReport(transRep);
						
						transTPanel.setText("");
						priceLabel.setText("RM0.00");
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				buttonPane.requestFocus();
			}

			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
