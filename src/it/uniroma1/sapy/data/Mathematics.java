package it.uniroma1.sapy.data;

import it.uniroma1.sapy.data.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class Mathematics
{
	/**
	 * Metodo statico che restituisce uno statement a partire da un token
	 * 
	 * @param t il token da analizzare
	 * @return lo statement rappresentato dal token
	 * @throws SyntaxErrorException
	 */
    public static Statement getStatementFromToken(Token t) throws SyntaxErrorException
    {
        if(t.getType() == TokenType.PLUS)
            return new AritmeticaStatement(TokenType.PLUS);
        else if(t.getType() == TokenType.MINUS)
            return new AritmeticaStatement(TokenType.MINUS);
        else if(t.getType() == TokenType.MUL)
            return new AritmeticaStatement(TokenType.MUL);
        else if(t.getType() == TokenType.DIV)
            return new AritmeticaStatement(TokenType.DIV);
        else if(t.getType() == TokenType.MOD)
            return new AritmeticaStatement(TokenType.MOD);
        else if(t.getType() == TokenType.GREATER)
            return new ComparaStatement(TokenType.GREATER);
        else if(t.getType() == TokenType.LESS)
            return new ComparaStatement(TokenType.LESS);
        else if(t.getType() == TokenType.GTE)
            return new ComparaStatement(TokenType.GTE);
        else if(t.getType() == TokenType.LTE)
            return new ComparaStatement(TokenType.LTE);
        else if(t.getType() == TokenType.EQUAL)
            return new ComparaStatement(TokenType.EQUAL);
        else if(t.getType() == TokenType.NOTEQUAL)
            return new ComparaStatement(TokenType.NOTEQUAL);
        else if(t.getType() == TokenType.AND)
            return new LogicaStatement(TokenType.AND);
        else if(t.getType() == TokenType.OR)
            return new LogicaStatement(TokenType.OR);
        else if(t.getType() == TokenType.NOT)
            return new LogicaStatement(TokenType.NOT);
        else if(t.getType() == TokenType.INTEGER)
            return new NumberStatement(Double.parseDouble(t.getLexeme()));
        else if(t.getType() == TokenType.TRUE)
            return new NumberStatement(1);
        else if(t.getType() == TokenType.FALSE)
            return new NumberStatement(0);
        else throw new SyntaxErrorException("Parametro " + t.getLexeme() + " incompatibile con il formato espressione.");
    }
    
    /**
     * Metodo statico per il calcolo della priorit&agrave; nelle espressioni aritmetiche, booleane e comparative.
     * 
     * @param a un token da comparare
     * @param b un token da comparare
     * @return un booleano che rappresenta il risultato della comparazione
     */
    public static Boolean higherOrEqualPriorityToken(Token a, Token b)
    {
            String ari = "\\*|/|%|\\+|-";
            String cmp = ">=|>|<=|<|=";
            String log = "!|&|\\|";

            String ari_hi = "\\*|/|%";

            String ari_lo = "\\+|-";
            String log_lo = "&|\\|";

            if(
                    ( a.getLexeme().matches(ari_hi) ) ||
                    ( a.getLexeme().matches(ari_lo) && b.getLexeme().matches(ari_lo) ) ||
                    ( a.getLexeme().matches(ari) && ( b.getLexeme().matches(ari) == false ) ) ||
                    ( a.getLexeme().matches(cmp) && ( b.getLexeme().matches(cmp) || b.getLexeme().matches(log) ) ) ||
                    ( a.getLexeme().matches(log) && b.getLexeme().matches(log_lo) )
              )
                return true;

            return false;
    }

    /**
     * Metodo statico che traduce un'espressione infissa in notazione postfissa
     * 
     * @param list un ArrayList di token che rappresenta l'espressione infissa
     * @return un ArrayList di token che rappresenta l'espressione postfissa
     */
    public static ArrayList<Token> infixToPostfix(ArrayList<Token> list)
    {
        ArrayList<Token> postfixed = new ArrayList<Token>();
        Stack<Token> operators = new Stack<Token>();
        for(Token t : list)
        {
            if(t.getType() == TokenType.INTEGER)
            {
                postfixed.add(new Token(t));
            }
            else if(t.getType().getValue() < 3)
            {
                while(operators.isEmpty() == false &&
                        higherOrEqualPriorityToken(operators.peek(), t) &&
                        (operators.peek().getType() != TokenType.PARLEFT))
                {
                    postfixed.add(operators.pop());
                }
                operators.push(new Token(t));
            }
            else
            {
                if(t.getType() == TokenType.PARLEFT)
                {
                    operators.push(new Token(t));
                }
                else if(t.getType() == TokenType.PARRIGHT)
                {
                    while(operators.isEmpty() == false && operators.peek().getType() != TokenType.PARLEFT)
                    {
                        postfixed.add(operators.pop());
                    }
                    if(operators.isEmpty() == false)
                    {
                        operators.pop();
                    }
                }
            }
        }
        while(operators.isEmpty() == false)
        {
                postfixed.add(operators.pop());
        }
        return postfixed;
    }

    /**
     * Un metodo statico che permette di costruire un albero sintattico a partire da una lista di token che rappresenta un'espressione matematica
     * 
     * @param e l'espressione matematica da tradurre
     * @return l'albero sintattico implementato come un'istruzione (albero di statement)
     * @throws SyntaxErrorException
     */
    public static Istruzione buildAST(ArrayList<Token> e) throws SyntaxErrorException
    {
    	if(e.size() == 1)
    	{
    		if(e.get(0).getType() == TokenType.INTEGER)
    		{
    			return new Istruzione(new NumberStatement(e.get(0).getLexeme()));
    		}
    		else if(e.get(0).getType() == TokenType.TRUE)
    		{
    			return new Istruzione(new NumberStatement(1));
    		}
    		else if(e.get(0).getType() == TokenType.FALSE)
    		{
    			return new Istruzione(new NumberStatement(0));
    		}
    	}
    	
    	ArrayList<Token> newer = new ArrayList<Token>();
    	
    	for(Token t : e)
    	{
    		if(t.getType() == TokenType.TRUE)
    		{
    			newer.add(new Token(TokenType.INTEGER, 1));
    		}
    		else if(t.getType() == TokenType.FALSE)
    		{
    			newer.add(new Token(TokenType.INTEGER, 0));
    		}
    		else
    		{
    			newer.add(t);
    		}
    	}
    	
    	ArrayList<Token> toResolve = new ArrayList<Token>();
        toResolve.addAll(infixToPostfix(newer));
        Istruzione ast = new Istruzione();
        
        Stack<Statement> leafStack = new Stack<Statement>();
        Stack<Statement> opStack = new Stack<Statement>();
        for(int i = 0; i < toResolve.size(); i++)
        {
            if(toResolve.get(i).getType() == TokenType.INTEGER)
            {
                leafStack.push(getStatementFromToken(toResolve.get(i)));
            }
            else if(toResolve.get(i).getType().getValue() < 3)
            {
                if((leafStack.empty() == true) || (leafStack.capacity() == 0))
                {
                    Statement ope = getStatementFromToken(toResolve.get(i));
                    
                    while(opStack.empty() == false)
                    {
                        ope.addChild(opStack.pop());
                    }
                    
                    opStack.push(ope);
                }
                else
                {
                    Statement ope = getStatementFromToken(toResolve.get(i));
                    
                    if(leafStack.peek() != null)
                    {
                        ope.addChild(leafStack.pop());
                        if(leafStack.empty() == false && leafStack.peek() != null)
                        {
                            ope.addChild(leafStack.pop());
                        }
                        else
                        {
                            while(opStack.empty() == false)
                            {
                                ope.addChild(opStack.pop());
                            }
                        }
                    }

                    opStack.push(ope);
                }
            }
        }
        while(opStack.empty() == false)
        {
            ast.addChild(opStack.pop());
        }
        return new Istruzione(ast);
    }	
    
    /**
     * Metodo statico per il calcolo di addizioni
     * 
     * @param a addendo
     * @param b addendo
     * @return un token che rappresenta la somma
     */
    public static Token Add(Token a, Token b)
    {
        double x = 0, z = 0, y = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }
        y = x + z;
        return new Token(TokenType.INTEGER, y);
    }

    /**
     * Metodo statico per il calcolo di sottrazioni
     * 
     * @param a minuendo
     * @param b sottraendo
     * @return un token che rappresenta la differenza
     */
    public static Token Sub(Token a, Token b)
    {
        double x = 0, z = 0, y = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }
        y = x - z;
        return new Token(TokenType.INTEGER, y);
    }

    /**
     * Metodo statico per il calcolo di divisioni
     * 
     * @param a dividendo
     * @param b divisore
     * @return un token che rappresenta il risultato della divisione
     */
    public static Token Div(Token a, Token b)
    {
        double x = 1, z = 1, y = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }
        y = x / z;
        return new Token(TokenType.INTEGER, y);
    }

    /**
     * Metodo statico per il calcolo di moltiplicazioni
     * 
     * @param a operando
     * @param b operando
     * @return token che rappresenta il prodotto
     */
    public static Token Mul(Token a, Token b)
    {
        double x = 0, z = 0, y = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }
        y = x * z;
        return new Token(TokenType.INTEGER, y);
    }

    /**
     * Metodo statico per il calcolo del resto
     * 
     * @param a dividendo
     * @param b divisore
     * @return un token che rappresenta il resto
     */
    public static Token Mod(Token a, Token b)
    {
        double x = 0, z = 0, y = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }
        y = x % z;
        return new Token(TokenType.INTEGER, y);
    }



    /**
     * Un metodo statico per il calcolo della maggioranza
     * 
     * @param a termine
     * @param b termine
     * @return un token che rappresenta l'esito della maggioranza
     */
    public static Token Greater(Token a, Token b)
    {
        double x = 0, z = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }

        if(x>z)
        {
            return new Token(TokenType.TRUE, 1);
        }
        else
        {
            return new Token(TokenType.FALSE, 0);
        }
    }

    /**
     * Metodo statico per il calcolo della minoranza
     * 
     * @param a termine
     * @param b termine
     * @return risultato della minoranza (Token)
     */
    public static Token Less(Token a, Token b)
    {
        double x = 0, z = 0;
        if(a.getCode() == 6 && b.getCode() == 6)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }

        if(x<z)
        {
            return new Token(TokenType.TRUE, 1);
        }
        else
        {
            return new Token(TokenType.FALSE, 0);
        }
    }

    /**
     * Metodo per il calcolo di maggiore o uguale
     * 
     * @param a termine
     * @param b termine
     * @return token: risultato della comparazione
     */
    public static Token Gte(Token a, Token b)
    {
        double x = 0, z = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }
        if(x>=z)
        {
            return new Token(TokenType.TRUE, 1);
        }
        else
        {
            return new Token(TokenType.FALSE, 0);
        }
    }

    /**
     * Metodo statico per il calcolo di minore o uguale
     * 
     * @param a termine
     * @param b termine
     * @return token che rappresenta l'esito della comparazione
     */
    public static Token Lte(Token a, Token b)
    {
        double x = 0, z = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }

        if(x<=z)
        {
            return new Token(TokenType.TRUE, 1);
        }
        else
        {
            return new Token(TokenType.FALSE, 0);
        }
    }

    /**
     * Metodo statico per il calcolo di uguaglianze
     * 
     * @param a termine
     * @param b termine
     * @return il risultato della comparazione
     */
    public static Token Equal(Token a, Token b)
    {
        double x = 0, z = 0;
        if(a.getType() == TokenType.INTEGER && b.getType() == TokenType.INTEGER)
        {
            x = Double.parseDouble(a.getLexeme());
            z = Double.parseDouble(b.getLexeme());
        }

        if(x==z)
        {
            return new Token(TokenType.TRUE, 1);
        }
        else
        {
            return new Token(TokenType.FALSE, 0);
        }
    }


    /**
     * Metodo statico per il calcolo dell'AND logico
     * 
     * @param a termine
     * @param b termine
     * @return token: risultato dell'operazione
     */
    public static Token And(Token a, Token b)
    {
        if(
                ( a.getType() == TokenType.TRUE && b.getType() == TokenType.TRUE ) ||
                ( Integer.parseInt(a.getLexeme()) == 1 && Integer.parseInt(b.getLexeme()) == 1 )
            )
            return new Token(TokenType.TRUE, 1);
        return new Token(TokenType.FALSE, 0);
    }

    /**
     * Metodo statico per il calcolo dell'OR logico
     * 
     * @param a termine
     * @param b termine
     * @return token: risultato dell'operazione
     */
    public static Token Or(Token a, Token b)
    {
        if(
                ( a.getType() == TokenType.TRUE || b.getType() == TokenType.TRUE ) ||
                ( Integer.parseInt(a.getLexeme()) == 1 || Integer.parseInt(b.getLexeme()) == 1 )
            )
            return new Token(TokenType.TRUE, 1);
        return new Token(TokenType.FALSE, 0);
    }

    /**
     * Metodo statico per il calcolo del NOT logico
     * 
     * @param a termine
     * @return token: risultato
     */
    public static Token Not(Token a)
    {
        if(a.getType() == TokenType.TRUE || Integer.parseInt(a.getLexeme()) == 1)
            return new Token(TokenType.FALSE, 0);
        else if(a.getType() == TokenType.FALSE || Integer.parseInt(a.getLexeme()) == 0)
            return new Token(TokenType.TRUE, 1);
        return new Token(TokenType.FALSE, 0);
    }
}