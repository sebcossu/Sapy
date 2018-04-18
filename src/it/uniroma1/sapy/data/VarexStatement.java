/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 *
 * @author Sebastiano Michele Cossu
 */
public class VarexStatement extends Statement implements Eseguibile
{
	ArrayList<Token> vars;
	
	/**
	 * Costruttore
	 * 
	 * @param art un ArrayList contenente l'espressione di variabili
	 */
    public VarexStatement(ArrayList<Token> art)
    {
        super(TokenType.VAREX);
        vars = new ArrayList<Token>();
        vars.addAll(art);
        setChildren(new NullStatement(), new NullStatement());
    }    

    /**
     * Imposta l'espressione di variabili
     * 
     * @param art un ArrayList contenente le variabili
     */
    public void setValue(ArrayList<Token> art)
    {
        vars.addAll(art);
    }

    /**
     * Restituisce una lista di variabili, se ci sono; oppure una lista vuota
     * 
     * @return un ArrayList
     */
    @Override
    public ArrayList<Token> getVars()
    {
    	return this.vars;
    }

    /**
     * Imposta il valore della classe
     * 
     */
    @Override
    public void setVal(Object val)
    {
    }
    
    /**
     * Restituisce la classe sotto forma di stringa
     * 
     * @return una stringa
     */
    @Override
    public String toString()
    {
        return val.toString() + " = " + this.vars.toString();
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli; altrimenti, falso
     * 
     * @return un booleano
     */
    @Override
    public Boolean canHaveChildren()
    {
        return false;
    }
    
    /**
     * Restituisce il valore della classe
     * 
     * @return un ArrayList contenente le variabili
     */
    @Override
    public ArrayList<Token> getValue()
    {
        return this.vars;
    }

    /**
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * @return un oggetto (la label)
     */
    @Override
    public Object getLabel()
    {
        return getValue();
    }

    /**
     * Esegue il nodo
     * 
     * @return un ArrayList contenente le variabili
     */
    @Override
    public ArrayList<Token> esegui()
    {
    	return getValue();
    }
}
