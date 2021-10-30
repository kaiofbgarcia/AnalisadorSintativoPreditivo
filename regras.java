/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintaticopredit;

import java.util.ArrayList;

public class regras {
	public String source;
	public ArrayList<String> production = new ArrayList<>();
	public regras(String source, ArrayList<String> destinationList) {
            source = this.source;
            destinationList = this.production;
	}
	public regras() {
		
	}
}
