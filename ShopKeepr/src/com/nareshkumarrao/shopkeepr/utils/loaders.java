package com.nareshkumarrao.shopkeepr.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.nareshkumarrao.shopkeepr.utils.srType.inventory;
import com.nareshkumarrao.shopkeepr.utils.srType.inventoryReport;
import com.nareshkumarrao.shopkeepr.utils.srType.transactionReport;

public class loaders {
	public static String loadLicense()
	{
		try {
			String filename = "shopkeepr_license";
			File cfgFile = new File(filename);
			if (cfgFile.isFile()) {

				BufferedReader br = new BufferedReader(new FileReader(
						filename));

				String str_line;
				String fullString="";
				while ((str_line = br.readLine()) != null) {
					if ((str_line.length() != 0)) {
						fullString=fullString+str_line;
					}
				}
				br.close();
				return fullString;

			} else {
				return "FALSE";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "FALSE";
		} catch (IOException e) {
			e.printStackTrace();
			return "FALSE";
		}
	}
	public static String[] loadCFG() {
		String[] sessions = null;
		List<String> sessionsList = new ArrayList<String>();
		try {

			File cfgFile = new File("shopkeepr.ini");
			if (cfgFile.isFile()) {

				BufferedReader br = new BufferedReader(new FileReader(
						"shopkeepr.ini"));

				String str_line;
				while ((str_line = br.readLine()) != null) {
					if ((str_line.length() != 0)) {
						sessionsList.add(str_line);
					}
				}
				sessions = (String[]) sessionsList
						.toArray(new String[sessionsList.size()]);
				br.close();

			} else {
				PrintWriter writer = new PrintWriter("shopkeepr.ini");
				writer.println("Cashier 1");
				writer.println("Cashier 2");
				writer.println("Cashier 3");
				writer.close();
				sessions = new String[] { "Cashier 1", "Cashier 2", "Cashier 3" };
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sessions;
	}

	public static void saveInventory(inventory inv) {
		Gson gson = new Gson();
		String filename = "data" + File.separator + "inventory.json";
		String toWrite = gson.toJson(inv);
		try {
			PrintWriter writer = new PrintWriter(filename);
			writer.println(toWrite);
			writer.close();
		} catch (FileNotFoundException e) {

		}
	}

	public static inventory loadInventory() {
		Gson gson = new Gson();
		String filename = "data" + File.separator + "inventory.json";
		File invFile = new File(filename);
		inventory toReturn = new inventory();
		try {
			if (invFile.isFile()) {
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String totalStr = "";
				String str_line;

				while ((str_line = br.readLine()) != null) {
					if (str_line.length() != 0) {
						totalStr = str_line;
					}
				}
				br.close();
				toReturn = gson.fromJson(totalStr, inventory.class);
			} else {
				new File("data").mkdirs();
				String toWrite = gson.toJson(toReturn);
				PrintWriter writer = new PrintWriter(filename);
				writer.println(toWrite);
				writer.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toReturn;
	}

	public static void saveInventoryReport(inventoryReport invRep) {
		Gson gson = new Gson();
		String toWrite = gson.toJson(invRep);
		String filename = "reports" + File.separator + "inventory_reports.json";
		try {
			PrintWriter writer = new PrintWriter(filename);
			writer.println(toWrite);
			writer.close();
		} catch (FileNotFoundException e) {

		}
	}
	
	public static inventoryReport loadInventoryReport()
	{
		Gson gson = new Gson();
		String filename = "reports" + File.separator + "inventory_reports.json";
		File invFile = new File(filename);
		inventoryReport toReturn = new inventoryReport();
		
		try {
			if (invFile.isFile()) {
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String totalStr = "";
				String str_line;

				while ((str_line = br.readLine()) != null) {
					if (str_line.length() != 0) {
						totalStr = str_line;
					}
				}
				br.close();
				toReturn = gson.fromJson(totalStr, inventoryReport.class);
			} else {
				new File("reports").mkdirs();
				String toWrite = gson.toJson(toReturn);
				PrintWriter writer = new PrintWriter(filename);
				writer.println(toWrite);
				writer.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return toReturn;
	}
	
	public static void saveTransactionReport(transactionReport transRep)
	{
		Gson gson = new Gson();
		String toWrite = gson.toJson(transRep);
		String filename = "reports" + File.separator + "transaction_reports.json";
		try {
			PrintWriter writer = new PrintWriter(filename);
			writer.println(toWrite);
			writer.close();
		} catch (FileNotFoundException e) {

		}
	}
	
	public static transactionReport loadTransactionReport()
	{
		Gson gson = new Gson();
		String filename = "reports" + File.separator + "transaction_reports.json";
		File invFile = new File(filename);
		transactionReport toReturn = new transactionReport();
		
		try {
			if (invFile.isFile()) {
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String totalStr = "";
				String str_line;

				while ((str_line = br.readLine()) != null) {
					if (str_line.length() != 0) {
						totalStr = str_line;
					}
				}
				br.close();
				toReturn = gson.fromJson(totalStr, transactionReport.class);
			} else {
				new File("reports").mkdirs();
				String toWrite = gson.toJson(toReturn);
				PrintWriter writer = new PrintWriter(filename);
				writer.println(toWrite);
				writer.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toReturn;
	}
}
