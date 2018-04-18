package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class NullStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore
	 */
	public NullStatement()
	{
		super();
	}
	
	/**
	 * Restituisce una lista di variabili, se ci sono, altrimenti una lista vuota
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
        return "NULL";
    }
        
    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli, altrimenti, falso.
     * 
     * @return booleano
     */
	@Override
	public Boolean canHaveChildren()
	{
		return false;
	}
	
	/**
	 * Restituisce il valore della classe
	 * 
	 * @return un oggetto
	 */
	@Override
    public Object getValue()
    {
        return "";
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
     * @return void
     */
	@Override
	public Void esegui()
	{
		return null;
	}
}