package it.uniroma1.sapy.data;

/*
 * @author Sebastiano Michele Cossu
 */
public class Istruzione<T> implements Eseguibile
{
    private Statement root;
    
    /*
     * Costruttore della classe
     * 
     */
    public Istruzione()
    {
        this.root = null;
    }

    /**
     * Costruttore della classe
     * @param i : uno statement che viene preso come root del nodo <code>Istruzione</code>
     */
    public Istruzione(Statement i)
    {
        this.root = i;
    }

    /**
     * Costruttore della classe
     * 
     * @param i un'istruzione da cui essere copiato
     */
    public Istruzione(Istruzione i)
    {
   		this.root = i.getRoot();
    }

    /**
     * Imposta il nodo radice dell'istruzione
     * 
     * @param root il nodo radice
     */
    public void setRoot(Statement root)
    {
        this.root = root;
    }

    /**
     * Restituisce il nodo radice dell'istruzione
     * 
     * @return uno Statement che rappresenta il nodo radice dell'istruzione
     */
    public Statement getRoot()
    {
        return this.root;
    }

    /**
     * Aggiunge un figlio all'albero
     * 
     * @param ch il figlio da aggiungere (Statement)
     */
    public void addChild(Statement ch)
    {
        if(this.root == null)
        {
            root = ch;
        }
        else
        {
            this.root.addChild(ch);
        }
    }
    
    /**
     * Restituisce l'altezza dell'albero
     * 
     * @return un intero
     */
    public int getSize()
    {
        if(this.root == null)
        {
            return 0;
        }
        return this.root.getSize();
    }
    
    /**
     * Pulisce l'albero eliminando la radice
     * 
     */
    public void clear()
    {
        this.root = null;
    }
    
    /**
     * Restituisce vero, se l'albero &egrave; vuoto; altrimenti, falso.
     * 
     * @return un booleano
     */
    public Boolean isEmpty()
    {
        return this.root == null;
    }
    
    /**
     * Restituisce una stringa che rappresenta la classe
     * 
     * @return una stringa che rappresenta la classe
     */
    @Override
    public String toString()
    {
        return (String) this.root.getValue();
    }

    /**
     * Esegue l'istruzione
     * 
     * @return un oggetto che rappresenta il risultato dell'istruzione
     */
    @Override
    public Object esegui()
    {
    	if(this.root != null)
    		return this.root.esegui();
    	else return new Object();
    }
}