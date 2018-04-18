package it.uniroma1.sapy.lexer;

import it.uniroma1.sapy.data.Token;
import it.uniroma1.sapy.data.TokenType;
import it.uniroma1.sapy.data.SymbolErrorException;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * E' la classe che, a partire dal file sorgente, effettua l'analisi lessicale e genera una lista di token e la restituisce.
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class Lexer
{
    private ArrayList<String> linesList = new ArrayList<String>();
    private ArrayList<Token> tokenList = new ArrayList<Token>();
    private String literal = "[a-zA-z]+";
    private String numeric = "^[0-9]+([.,][0-9]{1,3})?$";
    private String numericmix = "[0-9]+([.,][0-9]{1,3})?+([\\+\\*-/%]+[0-9]+([.,][0-9]{1,3})?)*";
    private String conString = "^(\")(.)*(\")";
    private String conIdentifiers = "^(\\$)[a-zA-Z]+[a-zA-Z0-9]*";
    private String undefined = "[@#^_[{]};.\\?.]+";
    private String operators = "[\\+-/\\*%]";
    private String logical = "[&\\|!]";
    private String logical_names = "AND|OR|NOT";
    private String comparison = "<=|>=|[=><]|!=";
    private String separators = "[:,\\(\\)\\n]";
    private String[] keywords = {"TRUE", "FALSE", "NULL", "PRINT", "INPUT", "IF", "THEN", "ELSE", "ENDIF", "FOR", "TO", "DO", "NEXT", "STEP", "WHILE", "LOOP", "REM", "AND", "OR", "NOT"};
    private String[] opSymbols = {"+", "-", "/", "*", "%"};
    private String[] opNames = {"PLUS", "MINUS", "DIV", "MUL", "MOD"};
    private String[] logSymbols = {"&", "|", "!"};
    private String[] logNames = {"AND", "OR", "NOT"};
    private String[] cmpSymbols = {"=", ">", "<", ">=", "<=", "!="};
    private String[] cmpNames = {"EQUAL", "GREATER", "LESS", "GTE", "LTE", "NOTEQUAL"};
    private String[] sepSymbols = {":", ",", "(", ")", "\n"};
    private String[] sepNames = {"TWOPOINTS", "COMMA", "PARLEFT", "PARRIGHT", "EOL"};

    /**
     * Costruttore vuoto
     */
    public Lexer()
    {
    }

    /**
     * Costruttore che prende la stringa da tokenizzare
     * 
     * @param s la stringa da tokenizzare
     * @throws SymbolErrorException 
     */
    public Lexer(String s) throws SymbolErrorException
    {
        this.splitBuffer(s);
        this.createTokenList();
    }

    /**
     * Stampa a video la stringa e la lista di token
     * 
     */
    public void showAll()
    {
        for(String s : linesList)
        {
                System.out.println(s + "\n");
        }
        for(Token t : tokenList)
        {
                System.out.println(t.getType());
        }
    }

    /**
     * Fa l'analisi lessicale della stringa
     * 
     * @param s la stringa da analizzare
     * @throws SymbolErrorException 
     */
    public void lexify(String s) throws SymbolErrorException
    {
        this.cleanAll();
        this.splitBuffer(s);
        this.createTokenList();
    }

    /**
     * Inizializza la stringa e la lista di token
     * 
     * @return un booleano che rappresenta l'esito dell'operazione
     */
    public Boolean cleanAll()
    {
        try
        {
            this.linesList.clear();
            this.tokenList.clear();
            return true;
        }
        catch(Exception e)
        {
            System.out.println("ERROR: " + e);
            return false;
        }
    }

    /**
     * Verifica se la stringa &egrave; tokenizzabile
     * 
     * @param s la stringa da analizzare
     * @param r la riga del file in cui si trova la stringa
     * @param c la colonna del file in cui si trova la stringa
     * @return un booleano che rappresenta l'esito dell'operazione
     */
    public Boolean tokenizable(String s, int r, int c)
    {
        if(s.matches(literal))
        {
            return true;
        }
        else if(s.matches(numeric))
        {
            return true;
        }
        else if(s.matches(conString))
        {
            return true;
        }
        else if(s.matches(conIdentifiers))
        {
            return true;
        }
        else if(s.matches(operators))
        {
            for(String k : opSymbols)
            {
                if(s.equals(k))
                {
                    return true;
                }
            }
        }
        else if(s.matches(logical))
        {
            for(String k : logSymbols)
            {
                if(s.equals(k))
                {
                    return true;
                }
            }
        }
        else if(s.matches(comparison))
        {
            for(String k : cmpSymbols)
            {
                if(s.equals(k))
                {
                    return true;
                }
            }
        }
        else if(s.matches(separators))
        {
            for(String k : sepSymbols)
            {
                if(s.equals(k))
                {
                    return true;
                }
            }
        }
        else if(s.matches(numericmix))
        {
        	return true;
        }
        else if(s.matches(undefined))
        {
            printError(r, c, "Impossibile riconoscere la parola -> " + s);
            return false;
        }

        return false;
    }

    /**
     * Trasforma la stringa in token
     * 
     * @param s la stringa da analizzare
     * @param r la riga in cui si trova la stringa
     * @param c la colonna in cui si trova la stringa
     * @return un Token
     */
    public Token getTokenFromString(String s, int r, int c)
    {
        if(s.matches(literal))
        {
            for(String k : keywords)
            {
                if(s.equalsIgnoreCase(k))
                {
                    if(k.equalsIgnoreCase("PRINT") || k.equalsIgnoreCase("INPUT"))
                        return new Token(TokenType.FUNCTION, k);
                    return new Token(k, k);
                }
            }
            return new Token(TokenType.FUNCTION, s.toUpperCase());
        }
        else if(s.matches(numeric))
        {
            return new Token(TokenType.INTEGER, s);
        }
        else if(s.matches(conString))
        {
            String trueString = "";
            for(int i = 0; i < s.length(); i++)
            {
                if(s.charAt(i) != '"')
                {
                    trueString += s.charAt(i);
                }
            }
            return new Token(TokenType.STRING, trueString);
        }
        else if(s.matches(conIdentifiers))
        {
                return new Token(TokenType.VARIABLE, s);
        }
        else if(s.matches(operators))
        {
            for(int i = 0; i < opSymbols.length; i++)
            {
                if(s.equals(opSymbols[i]))
                {
                    return new Token(opNames[i], opSymbols[i]);
                }
            }
        }
        else if(s.matches(logical))
        {
            for(int i = 0; i < logSymbols.length; i++)
            {
                if(s.equals(logSymbols[i]))
                {
                    return new Token(logNames[i], logSymbols[i]);
                }
            }
        }
        else if(s.matches(comparison))
        {
            for(int i = 0; i < cmpSymbols.length; i++)
            {
                if(s.equals(cmpSymbols[i]))
                {
                    return new Token(cmpNames[i], cmpSymbols[i]);
                }
            }
        }
        else if(s.matches(separators))
        {
            for(int i = 0; i < sepSymbols.length; i++)
            {
                if(s.equals(sepSymbols[i]))
                {
                    return new Token(sepNames[i], sepSymbols[i]);
                }
            }
        }
        else if(s.matches(undefined))
        {
            printError(r, c, "Impossibile riconoscere la parola -> " + s);
            return new Token();
        }

        return new Token();
    }

    /**
     * Divide la stringa in righe ordinate in un array di stringhe
     * @param buf la stringa da processare
     */
    public void splitBuffer(String buf)
    {
        List<String> x = Arrays.asList(buf.split("\n"));
        linesList.addAll(x);
        for(int i = 0; i < linesList.size(); i++)
        {
            String s = linesList.get(i);
            s += '\n';
            linesList.set(i, s);
        }
    }

    /**
     * Aggiunge un token alla tokenList
     * 
     * @param t il token da aggiungere
     */
    public void addToken(Token t)
    {
        tokenList.add(t);
    }

    /**
     * Verifica se il carattere &egrave; un carattere di spazio
	 *
     * @param c il carattere da analizzare
     * @return un booleano
     */
    public Boolean isSpacingCharacter(char c)
    {
        if(c == '\n' || c == '\t' || c == '\r' || c == ' ')
        {
            return true;
        }
        return false;
    }

    /**
     * Stampa a video un errore
     * 
     * @param i riga del codice
     * @param j colonna del codice
     * @param e lessema invalido
     */
    public void printError(int i, int j, String e)
    {
        System.out.println("\nErrore (" + i + ":" + j + "): " + e + "\n");
    }

    /**
     * Crea una tokenlist
     * 
     * @throws SymbolErrorException
     */
    public void createTokenList() throws SymbolErrorException
    {
        int i = 0;
        int j = 0;
        String saved = "";
        for(i = 0; i < linesList.size(); i++)
        {
            for(j = 0; j < linesList.get(i).length(); j++)
            {
                String line = linesList.get(i);
        		if(isSpacingCharacter(line.charAt(j)) == false)
        		{
                	if((line.charAt(j) == 'R' && line.charAt(j+1) == 'E' && line.charAt(j+2) == 'M') ||
                			(line.charAt(j) == 'r' && line.charAt(j+1) == 'e' && line.charAt(j+2) == 'm'))
                	{
                		j = line.length();
                	}
                	else if(line.charAt(j) == '"')
                	{
                		try
                		{
                			saved += line.charAt(j);
                			j++;
                    		while (line.charAt(j) != '"')
                    		{
                    			saved += line.charAt(j);
                    			j++;
                    		}
                    		saved += line.charAt(j);
                		}
                		catch(IndexOutOfBoundsException e)
                		{
                			System.out.println("Errore: Stringa non chiusa");
                		}
                	}
                	else if(line.charAt(j) >= '0' && line.charAt(j) <= '9')
            		{
            			while((line.charAt(j) >= '0' && line.charAt(j) <= '9') || line.charAt(j) == '.' || line.charAt(j) == ',')
            			{
            				saved += line.charAt(j);
            				j++;
            			}
            			j--;
            		}
            		else if(line.charAt(j) == '>' || line.charAt(j) == '<' || line.charAt(j) == '=' ||
       					 line.charAt(j) == '&' || line.charAt(j) == '|' || line.charAt(j) == '!' ||
       					 line.charAt(j) == '+' || line.charAt(j) == '-' || line.charAt(j) == '*' || line.charAt(j) == '/' || line.charAt(j) == '%')
            		{
            			while(line.charAt(j) == '>' || line.charAt(j) == '<' || line.charAt(j) == '=' ||
            					 line.charAt(j) == '&' || line.charAt(j) == '|' || line.charAt(j) == '!' ||
            					 line.charAt(j) == '+' || line.charAt(j) == '-' || line.charAt(j) == '*' || line.charAt(j) == '/' || line.charAt(j) == '%')
            			{
            				saved += line.charAt(j);
            				j++;
            			}
            			j--;
            		}
            		else if(line.charAt(j) == '(')
            		{
            			saved += line.charAt(j);
            		}
            		else if(line.charAt(j) == ')')
            		{
            			saved += line.charAt(j);
            		}
            		else if(line.charAt(j) == ':')
            		{
            			saved += line.charAt(j);
            		}
                	else
                	{
            			while(isSpacingCharacter(line.charAt(j)) == false &&
            					!(line.charAt(j) == '>' || line.charAt(j) == '<' || line.charAt(j) == '=' ||
           					 line.charAt(j) == '&' || line.charAt(j) == '|' || line.charAt(j) == '!' ||
           					 line.charAt(j) == '+' || line.charAt(j) == '-' || line.charAt(j) == '*' || line.charAt(j) == '/' || line.charAt(j) == '%' ||
           					 line.charAt(j) == '(' || line.charAt(j) == ')'))
            			{
                			saved += line.charAt(j);
                			j++;
            			}
            			j--;
                	}
        		}
        		else if (isSpacingCharacter(line.charAt(j)) == true)
        		{
        			saved = "";
        		}
            	
            	if((saved.isEmpty() == false))
            	{
                    Token t = new Token();
                    t.copyFrom(getTokenFromString(saved, i, j));
                    if(t.isEmpty())
                    {
                    	throw new SymbolErrorException("(" + i + ", " + j + ") " + "Impossibile creare il token dal lessema -> " + saved);
                    }
                    else
                    {
                        addToken(t);
                        saved = "";
                    }            	
            	}
            }
            addToken(new Token(TokenType.EOL, "EOL"));
        }
    }

    /**
     * Traduce la tokenlist in una stringa
     * 
     * @return una stringa (la tokenlist)
     */
    public String stringifyTokenList()
    {
        String tmp = "";
        for(Token t : tokenList)
        {
            tmp += t.getType().toString() + " ";
        }

        return tmp;
    }

    /**
     * Restituisce la tokenlist
     * 
     * @return un ArrayList di Token
     */
    public ArrayList<Token> getTokenList()
    {
        return tokenList;
    }
}
