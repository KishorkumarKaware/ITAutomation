package com.chatbox.model;

public class Messages
{
private String id;

private String speech;

private String type;

public String getId ()
{
return id;
}

public void setId (String id)
{
this.id = id;
}

public String getSpeech ()
{
return speech;
}

public void setSpeech (String speech)
{
this.speech = speech;
}

public String getType ()
{
return type;
}

public void setType (String type)
{
this.type = type;
}

@Override
public String toString()
{
return "ClassPojo [id = "+id+", speech = "+speech+", type = "+type+"]";
}
}

