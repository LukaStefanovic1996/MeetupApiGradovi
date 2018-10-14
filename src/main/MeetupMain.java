package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import meetup.MeetupGetter;
 
public class MeetupMain {
	
	private static List<String> listaGradova  = new ArrayList<>();
	
	public static void main(String[] args) {
 
		String keyPath = "apikey.txt"; 							
		String key = "";
		String pathCode = "/2/cities";								
																			
		String events = "";
 
		MeetupGetter meetupGetter = new MeetupGetter();
		key = meetupGetter.getApiKey(keyPath);
 

		meetupGetter.setTopic("photo");										
 
		try {
			events = meetupGetter.getCityList(pathCode, key);					
		} catch (Exception e) {e.printStackTrace();}
		CityList(events);	
		Scanner scanner = new Scanner(System.in);
		boolean loop = true;
		pathCode="/2/open_events";
		while(loop)
		{
			try
			{
				int id = scanner.nextInt();
				if(id==0) 
				{
					loop=false;
				}
				else if (id<0) 
				{
					System.out.println("Greška! Uneli ste negativan broj");
				}
				else if (id>listaGradova.size()) 
				{
					System.out.println("Greška! Uneli ste prevelik broj");
				}
				else
				{
					meetupGetter.setCity(listaGradova.get(id-1));
					String s = meetupGetter.getEventsInCity(pathCode, key);
					EventList(s);
				}
				if(id!=0) System.out.println("Unesite redni broj da biste pregledali dogaðaje za željeni grad (0 za kraj rada)");
			}
			catch(InputMismatchException e)
			{
				scanner.nextLine();
				System.out.println("Greška u unetim podacima! Unesite redni broj grada:");			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("Završili ste rad sa programom.");
		scanner.close();
		
 
	}
 
	public static void CityList(String cities){
 
		try{
			JsonObject obj = (JsonObject) Jsoner.deserialize(cities);
			JsonArray results = (JsonArray) obj.get("results");
			System.out.println("Gradovi u Srbiji : ");
			int j = 1;
			Iterator<Object> i = results.iterator(); 
			while(i.hasNext()){
				JsonObject city = (JsonObject) i.next();
				String a = (String) city.get("city");
				System.out.println(j+" - "+a);
				j++;
				listaGradova.add(a);
				i.next();
			}
			System.out.println("Unesite redni broj da biste pregledali dogaðaje za željeni grad (0 za kraj rada)");
 
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public static void EventList(String events){	 
		try{
			JsonObject obj = (JsonObject) Jsoner.deserialize(events);
			JsonArray arr = (JsonArray) obj.get("results");
			Iterator<Object> i = arr.iterator(); 
			while(i.hasNext()){
				JsonObject event = (JsonObject) i.next();
				System.out.println("Naziv: "+event.get("name"));
				String s = (String) event.get("description");
				if(s!=null)
				{
					s= s.replaceAll("<p>", "");
					s= s.replaceAll("</p>", System.getProperty("line.separator"));
					s= s.replaceAll("<br/>", System.getProperty("line.separator"));
					while(s.contains("<a href"))
					{
						s = s.substring(0, s.indexOf("<a href"))+s.substring(s.indexOf(">", s.indexOf("<a href") )+1);
					}
					s= s.replaceAll("</a>", "");
				}
				else
				{
					s="Nije dostupan";
				}
				JsonObject venue =(JsonObject) event.get("venue");
				if(venue!=null)
				System.out.println("Lokacija: "+venue.get("name")+", "+venue.get("address_1"));
				else System.out.println("Lokacija: Nepoznata");
				System.out.println("Opis: "+s);		
				System.out.println("");
				if(i.hasNext())  i.next();
			}
 
		}
		catch(Exception e){e.printStackTrace();
		}
	}
 
}