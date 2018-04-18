package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class ThenStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore
	 */
	public ThenStatement()
	{
		super(TokenType.THEN);
	}
		
	/**
	 * restituisce una lista di variabili, se ci sono, altrimenti una lista vuota
	 * 
	 * @return un arraylist
	 */
    @Override
    public ArrayList<Token> getVars()
    {
    	return new ArrayList<Token>();
    }

    /**
     * restituisce una stringa che rappresenta la classe
     * 
     *  @return una stringa
     *  
     */
    @Override
    public String toString()
    {
    	return "THEN";
    }

    /**
     * Restituisce vero se il nodo pu&ograve; avere figli, altrimenti falso
     * 
     * @return un booleano
     */
    @Override
    public Boolean canHaveChildren()
    {
    	return false;
    }
    
    /**
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * @return un oggetto
     */
    @Override
    public Object getLabel()
    {
    	return this.val;
    }
    
    /**
     * Restituisce il valore della classe
     * 
     * @return un oggetto
     */
    @Override
    public Object getValue()
    {
    	return this.val;
    }
    
    /**
     * Esegue la classe
     * 
     * @return un intero
     */
    @Override public Integer esegui()
    {
    	return Integer.valueOf(1);
    }
}
