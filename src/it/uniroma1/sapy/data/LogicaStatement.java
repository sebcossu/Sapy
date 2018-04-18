package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class LogicaStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore
	 * 
	 * @param tt : un token type su cui costruire il nodo logico
	 */
    public LogicaStatement(TokenType tt)
    {
        super(tt);
        if(tt == TokenType.NOT)
            this.setRight(new NullStatement());
    }

    /**
     * Restituisce una lista di variabili, se ci sono; altrimenti una lista vuota.
     * 
     * @return ArrayList
     */
    @Override
    public ArrayList<Token> getVars()
    {
    	return new ArrayList<Token>();
    }

    /**
     * Restituisce una stringa che rappresenta la classe
     * 
     * @return una stringa
     */
    @Override
    public String toString()
    {
        return this.left.toString() + this.right.toString();
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli; altrimenti, falso.
     * 
     * @return un booleano.
     */
    @Override
    public Boolean canHaveChildren()
    {
        return true;
    }

    /**
     * Restituisce una stringa che rappresenta il valore della classe
     * 
     * @return una stringa
     */
    @Override
    public String getValue()
    {
        return val.toString();
    }

    /**
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * @return un oggetto
     */
    @Override
    public Object getLabel()
    {
        return val;
    }

    /**
     * Esegue ricorsivamente l'albero
     * 
     * @return un booleano che rappresenta l'esito dell'esecuzione.
     */
    @Override
    public Boolean esegui()
    {
        if(val == TokenType.AND)
        {
        	Object leftc = left.esegui();
        	Object rightc = right.esegui();
        	if( leftc instanceof Double && rightc instanceof Double )
        	{
        		Double leftd = (Double) left.esegui();
        		Double rightd = (Double) right.esegui();
        		if(leftd == 1 && rightd == 1)
        		{
        			return true;
        		}
        	}
        	else if( leftc instanceof Boolean && rightc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) left.esegui()).booleanValue();
                Boolean rightb = ((Boolean) right.esegui()).booleanValue();
                return (leftb && rightb);
        	}
        	else if( leftc instanceof String && rightc instanceof String )
        	{
        		return true;
        	}
        }
        else if(val == TokenType.OR)
        {
        	Object leftc = left.esegui();
        	Object rightc = right.esegui();
        	if( leftc instanceof Double && rightc instanceof Double )
        	{
        		Double leftd = (Double) left.esegui();
        		Double rightd = (Double) right.esegui();
        		if(leftd == 1 || rightd == 1)
        		{
        			return true;
        		}
        	}
        	else if( leftc instanceof Boolean && rightc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) left.esegui()).booleanValue();
                Boolean rightb = ((Boolean) right.esegui()).booleanValue();
                return (leftb || rightb);
        	}
        	else if( leftc instanceof String && rightc instanceof String )
        	{
        		return true;
        	}
        }
        else if(val == TokenType.NOT)
        {
        	Object leftc = right.esegui();
        	if( leftc instanceof Double )
        	{
        		Double leftd = (Double) right.esegui();
        		if(leftd == Double.valueOf(1))
        		{
        			return false;
        		}
        	}
        	else if( leftc instanceof Integer )
        	{
        		Integer leftd = (Integer) right.esegui();
        		if(leftd == 1)
        		{
        			return false;
        		}
        	}
        	else if( leftc instanceof Boolean )
        	{
                Boolean leftb = ((Boolean) right.esegui()).booleanValue();
                return (!leftb);
        	}
        	else if( leftc instanceof String )
        	{
        		return false;
        	}
        	return true;
        }
        return false;
    }
}