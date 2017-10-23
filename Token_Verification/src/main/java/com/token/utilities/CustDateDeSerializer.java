package com.token.utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.springframework.expression.ParseException;

/**
 * CustDateDeSerializer.java Purpose: Date format deserialization
 * 
 * @version 1.0 3/22/16
 */
public class CustDateDeSerializer extends JsonDeserializer<Date>
{
	
	
	    @Override
	    public Date deserialize(JsonParser jsonparser,
	            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

	        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
	        String date = jsonparser.getText();
	        try {
	            return format.parse(date);
	        } catch (java.text.ParseException e) {
	            throw new ParseException(date.length(),e.getMessage());
	        }

	    }

	    public static Date stringToDateFormat(String value,String format ) throws java.text.ParseException
		{
			try
			{
				return new SimpleDateFormat(format).parse(value);
			}
			catch (ParseException e)
			{
				return null;
			}

		} 
	
		  public static String dateToStringFormatter(Date value,String format ) throws java.text.ParseException
			{
				try
				{
					if(value != null ){
						SimpleDateFormat sdf = new SimpleDateFormat(format);
						String retDate = sdf.format(value); 
					return retDate ;
					}
					else
						return null ;
					
					 
				}
				catch (ParseException e)
				{
					return null;
				}

			} 
	    
}
