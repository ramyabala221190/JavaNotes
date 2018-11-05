package Travel;

import java.io.BufferedReader;
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
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TrainServiceManager {

	Map<Integer,String> map=new HashMap<Integer,String>();
	/* Return the list of trains for the given parameter */
	public List<TrainDetailsVO> getTrainDetails(final String filePath, int source,
			int destination, String dateOfTravel)
			throws TrainServiceException, IOException, ParseException {
		SimpleDateFormat sdf3=new SimpleDateFormat("dd-MM-yyyy");
		Date d3=sdf3.parse(dateOfTravel);
		int day=d3.getDay();
		System.out.println(day);
	String str=null;
	String[] strarr2=null;
	
	List<TrainDetailsVO> lst=new ArrayList<TrainDetailsVO>();
	int i=0;
	int j=0;
	
		if(!(source >=1 && source <=20) || !(destination >=1 && destination <=20))
		{
		throw new  TrainServiceException("Source and Destination can range from 01 to 20 only");		
		}
		else if (!checkDate(dateOfTravel) || !validateDate(dateOfTravel))
		{
			throw new  TrainServiceException("Date of travel should be greater than current date");
		}
		else
		{
			
		
		BufferedReader bf=new BufferedReader(new FileReader(filePath));
		
		while((str=bf.readLine()) !=null)
		{
			map.put(i, str);
			i++;
		}
		String[] strarr=new String[map.size()];
		
		for(Map.Entry<Integer,String> entry:map.entrySet())
		{
			strarr[j]=entry.getValue();
			
			j++;
		}
		
		if(ValidateInput(strarr))
		{
				
			for(String fields:strarr)
			{
				TrainDetailsVO tdv=new TrainDetailsVO();
				strarr2=fields.split("\\|");
				
				if(Integer.parseInt(strarr2[2])==source && Integer.parseInt(strarr2[3])==destination)
				{
					char c=strarr2[4].charAt(0);
					System.out.println(c);
					
					if(strarr2[4].equals("N"))
					{
						System.out.println("special length"+strarr2[4].length());
						if(day >=1 && day <=6)
						{
							
							tdv.setDateOfTravel(d3);
							tdv.setDestination(Integer.parseInt(strarr2[3]));
							tdv.setRoute(strarr2[1]);
							tdv.setSource(Integer.parseInt(strarr2[2]));
							tdv.setSpecial(c);
							tdv.setTrainNumber(strarr2[0]);
							lst.add(tdv);	
						}
					}
					
					else if(strarr2[4].equals("Y"))
					{
						
						
						if(day >=0 && day <=6)
						{
							
							tdv.setDateOfTravel(d3);
							tdv.setDestination(Integer.parseInt(strarr2[3]));
							tdv.setRoute(strarr2[1]);
							tdv.setSource(Integer.parseInt(strarr2[2]));
							tdv.setSpecial(c);
							tdv.setTrainNumber(strarr2[0]);
							lst.add(tdv);	
						}
					}
				}
				
			}
			
			
		}
		return lst;
	//Write the code here
		}
		
	}
	
	public boolean ValidateInput(String[] input) throws TrainServiceException
	{
	String[] strarr1=null;
	boolean result=false;
	
	for(String fields:input)
	{
		strarr1=fields.split("\\|");
		
		if(strarr1[0].length() !=5 || !strarr1[0].matches("\\d+"))
		{
			throw new TrainServiceException("Train Number cannot exceed 5 digits");
		}
		else if(!strarr1[1].equals("TR1") && !strarr1[1].equals("TR2") )
		{
			throw new TrainServiceException("Route No can be TR1 or TR2");
		}
		
		else if(strarr1[1].equals("TR1"))
		{
		if(Integer.parseInt(strarr1[2]) < 1 || Integer.parseInt(strarr1[2]) >10)
		{
			throw new TrainServiceException("For TR1, Source/Destination can be only from 01 to 10");
		}	
		}
		
		else if(strarr1[1].equals("TR2"))
		{
		if(Integer.parseInt(strarr1[2]) < 11 || Integer.parseInt(strarr1[2]) >20)
		{
			throw new TrainServiceException("For TR2, Source/Destination can be only from 11 to 20");
		}	
		}
		
		else if(!strarr1[4].equals("Y") && !strarr1[4].equals("N"))
		{
			throw new TrainServiceException("Special Trains is denoted as Y and Others as N");
		}
	
	}
		
	result=true;	
		return result;
	}
	
	public boolean validateDate(String date)
	{
	SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	boolean status=true;
	sdf.setLenient(false);
	try {
		sdf.parse(date);
	} catch (ParseException e) {
		status=false;
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	return status;	
	}
	
	public boolean checkDate(String date) throws ParseException
	{
		boolean res=false;
		SimpleDateFormat sdf1=new SimpleDateFormat("dd-MM-yyyy");
		Date d1=new Date();
		
		Date d2=sdf1.parse(date);
		
		if(d2.after(d1))
		{
			res=true;
		}
		
		
	return res;
	}
	
	 /* Return the special trains*/
	public Map getTrainSchedule(String filePath) throws TrainServiceException, IOException {
		
		TreeSet<String> st=new TreeSet<String>();
		Map<Integer,TreeSet<String>>mp=new HashMap<Integer,TreeSet<String>>();
		
BufferedReader bf=new BufferedReader(new FileReader(filePath));
String str=null;
int i=0;
int j=0;
String[] strarr2=null;
		
		while((str=bf.readLine()) !=null)
		{
			map.put(i, str);
			i++;
		}
		String[] strarr=new String[map.size()];
		
		for(Map.Entry<Integer,String> entry:map.entrySet())
		{
			strarr[j]=entry.getValue();
			
			j++;
		}

		for(String fields:strarr)
		{
			
			strarr2=fields.split("\\|");
			
			if(strarr2[4].equals("Y"))
			{
				st.add(strarr2[0]);
			}
			
		}
		
		mp.put(1, st);
		
	//Write the code here
	return mp;
	}
	
	public static void main(String[] args) throws TrainServiceException, IOException, ParseException
	{
		String filePath="src/Travel/TrainDetails.txt";
		int source=11;
		int destination=17;
		String dateOfTravel="28-10-2018";
		List<TrainDetailsVO> lstmain=new ArrayList<TrainDetailsVO>();
		Map<Integer,TreeSet<String>>mp1=new HashMap<Integer,TreeSet<String>>();
		TrainServiceManager tsm=new TrainServiceManager();
		
		lstmain=tsm.getTrainDetails(filePath, source, destination, dateOfTravel);
		
		for(TrainDetailsVO lost:lstmain)
		{
		System.out.println(lost.getDestination());
		System.out.println(lost.getRoute());
		System.out.println(lost.getSource());
		System.out.println(lost.getSpecial());
		System.out.println(lost.getTrainNumber());
		System.out.println(lost.getDateOfTravel());
			
		}
		
		mp1=tsm.getTrainSchedule(filePath);
		
		for(Map.Entry<Integer, TreeSet<String>>entry:mp1.entrySet())
		{
			System.out.println(entry.getValue());
		}
		
		
	}


}
