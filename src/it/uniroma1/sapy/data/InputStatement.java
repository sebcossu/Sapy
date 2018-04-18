package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class InputStatement extends Statement implements Eseguibile
{
	protected String label;
	
	/**
	 * Costruttore della classe
	 * 
	 * @param lab prende in input una stringa che rappresenta la label della variabile in cui salvare il valore catturato da input.
	 */
    public InputStatement(String lab)
    {
        super("INPUT");
        setRight(new NullStatement());
        setLeft(new NullStatement());
        this.label = lab;
    }

    /**
     * Restituisce una lista di variabili, se ci sono; altrimenti, una lista vuota.
     * 
     * @return un ArrayList
     */
    @Override
    public ArrayList<Token> getVars()
    {
    	return new ArrayList<Token>();
    }

    /**
     * Restituisce una stringa che rappresenta la classe
     * 
     * @return una stringa che rappresenta il nodo
     */
    @Override
    public String toString()
    {
        return this.label;
    }

    /**
     * Restituisce una stringa che rappresenta il valore della classe
     * 
     * @return una stringa: il valore della classe
     */
    @Override
    public String getValue()
    {
        return "INPUT";
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
     * Restituisce vero, se il nodo pu&ograve; avere figli; altrimenti, falso.
     * 
     * @return booleano
     */
    @Override
    public Boolean canHaveChildren()
    {
        return false;
    }

    /**
     * Esegue la classe
     * 
     * @return una stringa che rappresenta il nome della variabile da salvare in memoria
     */
    @Override
    public String esegui()
    {
    	return this.label;
    }
}