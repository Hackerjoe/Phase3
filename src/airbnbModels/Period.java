package airbnbModels;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Period {
	public String start;
	public String end;
	public int pid;
	public Float ppn;
	public Period(String start, String end, int pid, Float ppn)
	{
		this.start = start;
		this.end = end;
		this.pid = pid;
		this.ppn = ppn;
	}
	
	public Date getStartAsDate()
	{
		DateFormat formatter ; 
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(start);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Date getEndAsDate()
	{
		DateFormat formatter ; 
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int GetDateDayDif()
	{
		DateFormat formatter ; 
		Date ed = null,sd = null;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 sd = formatter.parse(start);
			 ed = formatter.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int)( (ed.getTime() - sd.getTime()) / (1000 * 60 * 60 * 24) );
	}
	
	static int GetDateDif(String date1, String date2)
	{
		DateFormat formatter ; 
		Date ed = null,sd = null;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 sd = formatter.parse(date1);
			 ed = formatter.parse(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int)( (ed.getTime() - sd.getTime()) / (1000 * 60 * 60 * 24) );
	}
	
	static int ConvertCompare(String date1, String date2)
	{
		DateFormat formatter ; 
		Date ed = null,sd = null;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 sd = formatter.parse(date1);
			 ed = formatter.parse(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sd.compareTo(ed);
	}
	

}