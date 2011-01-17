package gov.auth.data;

public class AuthDataObject {

	public String subject= null;
	public String predicate= null;
	public String object= null;
	
	public AuthDataObject(String subject, String predicate, String object) {
		super();
		
		this.subject = new String(subject);
		this.predicate = new String(predicate);
		this.object = new String(object);
		
	}
	
	public AuthDataObject() {
		this("","","");
	}

	@Override
	public String toString() {
		return "AuthDataObject [subject=" + subject + ", predicate="
				+ predicate + ", object=" + object + "]";
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

}
