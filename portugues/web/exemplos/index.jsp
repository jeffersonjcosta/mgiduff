<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Mineração de Grafos | IDUFF</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/public/css/grafos.css" cgharset="utf-8" />   
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/public/css/menu.css" cgharset="utf-8" />            
    </head>
    <body>
        <div id="menu_superior">
            <ul class="menu">
                <li><a href="${pageContext.request.contextPath}">Inputs</a></li>

                <li><a href="#">Datasets <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="${pageContext.request.contextPath}/datasets/estrutura">Estrutura</a></li>
                        <li><a href="${pageContext.request.contextPath}/datasets/download.jsp">Download</a></li>                                       
                    </ul>
                </li>

                <li><a href="#">Grafos <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=gradeCurricular">Grade Curricular</a></li>
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=historicos">Hist&oacute;ricos</a></li>                                       
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=grafoMedio">Grafo m&eacute;dio</a></li>       
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=grafoMediano">Grafo mediano</a></li>       
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=grafoMaximo">Grafo m&aacute;ximo</a></li>  
                    </ul>
                </li>

                <li><a href="#">Resultados <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="#">Caminhos mais longos &rsaquo;</a>                        
                            <ul class="submenu-2">                                                     
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=gradeCurricular'>Grade Curricular</a></li>                                                       
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=historicos'>Hist&oacute;ricos</a></li>  
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=grafoMedio'>Grafo m&eacute;dio</a></li>
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=grafoMediano'>Grafo mediano</a></li>
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=grafoMaximo'>Grafo m&aacute;ximo</a></li>
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=frequentes'>Mais frequentes</a></li>                                                                                                                 
                            </ul>
                        </li>                                                            
                        <li><a href="#">Arestas mais pesadas &rsaquo;</a>
                            <ul class="submenu-2">
                                <li><a href="#">Usando os CML &rsaquo;</a>
                                    <ul class="submenu-3">
                                        <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=arestasMaisPesadas&subitem=cml&soma=pesos'>Soma dos Pesos</a></li>
                                        <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=arestasMaisPesadas&subitem=cml&soma=qtd'>Quantidade das Arestas</a></li>                                          
                                    </ul>
                                </li>                                                         
                                <li><a href="#">Usando os históricos &rsaquo;</a>
                                    <ul class="submenu-3">
                                        <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=arestasMaisPesadas&subitem=historicos&soma=pesos'>Soma dos Pesos</a></li>
                                        <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=arestasMaisPesadas&subitem=historicos&soma=qtd'>Quantidade das Arestas</a></li>                                          
                                    </ul>
                                </li>                                                                                     
                            </ul>
                        </li>
                    </ul>
                </li>

                <li class="current-item"><a href='${pageContext.request.contextPath}/exemplos'>Exemplos</a></li>
                <li><a href='${pageContext.request.contextPath}/sobre.jsp'>Sobre</a></li>
                <li><a href='${pageContext.request.contextPath}/contato.jsp'>Contato</a></li>    
            </ul>
        </div>

        <div class="conteudo">            
             
            <h2 style="line-height: 5px;">
                &rsaquo;&rsaquo; Exemplos
                <hr/>
            </h2>
            
            <ul>
                <li><a href="${pageContext.request.contextPath}/exemplos/arquivos/pre_requisitos.csv">Exemplo de CSV com Disciplinas e Pr&eacute;-requisitos</a></li>
                <li><a href="${pageContext.request.contextPath}/exemplos/arquivos/equivalencias.csv">Exemplo de CSV com Disciplinas e Equival&ecirc;ncias</a></li>
                <li><a href="${pageContext.request.contextPath}/exemplos/arquivos/acompanhamentos.csv">Exemplo de CSV com Acmpanhamentos dos Alunos</a></li>
                <li><a href="${pageContext.request.contextPath}/exemplos/arquivos/historicos.csv">Exemplo de CSV com Hist&oacute;ricos dos Alunos</a></li>
            </ul>  

            <br />

            <fieldset>
                <legend>&nbsp;Orienta&ccedil;&otilde;es&nbsp;</legend>
                <ul>
                    <li>Os arquivos de entrada devem estar no formato TXT ou CSV;</li>
                    <li>Os valores devem ser separadas por ';' (ponto e vírgula);</li>                                       
                    <li>Os datasets gerados ter&atilde;o uma estrutura baseada no software gSpan de YAN e HAN (2002) - como pode ser visto na seção <b><a href="${pageContext.request.contextPath}/datasets/estrutura">Datasets -> Estrutura</a></b></li>                                                                            
                    <li>Para a gera&ccedil;&atilde;o dos grafos da grade curricular de cada curso, dever&aacute; ser enviado um arquivo (CSV ou TXT) contendo as disciplinas com seus respectivos pr&eacute;-requisitos;</li>                    
                    <li>&Eacute; importante ressaltar que as disciplinas que n&atilde;o obrigat&oacute;rias e as que n&atilde;o possuem pr&eacute;-requisitos n&atilde;o devem estar presentes nesse <i>input</i>;</li>
                    <li>No caso de co-requisitos, a disciplina com maior carga hor&aacute;ria te&oacute;rica deve aparecer como pr&eacute;-requisito da disciplina com carga hor&aacute;ria pr&aacute;tica.</li>
                    <li>&Eacute; necess&aacute;rio enviar tamb&eacute;m um arquivo contendo as equival&ecirc;ncias de cada disciplina. Esse <i>input</i> deve conter a identifica&ccedil;&atilde;o do curr&iacute;culo, seguido da disciplina e sua equival&ecirc;ncia;</li>
                    <li>Para a gera&ccedil;&atilde;o dos grafos dos hist&oacute;ricos dos alunos &eacute; necess&aacute;rio o envio de dois arquivos: um com o acompanhamento dos alunos (semestre a semestre) e outro com os hist&oacute;ricos;</li>
                    <li>No arquivo de acompanhamento &eacute; necess&aacute;rio constar a identifica&ccedil;&atilde;o do aluno, a situa&ccedil;&atilde;o em cada semestre, o ano/semestre e o seu curr&iacute;culo naquele semestre;</li>
                    <li>O hist&oacute;rico do aluno deve conter o seu <i>id</i>, o ano e o semestre, a disciplina que cursou nesse semestre, a nota na disciplina, a nota da verificação sumplementar (VS, caso haja), a frequencia e a situa&ccedil;&atilde;o do aluno na disciplina;</li>
                </ul>                                         
            </fieldset> 

            <br /><br />            

            <fieldset>
                <legend>&nbsp;Formatos&nbsp;</legend>
                <ul>
                    <li>
                        Formato padr&atilde;o - Disciplinas e Pr&eacute;-requisitos: 
                        <br />
                        ID_CURRICULO;ID_DISCIPLINA;PERIODO_DISCIPLINA;CHT;CHP;ID_PRE_REQUISITO;PERIODO_PRE_REQUISITO                     
                    </li> 
                    <li>
                        Formato padr&atilde;o - Equival&ecirc;ncias: 
                        <br />
                        ID_CURRICULO;ID_DISCIPLINA_CURRICULO;ID_EQUIVALENCIA                      
                    </li> 
                    <li>
                        Formato padr&atilde;o - Acompanhamentos: 
                        <br />
                        ID_ALUNO;STATUS;SITUACAO;ANO_SEMESTRE;ID_CURRICULO                       
                    </li> 
                    <li>
                        Formato padr&atilde;o - Hist&oacute;ricos: 
                        <br />
                        ID_ALUNO;ANO_SEMESTRE;ID_DISCIPLINA;NOTA;NOTA_VS;FREQUENCIA;SITUAÇÃO_DISCIPLINA                       
                    </li> 
                </ul>
            </fieldset>         
        </div>
    </body>       
</html>
