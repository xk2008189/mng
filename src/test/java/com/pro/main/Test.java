package com.pro.main;

public class Test {
	public static void main(String[] args){
		StringBuffer sb = new StringBuffer("update production_plan_item set bakstr5='16ac8aab-92ac-4670-9d6a-9290706b0fd3', bakstr6='82037e47-2d38-4dcf-b958-fdd6d09320c5', bakstr9='40d21b3e-65d6-4245-b5c4-46b55c8e5e97',");
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
	}
}
