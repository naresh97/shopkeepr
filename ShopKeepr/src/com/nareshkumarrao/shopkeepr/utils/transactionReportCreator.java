package com.nareshkumarrao.shopkeepr.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.nareshkumarrao.shopkeepr.utils.srType.transactionList;
import com.nareshkumarrao.shopkeepr.utils.srType.transactionReport;

public class transactionReportCreator {
	public static void createReport(transactionReport transRep, String startDate, String session)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		
		String html = "<html>" +
				"<head>" +
				"</head><body>" +
				"Transaction Report - Session '"+session+"': Generated On - " + date + "<br/>" +
				"Report Date: [<i>"+startDate+"</i>]" +
				"<table border=1><tr>" +
				"<td><b>No.</b></td>" +
				"<td><b>Transaction ID</b></td>" +
				"<td><b>Transaction Time</b></td>" +
				"<td><b>Price<b/></td>" +
				"</tr>";
		
		transactionList transList = transRep.getTransList(startDate);
		
		for(int i=0; i<transList.transList.size(); i++)
		{
			
			if(!transList.transList.get(i).session.matches(session))
			{
				break;
			}
			
			Date time = new Date(transList.transList.get(i).time);
			DateFormat timeFormat = new SimpleDateFormat("HH:mm");

			String toAdd="<tr>" +
					"<td>"+(i+1)+"</td>" +
					"<td>"+transList.transList.get(i).time+"</td>" +
					"<td>"+timeFormat.format(time)+"</td>"+
					"<td>"+transList.transList.get(i).totalPrice+"</td>"+
					"</tr>";
			
			html = html+toAdd;
		}
		
		html = html+"<tr><td></td><td></td><td><b>TOTAL</b></td><td><b>"+transList.getFullPrice(session)+"</b></td></tr>" +
				"</table></body></html>";
		
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
