package com.zdl.util.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
/**
 * 反序列化器
 * 继承 jsonserializer转换Date日期类型
 * @author DELL
 *
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator JsonGenerator, SerializerProvider SerializerProvider)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		//数据库精确到毫秒，所以去三个0
		JsonGenerator.writeNumber(date.getTime()/1000);
		
	}
	

}
