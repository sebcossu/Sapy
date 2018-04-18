package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 *
 * @author Sebastiano Michele Cossu
 *
 */
public class StringStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore
	 * @param val il valore della stringa
	 */
	public StringStatement(String val)
	{
		super(val);
		setChildren(new NullStatement(), new NullStatement());
	}

	/**
	 * restituisce una lista di variabili, se esistono, altrimenti una lista vuota
	 * 
	 * @return un ArrayList
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
        return String.valueOf(this.val);
    }
    
    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli
     * 
     * @return un booleano
     */
	@Override
	public Boolean canHaveChildren()
	{
		return false;
	}
	
	/**
	 * Restituisce il valore del nodo
	 * 
	 * @return una stringa
	 */
	@Override
	public String getValue()
	{
		return (String) val;
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
     * Esegue la classe
     * 
     * @return una stringa
     */
	@Override
	public String esegui()
	{
		return (String) val;
	}
}