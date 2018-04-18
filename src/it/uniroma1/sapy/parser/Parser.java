package it.uniroma1.sapy.parser;

import it.uniroma1.sapy.data.IfStatement;
import it.uniroma1.sapy.data.ThenStatement;
import it.uniroma1.sapy.data.Token;
import it.uniroma1.sapy.data.NumberStatement;
import it.uniroma1.sapy.data.StringStatement;
import it.uniroma1.sapy.data.DoStatement;
import it.uniroma1.sapy.data.PrintStatement;
import it.uniroma1.sapy.data.ElseStatement;
import it.uniroma1.sapy.data.EndifStatement;
import it.uniroma1.sapy.data.ForStatement;
import it.uniroma1.sapy.data.ForStepStatement;
import it.uniroma1.sapy.data.Istruzione;
import it.uniroma1.sapy.data.Mathematics;
import it.uniroma1.sapy.data.ProgrammaEseguibile;
import it.uniroma1.sapy.data.TokenType;
import it.uniroma1.sapy.data.SyntaxErrorException;
import it.uniroma1.sapy.data.VariabileStatement;
import it.uniroma1.sapy.data.InputStatement;
import it.uniroma1.sapy.data.NextStatement;
import it.uniroma1.sapy.data.WhileStatement;
import it.uniroma1.sapy.data.LoopStatement;

import java.util.ArrayList;

/**
 * E' la classe che effettua l'analisi sintattica e costruisce il ProgrammaEseguibile contente una lista di Istruzioni ognuna delle quali contiene un albero di Statement.
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class Parser
{
    ArrayList<Token> tokenList;
    
    /**
     * Costruttore
     * 
     * @param tokenList una lista da parsare
     */
    public Parser(ArrayList<Token> tokenList)
    {
        this.tokenList = tokenList;
    }
    
    /**
     * Costruttore vuoto
     */
    public Parser()
    {
        this.tokenList = null;
    }
    
    /**
     * Imposta la lista di token da parsare
     * 
     * @param tokenList la lista da parsare
     */
    public void setTokenList(ArrayList<Token> tokenList)
    {
        this.tokenList = tokenList;
    }
    
    /**
     * Parsa la lista di token e restituisce un programma eseguibile
     * 
     * @return un <code>ProgrammaEseguibile</code>
     * @throws SyntaxErrorException
     */
    public ProgrammaEseguibile parse() throws SyntaxErrorException
    {
        Istruzione istr = new Istruzione();
        ProgrammaEseguibile prog = new ProgrammaEseguibile();
        Boolean newLine = true;
        for(int i = 0; i < tokenList.size(); i++)
        {
            if(newLine)
            {
                if(tokenList.get(i).getType() == TokenType.TWOPOINTS || tokenList.get(i).getType() == TokenType.EOL)
                {
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    continue;
                }
                else if(tokenList.get(i).getType() == TokenType.FUNCTION)
                {
                    istr.clear();
                    newLine = false;
                    if(tokenList.get(i).getLexeme().equalsIgnoreCase("PRINT"))
                    {
                         istr.addChild(new PrintStatement());
                    }
                    else if(tokenList.get(i).getLexeme().equalsIgnoreCase("INPUT"))
                    {
                    	istr.addChild(new InputStatement(tokenList.get(i+1).getLexeme().toString()));
                    }
                }
                else if(tokenList.get(i).getType() == TokenType.EQUAL)
                {
                    if(tokenList.get(i-1).getType() == TokenType.VARIABLE)
                    {
                    	String lab = tokenList.get(i-1).getLexeme().toString();
                        if(tokenList.get(i+1).getType() == TokenType.STRING)
                        {
                        	String a = tokenList.get(i-1).getLexeme();
                        	String b = tokenList.get(i+1).getLexeme();
                            istr.addChild(new VariabileStatement(a,b));
                        }
                        else
                        {
                    		Boolean foundVar = false;
                    		int k = i;
                            while(tokenList.get(k).getType() != TokenType.EOL && tokenList.get(k).getType() != TokenType.TWOPOINTS)
                            {
                                if(tokenList.get(k).getType() == TokenType.VARIABLE)
                                {
                                	foundVar = true;
                                }
                                k++;
                            }
                            
                            if(foundVar == true)
                            {
                                i++;
                                ArrayList<Token> expr = new ArrayList<Token>();
                                for(int j = i; j < tokenList.size(); j++)
                                {
                                    if(tokenList.get(j).getType() == TokenType.TWOPOINTS ||
                                            tokenList.get(j).getType() == TokenType.EOL ||
                                            tokenList.get(j).getType() == TokenType.THEN ||
                                            tokenList.get(j).getType() == TokenType.ENDIF)
                                    {
                                        i = j;
                                        i--;
                                        j = tokenList.size();
                                        newLine = true;
                                    }
                                    else
                                    {
                                        expr.add(new Token(tokenList.get(j)));
                                    }
                                }
                                istr.addChild(new VariabileStatement(lab, expr));
                            }
                            else
                            {
                                if(tokenList.get(i+1).getType() == TokenType.INTEGER ||
                                        tokenList.get(i+1).getType() == TokenType.PARLEFT ||
                                        tokenList.get(i+1).getType() == TokenType.TRUE ||
                                        tokenList.get(i+1).getType() == TokenType.FALSE)
                                {
                                    ArrayList<Token> expr = new ArrayList<Token>();
                                    String label = tokenList.get(i-1).getLexeme();
                                    for(int j = i+1; j < tokenList.size(); j++)
                                    {
                                        if(tokenList.get(j).getType() == TokenType.TWOPOINTS ||
                                           tokenList.get(j).getType() == TokenType.EOL ||
                                           tokenList.get(j).getType() == TokenType.THEN ||
                                           tokenList.get(j).getType() == TokenType.ENDIF ||
                                           tokenList.get(j).getType() == TokenType.TO)
                                        {
                                            i = j;
                                            j = tokenList.size();
                                            i--;
                                            newLine = true;
                                        }
                                        else
                                        {
                                            expr.add(tokenList.get(j));
                                        }
                                    }
                                    istr.addChild(new VariabileStatement(label, Mathematics.buildAST(expr).getRoot().esegui().toString()));
                                }
                            }
                        }
                    }
                    else
                    {
                        throw new SyntaxErrorException("Argomento non valido per l'assegnazione.");
                    }
                }
                else if(tokenList.get(i).getType() == TokenType.IF)
                {
                	if(tokenList.get(i+1).getType() == TokenType.STRING)
                	{
                		Istruzione x = new Istruzione(new NumberStatement(1));
                        IfStatement ifstat = new IfStatement(new Istruzione(x));
                        System.out.println(ifstat.toString());
                        istr.addChild(new IfStatement(ifstat));
                	}
                	else
                	{
                		Boolean foundVar = false;
                		int k = i;
                        while(tokenList.get(k).getType() != TokenType.THEN)
                        {
                            if(tokenList.get(k).getType() == TokenType.VARIABLE)
                            {
                            	foundVar = true;
                            }
                            k++;
                        }
                        
                        if(foundVar == false)
                        {
                            ArrayList<Token> expr = new ArrayList<Token>();
                            for(int j = i; j < tokenList.size(); j++)
                            {
                                if(tokenList.get(j).getType() == TokenType.TWOPOINTS ||
                                        tokenList.get(j).getType() == TokenType.EOL ||
                                        tokenList.get(j).getType() == TokenType.THEN ||
                                        tokenList.get(j).getType() == TokenType.ENDIF)
                                {
                                    i = j;
                                    j = tokenList.size();
                                    i--;
                                    newLine = true;
                                }
                                else
                                {
                                    expr.add(tokenList.get(j));
                                }
                            }
                            IfStatement ifstat = new IfStatement(Mathematics.buildAST(expr));
                            istr.addChild(new IfStatement(ifstat));
                        }
                        else
                        {
                            i++;
                            ArrayList<Token> expr = new ArrayList<Token>();
                            for(int j = i; j < tokenList.size(); j++)
                            {
                                if(tokenList.get(j).getType() == TokenType.TWOPOINTS ||
                                        tokenList.get(j).getType() == TokenType.EOL ||
                                        tokenList.get(j).getType() == TokenType.THEN ||
                                        tokenList.get(j).getType() == TokenType.ENDIF)
                                {
                                    i = j;
                                    i--;
                                    j = tokenList.size();
                                    newLine = true;
                                }
                                else
                                {
                                    expr.add(new Token(tokenList.get(j)));
                                }
                            }
                            IfStatement ifstat = new IfStatement(expr);
                            istr.addChild(new IfStatement(ifstat.getVars()));
                        }
                	}
                }
                else if(tokenList.get(i).getType() == TokenType.THEN)
                {
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    istr.addChild(new ThenStatement());
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    newLine = true;
                    continue;
                }
                else if(tokenList.get(i).getType() == TokenType.ELSE)
                {
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    istr.addChild(new ElseStatement());
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    newLine = true;
                    continue;
                }
                else if(tokenList.get(i).getType() == TokenType.ENDIF)
                {
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    istr.addChild(new EndifStatement());
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    newLine = true;
                    continue;
                }
                else if(tokenList.get(i).getType() == TokenType.FOR)
                {
                    int j = i;
                    String labIndex = "";
                    Double valIndex = 0.0;
                    Double valto = 0.0;
                    Double stepto = 0.0;
                    Boolean stepIsDefined = false;
                    while(tokenList.get(j).getType() != TokenType.DO)
                    {
                        j++;
                        if(tokenList.get(j).getType() != TokenType.EOL)
                        {
                            if(tokenList.get(j).getType() == TokenType.VARIABLE)
                            {
                                labIndex = tokenList.get(j).getLexeme();
                                j++;
                                if(tokenList.get(j).getType() == TokenType.EQUAL)
                                {
                                    j++;
                                    if(tokenList.get(j).getType() == TokenType.INTEGER)
                                    {
                                        valIndex = Double.parseDouble(tokenList.get(j).getLexeme().toString());
                                    }
                                    else throw new SyntaxErrorException("FOR accetta solo variabili numeriche.");
                                }
                                else throw new SyntaxErrorException("FOR accetta solo variabili numeriche.");
                            }
                            else if(tokenList.get(j).getType() == TokenType.TO)
                            {
                                Boolean loopme = true;
                                while(loopme)
                                {
                                    j++;
                                    if(tokenList.get(j).getType() != TokenType.EOL)
                                    {
                                        if(tokenList.get(j).getType() == TokenType.INTEGER)
                                        {
                                            valto = Double.parseDouble(tokenList.get(j).getLexeme().toString());
                                            loopme = false;
                                        }
                                        else throw new SyntaxErrorException("Limite del FOR non definito.");
                                    }
                                }
                            }
                            else if(tokenList.get(j).getType() == TokenType.STEP)
                            {
                                Boolean loopme = true;
                                while(loopme)
                                {
                                    j++;
                                    if(tokenList.get(j).getType() != TokenType.EOL)
                                    {
                                        if(tokenList.get(j).getType() == TokenType.INTEGER)
                                        {
                                            stepto = Double.parseDouble(tokenList.get(j).getLexeme().toString());
                                            loopme = false;
                                            stepIsDefined = true;
                                        }
                                        else throw new SyntaxErrorException("Step del FOR non definito.");
                                    }
                                }
                            }
                        }
                    }
                    Token lind = new Token(TokenType.VARIABLE, labIndex);
                    Token vind = new Token(TokenType.INTEGER, valIndex.doubleValue());
                    Token vato = new Token(TokenType.INTEGER, valto.doubleValue());
                    Token vast = new Token(TokenType.INTEGER, stepto.doubleValue());
                    if(stepIsDefined)
                    {
                        istr.addChild(new ForStepStatement(lind, vind, vato, vast));
                    }
                    else
                    {
                        istr.addChild(new ForStatement(lind, vind, vato));
                    }
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    istr.addChild(new DoStatement());
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    i = j;
                }
                else if(tokenList.get(i).getType() == TokenType.NEXT)
                {
                    istr.clear();
                    istr.addChild(new NextStatement());
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    newLine = true;
                    continue;
                }
                else if(tokenList.get(i).getType() == TokenType.WHILE)
                {
                	if(tokenList.get(i+1).getType() == TokenType.STRING)
                	{
                		Istruzione x = new Istruzione(new NumberStatement(1));
                        IfStatement ifstat = new IfStatement(new Istruzione(x));
                        System.out.println(ifstat.toString());
                        istr.addChild(new IfStatement(ifstat));
                	}
                	else
                	{
                		Boolean foundVar = false;
                		int k = i;
                        while(k < tokenList.size() && tokenList.get(k).getType() != TokenType.DO)
                        {
                            if(tokenList.get(k).getType() == TokenType.VARIABLE)
                            {
                            	foundVar = true;
                            }
                            k++;
                        }
                        
                        if( k > tokenList.size()) throw new SyntaxErrorException("Ciclo WHILE costruito senza parola chiave DO.");
                        
                        if(foundVar == false)
                        {
                            ArrayList<Token> expr = new ArrayList<Token>();
                            for(int j = i; j < tokenList.size(); j++)
                            {
                                if(tokenList.get(j).getType() == TokenType.TWOPOINTS ||
                                        tokenList.get(j).getType() == TokenType.EOL ||
                                        tokenList.get(j).getType() == TokenType.DO)
                                {
                                    i = j;
                                    j = tokenList.size();
                                    i--;
                                    newLine = true;
                                }
                                else
                                {
                                    expr.add(tokenList.get(j));
                                }
                            }
                            WhileStatement whilestat = new WhileStatement(Mathematics.buildAST(expr));
                            istr.addChild(new WhileStatement(whilestat));
                        }
                        else
                        {
                            i++;
                            ArrayList<Token> expr = new ArrayList<Token>();
                            for(int j = i; j < tokenList.size(); j++)
                            {
                                if(tokenList.get(j).getType() == TokenType.TWOPOINTS ||
                                        tokenList.get(j).getType() == TokenType.EOL ||
                                        tokenList.get(j).getType() == TokenType.DO)
                                {
                                    i = j;
                                    i--;
                                    j = tokenList.size();
                                    newLine = true;
                                }
                                else
                                {
                                    expr.add(new Token(tokenList.get(j)));
                                }
                            }
                            WhileStatement whilestat = new WhileStatement(expr);
                            istr.addChild(new WhileStatement(whilestat.getVars()));
                        }
                	}
                }
                else if(tokenList.get(i).getType() == TokenType.LOOP)
                {
                    istr.clear();
                    istr.addChild(new LoopStatement());
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    newLine = true;
                    continue;
                }
            }
            else
            {
                if(tokenList.get(i).getType() == TokenType.TWOPOINTS || tokenList.get(i).getType() == TokenType.EOL)
                {
                    prog.add(new Istruzione(istr));
                    istr.clear();
                    newLine = true;
                }
                else if(tokenList.get(i-1).getType() == TokenType.FUNCTION)
                {
                    if(tokenList.get(i-1).getLexeme().equalsIgnoreCase("PRINT"))
                    {
                        if(tokenList.get(i).getType() == TokenType.STRING)
                        {
                            istr.addChild(new StringStatement(tokenList.get(i).getLexeme()));
                            newLine = true;
                        }
                        else if(tokenList.get(i).getType() == TokenType.INTEGER ||
                                tokenList.get(i).getType() == TokenType.PARLEFT ||
                                tokenList.get(i).getType() == TokenType.TRUE ||
                                tokenList.get(i).getType() == TokenType.FALSE)
                        {
                            ArrayList<Token> expr = new ArrayList<Token>();
                            for(int j = i; j < tokenList.size(); j++)
                            {
                                if(tokenList.get(j).getType() == TokenType.TWOPOINTS ||
                                   tokenList.get(j).getType() == TokenType.EOL ||
                                   tokenList.get(j).getType() == TokenType.THEN ||
                                   tokenList.get(j).getType() == TokenType.ENDIF)
                                {
                                    i = j;
                                    j = tokenList.size();
                                    i--;
                                    newLine = true;
                                }
                                else
                                {
                                    expr.add(tokenList.get(j));
                                }
                            }
                            istr.addChild(Mathematics.buildAST(expr).getRoot());
                        }
                        else if(tokenList.get(i).getType() == TokenType.VARIABLE)
                        {
                        	istr.addChild(new VariabileStatement(tokenList.get(i).getLexeme(), ""));
                        }
                        else
                        {
                            throw new SyntaxErrorException("Argomento non valido per PRINT.");
                        }
                    }
                    else if(tokenList.get(i-1).getLexeme().equalsIgnoreCase("INPUT"))
                    {
                        if(tokenList.get(i).getType() == TokenType.VARIABLE)
                        {
                        }
                        else
                        {
                            throw new SyntaxErrorException("Argomento non valido per INPUT.");
                        }
                    }
                }
                else if(tokenList.get(i-1).getType() == TokenType.THEN)
                {
                }
                else if(tokenList.get(i-1).getType() == TokenType.ELSE)
                {
                }
                else if(tokenList.get(i-1).getType() == TokenType.ENDIF)
                {
                }
            }
        }
        return new ProgrammaEseguibile(prog);
    }
}