package com.chatbox.bussiness;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.chatbox.model.JavaModel;
import com.chatbox.model.Metadata;
import com.chatbox.model.OriginalRequest;

import com.chatbox.model.ResponseMdl;
import com.chatbox.model.Result;

@Path("itautomation")
public class RequestResponce {
static String result="";
	@GET
	public Response getMsg() {
		return Response.status(200).entity("Hello").build();


	}

	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getConf(String outputJSON) throws Exception{
		
		System.out.println("Request recieved");
		API_AI_Request response = new API_AI_Request();

		System.out.println("responceBO : "+response.toString());
		JavaModel apiAiResponse = response.jsonToJava(outputJSON);

		System.out.println("apiAiResponse : " +apiAiResponse);
	

		Result rs=apiAiResponse.getResult();

		System.out.println("rs :"+rs.toString());
		
		Metadata m =rs.getMetadata();
		OriginalRequest or=apiAiResponse.getOriginalRequest();
		String token = "UNKNOWN";
		String aeRequestId = "UNKNOWN";
		
    	String slackUser=or.getData().getEvent().getUser();
    	String slackChannel=or.getData().getEvent().getChannel();
    	System.out.println("Chanel: " + slackChannel);
    	System.out.println("User: " + slackUser);
    
	    String intentname=m.getIntentName();
	    
	    Map<String, String> wfParams = new HashMap<>();
	    AERestCall aeRestCall = new AERestCall();
	    ResponseMdl res=new ResponseMdl();
	    //Case: Software Install
	    if(intentname.equalsIgnoreCase("software_install"))
	    {
	    	System.out.println("intent name is:"+intentname);
	    	//AERestCall aeRestCall = new AERestCall();
	    	token = aeRestCall.authenticate();
	    	
	    	String jsonInput = "{\"ServiceRequest\": \"Software Installation\",\"params\": [{\"question\": \"Software\",\"answer\": \"%%SOFTWARE_NAME%%\"}]}";
	    	jsonInput = jsonInput.replace("%%SOFTWARE_NAME%%",rs.getParameters().getSoftware());
	    	
	    	//Map<String, String> wfParams = new HashMap<>();
	    	wfParams.put("clientEmail", aeRestCall.getClientEmail(""));
	    	wfParams.put("jsonInput",jsonInput);
	    	wfParams.put("slackChannel", slackChannel);
	    	wfParams.put("slackUser", slackUser);

	    	System.out.println("token: " + token);
			aeRequestId = aeRestCall.execute(token, "Create Service Request In Remedyforce", 
					wfParams);
			
			res.setSource("policyWS");
			res.setSpeech("Please wait while we work on your request.");
			res.setDisplayText("Please wait while we work on your request.");

	    }
	    //case: Get Incident Status
	    else if(intentname.equalsIgnoreCase("incident_status"))
	    {
	    	System.out.println("intent name is:"+intentname);
	    /*	AERestCall aeRestCall = new AERestCall();
	    	token = aeRestCall.authenticate();
	    	
	    	String jsonInput = "{\"ServiceRequest\": \"Software Install\",\"params\": [{\"question\": \"software\",\"answer\": \"%%SOFTWARE_NAME%%\"}]}";
	    	jsonInput = jsonInput.replace("%%SOFTWARE_NAME%%",rs.getParameters().getSoftware());
	    	
	    	Map<String, String> wfParams = new HashMap<>();
	    	wfParams.put("clientEmail", aeRestCall.getClientEmail("saurabh.kulkarni@vyomlabs.com"));
	    	wfParams.put("jsonInput",jsonInput);
	    	System.out.println("token: " + token);
			aeRequestId = aeRestCall.execute(token, "Create Service Request In Remedyforce", 
					wfParams);*/
	    	
	    	res.setSource("policyWS");
			res.setSpeech("Please wait while we work on your request.");
			res.setDisplayText("Please wait while we work on your request.");

	    }
	    //Case: create Incident
	    else if(intentname.equalsIgnoreCase("incident")){
	    	System.out.println("intent name is:"+intentname);
	    	
	    	//AERestCall aeRestCall = new AERestCall();
	    	token = aeRestCall.authenticate();
	    	//Map<String, String> wfParams = new HashMap<>();
	    	wfParams.put("clientEmail", aeRestCall.getClientEmail(""));
	    	wfParams.put("description", rs.getParameters().getDescription());
	    	wfParams.put("slackChannel", slackChannel);
	    	wfParams.put("slackUser", slackUser);

			System.out.println("token: " + token);
			aeRequestId = aeRestCall.execute(token, "Create Incident In Remedyforce", 
					wfParams);
			res.setSource("policyWS");
			res.setSpeech("Please wait while we work on your request.");
			res.setDisplayText("Please wait while we work on your request.");
	    }
	    //case: Create Box Account
	    else if(intentname.equalsIgnoreCase("CreateBoxAccount"))
	    {   
	    	System.out.println("intent name is:"+intentname);
	    	//AERestCall aeRestCall = new AERestCall();
	    	token = aeRestCall.authenticate();
	    	String jsonInput = "{\"ServiceRequest\": \"Create Box Account\",\"params\": [{\"question\":\"First Name\",\"answer\": \"%%FIRST_NAME%%\"},{\"question\": \"Last Name\",\"answer\": \"%%LAST_NAME%%\"}]}";
	     	
	    	jsonInput = jsonInput.replace("%%FIRST_NAME%%",rs.getParameters().getFirstName());
	    	jsonInput = jsonInput.replace("%%LAST_NAME%%", rs.getParameters().getLastName());
	    	//Map<String, String> wfParams = new HashMap<>();
	    	wfParams.put("clientEmail", aeRestCall.getClientEmail(""));
	    	wfParams.put("jsonInput",jsonInput);
	    	wfParams.put("slackChannel", slackChannel);
	    	wfParams.put("slackUser", slackUser);
	    	
	    	System.out.println("token: " + token);
			aeRequestId = aeRestCall.execute(token, "Create Service Request In Remedyforce", 
					wfParams);
			res.setSource("policyWS");
			res.setSpeech("Please wait while we work on your request.");
			res.setDisplayText("Please wait while we work on your request.");
	    }
	    //Case: Add Member DL
	    else if(intentname.equalsIgnoreCase("AddMemberDL"))
	    {
	    	System.out.println("intent name is:"+intentname);
	    	//AERestCall aeRestCall = new AERestCall();
	    	token = aeRestCall.authenticate();
	    	
	    	String jsonInput = "{\"ServiceRequest\": \"Add Members to DL\",\"params\": [{\"question\":\"Username\",\"answer\": \"%%USER_NAME%%\"},{\"question\": \"Group Name\",\"answer\": \"%%GROUP_NAME%%\"}]}";
	     	jsonInput = jsonInput.replace("%%USER_NAME%%",rs.getParameters().getUserName());
	    	jsonInput = jsonInput.replace("%%GROUP_NAME%%", rs.getParameters().getGroupName());
	    	System.out.println("JsonInput:"+jsonInput);
	    	//Map<String, String> wfParams = new HashMap<>();
	    	wfParams.put("clientEmail", aeRestCall.getClientEmail(""));
	    	wfParams.put("jsonInput",jsonInput);
	    	wfParams.put("slackChannel", slackChannel);
	    	wfParams.put("slackUser", slackUser);

	    	System.out.println("token: " + token);
			aeRequestId = aeRestCall.execute(token, "Create Service Request In Remedyforce", 
					wfParams);
			res.setSource("policyWS");
			res.setSpeech("Please wait while we work on your request.");
			res.setDisplayText("Please wait while we work on your request.");
	    }
	 
		
		/*ResponseMdl res=new ResponseMdl();
	    res.setSource("policyWS");
		res.setSpeech("Please wait while we work on your request.");
		res.setDisplayText("Please wait while we work on your request.");*/
		
		ObjectMapper om=new ObjectMapper();
		String str2=om.writeValueAsString(res);

		return Response.status(200).entity(str2).header("Content-Type", "application/json").build();
	}
}
