package it.uniroma1.sapy.data;

/**
 * 
 * I tipi di token associati al loro valore intero che li divide in categorie
 * 
 * @author Sebastiano Michele Cossu
 *
 */
public enum TokenType
{
    AND(0), OR(0), NOT(0),
    GREATER(1), LESS(1), GTE(1), LTE(1), EQUAL(1), NOTEQUAL(1),
    PLUS(2), MINUS(2), DIV(2), MUL(2), MOD(2),
    TWOPOINTS(3), PARLEFT(3), PARRIGHT(3), EOL(3),
    IF(4), THEN(4), ELSE(4), ENDIF(4), FOR(4), TO(4), DO(4), NEXT(4), STEP(4), WHILE(4), LOOP(4),
    FUNCTION(5),
    INTEGER(6), TRUE(6), FALSE(6), STRING(6),
    VARIABLE(7),
    ASSIGN(8),
    VAREX(9),
    REM(10);

    private int value;

    /**
     * Costruisce il TokenType
     * 
     * @param value il valore del TokenType
     */
    private TokenType(int value)
    {
        this.value = value;
    }

    /**
     * Restituisce il valore del TokenType
     * @return il valore del TokenType
     */
    public int getValue()
    {
        return this.value;
    }
    
    /**
     * Traduce il TokenType in stringa
     * 
     * @return una stringa
     */
    public String toString()
    {
        switch(this)
        {
            case AND: return "AND";
            case OR: return "OR";
            case NOT: return "NOT";
            case GREATER: return "GREATER";
            case LESS: return "LESS";
            case GTE: return "GTE";
            case LTE: return "LTE";
            case EQUAL: return "EQUAL";
            case PLUS: return "PLUS";
            case MINUS: return "MINUS";
            case DIV: return "DIV";
            case MUL: return "MUL";
            case MOD: return "MOD";
            case TWOPOINTS: return "TWOPOINTS";
            case PARLEFT: return "PARLEFT";
            case PARRIGHT: return "PARRIGHT";
            case EOL: return "EOL";
            case IF: return "IF";
            case THEN: return "THEN";
            case ELSE: return "ELSE";
            case ENDIF: return "ENDIF";
            case FOR: return "FOR";
            case TO: return "TO";
            case DO: return "DO";
            case NEXT: return "NEXT";
            case STEP: return "STEP";
            case FUNCTION: return "FUNCTION";
            case INTEGER: return "INTEGER";
            case TRUE: return "TRUE";
            case FALSE: return "FALSE";
            case STRING: return "STRING";
            case VARIABLE: return "VARIABLE";
            default: return "NULL";
        }
    }
}
