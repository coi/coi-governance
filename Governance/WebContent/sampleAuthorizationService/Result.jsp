<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleAuthorizationServiceid" scope="session" class="gov.auth.service.AuthorizationService" />

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
        String org_id_0id=  request.getParameter("org_id5");
            java.lang.String org_id_0idTemp = null;
        if(!org_id_0id.equals("")){
         org_id_0idTemp  = org_id_0id;
        }
        String subject_1id=  request.getParameter("subject7");
            java.lang.String subject_1idTemp = null;
        if(!subject_1id.equals("")){
         subject_1idTemp  = subject_1id;
        }
        String predicate_2id=  request.getParameter("predicate9");
            java.lang.String predicate_2idTemp = null;
        if(!predicate_2id.equals("")){
         predicate_2idTemp  = predicate_2id;
        }
        String object_3id=  request.getParameter("object11");
            java.lang.String object_3idTemp = null;
        if(!object_3id.equals("")){
         object_3idTemp  = object_3id;
        }
        int grantAuthorization2mtemp = sampleAuthorizationServiceid.grantAuthorization(org_id_0idTemp,subject_1idTemp,predicate_2idTemp,object_3idTemp);
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(grantAuthorization2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
break;
case 13:
        gotMethod = true;
        String org_id_4id=  request.getParameter("org_id16");
            java.lang.String org_id_4idTemp = null;
        if(!org_id_4id.equals("")){
         org_id_4idTemp  = org_id_4id;
        }
        String subject_5id=  request.getParameter("subject18");
            java.lang.String subject_5idTemp = null;
        if(!subject_5id.equals("")){
         subject_5idTemp  = subject_5id;
        }
        String predicate_6id=  request.getParameter("predicate20");
            java.lang.String predicate_6idTemp = null;
        if(!predicate_6id.equals("")){
         predicate_6idTemp  = predicate_6id;
        }
        String object_7id=  request.getParameter("object22");
            java.lang.String object_7idTemp = null;
        if(!object_7id.equals("")){
         object_7idTemp  = object_7id;
        }
        int revokeAuthorization13mtemp = sampleAuthorizationServiceid.revokeAuthorization(org_id_4idTemp,subject_5idTemp,predicate_6idTemp,object_7idTemp);
        String tempResultreturnp14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(revokeAuthorization13mtemp));
        %>
        <%= tempResultreturnp14 %>
        <%
break;
case 24:
        gotMethod = true;
        String org_id_8id=  request.getParameter("org_id27");
            java.lang.String org_id_8idTemp = null;
        if(!org_id_8id.equals("")){
         org_id_8idTemp  = org_id_8id;
        }
        String subject_id_9id=  request.getParameter("subject_id29");
            java.lang.String subject_id_9idTemp = null;
        if(!subject_id_9id.equals("")){
         subject_id_9idTemp  = subject_id_9id;
        }
        String key_10id=  request.getParameter("key31");
            java.lang.String key_10idTemp = null;
        if(!key_10id.equals("")){
         key_10idTemp  = key_10id;
        }
        String value_11id=  request.getParameter("value33");
            java.lang.String value_11idTemp = null;
        if(!value_11id.equals("")){
         value_11idTemp  = value_11id;
        }
        int attestAttribute24mtemp = sampleAuthorizationServiceid.attestAttribute(org_id_8idTemp,subject_id_9idTemp,key_10idTemp,value_11idTemp);
        String tempResultreturnp25 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(attestAttribute24mtemp));
        %>
        <%= tempResultreturnp25 %>
        <%
break;
case 35:
        gotMethod = true;
        String org_id_12id=  request.getParameter("org_id38");
            java.lang.String org_id_12idTemp = null;
        if(!org_id_12id.equals("")){
         org_id_12idTemp  = org_id_12id;
        }
        String subject_id_13id=  request.getParameter("subject_id40");
            java.lang.String subject_id_13idTemp = null;
        if(!subject_id_13id.equals("")){
         subject_id_13idTemp  = subject_id_13id;
        }
        String key_14id=  request.getParameter("key42");
            java.lang.String key_14idTemp = null;
        if(!key_14id.equals("")){
         key_14idTemp  = key_14id;
        }
        String value_15id=  request.getParameter("value44");
            java.lang.String value_15idTemp = null;
        if(!value_15id.equals("")){
         value_15idTemp  = value_15id;
        }
        int addAttribute35mtemp = sampleAuthorizationServiceid.addAttribute(org_id_12idTemp,subject_id_13idTemp,key_14idTemp,value_15idTemp);
        String tempResultreturnp36 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(addAttribute35mtemp));
        %>
        <%= tempResultreturnp36 %>
        <%
break;
case 46:
        gotMethod = true;
        String org_id_16id=  request.getParameter("org_id49");
            java.lang.String org_id_16idTemp = null;
        if(!org_id_16id.equals("")){
         org_id_16idTemp  = org_id_16id;
        }
        String subject_17id=  request.getParameter("subject51");
            java.lang.String subject_17idTemp = null;
        if(!subject_17id.equals("")){
         subject_17idTemp  = subject_17id;
        }
        String predicate_18id=  request.getParameter("predicate53");
            java.lang.String predicate_18idTemp = null;
        if(!predicate_18id.equals("")){
         predicate_18idTemp  = predicate_18id;
        }
        String object_19id=  request.getParameter("object55");
            java.lang.String object_19idTemp = null;
        if(!object_19id.equals("")){
         object_19idTemp  = object_19id;
        }
        int checkAuthorization46mtemp = sampleAuthorizationServiceid.checkAuthorization(org_id_16idTemp,subject_17idTemp,predicate_18idTemp,object_19idTemp);
        String tempResultreturnp47 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(checkAuthorization46mtemp));
        %>
        <%= tempResultreturnp47 %>
        <%
break;
case 57:
        gotMethod = true;
        String org_id_20id=  request.getParameter("org_id60");
            java.lang.String org_id_20idTemp = null;
        if(!org_id_20id.equals("")){
         org_id_20idTemp  = org_id_20id;
        }
        int registerOrg57mtemp = sampleAuthorizationServiceid.registerOrg(org_id_20idTemp);
        String tempResultreturnp58 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(registerOrg57mtemp));
        %>
        <%= tempResultreturnp58 %>
        <%
break;
case 62:
        gotMethod = true;
        String org_id_21id=  request.getParameter("org_id65");
            java.lang.String org_id_21idTemp = null;
        if(!org_id_21id.equals("")){
         org_id_21idTemp  = org_id_21id;
        }
        int deregisterOrg62mtemp = sampleAuthorizationServiceid.deregisterOrg(org_id_21idTemp);
        String tempResultreturnp63 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(deregisterOrg62mtemp));
        %>
        <%= tempResultreturnp63 %>
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