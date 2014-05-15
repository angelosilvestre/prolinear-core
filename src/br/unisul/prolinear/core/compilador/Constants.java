package br.unisul.prolinear.core.compilador;

public interface Constants extends ScannerConstants, ParserConstants
{
    int EPSILON  = 0;
    int DOLLAR   = 1;

    int t_IDENTIFICADOR = 2;
    int t_STRING = 3;
    int t_VALOR = 4;
    int t_MAX = 5;
    int t_MIN = 6;
    int t_END = 7;
    int t_FIM = 8;
    int t_ST = 9;
    int t_SUB = 10;
    int t_TO = 11;
    int t_REST = 12;
    int t_PAR = 13;
    int t_DESC = 14;
    int t_CONV = 15;
    int t_CAMPO = 16;
    int t_FOLGA = 17;
    int t_SOMENTE_S = 18;
    int t_TRUE = 19;
    int t_FALSE = 20;
    int t_MOSTRA_ESTATISTICAS = 21;
    int t_TOKEN_22 = 22; //"<="
    int t_TOKEN_23 = 23; //">="
    int t_TOKEN_24 = 24; //"+"
    int t_TOKEN_25 = 25; //"-"
    int t_TOKEN_26 = 26; //"="
    int t_TOKEN_27 = 27; //"/"
    int t_TOKEN_28 = 28; //"*"
    int t_TOKEN_29 = 29; //")"
    int t_TOKEN_30 = 30; //"("
    int t_DECIMAIS = 31;
    int t_REESCREVER = 32;
    int t_INT = 33;
    int t_GIN = 34;
    int t_TOKEN_35 = 35; //"'"

}
