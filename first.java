/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintaticopredit;

import java.util.ArrayList;

public class first {
	public String variable;
	public ArrayList<String> setOfFirst = new ArrayList<>();
	public first(String variable, ArrayList<String> setOfFirst) {
            variable = this.variable;
            setOfFirst = this.setOfFirst;
	}
	public first() { }
	public void print() {
            System.out.print("First(" + variable + ")" + ":");
            System.out.println(setOfFirst);
	}
	public String printInFile() {
            return "First(" + variable + ")" + ":" + setOfFirst;
	}
}
