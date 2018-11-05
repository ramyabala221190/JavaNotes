package BankDigital;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BankDepositAccountOrganizer {
	
	public static Map<String, List<ParentAccountVO>> processBankDepositData(
			String filePath) throws BankOrganizerException, IOException, ParseException {
		List<LinkedDepositVO> wmldlist=new ArrayList<LinkedDepositVO>();
		List<LinkedDepositVO> savldlist=new ArrayList<LinkedDepositVO>();
		List<LinkedDepositVO> nrildlist=new ArrayList<LinkedDepositVO>();
		
		List<ParentAccountVO>wmlst=new ArrayList<ParentAccountVO>();
		List<ParentAccountVO>savlst=new ArrayList<ParentAccountVO>();
		List<ParentAccountVO>nrilst=new ArrayList<ParentAccountVO>();
		
		Date d1=null;
		Date d2=null;
		
		BankDepositAccountOrganizer bao1 =new BankDepositAccountOrganizer();
		
		Map<String, List<ParentAccountVO>> map1=new HashMap<String, List<ParentAccountVO>>();
		Map<Integer,String> mp=new HashMap<Integer,String>();
		int i=0;
		int j=0;
		
		BufferedReader bf=new BufferedReader(new FileReader(filePath));
		String str=null;
		
		while((str=bf.readLine()) !=null)
		{
		mp.put(i, str);	
		i++;
		}
		
		String strarr[]=new String[mp.size()];
		
		for(Map.Entry<Integer,String> entry:mp.entrySet())
		{
			strarr[j]=entry.getValue();
			j++;
		}
		
		
		if(validateData(strarr))
		{
			String[] strarr2=null;
			
			for(String fields:strarr)
			{
				strarr2=fields.split(",");
				
				if(strarr2[2].equals("WM"))
				{
					ParentAccountVO pv1=new ParentAccountVO();
					LinkedDepositVO ld1=new LinkedDepositVO();
					
					d1=new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[5]);
					d2=new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[6]);
					
					
	
					ld1.setDepositAmount(Integer.parseInt(strarr2[4]));
					ld1.setDepositMaturityDate(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[6]));
					ld1.setDepositStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[5]));
					ld1.setLinkedDepositNo(strarr2[3].substring(3));
					ld1.setMaturityAmount(bao1.calculateMaturityAmount(d1, d2, Integer.parseInt(strarr2[4])));
					
					wmldlist.add(ld1);
					
					pv1.setParentAccNo(Integer.parseInt(strarr2[0]));
					pv1.setName(strarr2[1]);
					pv1.setAccType(strarr2[2]);
					pv1.setLinkedDeposits(wmldlist);
					
					wmlst.add(pv1);
				}
				
				else if(strarr2[2].equals("SAV"))
				{
					ParentAccountVO pv2=new ParentAccountVO();
					LinkedDepositVO ld2=new LinkedDepositVO();
					
					d1=new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[5]);
					d2=new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[6]);
					
					ld2.setDepositAmount(Integer.parseInt(strarr2[4]));
					ld2.setDepositMaturityDate(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[6]));
					ld2.setDepositStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[5]));
					ld2.setLinkedDepositNo(strarr2[3].substring(3));
					ld2.setMaturityAmount(bao1.calculateMaturityAmount(d1, d2, Integer.parseInt(strarr2[4])));
					
					savldlist.add(ld2);
					
					pv2.setParentAccNo(Integer.parseInt(strarr2[0]));
					pv2.setName(strarr2[1]);
					pv2.setAccType(strarr2[2]);
					pv2.setLinkedDeposits(savldlist);
					
					savlst.add(pv2);
				}
				
				else if(strarr2[2].equals("NRI"))
				{
					ParentAccountVO pv3=new ParentAccountVO();
					LinkedDepositVO ld3=new LinkedDepositVO();
					
					d1=new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[5]);
					d2=new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[6]);
					
					ld3.setDepositAmount(Integer.parseInt(strarr2[4]));
					ld3.setDepositMaturityDate(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[6]));
					ld3.setDepositStartDate(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[5]));
					ld3.setLinkedDepositNo(strarr2[3].substring(3));
					ld3.setMaturityAmount(bao1.calculateMaturityAmount(d1, d2, Integer.parseInt(strarr2[4])));
					
					nrildlist.add(ld3);
					
					pv3.setParentAccNo(Integer.parseInt(strarr2[0]));
					pv3.setName(strarr2[1]);
					pv3.setAccType(strarr2[2]);
					pv3.setLinkedDeposits(nrildlist);
					
					nrilst.add(pv3);
					
				}
				
			}
			
			map1.put("WM",wmlst);
			map1.put("SAV", savlst);
			map1.put("NRI",nrilst);
		}
		
		// Write your code here
		
		return map1;
	}
	
	private float calculateMaturityAmount(Date date1, Date date2,int depositamount){
		float maturity_amount=0.00f;
		float roi=0.0f;
		
		//Calendar cal=Calendar.getInstance();
		
		long milli=date2.getTime()-date1.getTime();
		
		/* 1sec=1000ms
		 * 1hr=3600sec
		 * 1 day=24hrs
		 * 
		 * 1day=24*3600sec
		 * 1 day=24*3600*1000ms
		 * 
		 */
		int factor=24*3600*1000;
		
		long days=milli/factor;
		//System.out.println(days);
		
		if(days >=0 && days <=200)
		{
		roi=6.75f/100;
		
		maturity_amount=depositamount+(depositamount*roi);
		
		}
		
		else if(days >=201 && days <=400)
		{
			roi=7.5f/100;
			
			maturity_amount=depositamount+(depositamount*roi);
			
		}
		
		else if (days >=401 && days <=600)
		{
roi=8.75f/100;
			
			maturity_amount=depositamount+(depositamount*roi);
			
		}
		else
		{
roi=10f/100;
maturity_amount=depositamount+(depositamount*roi);
				
		}
		
	// Write your code here
	return maturity_amount;	
		
	}
	
	public static boolean validateData(String[] str) throws BankOrganizerException {
		//write your code here
		boolean result=false;
		String[] strarr2=null;
		
		for(String fields:str)
		{
			strarr2=fields.split(",");
			
			
			if(!(strarr2[0].matches("\\d+"))|| strarr2[0].substring(0,1).equals("0"))
			{
				throw new BankOrganizerException("Account number should be numeric and should not begin with 0");
			}
			
			else if(strarr2.length !=7)
			{
				throw new BankOrganizerException("All fields are mandatory");
			}
			else if (!validateDate(strarr2[5]) || !validateDate(strarr2[6]))
			{
				throw new BankOrganizerException("Date should be in dd-MM-yyyy format ");
			}
			
			else if((!strarr2[2].equals("WM")&& !strarr2[2].equals("SAV") && !strarr2[2].equals("NRI"))|| !checkUpper(strarr2[2]) )
			{
				throw new BankOrganizerException("Account fields can be WM,SAV or NRI and they should be in uppser case");
			}
			
			else if((!strarr2[3].substring(0,2).equals("FD") && !strarr2[3].substring(0,2).equals("RD")
					&& !strarr2[3].substring(0,3).equals("MUT")))
			{
				System.out.println(strarr2[3].substring(0,3));
				throw new BankOrganizerException("Linked Deposit Account No can be FD,RD or MUT");
			}
			else if(strarr2[3].substring(0,2).equals("FD") || strarr2[3].substring(0,2).equals("RD"))
			{
			  if (!checkUpper(strarr2[3].substring(0,2)))
					  {
				  throw new BankOrganizerException("Linked Deposit Account No should be in upper case");
					  }
			}
			else if(strarr2[3].substring(0,3).equals("MUT"))
			{
				if (!checkUpper(strarr2[3].substring(0,3)))
				  {
			  throw new BankOrganizerException("Linked Deposit Account No should be in upper case");
				  }
			}
				
		}
		result=true;		
return result;
	}
	
	public static boolean validateDate(String dat)
	{
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	boolean status=true;
	try {
		sdf.parse(dat);
	} catch (ParseException e) {
		status=false;
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	

	return status;	
	}
	
	public static boolean checkUpper(String word)
	{
		boolean status=true;
		int i=0;
	while(i<word.length())
	{
	char c=word.charAt(i);
	if(!Character.isUpperCase(c))
	{
		status=false;
		break;
	}
		i++;
	}
	return status;
	}
	
	public static void main(String[] args) throws BankOrganizerException, IOException, ParseException
	{
		Map<String, List<ParentAccountVO>> map2=new HashMap<String, List<ParentAccountVO>>();
		String filePath="src/BankDigital/BankDetails.txt";
		
		
		BankDepositAccountOrganizer bao=new BankDepositAccountOrganizer();
		
		map2=bao.processBankDepositData(filePath);

		for(Map.Entry<String, List<ParentAccountVO>>entry:map2.entrySet())
		{
			System.out.println(entry.getValue());
		}
		
		
		
		
	
	}

}
