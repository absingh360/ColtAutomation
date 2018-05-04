package com.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class Learning {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*Scanner sc = new Scanner(System.in);

		String res="";
		String str = sc.nextLine();*/

	/*	char c[] = str.toCharArray();

		Map<Character, Integer> ch = new HashMap<Character, Integer>();
		for (Character ct : c) {
			if (ch.containsKey(ct)) {
				ch.put(ct, ch.get(ct) + 1);
			} else {
				ch.put(ct, 1);
			}
		}

		Set<Map.Entry<Character, Integer>> entrySet = ch.entrySet();
		System.out.printf("List of duplicate characters in String '%s' %n", str);
		for (Map.Entry<Character, Integer> entry : entrySet) {
			if (entry.getValue() > 1) {
				System.out.printf("%s : %d %n", entry.getKey(), entry.getValue());
			}
		}*/
		
		/*while(str.length()>0)
		{
		int d=0;
		for(int j=0;j<str.length();j++)
		{
		if(str.charAt(0)==str.charAt(j))
		d=d+1;
		}
		res=res+str.charAt(0)+"="+d+"\n";
		String k[]=str.split(str.charAt(0)+"");
		str=new String("");
		for(int i=0;i<k.length;i++)
		str=str+k[i];
		}
		System.out.println(res);*/

		String t = "This is the is Ram Pencil is";
		System.out.println("test");
		String st[] = t.split(" ");
		System.out.println("st[0]");
		int c = 0;
for(int i=0;i<st.length;i++)
{
	if(st[i].equals("is"))
	c++;
}
System.out.println(c);
		/*String s =  "hello world";
		if(s.contains(t))
			System.out.println(t +" is present in " + s);
		else 
			System.out.println(t+ " is not present in "+ s );
		
		
		if(s.indexOf(t)!=-1)
			System.out.println(t +" is present in " + s);*/


	}

}
