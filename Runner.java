
import java.util.Scanner;
public class Runner
{
    public static void main(String[] args)
    {
        //String test = test();
        //System.out.println(test);
        //System.out.println(new Function("8-2-3", true).function(5));
        //System.out.println(new Function("logx", true).function(0.00001));
        
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        String func = "hi";
        
        System.out.println();
        System.out.println("Enter a function: ");
        func = reader.nextLine();
        if(func.length() > 0)
        {
            Function function = new Function(func);            
            function.graph();
            //System.out.println(function.function(-10));
            //System.out.println("Answer is " + function.function(-10));
        }
    }
    private static boolean equals(double one, double two)
    {
        return one - two > -0.00001 && one - two < 0.00001;
    }
    private static String test()
    {
        if(!equals(new Function("3 ").function(5), 3))
            return "3 ";
        if(!equals(new Function("x").function(5), 5))
            return "x";
        if(!equals(new Function("pi").function(5), Math.PI))
            return "pi";
        if(!equals(new Function("2x").function(5), 10))
            return "2x";
        if(!equals(new Function("2(x)").function(5), 10))
            return "2(x)";
        if(!equals(new Function("(2 + 3)x").function(5), 25))
            return "(2 + 3)x ";
        if(!equals(new Function("3sinx").function(2), 3*Math.sin(2)))
            return "3sinx";
        if(!equals(new Function("logx").function(0.00001), -5))
            return "logx";
        if(!equals(new Function("log1 + log10").function(0.00001), 1))
            return "log1 + log10";
        if(!equals(new Function("2^x").function(2), 4))
            return "2^x";
        if(!equals(new Function("2^x + 3^x").function(2), 13))
            return "2^x + 3^x";
        if(!equals(new Function("2*3").function(5), 6))
            return "2*3";
        if(!equals(new Function("2*3*4").function(5), 24))
            return "2*3*4";
        if(!equals(new Function("2/3").function(5), 2.0/3))
            return "2/3";
        if(!equals(new Function("8/4/2").function(5), 1))
            return "8/4/2";
        if(!equals(new Function("2+4").function(5), 6))
            return "2+4";
        if(!equals(new Function("2+4+1").function(5), 7))
            return "2+4+1";
        if(!equals(new Function("-3").function(5), -3))
            return "-3";
        if(!equals(new Function("1-2").function(5), -1))
            return "1-2";
        if(!equals(new Function("8-3-2").function(5), 3))
            return "8-2-3";
        if(!equals(new Function("3*-3").function(5), -9))
            return "3*-3";
        if(!equals(new Function("2.0E-2").function(5), 0.02))
            return "2.0E-2";
        if(!equals(new Function("2.0E-2 + 2.0E-3").function(5), 0.022))
            return "2.0E-2 + 2.0E-3";
        if(!equals(new Function("3-(2)").function(5), 1))
            return "3-(2)";
        if(!equals(new Function("2*3").function(5), 6))
            return "2*3";
        if(!equals(new Function("3.2").function(5), 3.2))
            return "3.2";
        return "good";
    }
    
}
