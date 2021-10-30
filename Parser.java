/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analisadorsintaticopredit;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class Parser {
    public static String table[][];

    public static ArrayList<derivacoes> output = new ArrayList<derivacoes>();

    public static void parser(gramatica grammar, ArrayList<String> input) {
        Stack<String> pda = new Stack<>();
        boolean error = false;
        boolean first = true;
        for (int a = 0; a < input.size(); a++) {
            boolean done = false;
            while (!done) {
                if (error){
                    break;
                }
                for (String[] table1 : table) {
                    for (int j = 0; j < table1.length; j++) {
                        String var = input.get(a);
                        if (table[0][j].equals(var)) {
                            if (!(table1[0].equals(""))) {
                                if (table1[j] != null && first) {
                                    pda.push(table1[0]);
                                    first = false;
                                }
                                if (table1[j] != null && !pda.isEmpty() && !first && !(table1[j].equals(var)) && table1[0].equals(pda.peek())) {
                                    derivacoes d = new derivacoes();
                                    d.input = var;
                                    d.topOfStack = pda.pop();
                                    d.out = table1[j];
                                    ArrayList<String> temp = new ArrayList<>();
                                    temp.add(d.out);
                                    ArrayList<ArrayList<String>> productions = splitProduction(temp, grammar);
                                    for (int k = productions.get(0).size() - 1; k >= 0; k--) {
                                        if (!(productions.get(0).get(k)).equals("!"))
                                            pda.push(productions.get(0).get(k));
                                    }
                                    output.add(d);
                                }
                                if (table1[j] != null && !pda.isEmpty() && (!first && table1[j].equals(var)) && table1[0].equals(pda.peek())) {
                                    derivacoes d = new derivacoes();
                                    d.input = var;
                                    d.out = table1[j];
                                    d.topOfStack = pda.pop();
                                    ArrayList<String> temp = new ArrayList<>();
                                    temp.add(d.out);
                                    ArrayList<ArrayList<String>> productions = splitProduction(temp, grammar);
                                    for (int k = productions.get(0).size() - 1; k >= 0; k--) {
                                        if (!(productions.get(0).get(k)).equals("!"))
                                            pda.push(productions.get(0).get(k));
                                    }
                                    output.add(d);
                                }
                                if (table1[j] != null && grammar.terminals.contains(pda.peek()) && pda.peek().equals(var)) {
                                    derivacoes d = new derivacoes();
                                    d.input = "";
                                    d.out = pda.peek();
                                    d.topOfStack = pda.pop();
                                    done = true;
                                    output.add(d);
                                }
                                if (table1[j] == null && table[0][j].equals(var) && table1[0].equals(pda.peek()) && !done) {
                                    error = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!error)
            dollarPrsing(pda, grammar);
        if (error) {
            output = null;
        }
    }

    public static boolean haveNonEpislon(String table[][], String var) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[0][j].equals(var)) {
                    if (table[i][j] != null && (!table[i][j].equals("!")) && i > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void dollarPrsing(Stack<String> pda, gramatica grammar) {
        while (!pda.isEmpty()) {
            for (String[] table1 : table) {
                for (int j = 0; j < table1.length; j++) {
                    if (table[0][j].equals("$")) {
                        if (table1[j] != null && !(table1[0].equals(""))) {
                            if (!pda.isEmpty() && table1[0].equals(pda.peek())) {
                                derivacoes d = new derivacoes();
                                d.input = "$";
                                d.topOfStack = pda.pop();
                                d.out = table1[j];
                                ArrayList<String> temp = new ArrayList<>();
                                temp.add(d.out);
                                ArrayList<ArrayList<String>> productions = splitProduction(temp, grammar);
                                for (int k = productions.get(0).size() - 1; k >= 0; k--) {
                                    if (!(productions.get(0).get(k)).equals("!"))
                                        pda.push(productions.get(0).get(k));
                                }
                                output.add(d);
                            }
                        }
                    }
                }
            }
        }
    }

    public static ArrayList<ArrayList<String>> splitProduction(ArrayList<String> production, gramatica grammer) {
        ArrayList<ArrayList<String>> out = new ArrayList<>();
        for (int i = 0; i < production.size(); i++) {
            String tempo = production.get(i);
            ArrayList<String> aProduction = new ArrayList<>();
            String temp = "";
            for (int j = 0; j < tempo.length(); j++) {
                temp += tempo.charAt(j);
                if (j + 1 < tempo.length() && (tempo.charAt(j + 1) + "").equals("'")) {
                    temp += "'";
                    j++;
                }
                if (grammer.nonTerminals.contains(temp) || grammer.terminals.contains(temp) || temp.equals("!")) {aProduction.add(temp);
                    temp = "";
                }
            }
            out.add(aProduction);
        }
        return out;
    }

    public static void printOutput(ArrayList<derivacoes> output) {
        if (output != null){
            for (int i = 0; i < output.size(); i++) {
                System.out.print(output.get(i).topOfStack);
                if (!output.get(i).input.isEmpty()){
                    System.out.print("[" + output.get(i).input + "]" + "--->" + output.get(i).out);
                }
                else{
                    System.out.print("--->" + output.get(i).out);
                    System.out.println();
                }
            }
        }
        else{
            System.out.println("Error");
        }
    }

    public static void writeOutputInFile(ArrayList<derivacoes> output) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter("Input.txt"))) {
            if (output != null){
                for (int i = 0; i < output.size(); i++) {
                    out.print(output.get(i).topOfStack);
                    if (!output.get(i).input.isEmpty()){
                        out.print("[" + output.get(i).input + "]" + "--->" + output.get(i).out);
                    }
                    else{
                        out.print("--->" + output.get(i).out);
                    }
                    out.println();
                }
            }
            else {
                out.println("Error");
            }
        }
    }

    @SuppressWarnings("static-access")
    public static void printTable(parseTable t) {
        for (String[] row : t.table) {
            t.printRow(row);
        }
    }

    public static String printRowInFile(String[] row) {
        String out ="";
        for (String i : row) {
            out+= i + "		";
        }
        return out;
    }

    @SuppressWarnings("static-access")
    public static void writeTableInFile(parseTable t) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter("Table.txt"))) {
            for (String[] row : t.table) {
                out.println(printRowInFile(row));
            }
        }
    }

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        gramatica grammar = new gramatica("Sample2.txt");
        var f = new FirstandFollow();
        f.First(grammar);
        f.Follow(grammar);
        parseTable t = new parseTable();
        t.first = f.first;
        t.follow = f.follow;
        t.table = new String[grammar.nonTerminals.size() + 1][grammar.terminals.size() + 2];
        t.filler(grammar);
        printTable(t);
        writeTableInFile(t);
        table = t.table;
        inputParser n = new inputParser("Input.txt", grammar);
        parser(grammar, n.elements);
        printOutput(output);
        writeOutputInFile(output);
    }
}