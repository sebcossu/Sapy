package it.uniroma1.sapy.data;

/**
 * Eccezione causata da simbolo non riconosciuto durante l'analisi lessicale
 *
 * @author Sebastiano Michele Cossu
 */
public class SymbolErrorException extends Exception
{
    public SymbolErrorException()
    {
    }
    
    public SymbolErrorException(String message)
    {
        super(message);
    }
}
