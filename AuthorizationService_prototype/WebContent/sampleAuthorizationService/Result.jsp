<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleAuthorizationServiceid" scope="session" class="auth.service.AuthorizationService" />

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        String subject_0id=  request.getParameter("subject5");
            java.lang.String subject_0idTemp = null;
        if(!subject_0id.equals("")){
         subject_0idTemp  = subject_0id;
        }
        String predicate_1id=  request.getParameter("predicate7");
            java.lang.String predicate_1idTemp = null;
        if(!predicate_1id.equals("")){
         predicate_1idTemp  = predicate_1id;
        }
        String object_2id=  request.getParameter("object9");
            java.lang.String object_2idTemp = null;
        if(!object_2id.equals("")){
         object_2idTemp  = object_2id;
        }
        int grantAuthorization2mtemp = sampleAuthorizationServiceid.grantAuthorization(subject_0idTemp,predicate_1idTemp,object_2idTemp);
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(grantAuthorization2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
break;
case 11:
        gotMethod = true;
        String subject_3id=  request.getParameter("subject14");
            java.lang.String subject_3idTemp = null;
        if(!subject_3id.equals("")){
         subject_3idTemp  = subject_3id;
        }
        String predicate_4id=  request.getParameter("predicate16");
            java.lang.String predicate_4idTemp = null;
        if(!predicate_4id.equals("")){
         predicate_4idTemp  = predicate_4id;
        }
        String object_5id=  request.getParameter("object18");
            java.lang.String object_5idTemp = null;
        if(!object_5id.equals("")){
         object_5idTemp  = object_5id;
        }
        int revokeAuthorization11mtemp = sampleAuthorizationServiceid.revokeAuthorization(subject_3idTemp,predicate_4idTemp,object_5idTemp);
        String tempResultreturnp12 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(revokeAuthorization11mtemp));
        %>
        <%= tempResultreturnp12 %>
        <%
break;
case 20:
        gotMethod = true;
        String key_6id=  request.getParameter("key23");
            java.lang.String key_6idTemp = null;
        if(!key_6id.equals("")){
         key_6idTemp  = key_6id;
        }
        String value_7id=  request.getParameter("value25");
            java.lang.String value_7idTemp = null;
        if(!value_7id.equals("")){
         value_7idTemp  = value_7id;
        }
        int attestAttribute20mtemp = sampleAuthorizationServiceid.attestAttribute(key_6idTemp,value_7idTemp);
        String tempResultreturnp21 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(attestAttribute20mtemp));
        %>
        <%= tempResultreturnp21 %>
        <%
break;
case 27:
        gotMethod = true;
        int query27mtemp = sampleAuthorizationServiceid.query();
        String tempResultreturnp28 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(query27mtemp));
        %>
        <%= tempResultreturnp28 %>
        <%
break;
}
} catch (Exception e) { 
%>
exception: <%= e %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>