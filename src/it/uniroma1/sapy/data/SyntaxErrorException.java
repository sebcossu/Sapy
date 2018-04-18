package it.uniroma1.sapy.data;

/**
 * Eccezione causata da errore sintattico
 *
 * @author Sebastiano Michele Cossu
 */
public class SyntaxErrorException extends Exception
{
	public SyntaxErrorException()
    {
    }
    
    public SyntaxErrorException(String message)
    {
        super(message);
    }
}
