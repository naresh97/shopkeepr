package com.nareshkumarrao.shopkeepr;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import com.nareshkumarrao.shopkeepr.utils.loaders;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel loginPane;
	private String[] sessions;

	public login() {
		sessions = loaders.loadCFG();
		
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 287, 145);
		loginPane = new JPanel();
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(loginPane);
		loginPane.setLayout(null);
		
		JLabel lblSession = new JLabel("Session:");
		lblSession.setBounds(6, 31, 61, 16);
		loginPane.add(lblSession);

		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(sessions));
		comboBox.setBounds(79, 27, 185, 27);
		loginPane.add(comboBox);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				mainPOS mpos = new mainPOS((String) comboBox.getSelectedItem());
				mpos.setVisible(true);
				
				setVisible(false);
				dispose();
			}
		});
		btnLogin.setBounds(22, 73, 242, 29);
		loginPane.add(btnLogin);
	}
}
