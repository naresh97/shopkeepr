package com.nareshkumarrao.shopkeepr;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class aboutDialog extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public aboutDialog() {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("About Shopkeepr");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 211, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblShopkeeprV = new JLabel("Shopkeepr V1.4");
		lblShopkeeprV.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblShopkeeprV.setBounds(10, 11, 110, 19);
		contentPane.add(lblShopkeeprV);
		
		JLabel lblCopyrightNareshkumarRao = new JLabel("Copyright Nareshkumar Rao 2013");
		lblCopyrightNareshkumarRao.setBounds(10, 41, 168, 14);
		contentPane.add(lblCopyrightNareshkumarRao);
		
		JLabel lblEmailInareshonlinegmailcom = new JLabel("email: inaresh.online@gmail.com");
		lblEmailInareshonlinegmailcom.setBounds(10, 66, 168, 14);
		contentPane.add(lblEmailInareshonlinegmailcom);
		
		JLabel lblPhone = new JLabel("phone: +60-124492644");
		lblPhone.setBounds(10, 91, 168, 14);
		contentPane.add(lblPhone);
	}
}
