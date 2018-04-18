package it.uniroma1.sapy.data;

import java.util.ArrayList;

/**
 *
 * @author seb
 */
public class NextStatement extends Statement implements Eseguibile
{
    private String label;
    private Boolean hasVars = true;
    
    public NextStatement()
    {
        super(TokenType.NEXT);
    }

    public NextStatement(String label)
    {
        super(TokenType.NEXT);
        this.label = label;
    }
    
   /**
    * Restituisce una lista di variabili, se ci sono, altrimenti una lista vuota
    * 
    * @return un ArrayList contenente le variabili conservate dal nodo
    */
    @Override
    public ArrayList<Token> getVars()
    {
        return new ArrayList<Token>();
    }

    /**
     * Restituisce una stringa che rappresnta il nodo
     * 
     * @return una stringa che rappresenta il nodo
     */
    @Override
    public String toString()
    {
        String ret = "NEXT " + this.label;
        return ret;
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli; altrimenti, falso.
     * 
     * @return un booleano
     */
    @Override
    public Boolean canHaveChildren()
    {
    	return this.hasVars;
    }

    /**
     * Restituisce il valore del nodo
     * 
     * @return un oggetto
     */
    @Override
    public Object getValue()
    {
        return this.val;
    }

    /**
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * @return il valore del nodo
     */
    @Override
    public Object getLabel()
    {
        return this.label;
    }

    /**
     * Esegue lo statement
     * 
     * @return il valore dell'espressione.
     */
    @Override
    public Integer esegui()
    {
        return 1;
    }
}
