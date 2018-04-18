package it.uniroma1.sapy.interprete;

import it.uniroma1.sapy.lexer.Lexer;
import it.uniroma1.sapy.parser.Parser;
import it.uniroma1.sapy.data.*;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Interprete &egrave; la classe che, come Sapy, segue il processo di analisi lessicale e sintattica del file sorgente, fino alla generazione del ProgrammaEseguibile che esegue appena disponibile.
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class Interprete
{
	public static void main(String args[]) throws SymbolErrorException
	{
		String filename ="";
		
		for(int i = 0; i < args.length; i++)
		{
			filename += args[i];
		}
		String fileContent = "";
		try
		{
			fileContent = new Scanner(new File(filename)).useDelimiter("\\Z").next();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		
		Lexer lex = new Lexer(fileContent); 

        System.out.println();
        Parser par = new Parser(lex.getTokenList());

        try
        {
            ProgrammaEseguibile exe = new ProgrammaEseguibile();
            exe.copyOf(par.parse());
                                
            exe.esegui();

            System.out.println();
        }
        catch(SyntaxErrorException e)
        {
            System.out.println(e);
        }
 	}
}