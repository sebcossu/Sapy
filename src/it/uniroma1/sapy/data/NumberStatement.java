package it.uniroma1.sapy.data;
import java.util.ArrayList;

public class NumberStatement extends Statement
{
	public NumberStatement(Double val)
	{
		super(val);
		setChildren(new NullStatement(), new NullStatement());
	}

	public NumberStatement(Integer val)
	{
		super(new Double(val));
		setChildren(new NullStatement(), new NullStatement());
	}

	public NumberStatement(String val)
	{
		super (Double.parseDouble(val));
		setChildren(new NullStatement(), new NullStatement());
	}
	
    @Override
    public ArrayList<Token> getVars()
    {
    	return new ArrayList<Token>();
    }

    @Override
    public String toString()
    {
        return String.valueOf(this.val);
    }
    
	@Override
	public Boolean canHaveChildren()
	{
		return false;
	}
	
	@Override
	public Double getValue()
	{
            return (Double) val;
	}

    @Override
    public Object getLabel()
    {
        return val;
    }

	@Override
	public Double esegui()
	{
		return (Double) val;
	}
}