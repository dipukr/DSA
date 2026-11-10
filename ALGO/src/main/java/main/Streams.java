package main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
	public record Person(String name, int age, int sal) {}
	
	public Map<String, List<Person>> groupByName(List<Person> persons) {
		return persons.stream()
			.collect(Collectors.groupingBy(elem -> elem.name()));
	}
	
	public Map<Integer, List<Person>> groupByAge(List<Person> persons) {
		return persons.stream()
			.collect(Collectors.groupingBy(Person::age));
	}
	
	public String reverseWords(String sentence) {
		return Stream.of(sentence.split(" "))
			.map(word -> new StringBuilder(word).reverse().toString())
			.collect(Collectors.joining(" "));
	}
	
	public int sumDigits(int number) {
		return String.valueOf(number).chars()
			.map(Character::getNumericValue)
			.sum();
	}
	
	public int nthLargestNumber(List<Integer> numbers, int n) {
		return numbers.stream()
			.distinct()
			.sorted(Comparator.reverseOrder())
			.skip(n - 1)
			.findFirst()
			.orElse(0);
	}
	
	public Map<Character, Long> charsFrequency(String data) {
		return data.chars().mapToObj(c -> (char) c)
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	
	public Map<String, Long> charsFreq(String data) {
		return Arrays.stream(data.split(""))
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	
	public Map<String, Long> wordFrequency(List<String> words) {
		return words.stream()
			.collect(Collectors.groupingBy(s -> s, Collectors.counting()));
	}
	
	public String longestString(String[] strs) {
		Map<String, Integer> strToLen = Stream.of(strs)
				.collect(Collectors.toMap(Function.identity(), String::length));
		return strToLen.entrySet().stream()
				.sorted(Collections.reverseOrder(Entry.comparingByValue()))
				.findFirst()
				.get()
				.getKey();
	}
	
	public String longestStr(String[] strs) {
		return Stream.of(strs)
			.reduce((a, b) -> a.length() > b.length() ? a : b)
			.get();
	}
	
	public String maxString(String[] strs) {
		return Stream.of(strs)
			.filter(Objects::nonNull)
			.max(Comparator.comparing(String::length))
			.orElse("");
	}
	
	public Map<String, Integer> findFrequency(List<String> words) {
		var result = new HashMap<String, Integer>();
		words.forEach(elem -> result.merge(elem, 1, Integer::sum));
		return result;
	}
	
	public Map<String, Integer> mergeAll(Map<String, Integer> x, Map<String, Integer> y) {
		y.forEach((key, value) -> x.merge(key, value, Integer::sum));
		return x;
	}
	
	public Map<String, Integer> findFrequency(Path root) throws Exception {
		Map<String, Integer> freq = new HashMap<>();
		List<Path> filePaths = Files.walk(root)
				.filter(Files::isRegularFile)
				.toList();
		for (Path filePath: filePaths) {
			Files.lines(filePath)
				.flatMap(line -> Arrays.stream(line.split("\\W+")))
				.filter(word -> !word.isEmpty())
				.forEach(word -> freq.merge(word, 1, Integer::sum));
		}
		return freq;
	}
	
	public List<String> findNthMostFrequentWords(Path root, int n) throws Exception {
		Map<String, Integer> freq = new HashMap<>();
		List<Path> filePaths = Files.walk(root)
				.filter(Files::isRegularFile)
				.toList();
		for (Path filePath: filePaths) {
			Files.lines(filePath)
				.flatMap(line -> Arrays.stream(line.split("\\W+")))
				.filter(word -> !word.isEmpty())
				.forEach(word -> freq.merge(word, 1, Integer::sum));
		}
		Map<Integer, List<String>> freqToWords = freq.entrySet().stream()
			.collect(Collectors.groupingBy(Entry::getValue, 
					Collectors.mapping(Entry::getKey, Collectors.toList())));
		return freqToWords.entrySet().stream()
			.sorted(Entry.comparingByKey(Comparator.reverseOrder()))
			.skip(n - 1)
			.map(Entry::getValue)
			.findFirst()
			.orElse(Collections.emptyList());
	}
	
	public Map<Boolean, List<Path>> getAllFolderAndFiles(Path root) throws Exception {
		return Files.walk(root)
			.collect(Collectors.partitioningBy(Files::isDirectory));
	}
}











