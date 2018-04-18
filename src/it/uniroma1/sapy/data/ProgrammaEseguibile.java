/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma1.sapy.data;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * E' una struttura che contiene al suo interno una lista di Istruzioni, ognuna delle quali contiene un albero di Statement.
 * ProgrammaEseguibile pu&ograve; essere eseguito tramite il metodo esegui(). Una volta eseguito, eseguir&agrave; in ordine tutte le istruzioni (facendo i dovuti salti avanti e indietro in caso di strutture di controllo), le quali eseguiranno ricorsivamente il loro albero di Statement.
 * 
 * @author Sebastiano Michele Cossu
 */
public class ProgrammaEseguibile implements Eseguibile
{
    protected ArrayList<String> varNames;
    protected ArrayList<String> varValues;
    protected ArrayList<Istruzione> elements;
    protected int openIf = 0;
    protected int openElse = 0;
    protected int openFor = 0;
    protected int openWhile = 0;
    
    /**
     * Costruttore
     */
    public ProgrammaEseguibile()
    {
    	varNames = new ArrayList<String>();
    	varValues = new ArrayList<String>();
        elements = new ArrayList<Istruzione>();
    }

    /**
     * Costruttore
     * @param prog il programma eseguibile da cui copiarsi
     */
    public ProgrammaEseguibile(ProgrammaEseguibile prog)
    {
    	varNames = new ArrayList<String>();
    	varValues = new ArrayList<String>();
        elements = new ArrayList<Istruzione>();
        copyOf(prog);
    }
        
    /**
     * Aggiunge un'istruzione al programma
     * @param element l'istruzione da aggiungere
     */
    public void add(Istruzione element)
    {
        elements.add(element);
    }
    
    /**
     * restituisce l'istruzione <code>i</code>
     * @param i l'indice dell'istruzione da trovare
     * @return l'istruzione all'indice definito
     */
    public Istruzione get(int i)
    {
        return elements.get(i);
    }
    
    /**
     * restituisce l'altezza dell'albero
     * @return un intero
     */
    public int getSize()
    {
        return elements.size();
    }

    /**
     * restituisce il valore del nodo (stampa tutte le istruzioni a video)
     */
    public void getValue()
    {
        for(Istruzione i : elements)
        {
            System.out.println(i.getRoot().getValue());
        }
    }

    /**
     * Copia il programma da un altro programma
     * @param prog programma da copiare
     */
    public void copyOf(ProgrammaEseguibile prog)
    {
        for(int i = 0; i < prog.getSize(); i++)
        {
            this.add(prog.get(i));
        }
    }
    
    /**
     * Stampa a video il contenuto della memoria
     */
    public void printMemory()
    {
    	System.out.println("MEMORY:");
        for(int i = 0; i < varNames.size(); i++)
        {
        	System.out.println(varNames.get(i) + " = " + varValues.get(i));
        }
    }
    
    /**
     * Legge dalla memoria
     * @param s nome della variabile
     * @return l'indice della variabile in memoria (indirizzo)
     */
    public int searchInMemory(String s)
    {
    	for(int i = 0; i < varNames.size(); i++)
    	{
    		if(varNames.get(i).equalsIgnoreCase(s))
    		{
    			return i;
    		}
    	}
    	return -1;
    }

    /**
     * Scrive sulla memoria (sovrascrive, se gi&agrave; esistente)
     * @param s nome della variabile
     * @param v il suo valore
     */
    public void writeToMemory(String s, String v)
    {
    	Boolean found = false;
    	for(int i = 0; i < varNames.size(); i++)
    	{
    		if(varNames.get(i).equalsIgnoreCase(s))
    		{
    			varValues.set(i, v);
    			found = true;
    		}
    	}
    	if(found == false)
    	{
    		varNames.add(s);
    		varValues.add(v);
    	}
    }

    /**
     * esegue il programma
     */
    @Override
    public Object esegui()
    {
        int indexToExecute = 0;
        while(indexToExecute < elements.size())
        {
            if((elements.get(indexToExecute).isEmpty()) || (elements.get(indexToExecute) == null))
            {
                indexToExecute++;
            }
            else
            {
            	if(elements.get(indexToExecute).getRoot().getValue().toString().contains("PRINT"))
            	{
            		if(elements.get(indexToExecute).getRoot().left != null && elements.get(indexToExecute).getRoot().left.getValue() == TokenType.VARIABLE)
            		{
            			int x = searchInMemory(elements.get(indexToExecute).getRoot().left.getLabel().toString());
            			if(x != -1)
            			{
            				elements.get(indexToExecute).getRoot().left.setVal(varValues.get(x));
            			}
            		}
                    indexToExecute += (Integer) elements.get(indexToExecute).esegui();
            	}
            	else if(elements.get(indexToExecute).getRoot().getValue().toString().contains("INPUT"))
            	{
					String takeIn = "";
        		    Scanner input = new Scanner(System.in);
        		    takeIn = input.next();
        		    String label = elements.get(indexToExecute).getRoot().getLabel().toString();
        		    writeToMemory(label, takeIn);
        		    indexToExecute++;
       		}
            	else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.VARIABLE)
                {
            		int indexVar = indexToExecute;
            		if(elements.get(indexToExecute).getRoot().isLeaf() == true)
            		{
                        VariabileStatement varrr = new VariabileStatement(elements.get(indexToExecute).getRoot());
                        String labe = varrr.getLabel().toString();
                        String valor = varrr.esegui().toString();
                        Boolean modified = false;
                        
                        for(int i = 0; i < varNames.size(); i++)
                        {
                        	if(varNames.get(i).equalsIgnoreCase(labe))
                        	{
                        		varValues.set(i, valor);
                        		modified = true;
                        		i = varNames.size();
                        	}
                        }
                        if(modified == false)
                        {
                        	varNames.add(labe);
                        	varValues.add(valor);
                        }

                        indexToExecute++;
            		}
            		else
            		{
            			ArrayList<Token> artt = new ArrayList<Token>();
            			artt.addAll(elements.get(indexToExecute).getRoot().getVars());
            			ArrayList<Token> finart = new ArrayList<Token>();
            			for(int j = 0; j < artt.size(); j++)
            			{
            				if(artt.get(j).getType() == TokenType.VARIABLE)
            				{
            					int im = searchInMemory(artt.get(j).getLexeme().toString());
            					try
            					{
            						if(im >= 0)
            						{
                						Double ddd = Double.parseDouble(varValues.get(im));
                    					finart.add(new Token(TokenType.INTEGER, ddd));
            						}
            						else throw new SyntaxErrorException("Variabile non definita: " + artt.get(j).getLexeme());
            					}
            					catch(SyntaxErrorException e)
            					{
            						System.out.println(e);
            					}
            				}
            				else
            				{
            					finart.add(artt.get(j));
            				}
            			}
            			
            			try
            			{
                			Istruzione istr = new Istruzione(Mathematics.buildAST(finart));
                			Object obi = istr.esegui();
                			String obis = obi.toString();
                			String labels = elements.get(indexToExecute).getRoot().getLabel().toString();
                			writeToMemory(labels, obi.toString());
                			indexToExecute++;
            			}
            			catch(SyntaxErrorException e)
            			{
            				System.out.println(e);
            			}
            		}
                }
            	else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.IF)
            	{
            		if(elements.get(indexToExecute).getRoot().isLeaf() == false)
            		{
            			ArrayList<Token> arttt = new ArrayList<Token>();
            			arttt.addAll(elements.get(indexToExecute).getRoot().getVars());
            			ArrayList<Token> finalArt = new ArrayList<Token>();
            			for(int z = 0; z < arttt.size(); z++)
            			{
            				if(arttt.get(z).getType() == TokenType.VARIABLE)
            				{
            					int im = searchInMemory(arttt.get(z).getLexeme().toString());
            					try
            					{
                					if(im >= 0)
                					{
                						Double ddd = Double.parseDouble(varValues.get(im));
                						Integer iii = ddd.intValue();
                    					finalArt.add(new Token(TokenType.INTEGER, iii));
                					}
                					else throw new SyntaxErrorException("Variabile non definita: " + arttt.get(z).getLexeme().toString());
            					}
            					catch(SyntaxErrorException e)
            					{
            						System.out.println(e);
            					}
            				}
            				else
            				{
            					finalArt.add(arttt.get(z));
            				}
            			}
            			try
            			{
                			Istruzione expr = new Istruzione(Mathematics.buildAST(finalArt));
                			Object exo = (Object)expr.esegui();
                			if(exo instanceof Integer)
                			{
                				Integer exi = (Integer) exo;
                				if(exi == 1)
                				{
                            		openIf++;
                    			}
                				else
                				{
                					openElse++;
                				}
                			}
                			else if(exo instanceof Boolean)
                			{
                				Boolean exb = (Boolean) exo;
                				if(exb == true || exo.equals(true))
                				{
                            		openIf++;
                				}
                				else
                				{
                					openElse++;
                				}
                			}
                			else if(exo instanceof Double)
                			{
                				Double exd = (Double) exo;
                				if(exd == 1)
                				{
                            		openIf++;
                				}
                				else
                				{
                					openElse++;
                				}
                			}
                			else
                			{
                				openElse++;
                			}
                    		indexToExecute++;
            			}
            			catch(SyntaxErrorException e)
            			{
            				System.out.println(e);
            			}
        			}
        			else
        			{
            			Object exo = elements.get(indexToExecute).getRoot().esegui();
            			if(exo instanceof Integer)
            			{
            				Integer exi = (Integer) exo;
            				if(exi == 1)
            				{
                        		openIf++;
                			}
            				else
            				{
            					openElse++;
            				}
            			}
            			else if(exo instanceof Boolean)
            			{
            				Boolean exb = (Boolean) exo;
            				if(exb == true || exo.equals(true))
            				{
                        		openIf++;
            				}
            				else
            				{
            					openElse++;
            				}
            			}
            			else if(exo instanceof Double)
            			{
            				Double exd = (Double) exo;
            				if(exd == 1)
            				{
                        		openIf++;
            				}
            				else
            				{
            					openElse++;
            				}
            			}
            			else
            			{
            				openElse++;
            			}
                		indexToExecute++;
        			}
            	}
            	else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.THEN)
            	{
            		if(openIf == 0)
            		{
            			Boolean goNext = true;
            			while(goNext)
            			{
            				if((elements.get(indexToExecute).getRoot() != null) &&
            					(elements.get(indexToExecute).getRoot().getValue() instanceof TokenType) &&
            					(elements.get(indexToExecute).getRoot().getValue() == TokenType.ELSE ||
            						elements.get(indexToExecute).getRoot().getValue() == TokenType.ENDIF))
            				{
            					goNext = false;
            				}
            				else
            				{
            					indexToExecute++;
            				}
            			}
            		}
            		else
            		{
            			openIf--;
            			indexToExecute++;
            		}
            	}
            	else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.ELSE)
            	{
            		if(openElse == 0)
            		{
            			Boolean goNext = true;
            			while(goNext)
            			{
            				if(
            						(elements.get(indexToExecute).getRoot() != null) &&
            						(elements.get(indexToExecute).getRoot().getValue() instanceof TokenType) &&
            						elements.get(indexToExecute).getRoot().getValue() == TokenType.ENDIF)
            				{
            					goNext = false;
            					indexToExecute--;
            				}
            				else
            				{
            					indexToExecute++;
            				}
            			}
            		}
            		else
            		{
            			openElse--;
            			indexToExecute++;
            		}
            	}
            	else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.ENDIF)
            	{
        			indexToExecute++;
            	}
                else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.FOR)
                {
                    ArrayList<Token> art = elements.get(indexToExecute).getRoot().getVars();
                    String label = art.get(0).getLexeme().toString();
                    Double value = Double.valueOf(art.get(1).getLexeme().toString());
                    Double toto = Double.valueOf(art.get(2).getLexeme().toString());
                    
                    int x = searchInMemory(label);
                    if(x < 0)
                    {
                        writeToMemory(label, value.toString());
                    }
                    else
                    {
                        value = Double.parseDouble(varValues.get(x));
                        value++;
                    }
                    
                    if(value <= toto)
                    {
                        openFor++;
                        writeToMemory(label, value.toString());
                        indexToExecute++;
                    }
                    else
                    {
                        Boolean intoTheLoop = true;
                        while(intoTheLoop)
                        {
                            if(elements.get(indexToExecute).getRoot() != null)
                            {
                                if(elements.get(indexToExecute).getRoot().getValue() != TokenType.NEXT)
                                {
                                    indexToExecute++;
                                }
                                else
                                {
                                    intoTheLoop = false;
                                }
                            }
                            else
                            {
                                indexToExecute++;
                            }
                        }
                    }
                }
                else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.STEP)
                {
                    ArrayList<Token> art = elements.get(indexToExecute).getRoot().getVars();
                    String label = art.get(0).getLexeme().toString();
                    Double value = Double.valueOf(art.get(1).getLexeme().toString());
                    Double toto = Double.valueOf(art.get(2).getLexeme().toString());
                    Double step = Double.valueOf(art.get(3).getLexeme().toString());
                    
                    int x = searchInMemory(label);
                    if(x < 0)
                    {
                        writeToMemory(label, value.toString());
                    }
                    else
                    {
                        value = Double.parseDouble(varValues.get(x));
                        value += step;
                    }
                    
                    if(value <= toto)
                    {
                        openFor++;
                        writeToMemory(label, value.toString());
                        indexToExecute++;
                    }
                    else
                    {
                        Boolean intoTheLoop = true;
                        while(intoTheLoop)
                        {
                            if(elements.get(indexToExecute).getRoot() != null)
                            {
                                if(elements.get(indexToExecute).getRoot().getValue() != TokenType.NEXT)
                                {
                                    indexToExecute++;
                                }
                                else
                                {
                                    intoTheLoop = false;
                                }
                            }
                            else
                            {
                                indexToExecute++;
                            }
                        }
                    }
                }
            	else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.DO)
            	{
        			indexToExecute++;
            	}
                else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.NEXT)
                {
                    int rollFor = 0;
                    if(openFor > 0)
                    {
                        while(rollFor < openFor)
                        {
                            if(elements.get(indexToExecute).getRoot() != null)
                            {
                                if(elements.get(indexToExecute).getRoot().getValue() != TokenType.FOR &&
                                   elements.get(indexToExecute).getRoot().getValue() != TokenType.STEP)
                                {
                                    indexToExecute--;
                                }
                                else
                                {
                                    openFor--;
                                }
                            }
                            else indexToExecute--;
                        }
                    }
                    else
                    {
                        indexToExecute++;
                    }
                }
            	else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.WHILE)
            	{
            		if(elements.get(indexToExecute).getRoot().isLeaf() == false)
            		{
            			ArrayList<Token> arttt = new ArrayList<Token>();
            			arttt.addAll(elements.get(indexToExecute).getRoot().getVars());
            			ArrayList<Token> finalArt = new ArrayList<Token>();
            			for(int z = 0; z < arttt.size(); z++)
            			{
            				if(arttt.get(z).getType() == TokenType.VARIABLE)
            				{
            					int im = searchInMemory(arttt.get(z).getLexeme().toString());
            					try
            					{
                					if(im >= 0)
                					{
                						Double ddd = Double.parseDouble(varValues.get(im));
                						Integer iii = ddd.intValue();
                    					finalArt.add(new Token(TokenType.INTEGER, iii));
                					}
                					else throw new SyntaxErrorException("Variabile non definita: " + arttt.get(z).getLexeme().toString());
            					}
            					catch(SyntaxErrorException e)
            					{
            						System.out.println(e);
            					}
            				}
            				else
            				{
            					finalArt.add(arttt.get(z));
            				}
            			}
            			try
            			{
                			Istruzione expr = new Istruzione(Mathematics.buildAST(finalArt));
                			Object exo = (Object)expr.esegui();
                			if(exo instanceof Integer)
                			{
                				Integer exi = (Integer) exo;
                				if(exi == 1)
                				{
                            		openWhile++;
                            		indexToExecute++;
                    			}
                                else
                                {
                                    Boolean intoTheLoop = true;
                                    while(intoTheLoop)
                                    {
                                        if(elements.get(indexToExecute).getRoot() != null)
                                        {
                                            if(elements.get(indexToExecute).getRoot().getValue() != TokenType.LOOP)
                                            {
                                                indexToExecute++;
                                            }
                                            else
                                            {
                                                intoTheLoop = false;
                                            }
                                        }
                                        else
                                        {
                                            indexToExecute++;
                                        }
                                    }
                                }
                			}
                			else if(exo instanceof Boolean)
                			{
                				Boolean exb = (Boolean) exo;
                				if(exb == true || exo.equals(true))
                				{
                            		openWhile++;
                            		indexToExecute++;
                				}
                                else
                                {
                                    Boolean intoTheLoop = true;
                                    while(intoTheLoop)
                                    {
                                        if(elements.get(indexToExecute).getRoot() != null)
                                        {
                                            if(elements.get(indexToExecute).getRoot().getValue() != TokenType.LOOP)
                                            {
                                                indexToExecute++;
                                            }
                                            else
                                            {
                                                intoTheLoop = false;
                                            }
                                        }
                                        else
                                        {
                                            indexToExecute++;
                                        }
                                    }
                                }
                			}
                			else if(exo instanceof Double)
                			{
                				Double exd = (Double) exo;
                				if(exd == 1)
                				{
                            		openWhile++;
                            		indexToExecute++;
                				}
                                else
                                {
                                    Boolean intoTheLoop = true;
                                    while(intoTheLoop)
                                    {
                                        if(elements.get(indexToExecute).getRoot() != null)
                                        {
                                            if(elements.get(indexToExecute).getRoot().getValue() != TokenType.LOOP)
                                            {
                                                indexToExecute++;
                                            }
                                            else
                                            {
                                                intoTheLoop = false;
                                            }
                                        }
                                        else
                                        {
                                            indexToExecute++;
                                        }
                                    }
                                }
                			}
            			}
            			catch(SyntaxErrorException e)
            			{
            				System.out.println(e);
            			}
        			}
        			else
        			{
            			Object exo = elements.get(indexToExecute).getRoot().esegui();
            			if(exo instanceof Integer)
            			{
            				Integer exi = (Integer) exo;
            				if(exi == 1)
            				{
                        		openWhile++;
                        		indexToExecute++;
                			}
                            else
                            {
                                Boolean intoTheLoop = true;
                                while(intoTheLoop)
                                {
                                    if(elements.get(indexToExecute).getRoot() != null)
                                    {
                                        if(elements.get(indexToExecute).getRoot().getValue() != TokenType.LOOP)
                                        {
                                            indexToExecute++;
                                        }
                                        else
                                        {
                                            intoTheLoop = false;
                                        }
                                    }
                                    else
                                    {
                                        indexToExecute++;
                                    }
                                }
                            }
            			}
            			else if(exo instanceof Boolean)
            			{
            				Boolean exb = (Boolean) exo;
            				if(exb == true || exo.equals(true))
            				{
                        		openWhile++;
                        		indexToExecute++;
            				}
                            else
                            {
                                Boolean intoTheLoop = true;
                                while(intoTheLoop)
                                {
                                    if(elements.get(indexToExecute).getRoot() != null)
                                    {
                                        if(elements.get(indexToExecute).getRoot().getValue() != TokenType.LOOP)
                                        {
                                            indexToExecute++;
                                        }
                                        else
                                        {
                                            intoTheLoop = false;
                                        }
                                    }
                                    else
                                    {
                                        indexToExecute++;
                                    }
                                }
                            }
            			}
            			else if(exo instanceof Double)
            			{
            				Double exd = (Double) exo;
            				if(exd == 1)
            				{
                        		openWhile++;
                        		indexToExecute++;
            				}
                            else
                            {
                                Boolean intoTheLoop = true;
                                while(intoTheLoop)
                                {
                                    if(elements.get(indexToExecute).getRoot() != null)
                                    {
                                        if(elements.get(indexToExecute).getRoot().getValue() != TokenType.LOOP)
                                        {
                                            indexToExecute++;
                                        }
                                        else
                                        {
                                            intoTheLoop = false;
                                        }
                                    }
                                    else
                                    {
                                        indexToExecute++;
                                    }
                                }
                            }
            			}
        			}
            	}
                else if(elements.get(indexToExecute).getRoot().getValue() == TokenType.LOOP)
                {
                    int rollWhile = 0;
                    if(openWhile > 0)
                    {
                        while(rollWhile < openWhile)
                        {
                            if(elements.get(indexToExecute).getRoot() != null)
                            {
                                if(elements.get(indexToExecute).getRoot().getValue() != TokenType.WHILE)
                                {
                                    indexToExecute--;
                                }
                                else
                                {
                                    openWhile--;
                                }
                            }
                            else indexToExecute--;
                        }
                    }
                    else
                    {
                        indexToExecute++;
                    }
                }
                else
                {
                    indexToExecute += (Integer) elements.get(indexToExecute).esegui();
                }
            }
        }
        
        return null;
    }
}
