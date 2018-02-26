

public class Dice
{
    private int numSides;
    public Dice(int num)
    {
        numSides = num;
    }
    public int roll()
    {
        return (int)(numSides * Math.random()) + 1;
    }
}
