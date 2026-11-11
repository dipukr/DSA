package observer;

public class MailObserver implements Observer<Mail> {

	private String name;
	
	@Override
	public void update(Mail mail) {
		if (mail.receiver().equals(name)) {
			String sender = mail.sender();
			String receiver = mail.receiver();
			System.out.printf("[%s] received mail from [%s]%n", receiver, sender);
		}
	}
}
