Biblioteca para resolução de problemas de programação linear
============================================================

Utilizada para problemas de maximização ou minimização de uma função objetivo, sujeita a uma série de restrições.
Exemplo de problema de programação linear:

```
MIN
	0.03A + 0.04B
SUBJECT TO
	5A + 25B >= 50
	25A + 10B >= 100
	10A+ 10B >= 60
	35A + 20B >= 180
	A >= 0
	B >= 0
END
```


Modelagem de um problema de programação linear com a sintaxe aceita:
--------------------------------------------------------------------
---
* **Definição da função objetivo**
Inicia com o tipo do problema, onde pode ser utilizado o literal "MAX" quando o problema for de maximização, ou o literal "MIN" quando o problema for de minimização, seguido da expressão que calcula o valor da função. É importante ressaltar que o sistema considera o caractere “.” (Ponto) como separador decimal.
 
* **Definição das restrições**
Inicia com o literal "ST" (também aceita "SUBJECT TO" ou "RESTRICOES"), seguido das restrições definidas durante a modelagem.
Para cada restrição pode ser criado um identificador, que servirá para que a restrição possa ser referenciada nos parâmetros. A definição do identificador da restrição é feita colocando o identificador desejado, que não pode conter espaços e nem caracteres especiais, entre parênteses antes da restrição.
Ex.: (DEMANDA_MINIMA) X1 >= 5

* **Definição dos parâmetros**
	parte opcional da modelagem do problema que serve para configurar os parâmetros utilizados na exibição dos relatórios.  O ajuste de parâmetros do ProLinear  é feito após a definição de restrições, iniciando com o literal "PARAMETROS” seguido dos parâmetros desejados. Os parâmetros aceitos são:
  * **Descrição da Restrição:** descrição do que a restrição representa (matéria-prima, nº de funcionários, etc).  Utilização: inicia com o literal "DESCRICAO" seguido do identificador da restrição, seguido do sinal de igual e da descrição desejada,   que deve estar entre apóstrofes.
Ex.: DESCRICAO DEMANDA = 'Demanda mínima dos produtos'
  * **Fator de conversão:** constante que converte o valor da restrição para o número de unidades reais da restrição, utilizado para a exibição do relatório.
Ex.: Uma empresa possui 10 máquinas de corte e a jornada de trabalho semanal é de 40 horas. Sendo assim, a restrição de corte seria de 400 horas. Configura-se, então, a taxa de conversão como 40, pois cada 40 horas representa uma máquina. Se durante a resolução é encontrada uma folga no valor de 80, o sistema converterá a folga de acordo com o fator configurado, informando, neste caso, uma folga de 2 máquinas. Utilização: inicia com o 	literal "CONVERSAO" seguido do identificador da restrição, seguido do sinal de igual e do fator de conversão utilizado.
Ex.: CONVERSAO MAQUINAS_CORTE = 40
  * **Descrição de folga:** descrição da folga, que será utilizada na geração do relatório. Seguindo o exemplo anterior, caso for configurado a descrição de folga como "ociosas", o relatório conterá a seguinte mensagem: "Foi detectado 2 máquinas de corte ociosas". Utilização: inicia com o literal "FOLGA", seguido do identificador da restrição, seguido do sinal de igual e da descrição da folga entre apóstrofes
Ex.: FOLGA MAQUINAS_CORTE = 'ociosas'  
  * **Casas Decimais:** número de casas decimais utilizadas no arredondamento dos valores.  Utilização: inicia com o literal "CASAS_DECIMAIS", seguido do sinal de igual e do número de casas decimais desejadas.
Ex.: CASAS_DECIMAIS = 3
  * **Exibir somente a solução:** utilizado quando o usuário desejar que sejam exibidos apenas os valores da função objetivo e das variáveis.  Utilização: inicia com o literal "SOMENTE_SOLUCAO", seguido do sinal de igual e de uma variável booleana. O valor padrão para este parâmetro é FALSE.
Ex.: SOMENTE_SOLUCAO = TRUE
  * **Exibir estatísticas:** utilizado quando o usuário desejar que o sistema exiba o número de variáveis e restrições do problema modelado. Utilização: inicia com o literal "MOSTRA_ESTATISTICAS", seguido do sinal de igual e uma variável booleana. O valor padrão para este parâmetro é FALSE.
Ex: MOSTRAR_ESTATISTICAS = TRUE
  * **Reescrever problema:** utilizado quando o usuário desejar que o sistema reescreva o problema original, ajustando o valor das restrições para remover as folgas, respeitando o fator de conversão, caso exista.  Seguindo o exemplo das máquinas de corte, o sistema reescreverá a restrição de máquinas de corte para 320, ou seja, 400 do valor original subtraindo a folga de 80.   Utilização: inicia com o literal "REESCREVER", seguido do sinal de igual e uma variável booleana. O valor padrão para este parâmetro é TRUE.
Ex.: REESCREVER = FALSE
  * **Transformar variável para valor inteiro:** utilizado quando o usuário desejar que determinada variável assuma, obrigatoriamente, um valor inteiro. Utilização: inicia com o literal "GIN", seguido do nome da variável. Para utilizar programação inteira o aluno pode utilizar o parâmetro “INT” ao lado do tipo do problema. Fazendo isso o sistema assumirá que todas as variáveis da função objetivo receberão valores inteiros
Ex.: GIN X1
  * **Definição de campo calculado:** utilizado para definir campos cujo valor é o resultado de uma expressão numérica contendo os valores das variáveis do problema. Utilização: inicia com o literal “CAMPO”, seguido do identificado desejado para o campo, seguido pelo sinal de igual e da expressão numérica desejada, entre apóstrofes. As operações suportadas são adição (+), subtração (-), multiplicação (*), divisão (/) e potenciação(^).
Ex.: CAMPO PERC_X1 = 'X1 / (X1 + X2)'


Bibliotecas utilizadas
----------------------
---
* [JEP](http://sourceforge.net/projects/jep/) Para resolver campos calculados
 
Exemplo de utilização
---------------------
```
try {
	SimplexSolver solver = new SimplexSolver();
	Solucao s = solver.solve("*modelagem do problema*");
	//set de valores encontrados para as variáveis
	for(Variavel v:s.getResultados().keySet()){
		//valor de cada variável
		Double valor = s.getValorVariavel(v);
	}
	//valor otimizado do problema
	Double valorTotalEncontrado = s.getValorOtimo();
	for(Restricao r:s.getProblema().getRestricoes()){
		//valor original do lado direito da equação
		Double valorOriginal = r.getValorEquacaoOriginal();
		//valor da folga da restrição
		Double folgaRestricao = r.getFolga();
		//valor do lado direito, descontando a folta = valorOriginal - folgaRestricao
		Double valorSemFolga = r.getValorOtimizado();
	}
} catch (LexicalError e) {
	//tratar erros léxicos
} catch (SyntaticError e) {
	//tratar erros sintáticos
} catch (SemanticError e) {
	//tratar erros semânticos
} catch (UnboundedModelException e) {
	//tratar erros onde o modelo não possui limites
} catch (InfeasibleModelException e) {
	//tratar erros onde o problema não possui solução
}
```
