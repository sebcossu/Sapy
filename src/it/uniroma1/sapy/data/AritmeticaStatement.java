package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class AritmeticaStatement extends Statement implements Eseguibile
{
	/**
	 * Costruttore di un'operazione aritmetica
	 * 
	 * @param tt il token type da cui creare l'operazione aritmetica
	 */
    public AritmeticaStatement(TokenType tt)
    {
        super(tt);
    }

    /**
     * Restituisce l'operazione sotto forma di stringa (senza risolverla)
     * 
     * @return una stringa rappresentante l'operazione
     */
    @Override
    public String toString()
    {
        return this.left.toString() + this.right.toString();
    }

    /**
     * Restituisce vero, se il nodo pu&ograve; avere figli
     * 
     * @return vero, se il nodo pu&ograve; avere figli; altrimenti, falso.
     */
    @Override
    public Boolean canHaveChildren()
    {
        return true;
    }

    /**
     * Restituisce il nodo in maniera schematizzata
     * 
     * @return una stringa che rappresenta il nodo
     */
    @Override
    public String getValue()
    {
        String toret = "\nARIT: ";
        if(this.val == TokenType.PLUS)
            toret += "+ (";
        else if(this.val == TokenType.MINUS)
            toret += "-";
        else if(this.val == TokenType.MUL)
            toret += "*";
        else if(this.val == TokenType.DIV)
            toret += "/";
        else if(this.val == TokenType.MOD)
            toret += "%";
        
        toret += "RIGHT: " + this.right.getValue().toString();
        toret += ", LEFT: " + this.left.getValue().toString();
        toret += ")";
        return toret;
    }

    /**
     * Restituisce il token che rappresenta l'operazione
     * 
     * @return un oggetto che contiene il TokenType
     */
    @Override
    public Object getLabel()
    {
        return val;
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
     * Esegue ricorsivamente l'operazione attraversando il sottoalbero rappresentato dal nodo
     * 
     * @return il risultato dell'operazione (Double)
     */
    @Override
    public Double esegui()
    {
        if(val == TokenType.PLUS)
        {
            return new Double((((Double)right.esegui()) + ((Double)left.esegui())));
        }
        else if(val == TokenType.MINUS)
        {
            return new Double((((Double)right.esegui()) - ((Double)left.esegui())));
        }
        else if(val == TokenType.MUL)
        {
            return new Double((((Double)right.esegui()) * ((Double)left.esegui())));
        }
        else if(val == TokenType.DIV)
        {
            return new Double((((Double)right.esegui()) / ((Double)left.esegui())));
        }
        else if(val == TokenType.MOD)
        {
            return new Double((((Double)right.esegui()) % ((Double)left.esegui())));
        }

        return -1.0;
    }
}