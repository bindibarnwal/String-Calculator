package com.company;

import java.util.Scanner;
import java.util.Stack;

//Assumption: All operators have been considered to be binary
public class StringCalculator {

    public static int check_valid(String s) {
        Stack<Character> st = new Stack<>();

        char pc = 'e';
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                if (pc == 'c')
                    return 0;
                st.push('(');
                pc = 'a';
            } else if (s.charAt(i) == ')') {
                if (st.empty())
                    return 0;
                st.pop();
                pc = 'b';
            } else if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                if (pc == 'c' || pc == 'b')
                    return 0;
                pc = 'c';
            } else {
                if (pc == 'd' || pc == 'a' || pc == 'e')
                    return 0;
                pc = 'd';
            }
        }
        char x = s.charAt(s.length() - 1);
        if (x == '(' || prec(x) != -1)
            return 0;
        return 1;
    }

    //Function to return precedence of operators
    public static int prec(char c) {
        if (c == '^')
            return 3;
        else if (c == '*' || c == '/')
            return 2;
        else if (c == '+' || c == '-')
            return 1;
        else
            return -1;
    }

    
    public static String infixToPostfix(String s) {
        Stack <Character> st = new Stack<>();
        st.push('N');
        int l = s.length();
        StringBuffer ns = new StringBuffer();
        for (int i = 0; i < l; i++) {
            // If the scanned character is an operand, add it to output string.
            if ((s.charAt(i) >= '0' && s.charAt(i) <= '9'))
                ns = ns.append(s.charAt(i));

                // If the scanned character is an ‘(‘, push it to the stack.
            else if (s.charAt(i) == '(')

                st.push('(');

                // If the scanned character is an ‘)’, pop and to output string from the stack
                // until an ‘(‘ is encountered.
            else if (s.charAt(i) == ')') {
                while (st.peek() != 'N' && st.peek() != '(') {
                    char c = st.peek();
                    st.pop();
                    ns = ns.append(c);
                }
                if (st.peek() == '(') {
                    char c = st.peek();
                    st.pop();
                }
            }

            //If an operator is scanned
            else {
                while (st.peek() != 'N' && prec(s.charAt(i)) <= prec(st.peek())) {
                    char c = st.peek();
                    st.pop();
                    ns.append(c);
                }
                st.push(s.charAt(i));
            }

        }
        //Pop all the remaining elements from the stack
        while (st.peek() != 'N') {
            char c = st.peek();
            st.pop();
            ns = ns.append(c);
        }

        String result = ns.toString();
        return result;
    }

    public static int power(int n1, int n2) {
        int x = 1;
        while (n2!=0){
            x *= n1;
            n2--;
        }
        return x;
    }

    public static int evaluate_postfix(String s) {
        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                st.push(s.charAt(i) - '0');
            } else {
                int n2 = st.peek();
                st.pop();
                int n1 = st.peek();
                st.pop();
                if (s.charAt(i) == '+')
                    st.push(n1 + n2);
                else if (s.charAt(i) == '-')
                    st.push(n1 - n2);
                else if (s.charAt(i) == '*')
                    st.push(n1 * n2);
                else if (s.charAt(i) == '/')
                    st.push(n1 / n2);
                else st.push(power(n1, n2));
            }
        }

        return st.peek();
    }

    //Driver program to test above functions
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int count = 1;
        while (t!=0) {
            t--;
            System.out.println("Case #" + count + ": ");
            String exp = sc.next();
            if (check_valid(exp) == 1) {
               String pf = infixToPostfix(exp);
                int val = evaluate_postfix(pf);
                System.out.println(val);
            } else
                System.out.println("INVALID EXPRESSION");
            count += 1;
        }
    }

}
