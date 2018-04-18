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
public class VariabileStatement extends Statement implements Eseguibile
{
    private String valore;
    private String label;
	protected Boolean hasVars = false;

	/**
	 * Costruttore
	 * 
	 * @param label label della variabile
	 */
    public VariabileStatement(String label)
    {
        super(TokenType.VARIABLE);
        this.label = label;
        setChildren(new NullStatement(), new NullStatement());
    }
    
    /**
     * Costruttore
     * 
     * @param label label della variabile
     * @param valore valore della variabile
     */
    public VariabileStatement(String label, String valore)
    {
        super(TokenType.VARIABLE);
        this.valore = valore;
        this.label = label;
        setChildren(new NullStatement(), new NullStatement());
    }

    /**
     * Costruttore
     * 
     * @param varia statement che rappresenta la variabile da cui clonare l'attuale variabile
     */
    public VariabileStatement(Statement varia)
    {
        super(TokenType.VARIABLE);
        this.label = varia.getLabel().toString();
        this.valore = varia.esegui().toString();
        setChildren(new NullStatement(), new NullStatement());
    }

    /**
     * Costruttore
     * 
     * @param lab label della variabile
     * @param art arraylist di variabili da calcolare con Varex per il calcolo del valore
     */
    public VariabileStatement(String lab, ArrayList<Token> art)
    {
        super(TokenType.VARIABLE);
        VarexStatement varex = new VarexStatement(art);
        this.right = varex;
        this.label = lab;
        hasVars = true;
    }

    /**
     * Imposta il valore della variabile
     * 
     * @param v il valore della variabile
     */
    public void setValue(String v)
    {
        this.valore = v;
    }

    /**
     * Restituisce una lista di variabili se ci sono, altrimenti una lista vuota
     * 
     * @return ArrayList
     */
    @Override
    public ArrayList<Token> getVars()
    {
    	if(this.canHaveChildren())
    		return this.right.getVars();
    	else return new ArrayList<Token>();
    }

    /**
     * Imposta il valore della classe
     * 
     */
    @Override
    public void setVal(Object val)
    {
        setValue(val.toString());
    }
    
    /**
     * Restituisce una stringa che rappresenta la classe
     * 
     * @return una stringa
     */
    @Override
    public String toString()
    {
        return this.valore.toString();
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli; altrimenti falso.
     * 
     * @return un booleano
     */
    @Override
    public Boolean canHaveChildren()
    {
    	return this.hasVars;
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
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * @return un oggetto
     */
    @Override
    public Object getLabel()
    {
        return this.label;
    }

    /**
     * Esegue il nodo ricorsivamente
     * 
     * @return un oggetto
     */
    @Override
    public Object esegui()
    {
    	if(this.hasVars)
    	{
    		return this.right.esegui();
    	}
    	else
    	{
        	if(this.valore == null)
        		return this.label;
        	else
        		return this.valore;
    	}
    }
}
