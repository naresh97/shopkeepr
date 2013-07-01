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
				".barcode{font-family:'Free 3 of 9'; src:url('FREE3OF9.ttf'); font-size:18;}" +
				"body{font-family:sans-serif;font-size:8;}" +
				"</style>" +
				"</head>" +
				"<body>";
		
		int val = 42;
		
		BigDecimal numberOfTables = new BigDecimal(inv.invID.size());
		numberOfTables = numberOfTables.divide(new BigDecimal(val), 4, RoundingMode.HALF_UP);
		numberOfTables = numberOfTables.setScale(0, RoundingMode.CEILING);
		
		for(int i = 0; i < numberOfTables.intValueExact(); i++)
		{
			String add = "<table>";
			int c=0;
			for(int i1 = ( (i)*val ); i1 < ( (i+1)*val ); i1++)
			{
				c++;
				if(i1>=inv.invID.size())
				{
					continue;
				}
				
				if(c==1)
				{
					add = add+"<tr><td>"+
							inv.invName.get(i1)+"<br/>"+
							"<span class='barcode'>*"+inv.invID.get(i1)+"*</span><br/>"+
							
							"RM"+inv.invPrice.get(i1)+"<br/>"+
							"</td>";
				}else if(c==2)
				{
					add = add+"<td>"+
							inv.invName.get(i1)+"<br/>"+
							"<span class='barcode'>*"+inv.invID.get(i1)+"*</span><br/>"+
							
							"RM"+inv.invPrice.get(i1)+"<br/>"+
							"</td>";
				}else if(c==3)
				{
					add = add+"<td>"+
							inv.invName.get(i1)+"<br/>"+
							"<span class='barcode'>*"+inv.invID.get(i)+"*</span><br/>"+
							
							"RM"+inv.invPrice.get(i1)+"<br/>"+
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
