package com.portalRest.connHeaders;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
public class JavaLogicalPrograms {
	
		public static void test() {
			List<Integer> elements = new ArrayList<>();
			
			for(int i=0;i<10;i++) {
				elements.add(i);
				
			}
			List<Integer> revEle = new ArrayList<>();
			for(int i=(elements.size()-1)/2;i>=0;i--){
				revEle.add(i);
			}
			System.out.println(revEle);
			
			/*Iterator itr = elements.iterator();
			while(itr.hasNext()) {
				Integer list = (Integer) itr.next();
				System.out.println(" Elements are " + list);
			}*/
			
			
			/*for(Integer ele : elements) {
				System.out.println(" " + ele);
			}
			
			System.out.println(elements.remove(1));
			System.out.println("deleted" + elements);*/
			
			System.out.println(elements);
			List<Integer> i1 = new ArrayList<>();
			i1.add(10);
			i1.add(20);
			i1.add(30);
			i1.add(40);
			System.out.println(i1);
			
			System.out.println(elements.addAll(i1));
			System.out.println(elements);
			
			System.out.println(elements.get(2));
			
			
			
		}
		
		//string reverse and palandrom, reverse sentense
		public static void rev(String str){
			
			char[] Char = str.toCharArray();
			/*for(int i=Char.length-1;i>=0;i--){
			System.out.print(Char[i]);
			}*/
			
			int i,j;
			char k;
			for(i=0, j=Char.length-1; i<=j;i++,j--) {
				k=Char[i];
				Char[i]=Char[j];
				Char[j]=k;
				
			}
			System.out.println(Char);
			
			
			
			// palendrom string or not
			/*for (i = 0, j = Char.length - 1; i <= j; i++, j--) {
				if (Char[i] != Char[j]) {
					System.out.print("its  not palindrom");
					return;
				} 
			}
			System.out.println("its palindrom");*/
		}
			
		public static void rev1(String s) {
			String rev = "";
			for(int i=s.length()-1;i>=0;i--) {
				rev = rev + s.charAt(i);
			}
			System.out.println(rev);
		}
		
		
		public static void revSentense(String s) {
			String rev = "";
			String[] strarr = s.split(" ");//hellow hai
			for(int i=strarr.length-1;i>=0;i--) {
				rev = rev + " " + strarr[i];
			}
			System.out.println(rev);
		}
		
		
		
		public static void revSentensewithoutsplit(String s) {
			// Find no.of words
			int noOfWords = 1;
			for (int i = 0; i < s.length(); ++i) {
				Character c = s.charAt(i);
				if (c.isSpaceChar(c)) {
					++noOfWords;
				}
			}

			System.out.println("noOfWords: " + noOfWords);
			String[] outPut = new String[noOfWords];

			StringBuilder word = new StringBuilder();

			for (int i = 0; i < s.length(); ++i) {
				Character c = s.charAt(i);
				if (!c.isSpaceChar(c)) {
					word.append(c);
				} else if (c.isSpaceChar(c)) {
					outPut[noOfWords - 1] = word.toString();
					--noOfWords;
					word = new StringBuilder();
				}
			}

			// outPut[noOfWords -1] = word.toString();

			for (String tmpStr : outPut) {
				System.out.print(tmpStr + " ");
			}

		}
		
		
		
			
		
		//prime number
		public static void primeNumber(int number) {
			for(int i=2;i<=number/2;i++) {
				if(number%i == 0) {
					System.out.println("Not prime number");
					return;
				}
			}
			System.out.println("Prime nuber");
		}
		
		
		
		
		
		//find duplicates using diff techniques
		public static void arrayduplicates()
		{
		 int [] arr = {1, 2, 3, 3, 1, 4, 4};  
		 
	     for(int i = 0; i < arr.length; i++) {  
	         for(int j = i + 1; j < arr.length; j++) {  
	             if(arr[i] == arr[j])  
	                 System.out.println(arr[i]); 
	             	
	         }  
	     	}
	     }
		
		
		
		public static void duplicateusingsort() {
			int [] arr = {1, 2, 3, 3, 1, 4, 4};//1,1,2,3,3,4,4,4
			Arrays.sort(arr);
			for(int i=0;i<arr.length-1;i++) {
				if(arr[i]==arr[i+1])
					System.out.println(arr[i]);
			}
		}
		
		public static void dupusingset() {
			int [] arr = {1, 2, 3, 3, 1, 4, 4};
			Set<Integer> set = new HashSet<>();
			for(int i=0;i<arr.length;i++) {
				if(!set.add(arr[i])) {
					System.out.println(arr[i]);
				}
			}
		}
		
		
		
		
		// find number dupliate count
		public static void duplicateusingmap() {
			int [] arr = {1, 2, 3, 3, 1, 4, 4,4,4,3};
			Map<Integer, Integer> map = new HashMap<>();
			
			for(int i=0;i<arr.length;i++) {
				if(map.containsKey(arr[i])) {
					int value = map.get(arr[i]);
					value = value+1;
					map.put(arr[i], value);
				}
				else
				{
					map.put(arr[i], 1);
				}
			}
			for(Entry<Integer, Integer> map1:map.entrySet()) {
				if(map1.getValue()>1)
					System.out.println(map1.getKey()+ "->" + map1.getValue());
			}
		}
		
		
		
		
		
		//find most repeated word in file
		public static void findwordfile() throws IOException {
			File file = new File ("C:/file/praveen2.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String s=null;
			StringBuffer sb = new StringBuffer();
			while((s=br.readLine())!=null) {
				sb.append(s);
			}
			System.out.println("file content is " + sb.toString());
			String str = sb.toString();
			
			String[] strarrary = str.split(" ");
			Map<String, Integer> map = new HashMap<>();
			for(int i=0;i<strarrary.length;i++) {
				if((strarrary[i].equalsIgnoreCase("error")) || (strarrary[i].equalsIgnoreCase("debug")) || (strarrary[i].equalsIgnoreCase("info")))
			  {	
				if(map.containsKey(strarrary[i])) {
					Integer value = map.get(strarrary[i]);
					value = value+1;
					map.put(strarrary[i], value);
				}
				else
				{
					map.put(strarrary[i], 1);
				}
			  }
			}
			int n=0;
			String word = null;
			for(Entry<String, Integer> map1:map.entrySet()) {
				if(map1.getValue()>n) {
					n=map1.getValue();
					word=map1.getKey();
				}	
				
			}
			System.out.println(word + "->" + n);
			
		}
			//second max repeated word
			/*int n=Integer.MAX_VALUE;
			String word = null;
			for(Entry<String, Integer> map1:map.entrySet()) {
				if(map1.getValue()<n) {
					n=map1.getValue();
					word=map1.getKey();
				}	
			}
			System.out.println(word + "->" + n);
			
		}*/
		
		//find second highest repetative char
		public static void secondhighestrepetativechar() {
			int[] arr = { 1, 2, 3, 1, 4, 4, 4, 4, 3 };
			Map<Integer, Integer> map = new HashMap<>();

			for (int i = 0; i < arr.length; i++) {
				if (map.containsKey(arr[i])) {
					int value = map.get(arr[i]);
					value = value + 1;
					map.put(arr[i], value);
				} else {
					map.put(arr[i], 1);
				}
			}
			int n = 0, second=0,second_highestkey = 0;
			for (Entry<Integer, Integer> map1 : map.entrySet()) {
				if (map1.getValue() > n)
					n = map1.getValue();
			}
			for (Entry<Integer, Integer> map2 : map.entrySet()) {
				if (map2.getValue() < n && map2.getValue() > second) {
					second = map2.getValue();
					second_highestkey = map2.getKey();
					
				}
				
			}
			System.out.println(second_highestkey);
		}
		
		
		public static void rearrangewithmap() {
			int[] arr = { 0, 1, 1, 0, 0, 0, 1 }; // 0,0,0,0,1,1,1
			Map<Integer,Integer> map = new HashMap<>();
			for(int i=0;i<arr.length;i++) {
				if(map.containsKey(arr[i])) {
					int value = map.get(arr[i]);
					value=value+1;
					map.put(arr[i], value);
				}
				else
				{
					map.put(arr[i], 1);
				}
			}
			int[] arr1 = new int[arr.length];
			int zeroscount = map.get(0);
			
			for(int i=0;i<zeroscount;i++) {
				arr1[i]=0;
			}
			for(int j=zeroscount;j<arr1.length;j++) {
				arr1[j]=1;
			}
			for(Integer result:arr1) {
				System.out.println(result);
			}
		}
		
			// arrange zeros and 1
			public static void arrangeZeros() {
				int[] arr = { 0, 1, 1, 0, 0, 0, 1 }; // 0,0,0,0,1,1,1
				Arrays.sort(arr);
				for (Integer sort : arr) {
					System.out.print(sort + ",");
				}

			}
		

		public static void findanagram(String s1, String s2) {
			char[] arr1 = s1.toCharArray();
			char[] arr2 = s2.toCharArray();

			Map<Character, Integer> map1 = new HashMap<>();
			Map<Character, Integer> map2 = new HashMap<>();

			for (int i = 0; i < arr1.length; i++) {
				if (map1.containsKey(arr1[i])) {
					int value = map1.get(arr1[i]);
					value = value + 1;
					map1.put(arr1[i], value);
				} else {
					map1.put(arr1[i], 1);
				}
			}
			for (int i = 0; i < arr2.length; i++) {
				if (map2.containsKey(arr2[i])) {
					int value = map2.get(arr2[i]);
					value = value + 1;
					map2.put(arr2[i], value);
				} else {
					map2.put(arr2[i], 1);
				}
			}
			for (Entry<Character, Integer> map2Entity : map2.entrySet()) {

				Character key = map2Entity.getKey();
				if (map1.containsKey(key)) {
					int value2 = map2Entity.getValue();
					int value1 = map1.get(key);
					if (value1 != value2) {
						System.out.println("not anagram");
						return;
					}
				} else {
					System.out.println("not anagram");
					return;
				}

			}
			System.out.println("anagrams");
		}
			
		
		
		public static void findoddeven(int range, String choice) {
			
			for (int i = 1; i <= range; i++) {
				if (choice.equalsIgnoreCase("even")) {
					if (i % 2 == 0) {
						System.out.println(i);
					}
				} else {
					if(i%2!=0)
					System.out.println(i);
				}
			}

		}

		//Emp->manager problem
		private static void managerList(Map<Character, Character> input) {

			Map<Character, List<Character>> output = new HashMap<>();

			for (Entry<Character, Character> tmpEle : input.entrySet()) {

				Character employ = tmpEle.getKey();
				Character manager = tmpEle.getValue();

				if (output.containsKey(manager)) {

					List<Character> empList = output.get(manager);
					empList.add(employ);
					output.put(manager, empList);

				} else {
					List<Character> employList = new ArrayList<>();
					employList.add(employ);
					output.put(manager, employList);
				}

			}

			for (Entry<Character, List<Character>> out : output.entrySet()) {

				System.out.print(out.getKey() + "---->");
				List<Character> emp = out.getValue();
				for (Character c : emp) {
					System.out.print(c + " ");
				}
				
				System.out.println();

			}

		}
		
		//print all possible substrings
		private static void printAllSubstrings(String inputString) {
			System.out.println("All possible substrings of " + inputString + " are : ");

			for (int i = 0; i < inputString.length(); i++) {
				for (int j = i + 1; j <= inputString.length(); j++) {
					System.out.println(inputString.substring(i, j));
				}
			}
		}
		
		public static void iterateArrayWithGivenNumber(int n) {
			int[] arr = { 1, 2, 3, 4, 5 };
			int[] arr1 = new int[arr.length];
			int filledSize=0;
			
			int filledSize1=n;
			int startOutputIndex = 0;
			
			/*for (int i = n; i < arr.length; i++) {

				arr1[filledSize]=arr[i];
				filledSize++;
			}
			
			for(int i=0;i<n;i++)
			{
				arr1[filledSize]=arr[i];
				filledSize++;
			}
			for (Integer output : arr1) {
				System.out.println(output);
			}*/
			
			
			
			for(int i=0;i<arr.length-n;i++)
			{
				arr1[filledSize1]=arr[i];
				filledSize1++;
			}
			
			
			for (int i = arr.length-n; i < arr.length; i++) {

				arr1[startOutputIndex]=arr[i];
				startOutputIndex++;
			}
			
			
			for (Integer output : arr1) {
				System.out.println(output);
			}
		}
		
		
		// from sorted array
		public static void remduplicates()
		{
			int[] input = new int[]{1, 1, 3, 7, 7, 8, 9, 9, 9, 10};
	        int current = input[0];
	        for (int i = 0; i < input.length; i++) {
	            if (current != input[i]) {
	                System.out.print(" " + current);
	                current = input[i];
	            }
	        }
	        System.out.print(" " + current);
		}
		
		
		//sorting
		public static void sorting() {
			int temp;
			int[] a = {2,4,3,1,9,7};
			for (int i = 0; i < a.length; i++) {
				for (int j = i + 1; j < a.length; j++) {

					if (a[i] > a[j]) {
						temp = a[i];
						a[i] = a[j];
						a[j] = temp;
					}
				}
			}
			for (Integer output : a)
				System.out.println(output);
		}
		
		public static void main(String args[]) throws IOException {
			//test();
			//rev("kiranmayi");
			//arrayduplicates();
			//duplicateCount();
			//primeNumber(17);
			//dup2();
			//arrangeZeros();
			//revSentense("He is hero hellow");
			//findwordfile();
			//rearrangewithmap();
			
			//rev("praveen hello");
			//revSentense1("hellow hai");
			//rev1("hellow hai");
			//findanagram("abc", "abc");
			
			//secondhighestrepetativechar();
			//findanagram("abc", "caa");
			//findoddeven(20, "odd");
			//printAllSubstrings("12");
			//dupusingset();
			//iterateArrayWithGivenNumber(1);
			
			//secondhighestrepetativechar();
			//findwordfile();
			//iterateArrayWithGivenNumber(1);
			//dupReturn("hellow");
			//removeduplicates();
			dupusingset();
		}

	}


