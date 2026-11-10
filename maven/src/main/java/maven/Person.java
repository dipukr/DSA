package maven;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Person {
	private String name;
	private int age;
	private int sal;
	public Person(String name, int age, int sal) {
		this.name = name;
		this.age = age;
		this.sal = sal;
	}
}
