package com.nareshkumarrao.shopkeepr.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.nareshkumarrao.shopkeepr.confirmationDialogs.alert;
import com.nareshkumarrao.shopkeepr.utils.srType.inventory;
import com.nareshkumarrao.shopkeepr.utils.srType.inventoryReport;

public class inventoryReportCreator {

	public static void createReport(inventoryReport invRep, String startDate, String endDate)
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		
		String html = "<html>" +
				"<head>" +
				"<style type='text/css'>" +
				"body{font-family:sans-serif}" +
				"</style>" +
				"</head><body>" +
				"Inventory Report: Generated On - " + date + "<br/>" +
				"Report Date: [<i>"+startDate+"</i>]" + " to [<i>"+endDate+"</i>]<br/><br/>" +
				"<table border=1><tr>" +
				"<td><b>Item ID</b></td>" +
				"<td><b>Item Name</b></td>" +
				"<td><b>Start Quantity</b></td>" +
				"<td><b>Quantity Movement</b></td>" +
				"<td><b>End Quantity</b></td>" +
				"</tr>";
		
		List<Integer> inventoryMovement = new ArrayList<Integer>();
		
		if(invRep.inventoryExists(startDate) && invRep.inventoryExists(endDate))
		{
			new alert("One of the dates, do not have an inventory report").setVisible(true);
			return;
		}
		
		inventory startInventory = invRep.getInventory(startDate);
		inventory endInventory  = invRep.getInventory(endDate);
		
		for(int i = 0; i<startInventory.invID.size(); i++)
		{
			inventoryMovement.add(	(startInventory.invQuantity.get(i)-endInventory.invQuantity.get(i))	);
		}
		
		for(int i = 0; i<endInventory.invID.size(); i++)
		{
			String toAdd = "<tr>" +
					"<td>" + endInventory.invID.get(i) + "</td>" +
					"<td>" + endInventory.invName.get(i) + "</td>" +
					"<td>" + startInventory.invQuantity.get(i) + "</td>" +
					"<td>" + inventoryMovement.get(i) + "</td>" +
					"<td>" + endInventory.invQuantity.get(i) + "</td>" +
					"</tr>";
			
			html = html+toAdd;
		}
		
		html = html + "</table></body></html>";
		
		new File("generated_reports").mkdirs();
		String filename = "generated_reports" + File.separator + "temp_report.html";
		try {
			PrintWriter writer = new PrintWriter(filename);
			writer.println(html);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			Desktop.getDesktop().open(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
