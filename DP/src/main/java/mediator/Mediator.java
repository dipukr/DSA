package mediator;

public class Mediator {
	public void main(String[] args) throws Exception {
		var mediator = new ChatMediatorImpl();
		var user1 = new UserImpl(mediator, "user1");
		var user2 = new UserImpl(mediator, "user2");
		var user3 = new UserImpl(mediator, "user3");
		var user4 = new UserImpl(mediator, "user4");
		mediator.addUser(user1);
		mediator.addUser(user2);
		mediator.addUser(user3);
		mediator.addUser(user4);
		user1.send("Hello everyone");
	}
}
