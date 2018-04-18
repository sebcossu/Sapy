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
public class AssegnaStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore base della classe
	 */
    public AssegnaStatement()
    {
        super(TokenType.ASSIGN);
    }
    
    /**
     * Restituisce la lista delle variabili ospitabili dal nodo
     * 
     * @return una lista vuota
     */
    @Override
    public ArrayList<Token> getVars()
    {
    	return new ArrayList<Token>();
    }

    /**
     * Restituisce il nodo sotto forma di stringa
     * 
     * @return una stringa che descrive l'operazione
     */
    @Override
    public String toString()
    {
        return "ASSEGNAMENTO";
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli
     * 
     * @return vero
     */
    @Override
    public Boolean canHaveChildren()
    {
        return true;
    }
    
    /**
     * Restituisce il valore del nodo
     * 
     * @return TokenType per l'assegnazione
     */
    @Override
    public Object getValue()
    {
        return val;
    }

    /**
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * @return valore della classe
     */
    @Override
    public Object getLabel()
    {
        return val;
    }

    /**
     * Esegue l'assegnamento
     * 
     * @return il risultato dell'assegnamento
     */
    @Override
    public Object esegui()
    {
        return left.getValue();
    }
}
