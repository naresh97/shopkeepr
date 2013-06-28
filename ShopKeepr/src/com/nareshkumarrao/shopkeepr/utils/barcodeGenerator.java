package com.nareshkumarrao.shopkeepr.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.nareshkumarrao.shopkeepr.utils.srType.inventory;

public class barcodeGenerator {

	public static void generate(inventory inv)
	{
		String html  = "<html>" +
				"<head>" +
				"<style type='text/css'>" +
				"table{page-break-after:always;width:755px;}" +
				".barcode{padding:10px;font-family:'Free 3 of 9'; src:url('FREE3OF9.ttf'); font-size:32;}" +
				"body{font-family:sans-serif;font-size:12;}" +
				"</style>" +
				"</head>" +
				"<body>";
		
		BigDecimal numberOfTables = new BigDecimal(inv.invID.size());
		numberOfTables = numberOfTables.divide(new BigDecimal(30));
		numberOfTables = numberOfTables.setScale(0, RoundingMode.CEILING);
		
		for(int i = 0; i < numberOfTables.intValueExact(); i++)
		{
			String add = "<table>";
			int c=0;
			for(int i1 = 0; i1 < inv.invID.size(); i1++)
			{
				c++;
				if(c==1)
				{
					add = add+"<tr><td>"+
							"<span class='barcode'>*"+inv.invID.get(i)+"*</span><br/>"+
							inv.invName.get(i)+"<br/>"+
							"RM"+inv.invPrice.get(i)+"<br/>"+
							"</td>";
				}else if(c==2)
				{
					add = add+"<td>"+
							"<span class='barcode'>*"+inv.invID.get(i)+"*</span><br/>"+
							inv.invName.get(i)+"<br/>"+
							"RM"+inv.invPrice.get(i)+"<br/>"+
							"</td>";
				}else if(c==3)
				{
					add = add+"<td>"+
							"<span class='barcode'>*"+inv.invID.get(i)+"*</span><br/>"+
							inv.invName.get(i)+"<br/>"+
							"RM"+inv.invPrice.get(i)+"<br/>"+
							"</td></tr>";
					c=0;
				}
			}
			add = add+"</table>";
			html = html+add;
			
		}
		
		html = html+"</body></html>";
		
		loaders.saveGeneratedReport(html);
	}

}
