/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma1.sapy.data;

import java.util.ArrayList;
/**
 *
 * @author seb
 */
public class ForStepStatement extends Statement implements Eseguibile
{
    private ArrayList<Token> vars = new ArrayList<Token>();
    private Boolean hasVars = true;
    
    public ForStepStatement(Token lab, Token val, Token toval, Token step)
    {
        super(TokenType.STEP);
        vars.add(lab);
        vars.add(val);
        vars.add(toval);
        vars.add(step);
    }
    
    public ForStepStatement(ForStatement fs)
    {
        super(TokenType.STEP);
        this.vars.addAll(fs.getVars());
    }

   /**
    * Restituisce una lista di variabili, se ci sono, altrimenti una lista vuota
    * 
    * @return un ArrayList contenente le variabili conservate dal nodo
    */
    @Override
    public ArrayList<Token> getVars()
    {
        return this.vars;
    }

    /**
     * Restituisce una stringa che rappresnta il nodo
     * 
     * @return una stringa che rappresenta il nodo
     */
    @Override
    public String toString()
    {
        String ret = "RET ";
        for(Token t : vars)
        {
            ret += t.getLexeme().toString() + " / ";
        }
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
        return val;
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
