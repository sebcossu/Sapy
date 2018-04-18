package it.uniroma1.sapy.data;

import java.util.HashMap;

/**
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public class Token
{    
    private TokenType type;
    private String lexeme;


    /**
     * Costruttore
     */
    public Token()
    {
        this.type = null;
        this.lexeme = "";
    }

    /**
     * Costruttore
     * @param t un token da cui copiarsi
     */
    public Token(Token t)
    {
        this.lexeme = t.getLexeme().toUpperCase();
        this.type = t.getType();
    }

    /**
     * Costruttore
     * 
     * @param tt un TokenType
     * @param lexeme un lessema stringa
     */
    public Token(TokenType tt, String lexeme)
    {
        this.lexeme = lexeme;
        this.type = tt;
    }

    /**
     * Costruttore
     * 
     * @param tt un TokenType
     * @param lexeme un lessema intero
     */
    public Token(TokenType tt, int lexeme)
    {
        this.lexeme = String.valueOf(lexeme);
        this.type = tt;
    }

    public Token(TokenType tt, double lexeme)
    {
        this.lexeme = String.valueOf(lexeme);
        this.type = tt;
    }

    /**
     * Costruttore
     * 
     * @param type un tipo di token (stringa)
     * @param lexeme un lessema (stringa)
     */
    public Token(String type, String lexeme)
    {
        if(type.equalsIgnoreCase("AND")) this.type = TokenType.AND;
        else if(type.equalsIgnoreCase("OR")) this.type = TokenType.OR;
        else if(type.equalsIgnoreCase("NOT")) this.type = TokenType.NOT;
        else if(type.equalsIgnoreCase("GREATER")) this.type = TokenType.GREATER;
        else if(type.equalsIgnoreCase("LESS")) this.type = TokenType.LESS;
        else if(type.equalsIgnoreCase("GTE")) this.type = TokenType.GTE;
        else if(type.equalsIgnoreCase("LTE")) this.type = TokenType.LTE;
        else if(type.equalsIgnoreCase("EQUAL")) this.type = TokenType.EQUAL;
        else if(type.equalsIgnoreCase("NOTEQUAL")) this.type = TokenType.NOTEQUAL;
        else if(type.equalsIgnoreCase("PLUS")) this.type = TokenType.PLUS;
        else if(type.equalsIgnoreCase("MINUS")) this.type = TokenType.MINUS;
        else if(type.equalsIgnoreCase("MUL")) this.type = TokenType.MUL;
        else if(type.equalsIgnoreCase("DIV")) this.type = TokenType.DIV;
        else if(type.equalsIgnoreCase("MOD")) this.type = TokenType.MOD;
        else if(type.equalsIgnoreCase("TWOPOINTS")) this.type = TokenType.TWOPOINTS;
        else if(type.equalsIgnoreCase("PARLEFT")) this.type = TokenType.PARLEFT;
        else if(type.equalsIgnoreCase("PARRIGHT")) this.type = TokenType.PARRIGHT;
        else if(type.equalsIgnoreCase("EOL")) this.type = TokenType.EOL;
        else if(type.equalsIgnoreCase("IF")) this.type = TokenType.IF;
        else if(type.equalsIgnoreCase("THEN")) this.type = TokenType.THEN;
        else if(type.equalsIgnoreCase("ELSE")) this.type = TokenType.ELSE;
        else if(type.equalsIgnoreCase("ENDIF")) this.type = TokenType.ENDIF;
        else if(type.equalsIgnoreCase("FOR")) this.type = TokenType.FOR;
        else if(type.equalsIgnoreCase("TO")) this.type = TokenType.TO;
        else if(type.equalsIgnoreCase("DO")) this.type = TokenType.DO;
        else if(type.equalsIgnoreCase("NEXT")) this.type = TokenType.NEXT;
        else if(type.equalsIgnoreCase("STEP")) this.type = TokenType.STEP;
        else if(type.equalsIgnoreCase("WHILE")) this.type = TokenType.WHILE;
        else if(type.equalsIgnoreCase("LOOP")) this.type = TokenType.LOOP;
        else if(type.equalsIgnoreCase("FUNCTION")) this.type = TokenType.FUNCTION;
        else if(type.equalsIgnoreCase("INTEGER")) this.type = TokenType.INTEGER;
        else if(type.equalsIgnoreCase("TRUE")) this.type = TokenType.TRUE;
        else if(type.equalsIgnoreCase("FALSE")) this.type = TokenType.FALSE;
        else if(type.equalsIgnoreCase("STRING")) this.type = TokenType.STRING;
        else if(type.equalsIgnoreCase("VARIABLE")) this.type = TokenType.VARIABLE;
        else if(type.equalsIgnoreCase("REM")) this.type = TokenType.REM;
        else this.type = null;
        
        this.lexeme = lexeme;
    }

    /**
     * Imposta il TokenType
     * 
     * @param tt il TokenType
     */
    public void setType(TokenType tt)
    {
        this.type = tt;
    }

    /**
     * Imposta il lessema
     * @param lexeme il lessema (stringa)
     */
    public void setLexeme(String lexeme)
    {
        this.lexeme = lexeme;
    }

    /**
     * Imposta il tokentype e il lessema
     * @param tt tokentype
     * @param lexeme stringa lessema
     */
    public void setAll(TokenType tt, String lexeme)
    {
        this.type = tt;
        this.lexeme = lexeme;
    }

    /**
     * restituisce il tokentype
     * 
     * @return tokentype
     */
    public TokenType getType()
    {
        return this.type;
    }

    /**
     * restituisce il lessema
     * @return stringa il lessema
     */
    public String getLexeme()
    {
        return this.lexeme;
    }

    /**
     * restituisce il tipo di tokentype
     * @return un intero
     */
    public int getCode()
    {
        return this.type.getValue();
    }

    /**
     * Restituisce vero se il Token &egrave; vuoto
     * 
     * @return un booleano
     */
    public Boolean isEmpty()
    {
        if(type == null && lexeme.isEmpty())
        {
            return true;
        }
        return false;
    }

    /**
     * inizializza il token
     */
    public void cleanAll()
    {
        this.type = null;
        this.lexeme = "";
    }

    /**
     * copia il token da un altro token
     * 
     * @param t il token da cui copiare il token attuale
     */
    public void copyFrom(Token t)
    {
        this.type = t.getType();
        this.lexeme = t.getLexeme();
    }
}
