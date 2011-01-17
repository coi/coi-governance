package gov.mess;

import java.util.HashMap;

public class ACLMessage extends HashMap<String, String> {

	public  static String sender = "sender";
	public  static String receiver = "receiver";
	public  static String performative = "performative";
	public  static String replyTo = "replyTo"; 
	public  static String content = "content";
	public  static String language = "language";
	public  static String encoding = "encoding";
	public  static String ontology = "ontology";
	public  static String protocol = "protocol";
	public  static String conversationId = "conversationId"; 
	public  static String replyWith = "replyWith";
	public  static String inReplyTo = "inReplyTo";
	public  static String replyBy = "replyBy";
	
	
	public  String getSender() {
		return sender;
	}
	public  void setSender(String sender) {
		this.put(ACLMessage.sender, sender);
	}
	public  String getReceiver() {
		return receiver;
	}
	public  void setReceiver(String receiver) {
		this.put(ACLMessage.receiver , receiver);
	}
	public  String getPerformative() {
		return performative;
	}
	public  void setPerformative(String performative) {
		this.put(ACLMessage.performative , performative);
	}
	public  String getReplyTo() {
		return replyTo;
	}
	public  void setReplyTo(String replyTo) {
		this.put(ACLMessage.replyTo , replyTo);
	}
	public  String getContent() {
		return content;
	}
	public  void setContent(String content) {
		this.put(ACLMessage.content , content);
	}
	public  String getLanguage() {
		return language;
	}
	public  void setLanguage(String language) {
		this.put(ACLMessage.language , language);
	}
	public  String getEncoding() {
		return encoding;
	}
	public  void setEncoding(String encoding) {
		this.put(ACLMessage.encoding , encoding);
	}
	public  String getOntology() {
		return ontology;
	}
	public  void setOntology(String ontology) {
		this.put(ACLMessage.ontology , ontology);
	}
	public  String getProtocol() {
		return protocol;
	}
	public  void setProtocol(String protocol) {
		this.put(ACLMessage.protocol , protocol);
	}
	public  String getConversationId() {
		return conversationId;
	}
	public  void setConversationId(String conversationId) {
		this.put(ACLMessage.conversationId , conversationId);
	}
	public  String getReplyWith() {
		return replyWith;
	}
	public  void setReplyWith(String replyWith) {
		this.put(ACLMessage.replyWith , replyWith);
	}
	public  String getInReplyTo() {
		return inReplyTo;
	}
	public  void setInReplyTo(String inReplyTo) {
		this.put(ACLMessage.inReplyTo , inReplyTo);
	}
	public  String getReplyBy() {
		return replyBy;
	}
	public  void setReplyBy(String replyBy) {
		this.put(ACLMessage.replyBy , replyBy);
	}
	
	
}
