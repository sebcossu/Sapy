package it.uniroma1.sapy.data;
import java.util.ArrayList;

/**
 * Classe astratta che definisce uno Statement: un nodo di un albero binario
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public abstract class Statement implements Eseguibile
{
    protected Object val;
    protected Statement left;
    protected Statement right;

    /**
     * Costruttore
     */
    public Statement()
    {
        this.val = null;
        this.left = null;
        this.right = null;
    }

    /**
     * Costruttore
     * 
     * @param val un oggetto adottato come radice
     */
    public Statement(Object val)
    {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    /**
     * Costruttore
     * 
     * @param val oggetto preso come radice
     * @param left statement preso come figlio
     */
    public Statement(Object val, Statement left)
    {
        this.val = val;
        this.left = left;
        this.right = null;
    }

    /**
     * Costruttore
     * 
     * @param val oggetto preso come radice
     * @param left statement preso come figlio sinistro
     * @param right statement preso come figlio destro
     */
    public Statement(Object val, Statement left, Statement right)
    {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * Imposta il valore della classe
     * 
     * @param val il valore della root
     */
    public void setVal(Object val)
    {
        this.val = val;
    }

    /**
     * Imposta il figlio sinistro 
     * @param left il figlio sinistro
     */
    public void setLeft(Statement left)
    {
        this.left = left;
    }

    /**
     * Imposta il figlio destro
     * @param right il figlio destro
     */
    public void setRight(Statement right)
    {
        this.right = right;
    }

    /**
     * Imposta entrambi i figli
     * @param left il figlio sinistro
     * @param right il figlio destro
     */
    public void setChildren(Statement left, Statement right)
    {
        this.left = left;
        this.right = right;
    }

    /**
     *  restituisce vero, se il nodo &egrave; una foglia
     * @return un booleano
     */
    public Boolean isLeaf()
    {
    	return !this.canHaveChildren();
    }

    /**
     * aggiunge un figlio all'albero
     * @param ch il nodo da aggiungere
     * @return un booleano
     */
    public Boolean addChild(Statement ch)
    {
        if(this.canHaveChildren() == false)
        {
            return false;
        }
        else if(this.isLeaf())
        {
            this.setLeft(ch);
        }
        else
        {
            if(this.left == null)
            {
                this.left = ch;
            }
            else if(this.right == null)
            {
                this.right = ch;
            }
            else
            {
                if(this.left.addChild(ch) == false)
                {
                    if(this.right.addChild(ch) == false)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * restituisce l'altezza dell'albero
     * @return un intero
     */
    public int getSize()
    {
        int size = 0;
        if(this.canHaveChildren())
        {
            if(this.left != null && (this.left instanceof NullStatement == false))
            {
                size += this.left.getSize();
            }
            if(this.right != null && (this.right instanceof NullStatement == false))
            {
                size += this.right.getSize();
            }
        }
        return 1 + size;
    }

    /**
     * restituisce una lista di variabili, se ci sono, altrimenti una lista vuota
     * @return un ArrayList
     */
    public abstract ArrayList<Token> getVars();
    
    /**
     * Restituisce il nodo sotto forma di stringa
     * @return una stringa
     */
    @Override
    public abstract String toString();

    /**
     * restituisce vero, se il nodo pu&ograve; avere figli
     * @return un booleano
     */
    public abstract Boolean canHaveChildren();
    /**
     * Restituisce un'etichetta, se esiste, altrimenti restituisce il valore della classe
     * 
     * 
     * @return un oggetto
     */
    public abstract Object getLabel();
    /**
     * Restituisce il valore della classe
     * @return un oggetto
     */
    public abstract Object getValue();
    /**
     * Esegue il nodo
     * @return un oggetto
     */
    @Override public abstract Object esegui();
}