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

                <li class="current-item"><a href="#">Grafos <span class="arrow">&#9660;</span></a>
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

                <li><a href='${pageContext.request.contextPath}/exemplos'>Exemplos</a></li>
                <li><a href='${pageContext.request.contextPath}/sobre.jsp'>Sobre</a></li>
                <li><a href='${pageContext.request.contextPath}/contato.jsp'>Contato</a></li>    
            </ul>
        </div>

        <div class="conteudo">   
            
            <h2 style="line-height: 5px;">
                &rsaquo;&rsaquo; Grafos - Grafo m&eacute;dio
                <hr/>
            </h2>
            
            Escolha o curr&iacute;culo para o qual deseja visualizar o grafo m&eacute;dio.            
            <br />
            O grafo m&eacute;dio possui a mesma estrutura (v&eacute;rtices e arestas) do grafo da grade curricular, por&eacute;m o peso das suas arestas &eacute; conseguido com a m&eacute;dia de alunos que cursaram cada aresta.
            <br />
            Total de grafos gerados: <b>${idsAlunosSize}</b>
            <br /><br />            
            <table width="60%" border='1'>               
                <tr style="background-color: #e2e2e2;">                    
                    <td><b>Curr&iacute;culo</b></td>
                    <td><b>Grafo m&eacute;dio</b></td>
                </tr>
                <c:forEach var="idCurr" items="${idsCurriculos}">                                                                         
                    <tr>
                        <td width="20%">${idCurr}</td>                      
                        <td>
                            <a href="exibirGrafo?idGrafo=${idCurr}&tipo=grafoMedio&gephi=true" title="Visualizar grafo m&eacute;dio com Gephi JS (sem peso das arestas)">Visualizar com Gephi</a>                           
                            |
                            <a href="exibirGrafo?idGrafo=${idCurr}&tipo=grafoMedio&graphViz=true" title="Visualizar grafo m&eacute;dio com GraphViz/Vis.JS (com peso das arestas)">Visualizar com GraphViz</a>                           
                            |
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=grafoMedio&gexf=true" title="Baixar arquivo GEXF - extensão do Gephi">Baixar GEXF</a>
                            |
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=grafoMedio&dot=true" title="Baixar arquivo em DOT Language - linguagem do GraphViz">Baixar DOT/GV</a>
                            |
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=grafoMedio&txt=true" title="Baixar grafo m&eacute;dio com a mesma estrutura dos datasets">Baixar TXT</a>                                                       
                        </td>
                    </tr>
                </c:forEach> 
            </table>                      
        </div>
    </body>       
</html>
