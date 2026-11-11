package mediator;

public abstract class User {
	private ChatMediator mediator;
	private String name;
	
	public User(ChatMediator mediator, String name) {
		this.mediator = mediator;
		this.name = name;
	}
	
	public String name() {return name;}
	public ChatMediator mediator() {return mediator;}
	
	public abstract void send(String message);
	public abstract void recv(String message, User sender);
}
