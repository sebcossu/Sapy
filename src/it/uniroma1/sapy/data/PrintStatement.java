package it.uniroma1.sapy.data;
import java.util.ArrayList;

public class PrintStatement extends Statement implements Eseguibile
{
    public PrintStatement()
    {
        super("PRINT");
        setRight(new NullStatement());
        left = null;
    }

    @Override
    public ArrayList<Token> getVars()
    {
    	return new ArrayList<Token>();
    }

    @Override
    public String toString()
    {
        return this.left.toString();
    }

    @Override
    public String getValue()
    {
        String toret = val.toString();
        if(this.left == null)
        {
        	return toret + "\n";
        }
        toret += this.left.getValue().toString();
        return toret;
    }

    @Override
    public Object getLabel()
    {
        return val;
    }

    @Override
    public Boolean canHaveChildren()
    {
        return true;
    }

    @Override
    public Integer esegui()
    {
    	if(left == null)
    	{
    		System.out.println();
    	}
    	else
    	{
            System.out.print(left.esegui());
    	}
        return Integer.valueOf(1);
    }
}