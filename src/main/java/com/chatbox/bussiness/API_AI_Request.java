package com.chatbox.bussiness;

import org.codehaus.jackson.map.ObjectMapper;

import com.chatbox.model.JavaModel;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class API_AI_Request {
	public JavaModel jsonToJava(String json) {
		JavaModel jmResponse = null;
		try {
			//System.out.println("...................");
			System.out.println(json);
			try {
				ObjectMapper mapper = new ObjectMapper();
				jmResponse =  mapper.readValue(json, JavaModel.class);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			System.out.println(jmResponse.getSessionId());
			//System.out.println("apiai res:"+jmResponse);
			//System.out.println("...................");
			return jmResponse;
			
		} /*catch (Exception e) {
			e.printStackTrace();
		}
		return apiAiResponse;*/
		catch (Exception e) {
			e.printStackTrace();
		}return null;
	}

}
