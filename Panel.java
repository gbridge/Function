import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Panel extends JPanel implements Runnable
{
    //panel width + height
    private int width;
    private int height;
    private double xmax;
    private double xmin;
    private double ymax;
    private double ymin;
    private double xStep;
    private double yStep;
    private Function function;
    
    private boolean[][] graph;
    
    public Panel(Function function, int width, int height, double xmin, double xmax, double ymin, double ymax)
    {
        this.height = height;
        this.width = width;
        
        graph = new boolean[width][height];
        this.xmax = xmax;
        this.xmin = xmin;
        this.ymax = ymax;
        this.ymin = ymin;
        xStep = (xmax - xmin)/height;
        yStep = (ymax - ymin)/width;
        this.function = function;
    }
    @Override
    public void run()
    {
        for(int col = 0; col < width; col++)
        {
            double ans = function.function(xmin + col * xStep);
            if(ans > ymin && ans < ymax)
            {
                //System.out.println(ans + " " + ymin);
                graph[(int)((ans - ymin)/yStep)][col] = true;
            }
        }
    }
    @Override
    public void paintComponent(Graphics g)
    {
        run();
        super.paintComponent(g);
        for(int row = 0; row < graph.length; row++)
            for(int col = 0; col < graph[row].length; col++)
            {
                if(row == -ymin/yStep || col == -xmin/xStep)
                {
                    g.setColor(Color.blue);
                    g.fillRect(col, height - row, 1, 1);
                }
                if(graph[row][col])
                {
                    g.setColor(Color.black);
                    g.fillRect(col, height - row, 2, 2);
                }
            }
    }
}
