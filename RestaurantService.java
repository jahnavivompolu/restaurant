package com.example.restaurant.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class RestaurantService {
	
	
public String restaurantOrderAnalyzer(String inputString) {
	
	   HashMap<String,String> map=new HashMap<String,String>();  
	   HashMap<Float,String> mapJac=new HashMap<Float,String>(); 
	   
	   List<Float> jacArr = new ArrayList<Float>();
	   
	   map.put("Hi, Mario's what can I get you?","Greeting");   
	   map.put("I'd like to order a pizza for pickup please.","HowCanIHelp");   
	   map.put("OK, what would you like to order?","ReadyToReceiveOrder");   
	   map.put("I'd like a medium supreme pizza.","OrderItem");   
	   map.put("Anything more?","AnyMoreItems");   
	   map.put("Also, a garlic bread.","OrderItem");   
	   map.put("Is that all?","AnyMoreItems");   
	   map.put("Yes, that is all thanks.","EndOfOrder"); 
	   map.put("OK, your order is a medium supreme and a garlic bread.","ConfirmItem");   
	   map.put("Should be ready in about 30 minutes.","DurationBeforePickupAnswer");  
	   map.put("Thank you, goodbye.","Goodbye"); 
	   
	   for(Map.Entry chatString : map.entrySet()){   
		   
		  float jac= jaccardSimilarity(inputString,chatString.getKey().toString(),mapJac);
		   
		  jacArr.add(jac);
		  
	   }
	   
	   float max = jacArr.get(0);
	   for (int i = 1; i < jacArr.size(); i++)
           if (jacArr.get(i) > max)
               max = jacArr.get(i);
	   
	   return map.get(mapJac.get(max));
	   
      }

public float jaccardSimilarity(String inputString,String chatString,HashMap<Float, String> mapJac) {
	
	 
	String newInputString = inputString.replaceAll("[.,?]", "");  
	String newChatString = chatString.replaceAll("[.,?]", "");  
	
	String[] input = newInputString.split(" ");
	String[] chat = newChatString.split(" ");
	
	 int count=0;
	 int union =input.length+chat.length;
	 
	 for(int i=0;i< input.length;i++){
		
	        for(int j=0;j< chat.length;j++){
	        	
	        if(input[i].equalsIgnoreCase(chat[j]) && !chat[j].equals(" ")){
	        	count++;
	            chat[j]=" ";
	        }	        
	      }
}

	  float jaccardSim =(float)count / (union-count) ;
	  mapJac.put(jaccardSim, chatString);
	  
	  return jaccardSim;
}

}
