
package com.sendgrid.utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * CustomDateSerializer.java Purpose: Date format customization
 * 
 * @version 1.0 3/22/16
 */
public class CustDateSerializer extends JsonSerializer<Date>
{

	/**
	 * serialize Method to customize the date format on the basis of date and
	 * serializer provider
	 * 
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws IOException
	 *             , JsonProcessingException
	 */
	@ Override
	public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException
	{
		

		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		String formattedDate = formatter.format(arg0);

		arg1.writeString(formattedDate);
	}

}