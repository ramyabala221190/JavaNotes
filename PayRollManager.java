package Pay;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PayRollManager {
	
	Map<Integer,Map<Integer,EmployeeVO>> map1=new HashMap<Integer,Map<Integer,EmployeeVO>>();
	Map<Integer,String>mp=new HashMap<Integer,String>();
	Map<Integer,String>mp1=new HashMap<Integer,String>();
	Map<Integer,EmployeeVO> evmap1=new HashMap<Integer,EmployeeVO>();
	
	public Map getEmployeeSalaryDetails(String filePath1,String filePath2) throws IOException, ParseException
	{
	String str=null;
	String str1=null;
	
	int i=0,j=0;
	int k=0,m=0;
		BufferedReader bf=new BufferedReader(new FileReader(filePath2));
		
		BufferedReader bf1=new BufferedReader(new FileReader(filePath1));
		
		while((str=bf.readLine()) !=null)
		{
		mp.put(i, str);
		i++;
		}
		
		while((str1=bf1.readLine()) !=null)
		{
		mp1.put(k, str1);
		k++;
		}
		
		String[] strarr=new String[mp.size()];
		String[] strarr1=new String[mp1.size()];
		for(Map.Entry<Integer, String> entry:mp.entrySet())
		{
			strarr[j]=entry.getValue();
			j++;
		}
		
		for(Map.Entry<Integer, String> entry:mp1.entrySet())
		{
			strarr1[m]=entry.getValue();
			m++;
		}
		
		evmap1=ValidateEmployees(strarr,strarr1);
		
		map1.put(1, evmap1);
		return map1;
	}
	
	public Map ValidateEmployees(String[]strarr,String[]strarr1) throws ParseException
	{
		Map<Integer,EmployeeVO> evmap=new HashMap<Integer,EmployeeVO>();
		
		Set<String>Jset=new HashSet<String>();
		Set<String>Fset=new HashSet<String>();
		Set<String>Mset=new HashSet<String>();
		List<String>invmap=new ArrayList<String>();
		
		String[]strarr2=null;
		
		String payslip[]=null;
		
		

	for(String fields:strarr)
	{
		strarr2=fields.split(",");
		payslip=strarr2[0].split("/");
		
		EmployeeVO ev1=new EmployeeVO();
		
		Date d1=new SimpleDateFormat("dd-MM-yyyy").parse(payslip[1]);
		
		if((d1.getMonth()+1) ==1)
		{
			if(Jset.add(payslip[0]) ==true)
			{
				ev1=EmployeeDetails(strarr2,payslip[0],strarr1);
				evmap.put(Integer.parseInt(payslip[0]), ev1);
				
			}
			else
			{
				invmap.add(payslip[0]);	
			}
			if(strarr2.length >3)
			{
if(new SimpleDateFormat("dd-MM-yyyy").parse(payslip[1]).after(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[3])))
				{
				invmap.add(payslip[0]);
				}
			}
		}
		
		if((d1.getMonth()+1) ==2)
		{
			if(Fset.add(payslip[0])==true)
			{
				
				ev1=EmployeeDetails(strarr2,payslip[0],strarr1);	
				evmap.put(Integer.parseInt(payslip[0]), ev1);
			}
			else
			{
				invmap.add(payslip[0]);	
			}
			if(strarr2.length >3)
			{
if(new SimpleDateFormat("dd-MM-yyyy").parse(payslip[1]).after(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[3])))
				{
				invmap.add(payslip[0]);
				}
			}
		}
		
		if((d1.getMonth()+1) ==3)
		{
			if(Mset.add(payslip[0])==true)
			{
				ev1=EmployeeDetails(strarr2,payslip[0],strarr1);	
				evmap.put(Integer.parseInt(payslip[0]), ev1);
			}
			else
			{
				invmap.add(payslip[0]);	
			}
			if(strarr2.length >3)
			{
if(new SimpleDateFormat("dd-MM-yyyy").parse(payslip[1]).after(new SimpleDateFormat("dd-MM-yyyy").parse(strarr2[3])))
				{
				invmap.add(payslip[0]);
				}
			}
		}
		
		
	}
	
	Iterator<Integer> iterator = evmap.keySet().iterator(); 
	
	
		while(iterator.hasNext()){
	
	//for(Map.Entry<Integer,EmployeeVO> entry:evmap.entrySet())
	
		//int key=entry.getKey();
	int key = iterator.next();
			for(String strrr:invmap)
			{
			
			if(key==Integer.parseInt(strrr))
			{
				if(evmap.containsKey(key))
				{
				System.out.println(key);
				
			iterator.remove();
				}
			} 
			}

	}
	

return evmap;	
	}
	
	
	
	public EmployeeVO EmployeeDetails(String[]strarr,String empid,String[]strarr1) throws ParseException
	{
		
		Map<String,Integer> salmap=new HashMap<String,Integer>();
		EmployeeVO ev=null;
		String[]strarr3=null;
		for(String fields2:strarr1)
		{
			strarr3=fields2.split(",");	
			 
			if(strarr3[0].equals(empid))
			{
		
				salmap.put(strarr[1], Integer.parseInt(empid));
					ev=new EmployeeVO();
					ev.setDepartmentCode(strarr3[2].charAt(0));
					ev.setEmailID(strarr3[5]);
					ev.setEmployeeID(Integer.parseInt(strarr3[0]));
					ev.setEmployeeName(strarr3[1]);
					ev.setJoiningDate(new SimpleDateFormat("dd-MM-yyyy").parse(strarr3[3]));
					//ev.setMobileNumber(Integer.p(strarr3[4]));
					ev.setNumberOfLeave(Integer.parseInt(strarr[2]));
					
					if(strarr.length==4)
					{
					ev.setResignationDate(new SimpleDateFormat("dd-MM-yyyy").parse(strarr[3]));
					}
					
					ev.setSalaryDetails(salmap);
					
					
					
			}
			
		}	
		
		return ev;
	}
	

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		String filePath1="src/Pay/EmployeeMaster.txt";
		String filePath2="src/Pay/EmployeePayRoll.txt";
		
		Map<Integer,Map<Integer,EmployeeVO>> map2=new HashMap<Integer,Map<Integer,EmployeeVO>>();
		
		PayRollManager prm=new PayRollManager();
		
		map2=prm.getEmployeeSalaryDetails(filePath1, filePath2);
		
		for(Map.Entry<Integer,Map<Integer,EmployeeVO>>entry:map2.entrySet())
		{
			System.out.println(entry.getValue());
		}

	}

}
