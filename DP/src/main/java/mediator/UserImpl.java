package mediator;

public class UserImpl extends User {
	
	public UserImpl(ChatMediator mediator, String name) {
		super(mediator, name);
	}

	@Override
	public void send(String message) {
		System.out.printf("%s sends message: %s\n", name(), message);
		mediator().sendMessage(message, this);
	}

	@Override
	public void recv(String message, User sender) {
		System.out.printf("%s receives message from %s: %s%n",
				name(), sender.name(), message);
	}
}
