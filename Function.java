import javax.swing.JFrame;

public class Function
{
    private boolean disp = false;
    private String func;
    public Function(String func)
    {
        this.func = func;
    }
    public boolean getDisp()
    {
        return disp;
    }
    //for if we want to deal with disp. It defaults to false
    //disp controls the messages placed periodically throughout this thing
    public Function(String func, boolean disp)
    {
        this.func = func;
        this.disp = disp;
    }
    //main processing. Breaks down the function to get an answer
    public double function(double x)
    {
        String fun = func;
        if(disp){System.out.println(fun + " start");}
        //get rid of the spaces 3x + 2 turns to 3x+2
        while(fun.indexOf(" ") != -1)
        {
            fun = fun.substring(0, fun.indexOf(" ")) + 
                fun.substring(fun.indexOf(" ") + 1);
            if(disp){System.out.println(fun + " take space");}
        }
        //replace letters with values, x to the input, pi to 3.14, etc.
        fun = replace(fun, "x", x);
        fun = replace(fun, "pi", Math.PI);
        fun = replace(fun, "e", Math.E);
        fun = shouldBeStar(fun);
        //treats things in paranthaces as their own function and breaks them
        //down first. (2x)^2 if x = 2 makes a new function of 2x, finds it to
        //be 4, then replaces it in the original, making 4^2
        while(fun.indexOf("(") != -1)
        {
            //System.out.println(fun.indexOf("(") + 1 + " " + otherPar(fun));
            fun = fun.substring(0, fun.indexOf("(")) + 
                 new Function(fun.substring(fun.indexOf("(") + 1,
                 otherPar(fun)), disp).function(x) + 
                 fun.substring(otherPar(fun) + 1);

            if(disp)System.out.println(fun + " remove (");
        }
        //does functions and operations in order. sin(0) to 0, 3*2 to 6
        fun = simplifyLoop(fun, new String[] {"sin", "cos", "tan", "csc", "cot", "sec", "log", "ln"});
        fun = simplifyLoop(fun, "^");
        fun = simplifyLoop(fun, new String[] {"*", "/"});
        fun = simplifyLoop(fun, new String[] {"+", "-"});
        //converts the resulting string to a returnable double
        double ret = stringToDouble(fun);
        if(disp) System.out.println(ret);
        return ret;
    }
    //performs an operation or preprogrammed function (like sin or log)
    private String simplify(String mat, String str)
    {
        double[] thing = surround(mat, str);
        String ret = mat.substring(0, (int)(thing[0] + .00001));
        if(str.equals("^")){ret += Math.pow(thing[2], thing[3]);}
        else if(str.equals("*")){ret += thing[2] * thing[3];}
        else if(str.equals("/")){ret += thing[2] / thing[3];}
        else if(str.equals("+")){ret += thing[2] + thing[3];}
        else if(str.equals("-"))
        {
            if(!mat.substring(mat.indexOf("-")-1,mat.indexOf("-")).equals("E")
                && mat.indexOf("-") != 0)
                ret += thing[2] - thing[3];
        }
        else if(str.equals("sin")){ret += Math.sin(thing[3]);}
        else if(str.equals("cos")){ret += Math.cos(thing[3]);}
        else if(str.equals("tan")){ret += Math.tan(thing[3]);}
        else if(str.equals("csc")){ret += 1/(Math.sin(thing[3]));}
        else if(str.equals("cot")){ret += 1/(Math.tan(thing[3]));}
        else if(str.equals("sec")){ret += 1/(Math.cos(thing[3]));}
        else if(str.equals("log")){ret += Math.log(thing[3])/Math.log(10);}
        else if(str.equals("ln")){ret += (Math.log(thing[3])/Math.log(Math.E));}
        
        ret += mat.substring((int)(thing[1] + 0.00001));
        if(disp) System.out.println(ret + " " + "Simplify of" + " " + thing[2] + " " + thing[3]);
        return ret;
    }
    //loops to simplify all of an operation
    private String simplifyLoop(String fun, String a)
    {
        while(fun.indexOf(a) > -1)
        {
            fun = simplify(fun, a);
        }
        if(disp){System.out.println(fun + " simplifyLoop of " + a);}
        return fun;
    }
    //loops to simplify all of several operations that are at the same
    //place in the order of operations (+ and -)
    private String simplifyLoop(String fun, String[] a)
    {
        if(disp && indPosMin(fun, a) != -1)System.out.println(fun + " " + "success " + a[indPosMin(fun, a)]);
        else if(disp)System.out.println(fun + " fail " + a[0]);
        while(indPosMin(fun, a) != -1)
        {
            if(disp){System.out.println(fun + " simploop of " + a[indPosMin(fun, a)]);}
            fun = simplify(fun, a[indPosMin(fun, a)]);
        }
        return fun;
    }
    //returns an array describing the surrounding numbers of a function or
    //preprogrammed operation. in the form {index of start of first number, 
    //index end of second number, value of first number, value of second number
    //2-25*3 while assessing the * returns {2.0, 5.0, 25.0, 3.0}
    private double[] surround(String str, String symb)
    {
        double[] ret = new double[4];
        if(isOperation(symb)) 
            ret[0] = findStartNum(str, str.indexOf(symb) - 1);
        else 
            ret[0] = str.indexOf(symb);
        ret[2] = stringToDouble(
            str.substring(
            (int)(ret[0] + 0.0000001), str.indexOf(symb)));
        //System.out.println(str);
        ret[1] = findEndNum(str, str.indexOf(symb) + symb.length());
        ret[3] = stringToDouble(
            str.substring(str.indexOf(symb) + symb.length(),
            (int)(ret[1] + 0.00001)));
        if(disp) System.out.println(str + " " + "surround" + " " + symb + " it's " + 
            ret[0] + ", " + ret[1] + ", " + ret[2] + ", " + ret[3] + ", " +
            str.substring(
            (int)(ret[0] + 0.0000001), str.indexOf(symb)));
        return ret;
    }
    //places in necessary stars. 2sin3 to 2*sin3
    private String shouldBeStar(String fun)
    {
        for(int num = 0; num + 1 < fun.length(); num++)
        {
            //System.out.println(fun.substring(num, num + 3) + " " + fun.substring(num + 1, num + 4));
            if(isDigit(fun.substring(num, num + 1)) && 
                !isDigit(fun.substring(num + 1, num + 2)) && 
                !isSymbol(fun.substring(num + 1, num + 2)))
                {
                    fun = fun.substring(0, num + 1) + "*" + fun.substring(num + 1);
                    num--;
                    if(disp){System.out.println(fun  + " shouldBeStar case1");}
                }
            else if(isDigit(fun.substring(num, num + 1)) &&
                fun.substring(num + 1, num + 2).equals("(") &&
                !fun.substring(num, num+1).equals("-")/*
                fun.substring(num, num + 1).equals(")") && 
                !isOperation(fun.substring(num + 1, num + 2)) &&
                !isFunction(fun.substring(num + 1, num + 4))*/) 
                {

                    fun = fun.substring(0, num + 1) + "*" + fun.substring(num + 1);
                    num--;
                    if(disp){System.out.println(fun + " shouldBeStar case2");}
                }
            else if(fun.substring(num, num + 1).equals(")") &&
                fun.substring(num + 1, num + 2).equals("("))
                {
                    fun = fun.substring(0, num + 1) + "*" + fun.substring(num + 1);
                    num--;
                    if(disp){System.out.println(fun + " shouldBeStar case2");}
                }
        }
        return fun;
    }
    //replaces a given String with another to simplify. 
    //3*pi to 3*3.14, x+2 if x = 1 to 1+2
    private String replace(String fun, String str, double num)
    {
        while(fun.indexOf(str) != -1)
        {
            fun = fun.substring(0, fun.indexOf(str)) + 
                "(" + num + ")" +
                fun.substring(fun.indexOf(str) + str.length());
            fun = shouldBeStar(fun);
            if(disp){System.out.println(fun + " replace " + str + " with " + num);}
        }
        return fun;
    }
    //shows if a given string is a symbol
    private boolean isSymbol(String str)
    {
        return str.equals("+") || 
        str.equals("-") || 
        str.equals("*") || 
        str.equals("/") || 
        str.equals("^") || 
        str.equals("(") || 
        str.equals(")") || 
        str.equals("%");
    }
    //shows if a given string is an operation
    private boolean isOperation(String str)
    {
        return str.equals("+") || 
        str.equals("-") || 
        str.equals("*") || 
        str.equals("/") || 
        str.equals("^") || 
        str.equals("%");
    }
    //shows if a given string is part of a number. The non-digits are so that 
    //the - in -10 registers as part of the number, as does the . in 3.4 and
    //the E in 3.44E-6
    private boolean isDigit(String str)
    {
        //System.out.println(str + "!");
        str = str.substring(0, 1);
        return str.equals("0") || 
        str.equals("1") || 
        str.equals("2") || 
        str.equals("3") || 
        str.equals("4") || 
        str.equals("5") || 
        str.equals("6") || 
        str.equals("7") || 
        str.equals("8") || 
        str.equals("9") ||
        str.equals(".") ||
        str.equals("-") ||
        str.equals("E");
    }
    //shows if a given string is a function
    private boolean isFunction(String str)
    {
        str = str.substring(0, 1);
        return str.equals("sin") ||
        str.equals("cos") ||
        str.equals("tan") ||
        str.equals("csc") ||
        str.equals("cot") ||
        str.equals("sec") ||
        str.substring(0, 2).equals("ln") ||
        str.equals("log");
    }
    //Finds the start of a number given a part of it. if shown the 3 in 243, 
    //it returns the index of the 2
    private int findStartNum(String str, int num)
    {
        if(isDigit(str.substring(num, num + 1)))
        {
            for(int ind = num; ind >= 0; ind--)
            {
                if(!isDigit(str.substring(ind, ind + 1)))
                {
                    if(disp) System.out.println(str + " " + "findStartNum" + " " + ind + 1);
                    return ind + 1;
                }
            }
            if(disp) System.out.println(str + " " + "findStartNum loopEnded" + " " + 0);
            return 0;
        }
        else
        {
            if(disp) System.out.println(str + " " + "findStartNum" + " " + -1);
            return -1;
        }
    }
    //same but the end of a number
    private int findEndNum(String str, int num)
    {
        if(isDigit(str.substring(num, num + 1)))
        {
            for(int ind = num; ind < str.length(); ind++)
            {
                if(!isDigit(str.substring(ind, ind + 1)))
                {
                    return ind;
                }
                else if(str.substring(ind, ind + 1).equals("-") &&
                    ind > num && !str.substring(ind-1, ind).equals("E"))
                {
                    return ind;
                }
            }
            return str.length();
        }
        else
            return -1;
    }
    //converts a string into a double
    public double stringToDouble(String str)
    {
        if(disp) System.out.println("stringToDouble" + " " + str);;
        if(str.equals("")) return 0.0;
        else if(str.equals("Infinity")) return Double.MAX_VALUE;
        else if(str.equals("-Infinity")) return -Double.MAX_VALUE;
        else if(str.equals("NaN")) return -Double.MAX_VALUE;
        double ret = 0.0;
        double mag = 1.0;
        boolean before = true;
        int start = 0;
        if(str.substring(0, 1).equals("-"))
        {
            start = 1;
        }
        for(int ind = start; ind < str.length(); ind++)
        {
            if(before)
            {
                ret *= 10;
            }
            else
            {
                mag /= 10;
            }
            if(str.substring(ind, ind + 1).equals("1")){ret += 1 * mag;}
            else if(str.substring(ind, ind + 1).equals("2")){ret += 2 * mag;}
            else if(str.substring(ind, ind + 1).equals("3")){ret += 3 * mag;}
            else if(str.substring(ind, ind + 1).equals("4")){ret += 4 * mag;}
            else if(str.substring(ind, ind + 1).equals("5")){ret += 5 * mag;}
            else if(str.substring(ind, ind + 1).equals("6")){ret += 6 * mag;}
            else if(str.substring(ind, ind + 1).equals("7")){ret += 7 * mag;}
            else if(str.substring(ind, ind + 1).equals("8")){ret += 8 * mag;}
            else if(str.substring(ind, ind + 1).equals("9")){ret += 9 * mag;}
            else if(str.substring(ind, ind + 1).equals("."))
            {
                ret /= 10;
                before = false;
            }
            else if(str.substring(ind, ind + 1).equals("E"))
            {
                if(before){ret/=10;}
                ret*=Math.pow(10, stringToDouble(str.substring(ind + 1)));
                ind = str.length();
            }
        }
        if(str.substring(0, 1).equals("-"))
        {
            ret *= -1;
        }
        return ret;
    }
    //given an array of strings, returns the lowest first index of all of them
    private int indPosMin(String fun, String[] a)
    {
        int arrInd = -1;
        int strInd = -1;
        for(int num = 0; num < a.length; num++)
        {
            int ind = fun.indexOf(a[num]);
            
            if(ind != -1 && (arrInd == -1 || ind < strInd))
            {
            boolean proceed = false;
            if(ind > 0) proceed = !isOperation(fun.substring(ind - 1, ind)) && 
                    !fun.substring(ind-1, ind).equals("E");
                if(!a[num].equals("-") || proceed)
                {
                    arrInd = num;
                    strInd = ind;
                }
            }
            //System.out.println(arrInd + " " + strInd + " " + fun.indexOf(a[num]));
        }
        //System.out.println(ind);
        //System.out.println();
        if(disp && arrInd != -1) System.out.println(fun + " indPosMin is " + a[arrInd]);
        return arrInd;
    }
    //finds a ('s matching )
    private int otherPar(String fun)
    {
        int par = 0;
        for(int num = fun.indexOf("("); num < fun.length(); num++)
        {
            if(fun.substring(num, num+1).equals("("))
                par++;
            else if(fun.substring(num, num+1).equals(")"))
                par--;
            if(par == 0)
                return num;
        }
        return -1;
    }
    //graphs the function
    public void graph(int width, int height, double xmin, double xmax, double ymin, double ymax)
    {
        Panel panel = new Panel(this, width, height, xmin, xmax, ymin, ymax);
        //System.out.println("here");
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(width, height + 22);
        frame.setVisible(true);
    }
    //same but uses default values
    public void graph()
    {
        this.graph(600, 600, -10, 10, -10, 10);
    }
    public void graph(int width, int height)
    {
        this.graph(width, height, -10, 10, -10, 10);
    }
    public void graph(double xmin, double xmax, double ymin, double ymax)
    {
        this.graph(600, 600, xmin, xmax, ymin, ymax);
    }
}
