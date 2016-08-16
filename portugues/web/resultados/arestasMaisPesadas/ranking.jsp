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

                <li class="current-item"><a href="#">Resultados <span class="arrow">&#9660;</span></a>
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
                    &rsaquo;&rsaquo; Arestas mais pesadas
                    <hr/>            
            </h2>
            
            <h2 style="margin-top:0;">                                                 
                Ranking das arestas mais pesadas - 
                Usando os
                <c:choose>
                    <c:when test="${tipoDeDataset eq 'cml'}">
                        caminhos mais longos
                    </c:when>
                    <c:otherwise>
                        hist&oacute;ricos
                    </c:otherwise>    
                </c:choose> 
                 
                <br />
                Tipo de soma: 
                <c:choose>
                    <c:when test="${tipoDeSoma eq 'pesos'}">
                        Pesos das arestas
                    </c:when>
                    <c:otherwise>
                        Quantidade de arestas
                    </c:otherwise>    
                </c:choose> 
            </h2>
            <h3>Currículo: ${idGrafo}</h3>
            <table width="30%" border='1'>
                <tr style="background-color: #e2e2e2; font-weight: bold;">
                    <td width="70%">Aresta</td>
                    <td style="text-align: center;">Peso*</td>
                </tr>

                <c:forEach var="map" items="${mapOrdenado}">
                    <tr>
                        <td>${map.key}</td>
                        <td style="text-align: center;">${map.value}</td>
                    </tr>                                            
                </c:forEach>     
            </table>

            <c:choose>
                <c:when test="${tipoDeSoma eq 'pesos'}">
                    <c:choose>
                        <c:when test="${tipoDeDataset eq 'cml'}">
                            * O peso da aresta, neste caso, representa a soma dos pesos de cada aresta encontrada em todos os caminhos mais longos dos hist&oacute;ricos. 
                            <br />
                            Ou seja, representa a soma da quantidade de vezes que cada aluno cursou a aresta.
                        </c:when>                                             
                        <c:otherwise>
                            * O peso da aresta, neste caso, representa a soma dos pesos de cada aresta encontrada nos hist&oacute;ricos dos alunos. 
                            <br />
                            Ou seja, representa a soma da quantidade de vezes que cada aluno cursou a aresta.
                        </c:otherwise>
                    </c:choose>                   
                </c:when>                    
                <c:otherwise>
                    <c:choose>
                        <c:when test="${tipoDeDataset eq 'cml'}">
                            * O peso da aresta, neste caso, representa a soma da quantidade de vezes que a aresta aparece nos caminhos mais longos dos hist&oacute;ricos.
                        </c:when>                                             
                        <c:otherwise>
                            * O peso da aresta, neste caso, representa a soma da quantidade de vezes que a aresta aparece nos hist&oacute;ricos dos alunos.
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    </body>       
</html>