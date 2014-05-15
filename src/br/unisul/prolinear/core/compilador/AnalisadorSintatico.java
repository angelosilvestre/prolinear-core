package br.unisul.prolinear.core.compilador;

import java.util.Stack;

import br.unisul.prolinear.core.exceptions.UnboundedModelException;

public class AnalisadorSintatico implements Constants
{
    private Stack stack = new Stack();
    private Token currentToken;
    private Token previousToken;
    private AnalisadorLexico scanner;
    private AnalisadorSemantico semanticAnalyser;

    private static final boolean isTerminal(int x)
    {
        return x < FIRST_NON_TERMINAL;
    }

    private static final boolean isNonTerminal(int x)
    {
        return x >= FIRST_NON_TERMINAL && x < FIRST_SEMANTIC_ACTION;
    }

    private static final boolean isSemanticAction(int x)
    {
        return x >= FIRST_SEMANTIC_ACTION;
    }

    private boolean step() throws LexicalError, SyntaticError, SemanticError, UnboundedModelException
    {
        if (currentToken == null)
        {
            int pos = 0;
            if (previousToken != null)
                pos = previousToken.getPosition()+previousToken.getLexeme().length();

            currentToken = new Token(DOLLAR, "$", pos);
        }

        int x = ((Integer)stack.pop()).intValue();
        int a = currentToken.getId();

        if (x == EPSILON)
        {
            return false;
        }
        else if (isTerminal(x))
        {
            if (x == a)
            {
                if (stack.empty())
                    return true;
                else
                {
                    previousToken = currentToken;
                    currentToken = scanner.nextToken();
                    return false;
                }
            }
            else
            {	
            	String anterior = "";
            	if(previousToken != null){
            		anterior = " depois de "+previousToken.getLexeme();
            	}else{
            		anterior = " no começo do modelo";
            	}
            	String atual = currentToken.getLexeme();            	
            	if(atual.equals("$")){
            		atual = "Fim de Arquivo";
            	}
            	atual = "Não esperava "+atual;
                throw new SyntaticError(atual +anterior, currentToken.getPosition());
            }
        }
        else if (isNonTerminal(x))
        {
            if (pushProduction(x, a))
                return false;
            else {
            	String anterior = "";
            	if(previousToken != null){
            		anterior = " depois de "+previousToken.getLexeme();
            	}else{
            		anterior = " no começo do modelo";
            	}
            	String atual = currentToken.getLexeme();            	
            	if(atual.equals("$")){
            		atual = "Fim de Arquivo";
            	}
            	atual = "Não esperava "+atual;
                throw new SyntaticError(atual +anterior, currentToken.getPosition());
            }
        }
        else // isSemanticAction(x)
        {
            semanticAnalyser.executeAction(x-FIRST_SEMANTIC_ACTION, previousToken);
            return false;
        }
    }

    private boolean pushProduction(int topStack, int tokenInput)
    {
        int p = PARSER_TABLE[topStack-FIRST_NON_TERMINAL][tokenInput-1];
        if (p >= 0)
        {
            int[] production = PRODUCTIONS[p];
            //empilha a produção em ordem reversa
            for (int i=production.length-1; i>=0; i--)
            {
                stack.push(new Integer(production[i]));
            }
            return true;
        }
        else
            return false;
    }

    public void parse(AnalisadorLexico scanner, AnalisadorSemantico semanticAnalyser) throws LexicalError, SyntaticError, SemanticError, UnboundedModelException
    {
        this.scanner = scanner;
        this.semanticAnalyser = semanticAnalyser;

        stack.clear();
        stack.push(new Integer(DOLLAR));
        stack.push(new Integer(START_SYMBOL));

        currentToken = scanner.nextToken();

        while ( ! step() )
            ;
    }
}
