package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class ElseStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore base della classe
	 */
	public ElseStatement()
	{
		super(TokenType.ELSE);
	}

	/**
	 * Restituisce una lista di variabili, se ospitate, altrimenti una lista vuota.
	 * 
	 * @return una lista vuota
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
    	return "ELSE";
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli
     * 
     * @return falso
     */
    @Override
    public Boolean canHaveChildren()
    {
    	return false;
    }
    
    /**
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * @return il valore del nodo
     */
    @Override
    public Object getLabel()
    {
    	return this.val;
    }
    
    /**
     * Restituisce il valore del nodo
     * 
     * @return il valore del nodo
     */
    @Override
    public Object getValue()
    {
    	return this.val;
    }
    
    /**
     * Esegue il nodo
     * 
     * @return un intero che manda avanti il program counter
     */
    @Override public Integer esegui()
    {
    	return Integer.valueOf(1);
    }
}
