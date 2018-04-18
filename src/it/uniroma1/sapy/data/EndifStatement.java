package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class EndifStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore base della classe
	 */
	public EndifStatement()
	{
		super(TokenType.ENDIF);
	}
	
	/**
	 * Restituisce una lista di variabili, se ci sono; altrimenti una lista vuota
	 * 
	 * @return una lista
	 */
    @Override
    public ArrayList<Token> getVars()
    {
    	return new ArrayList<Token>();
    }

    /**
     * Restituisce una stringa che rappresenta il nodo
     * 
     * @return una stringa che rappresenta il nodo
     */
    @Override
    public String toString()
    {
    	return "ENDIF";
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli; altrimenti falso.
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
     * @return un oggetto: il valore del nodo
     */
    @Override
    public Object getLabel()
    {
    	return this.val;
    }
    
    /**
     * Restituisce il valore del nodo
     * 
     * @return un oggetto: il valore del nodo
     */
    @Override
    public Object getValue()
    {
    	return this.val;
    }
    
    /**
     * Esegue il nodo
     * 
     * @return un intero per il PC
     */
    @Override public Integer esegui()
    {
    	return Integer.valueOf(1);
    }
}
