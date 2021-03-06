package br.unisul.prolinear.core.compilador;

public interface ParserConstants
{
    int START_SYMBOL = 36;

    int FIRST_NON_TERMINAL    = 36;
    int FIRST_SEMANTIC_ACTION = 76;

    int[][] PARSER_TABLE =
    {
        { -1, -1, -1, -1,  0,  0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1,  1,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1,  5,  6, -1,  7, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1,  8,  9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 18, -1, 18, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 30, 29, -1, -1, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 10, -1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 10, -1, -1, -1, -1, 10, -1, -1, -1, -1, -1 },
        { -1, 14, -1, 14, -1, -1, 15, 15, -1, -1, -1, -1, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 14, 14, -1, -1, -1, -1, 14, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, 35, 35, -1, -1, -1, -1, 34, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 12, -1, 12, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 12, 12, -1, -1, -1, -1, 11, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, 45, 45, -1, -1, -1, -1, -1, 36, 37, 38, 39, 40, -1, -1, 41, -1, -1, -1, -1, -1, -1, -1, -1, -1, 42, 43, -1, 44, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 46, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 47, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, 28, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 59, -1, 59, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 55, 56, -1, 58, 57, -1, 59, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 60, 61, -1, 63, 62, 64, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, 21, 21, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, 21, 19, 20, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 23, -1, 22, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 13, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, 13, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1 },
        { -1, 24, -1, -1, -1, -1, -1, -1, 25, 25, -1, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, 25, 25, 25, 25, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 48, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, 66, -1, 65, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 67, -1, -1, -1, -1, -1 },
        { -1, 68, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 69, 69, -1, 69, 69, 69, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 49, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 32, 33, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 50, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 52, -1, -1, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 53, -1, -1, -1 },
        { -1,  4, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4,  4, -1, -1, -1, -1, -1, -1, -1,  3, -1, -1 },
        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 54, -1 }
    };

    int[][] PRODUCTIONS = 
    {
        {  37,  77,  74,  78,  41,  38,  82,  45,  47, 175,  39 },
        {   5 },
        {   6 },
        {  33, 101 },
        {   0 },
        {   9 },
        {  10,  11 },
        {  12 },
        {   7 },
        {   8 },
        {  63,  46 },
        {  30,   2,  81,  29,  84 },
        {   0 },
        {  83,  49,  41,  44,  85,  54,  86 },
        {  63,  46 },
        {   0 },
        {  24,  61,  60 },
        {  25,  88,  61,  87,  60 },
        {  61,  60 },
        {  24,  61,  60 },
        {  25,  88,  61,  87,  60 },
        {   0 },
        {   4,  79,  64 },
        {   2,  80 },
        {   2,  80 },
        {   0 },
        {  24,   4,  79 },
        {  25,  88,   4,  79,  87 },
        {   4,  79 },
        {  23 },
        {  22 },
        {  26 },
        {  19 },
        {  20 },
        {  13,  51 },
        {   0 },
        {  52 },
        {  53 },
        {  65 },
        {  68 },
        {  70 },
        {  71 },
        {  72 },
        {  73 },
        {  75 },
        {   0 },
        {  14,   2,  89,  26,   3,  90,  51 },
        {  15,   2,  91,  26,   4,  92,  51 },
        {  16,   2,  93,  26,   3,  94,  51 },
        {  17,   2,  95,  26,   3,  96,  51 },
        {  18,  26,  69,  97,  51 },
        {  21,  26,  69,  98,  51 },
        {  31,  26,   4,  99,  51 },
        {  32,  26,  69, 100,  51 },
        {  34,   2, 102,  51 },
        {  24,  66,  57 },
        {  25,  66,  57 },
        {  28,  66,  57 },
        {  27,  66,  57 },
        {  66,  57 },
        {  24,  66,  57 },
        {  25,  66,  57 },
        {  28,  66,  57 },
        {  27,  66,  57 },
        {   0 },
        {   4,  67 },
        {   2 },
        {  30,  56,  29 },
        {   2 },
        {   0 }
    };

    String[] PARSER_ERROR =
    {
        "",
        "Era esperado fim de programa",
        "Era esperado IDENTIFICADOR",
        "Era esperado STRING",
        "Era esperado VALOR",
        "Era esperado MAX",
        "Era esperado MIN",
        "Era esperado END",
        "Era esperado FIM",
        "Era esperado ST",
        "Era esperado SUBJECT",
        "Era esperado TO",
        "Era esperado RESTRICOES",
        "Era esperado PARAMETROS",
        "Era esperado DESCRICAO",
        "Era esperado CONVERSAO",
        "Era esperado CAMPO",
        "Era esperado FOLGA",
        "Era esperado SOMENTE_SOLUCAO",
        "Era esperado TRUE",
        "Era esperado FALSE",
        "Era esperado MOSTRA_ESTATISTICAS",
        "Era esperado \"<=\"",
        "Era esperado \">=\"",
        "Era esperado \"+\"",
        "Era esperado \"-\"",
        "Era esperado \"=\"",
        "Era esperado \"/\"",
        "Era esperado \"*\"",
        "Era esperado \")\"",
        "Era esperado \"(\"",
        "Era esperado DECIMAIS",
        "Era esperado REESCREVER",
        "Era esperado INT",
        "Era esperado GIN",
        "Era esperado \"'\"",
        "Problema inv�lido",
        "Tipo de problema inv�lido",
        "Erro identificando restri��es",
        "Erro identificando fim de modelo",
        "<EQUACAO> inv�lido",
        "<EXPRESSAO> inv�lido",
        "<EXPTERMO> inv�lido",
        "<EXPTERMO2> inv�lido",
        "<SINAL> inv�lido",
        "<RESTRICOES> inv�lido",
        "<RESTRIC1> inv�lido",
        "<PARAMETROS> inv�lido",
        "<PARAMETRO> inv�lido",
        "<IDENTREST> inv�lido",
        "<DEFINICOES> inv�lido",
        "<DEFINICAO> inv�lido",
        "<DESCRICAO> inv�lido",
        "<CONVERSAO> inv�lido",
        "<VALOR> inv�lido",
        "<DEFCAMPO> inv�lido",
        "<EXPRESSAOCAMPO> inv�lido",
        "<REPEXPCAMPO> inv�lido",
        "<EXPSIMP> inv�lido",
        "<REPEXPSIMP> inv�lido",
        "<REPEXP> inv�lido",
        "<TERMO> inv�lido",
        "<FATOR> inv�lido",
        "<RESTRICAO> inv�lido",
        "<REPTERMO> inv�lido",
        "<SETCAMPO> inv�lido",
        "<TERMOCAMPO> inv�lido",
        "<REPTERMOCAMPO> inv�lido",
        "<FOLGA> inv�lido",
        "<BOOLEAN> inv�lido",
        "<SOMENTE_SOLUCAO> inv�lido",
        "<MOSTRA_ESTATISTICAS> inv�lido",
        "<DECIMAIS> inv�lido",
        "<REESCREVER> inv�lido",
        "<INT> inv�lido",
        "<GIN> inv�lido"
    };
}
