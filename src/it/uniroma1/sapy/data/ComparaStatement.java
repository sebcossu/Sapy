package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class ComparaStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore base della classe
	 * 
	 * @param tt il TokenType che costruisce la classe
	 */
    public ComparaStatement(TokenType tt)
    {
        super(tt);
    }

    /**
     * Restituisce la lista delle variabili ospitabili dalla classe, oppure una lista vuota.
     * 
     * @return una lista vuota
     */
    @Override
    public ArrayList<Token> getVars()
    {
    	return new ArrayList<Token>();
    }

    /**
     * Restituisce il nodo sotto forma di stringa
     * 
     * @return una stringa che rappresenta il nodo
     */
    @Override
    public String toString()
    {
    	String concat = "";
    	if(this.right != null)
    	{
    		concat += this.right.toString();
    	}
    	concat += " - ";
    	if(this.left != null)
    	{
    		concat += this.left.toString();
    	}
        return this.val.toString() + " " + concat;
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli.
     * 
     * @return vero
     */
    @Override
    public Boolean canHaveChildren()
    {
        return true;
    }

    /**
     * Restituisce il valore del nodo
     * 
     * @return il TokenType che rappresenta il nodo
     */
    @Override
    public String getValue()
    {
        return val.toString();
    }

    /**
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * @return il TokenType del nodo
     */
    @Override
    public Object getLabel()
    {
        return val;
    }

    /**
     * Esegue il nodo ricorsivamente
     * 
     * @return un booleano, risultato del confronto
     */
    @Override
    public Boolean esegui()
    {
        if(val == TokenType.GREATER)
        {
        	Object leftc = left.esegui();
        	Object rightc = right.esegui();
        	if( leftc instanceof Double && rightc instanceof Double )
        	{
        		Double leftd = (Double) left.esegui();
        		Double rightd = (Double) right.esegui();
        		if(rightd > leftd)
        		{
        			return true;
        		}
        	}
        	else if( leftc instanceof Boolean && rightc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) left.esegui()).booleanValue();
                Boolean rightb = ((Boolean) right.esegui()).booleanValue();
                return (rightb == true && leftb == false);
        	}
        	else if( leftc instanceof String && rightc instanceof String )
        	{
        		return true;
        	}
        }
        else if(val == TokenType.LESS)
        {
        	Object leftc = left.esegui();
        	Object rightc = right.esegui();
        	if( leftc instanceof Double && rightc instanceof Double )
        	{
        		Double leftd = (Double) left.esegui();
        		Double rightd = (Double) right.esegui();
        		if(rightd < leftd)
        		{
        			return true;
        		}
        	}
        	else if( leftc instanceof Boolean && rightc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) left.esegui()).booleanValue();
                Boolean rightb = ((Boolean) right.esegui()).booleanValue();
                return (rightb == false && leftb == true);
        	}
        	else if( leftc instanceof String && rightc instanceof String )
        	{
        		return true;
        	}
        }
        else if(val == TokenType.GTE)
        {
        	Object leftc = left.esegui();
        	Object rightc = right.esegui();
        	if( leftc instanceof Double && rightc instanceof Double )
        	{
        		Double leftd = (Double) left.esegui();
        		Double rightd = (Double) right.esegui();
        		if(rightd >= leftd)
        		{
        			return true;
        		}
        	}
        	else if( leftc instanceof Boolean && rightc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) left.esegui()).booleanValue();
                Boolean rightb = ((Boolean) right.esegui()).booleanValue();
                return ((rightb == true && leftb == true) || (rightb == true && leftb == false) || (rightb == false && leftb == false));
        	}
        	else if( leftc instanceof String && rightc instanceof String )
        	{
        		return true;
        	}
        }
        else if(val == TokenType.LTE)
        {
        	Object leftc = left.esegui();
        	Object rightc = right.esegui();
        	if( leftc instanceof Double && rightc instanceof Double )
        	{
        		Double leftd = (Double) left.esegui();
        		Double rightd = (Double) right.esegui();
        		if(rightd <= leftd)
        		{
        			return true;
        		}
        	}
        	else if( leftc instanceof Boolean && rightc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) left.esegui()).booleanValue();
                Boolean rightb = ((Boolean) right.esegui()).booleanValue();
                return ((rightb == false && leftb == true) || (rightb == true && leftb == true) || (rightb == false && leftb == false));
        	}
        	else if( leftc instanceof String && rightc instanceof String )
        	{
        		return true;
        	}
        }
        else if(val == TokenType.EQUAL)
        {
        	Object leftc = left.esegui();
        	Object rightc = right.esegui();
        	if( leftc instanceof Double && rightc instanceof Double )
        	{
        		Double leftd = Double.parseDouble(leftc.toString());
        		Double rightd = Double.parseDouble(rightc.toString());
        		if(rightd.doubleValue() == leftd.doubleValue())
        		{
        			return true;
        		}
        	}
        	else if( leftc instanceof Boolean && rightc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) left.esegui()).booleanValue();
                Boolean rightb = ((Boolean) right.esegui()).booleanValue();
                return ((rightb == true && leftb == true) || (rightb == false && leftb == false));
        	}
        	else if( leftc instanceof String && rightc instanceof String )
        	{
        		return true;
        	}
        	else
        	{
        		return rightc == leftc;
        	}
        }
        else if(val == TokenType.NOTEQUAL)
        {
        	Object leftc = left.esegui();
        	Object rightc = right.esegui();
        	if( leftc instanceof Double && rightc instanceof Double )
        	{
        		Double leftd = Double.parseDouble(leftc.toString());
        		Double rightd = Double.parseDouble(rightc.toString());
        		if(rightd.doubleValue() != leftd.doubleValue())
        		{
        			return true;
        		}
        	}
        	else if( leftc instanceof Boolean && rightc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) left.esegui()).booleanValue();
                Boolean rightb = ((Boolean) right.esegui()).booleanValue();
                return ((rightb == true && leftb == false) || (rightb == false && leftb == true));
        	}
        	else if( leftc instanceof String && rightc instanceof String )
        	{
        		return true;
        	}
        	else
        	{
        		return rightc == leftc;
        	}
        }

        return false;
    }
}