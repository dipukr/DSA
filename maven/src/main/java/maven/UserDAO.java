package maven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class UserDAO {
	private Map<Integer, User> data = new HashMap<>();

	public void save(User user) {
		data.put(data.size(), user);
	}

	public List<User> findAll() {
		return new ArrayList<>(data.values());
	}

	public Optional<User> find(int id) {
		return Optional.ofNullable(data.get(id));
	}

	public Optional<User> find(String username) {
		for (Entry<Integer, User> elem: data.entrySet())
			if (elem.getValue().name().equals(username))
				return Optional.ofNullable(elem.getValue());
		return Optional.empty();
	}
}
