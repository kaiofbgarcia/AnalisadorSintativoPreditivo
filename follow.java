/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintaticopredit;

import java.util.ArrayList;

public class follow {
	public String variable;
	public ArrayList<String> setOfFollow = new ArrayList<>();
	public follow(String variable, ArrayList<String> setOfFollow) {
		variable = this.variable;
		setOfFollow = this.setOfFollow;
	}
	public follow() { }
	public void print() {
		System.out.print("Follow(" + variable + ")" + ":");
		System.out.println(setOfFollow);
	}
	public String printInFile() {
		return "Follow(" + variable + ")" + ":" + setOfFollow;
	}
}