package com.nareshkumarrao.shopkeepr.utils;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nareshkumarrao.shopkeepr.confirmationDialogs.alert;

public class srType {
	public static class inventory {
		public final List<String> invID = new ArrayList<String>();
		final List<String> invName = new ArrayList<String>();
		final List<Integer> invQuantity = new ArrayList<Integer>();
		final List<BigDecimal> invPrice = new ArrayList<BigDecimal>();
		
		public boolean exists(String id)
		{
			if(invID.indexOf(id)<0)
			{
				return false;
			}
			return true;
		}
		
		public void subtractQuantity(int quantity, String id) {
			int itemIndex = invID.indexOf(id);
			invQuantity.set(itemIndex, (invQuantity.get(itemIndex)-quantity));
		}
		
		public void addQuantity(int quantity, String id)
		{
			int itemIndex = invID.indexOf(id);
			invQuantity.set(itemIndex, (invQuantity.get(itemIndex)+quantity));
		}

		public void addItem(String id, String name, int quantity, BigDecimal price) {
			invID.add(id);
			invName.add(name);
			invQuantity.add(quantity);
			invPrice.add(price);
		}

		public void removeItem(String id) {
			int itemIndex = invID.indexOf(id);

			invID.remove(itemIndex);
			invName.remove(itemIndex);
			invQuantity.remove(itemIndex);
			invPrice.remove(itemIndex);
		}

		public void renameItem(String id, String newName) {
			int itemIndex = invID.indexOf(id);

			invName.set(itemIndex, newName);

		}

		public void setPrice(String id, BigDecimal price) {
			int itemIndex = invID.indexOf(id);

			invPrice.set(itemIndex, price);
		}

		public void modify(String id, String name, int quantity, BigDecimal price) {
			int itemIndex = invID.indexOf(id);
			invName.set(itemIndex, name);
			invQuantity.set(itemIndex, quantity);
			invPrice.set(itemIndex, price);
		}

		public BigDecimal getPrice(String id) {
			int itemIndex = invID.indexOf(id);
			return invPrice.get(itemIndex);
		}

		public int getQuantity(String id) {
			int itemIndex = invID.indexOf(id);
			return invQuantity.get(itemIndex);
		}

		public String getName(String id) {
			int itemIndex = invID.indexOf(id);
			return invName.get(itemIndex);
		}
	}

	public static class transaction {
		final List<String> items = new ArrayList<String>();
		final List<Integer> quantity = new ArrayList<Integer>();
		long time = 0;
		public BigDecimal totalPrice = new BigDecimal(0);
		String session;
		
		public transaction(String ses)
		{
			session = ses;
		}
		
		public boolean addItems(String id, int qtt, inventory inv) {
			
			if (items.indexOf(id) > -1) {

				int itemIndex = items.indexOf(id);
				if ((inv.getQuantity(id) - (quantity.get(itemIndex) + qtt)) < 0) {
					new alert("No more of this item left").setVisible(true);
					return false;
				}
				quantity.set(itemIndex, (quantity.get(itemIndex) + qtt));
			} else {
				if ((inv.getQuantity(id) - qtt) < 0) {
					new alert("No more of this item left").setVisible(true);
					return false;
				}
				items.add(id);
				quantity.add(qtt);
			}

			totalPrice = totalPrice.add(inv.getPrice(id).multiply(BigDecimal.valueOf(qtt)));

			return true;
		}

		public int getQuantity(String id) {
			if (items.indexOf(id) > -1) {
				return quantity.get(items.indexOf(id));
			}
			return 0;
		}

		public inventory confirmPurchase(inventory inv) {

			for (int i = 0; i < items.size(); i++) {
				String item = items.get(i);
				int qtt = quantity.get(i);
				inv.subtractQuantity(qtt, item);
			}
			time = new Date().getTime();
			return inv;

		}

		public String generateTransactionText(inventory inv) {
			String toSend = new String();

			for (int i = 0; i < items.size(); i++) {
				String id = items.get(i);
				toSend = toSend + inv.getName(id) + "\t" + getQuantity(id)
						+ " units\tRM" + inv.getPrice(id) + "\n";
			}

			return toSend;
		}
	}

	public static class transactionList {
		public final List<transaction> transList = new ArrayList<transaction>();

		public void addTrans(transaction trans) {
			transList.add(trans);
		}
		
		public BigDecimal getFullPrice(String session)
		{
			BigDecimal fullPrice = new BigDecimal(0);
			for(int i = 0; i < transList.size(); i++)
			{
				if(transList.get(i).session.matches(session))
				{
					BigDecimal thisPrice = transList.get(i).totalPrice;
					fullPrice = fullPrice.add(thisPrice);
				}
			}
			return fullPrice;
			
		}
		
		public transaction getTrans(long time)
		{
			for(int i = 0; i < transList.size(); i++)
			{
				if(transList.get(i).time == time)
				{
					return transList.get(i);
				}
			}
			return null;
		}
	}
	
	public static class transactionReport {
		public List<String> dates = new ArrayList<String>();
		List<transactionList> listoftrans = new ArrayList<transactionList>();
		
		public void addTransList(transactionList transList){
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime());
			
			if(dates.indexOf(date)>-1)
			{
				listoftrans.set(dates.indexOf(date), transList);
			}else{
				dates.add(date);
				listoftrans.add(transList);
			}
			
			loaders.saveTransactionReport(this);
		}
		
		public boolean transListExists(String date)
		{
			if(dates.indexOf(date)>-1)
			{
				return true;
			}
			return false;
		}
		
		public transactionList getTransList(String date)
		{
			int itemIndex = dates.indexOf(date);
			return listoftrans.get(itemIndex);
		}
	}
	
	public static class inventoryReport {
		public List<String> dates = new ArrayList<String>();
		List<inventory> inventories = new ArrayList<inventory>();
		
		public void addInvToLog(inventory inv)
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			String date = dateFormat.format(cal.getTime());
			
			if(dates.indexOf(date)>-1)
			{
				inventories.set(dates.indexOf(date), inv);
			}else{
				dates.add(date);
				inventories.add(inv);
			}
			
			loaders.saveInventoryReport(this);
		}
		
		public boolean inventoryExists(String date)
		{
			int itemIndex = dates.indexOf(date);
			if(itemIndex>-1)
			{
				return true;
			}
			return false;
		}
		
		public inventory getInventory(String date)
		{
			int itemIndex = dates.indexOf(date);
			return inventories.get(itemIndex);
		}
	}

}
