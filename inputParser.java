/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintaticopredit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class inputParser {
    public ArrayList<String> elements;
    public inputParser() {}
    public inputParser(String fileName, gramatica grammer) throws Exception {
        try {
            File file = new File(fileName);
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String text = "";
            String var = "";
            while ((text = reader.readLine()) != null) {
                var = "";
                int i = 0;
                ArrayList<String> out = new ArrayList<>();
                while (i < text.length()) {
                    var = "";
                    while (true) {
                        if (i >= text.length())
                            break;
                        var += text.charAt(i);
                        i++;
                        if (grammer.terminals.contains(var)) {
                                break;
                        }
                    }
                    if (var != null || !var.equals("")) {
                            out.add(var);
                    }
                }
                elements = out;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
