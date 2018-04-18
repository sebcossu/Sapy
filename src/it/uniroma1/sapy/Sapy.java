package it.uniroma1.sapy;

import it.uniroma1.sapy.lexer.Lexer;
import it.uniroma1.sapy.parser.Parser;
import it.uniroma1.sapy.data.*;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Classe principale del progetto.
 * Prende un file sorgente in input e lo passa al Lexer per generare la lista di token da passare al Parser che produrr&agrave; il ProgrammaEseguibile che sar&agrave, infine, eseguito da Sapy stesso.
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class Sapy
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