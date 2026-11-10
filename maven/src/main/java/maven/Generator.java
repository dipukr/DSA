package maven;

import java.util.Random;

enum Gender {Male, Female}
enum Day {Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday}
enum Month {January, Febuary, March, April, May, June, July, August, September, October, November, December}
enum Season {Spring, Summer, Autumn, Winter}
enum Colors {Red, Green, Blue, Yellow, Black, White, Orange, Purple, Brown, Pink}
enum Planet {Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune}
enum Direction {North, South, East, West}
enum Size {Small, Medium, Large, ExtraLarge}
enum Vehicle {Car, Truck, Motorcycle, Bicycle, Bus, Train, Airplane, Boat}
enum Status {Active, Inactive, Pending, Suspended, Deleted}
enum Continent {Africa, Antarctica, Asia, Europe, NorthAmerica, Oceania, SouthAmerica}
enum Currency {USD, EUR, GBP, JPY, CNY, INR, AUD, CAD, CHF}
enum Department {HR, Finance, Marketing, Sales, Engineering, IT, CustomerCare, Legal,
	Operations, Procurement, Research, Administration, Logistics, Security}
enum State {Assam, Bihar, Chhattisgarh, Goa, Gujarat, Haryana, Jharkhand, Karnataka,
	Kerala, Maharashtra, Odisha, Punjab, Rajasthan, TamilNadu, UP, Uttarakhand, WestBengal}
enum MaleFirst {Aarav, Abhishek, Ajay, Akash, Akshay, Alok, Aman, Anand, Ankit, Arjun,
	 Arnav, Ashok, Rohit, Sameer, Sanjay, Sonu, Sudhir, Saurabh, Uday, Varun, Vikram, Vinay, Vivek,
	 Rahul, Raj, Rajesh, Karan, Kabir, Gaurav, Chirag, Deepak, Harsha, Ishaan, Mohan, Nikhil}
enum FemaleFirst {Aanya, Aditi, Amrita, Anjali, Ayesha, Bhavana, Divya, Jyoti, Kajal, Kavita,
    Kirti, Lakshmi, Meena, Neha, Neelam, Pooja, Priya, Priyanka, Rekha, Riya, Sakshi,
    Seema, Shreya, Sneha, Sunita, Swati, Tanvi}
enum Surname {Raj, Kumar, Gupta, Dube, Chaube, Trivedi, Chaturvedi, Mishra, Jha, Joshi, Sah, Modi}

public class Generator {
	public static final String ALPHA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String DELIM = "~!@#$%^&*()-_+={}[]|\\':;?/>.<,";
	public static final String DIGIT = "0123456789";
	public static final String HEX = "0123456789ABCDEF";
	public static final String KEYS = ALPHA + DELIM + DIGIT;
	public static final Random rand = new Random();
	
	public String password(int len) {
		var data = new StringBuilder();
		for (int i = 0; i < len; i++)
			data.append(KEYS.charAt(rand.nextInt(KEYS.length())));
		return data.toString();
	}
	
	public static void main(String[] args) throws Exception {
		var generator = new Generator();
		for (int i = 0; i < 10; i++)
			System.out.println(generator.password(10));
	}
}










