package it.uniroma1.sapy.data;

import java.util.ArrayList;
/**
 *
 * @author Sebastiano Michele Cossu
 */
public class IfStatement extends Statement implements Eseguibile
{
	protected Istruzione espressione;
	protected Boolean hasVars = false;
	
	/**
	 * Costruttore della classe
	 * 
	 * @param e un'istruzione che rappresenta l'espressione di validit&agrave;
	 */
    public IfStatement(Istruzione e)
    {
        super(TokenType.IF);
        espressione = new Istruzione(e);
        hasVars = false;
    }

    /**
     * Costruttore della classe
     * 
     * @param ifs uno statement da cui copiare l'espressione di validit&agrave;
     */
    public IfStatement(IfStatement ifs)
    {
        super(TokenType.IF);
        espressione = new Istruzione(ifs.getExpression());
        hasVars = false;
    }

    /**
     * Costruttore della classe per Varex
     * 
     * @param art un ArrayList di Token da cui costruire un IfStatement che supporti il calcolo delle variabili con VarexStatement
     */
    public IfStatement(ArrayList<Token> art)
    {
        super(TokenType.IF);
        espressione = null;
        VarexStatement varex = new VarexStatement(art);
        this.right = varex;
        hasVars = true;
    }

    /**
     * Esegue l'espressione di validit&agrave;
     * 
     * @return un booleano che rappresenta l'esito dell'espressione di validit&agrave;
     */
	public Boolean executeExpr()
	{
		Object res = espressione.esegui();
		if(res instanceof Boolean)
		{
			Boolean x = (Boolean) res;
			if(x == true) return true;
		}
		else if(res instanceof Double)
		{
			Double x = (Double) res;
			if(x == 1) return true;
		}
		else if(res instanceof Integer)
		{
			Integer x = (Integer) res;
			if(x == 1) return true;
		}
		else if(res instanceof String)
		{
			return true;
		}
		return false;
	}

	/**
	 * Restituisce l'espressione di validit&agrave;
	 * 
	 * @return una <code>Istruzione</code> che rappresenta l'espressione di validit&agrave; del nodo
	 */
	public Istruzione getExpression()
    {
		if(espressione == null)
			return new Istruzione();
		return this.espressione;
    }
    
	/**
	 * Restituisce una lista di variabili, se ci sono, altrimenti una lista vuota
	 * 
	 * @return un ArrayList contenente le variabili conservate dal nodo
	 */
    @Override
    public ArrayList<Token> getVars()
    {
    	if(this.canHaveChildren())
    		return this.right.getVars();
    	else return new ArrayList<Token>();
    }

    /**
     * Restituisce una stringa che rappresnta il nodo
     * 
     * @return una stringa che rappresenta il nodo
     */
    @Override
    public String toString()
    {
    	if(espressione != null)
    		return "IFSTAT + " + espressione.esegui().toString() + " -- made by " + espressione.getRoot().toString();
    	else return "IFSTAT " + this.right.esegui().toString();
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
        return val;
    }

    /**
     * Esegue lo statement
     * 
     * @return il valore dell'espressione.
     */
    @Override
    public Object esegui()
    {
    	if(this.hasVars == true)
    	{
    		return this.right.esegui();
    	}
    	else
    	{
        	if(executeExpr() == true)
        	{
        		return Integer.valueOf(1);
        	}
        	return Integer.valueOf(-1);
    	}
    }
}
