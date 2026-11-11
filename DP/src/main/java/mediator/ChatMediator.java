package mediator;

public interface ChatMediator {
	void addUser(User user);
	void sendMessage(String message, User sender);
}
