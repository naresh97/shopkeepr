package com.nareshkumarrao.shopkeepr;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.nareshkumarrao.shopkeepr.confirmationDialogs.alert;
import com.nareshkumarrao.shopkeepr.utils.loaders;

public class shopkeepr {

	public static void main(String[] args) {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "shopkeepr");
		try {
		    UIManager.setLookAndFeel(
		        UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException ex) {
		  System.out.println("Unable to load native look and feel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String hostname = "Unknown";

		try
		{
		    InetAddress addr;
		    addr = InetAddress.getLocalHost();
		    hostname = addr.getHostName();
		}
		catch (UnknownHostException ex)
		{
		    System.out.println("Hostname can not be resolved");
		}
		
		byte[] thedigest = null;

		try {
			byte[] bytesOfMessage = hostname.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			thedigest = md.digest(bytesOfMessage);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		  StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < thedigest.length; i++) {
	          sb.append(Integer.toString((thedigest[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        if(!sb.toString().matches(loaders.loadLicense()))
	        {
	        	new alert("License Not Found! Invalid Copy of Shopkeepr!").setVisible(true);
	        	return;
	        }
		
		login lg = new login();
		lg.setVisible(true);
	}

}
