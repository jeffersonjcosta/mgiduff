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
                &rsaquo;&rsaquo; Arestas mais pesadas - Usando os hist&oacute;ricos dos alunos
                <hr/>            
            </h2>
            
            Escolha um curr&iacute;culo para visualizar as arestas mais caras/pesadas.
            <br />
            Total de grafos gerados: <b>${idsCurriculosSize}</b>
            <br /><br />
            <table width="80%" border='1'>               
                <tr style="background-color: #e2e2e2;">                    
                    <td><b>Curr&iacute;culo</b></td>
                    <td>
                        <b>Arestas mais caras - 
                            <c:choose>
                                <c:when test="${tipoDeSoma eq 'pesos'}">                                
                                    <c:choose>
                                        <c:when test="${tipoDeDataset eq 'cml'}">                                
                                            Soma dos pesos das arestas nos caminhos mais longos dos hist&oacute;ricos
                                        </c:when>                                                           
                                        <c:otherwise>
                                            Soma dos pesos das arestas nos hist&oacute;ricos dos alunos
                                        </c:otherwise>                            
                                    </c:choose>                                    
                                </c:when>                                                           
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${tipoDeDataset eq 'cml'}">                                
                                            Quantidade de aresta nos caminhos mais longos dos hist&oacute;ricos
                                        </c:when>                                                           
                                        <c:otherwise>
                                            Quantidade de aresta nos hist&oacute;ricos dos alunos
                                        </c:otherwise>                            
                                    </c:choose>                                      
                                </c:otherwise>                            
                            </c:choose>
                        </b>
                    </td>
                </tr>
                <c:forEach var="idCurr" items="${idsCurriculos}">                                                                         
                    <tr>
                        <td width="30%">${idCurr}</td>                      
                        <td>
                            <a href="exibirGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&gephi=true" title="Visualizar grafo com Gephi JS (sem peso das arestas)">Visualizar com Gephi</a>                          
                            |
                            <a href="exibirGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&graphViz=true" title="Visualizar grafo com GraphViz/Vis.JS (com peso das arestas)">Visualizar com GraphViz</a>                           
                            |                                                    
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&gexf=true" title="Baixar arquivo GEXF - extensão do Gephi">Baixar GEXF</a>
                            |
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&dot=true" title="Baixar arquivo em DOT Language - linguagem do GraphViz">Baixar DOT/GV</a>
                            |
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&txt=true" title="Baixar grafo com a mesma estrutura dos datasets">Baixar TXT</a>
                            |
                            <a href="resultados?idGrafo=${idCurr}&tipo=rankingArestasMaisPesadas&dataset=historicos&soma=${tipoDeSoma}" title="Listar as arestas mais pesadas (da maior para a menor)">Ranking</a>                          
                        </td>
                    </tr>
                </c:forEach> 
            </table>                      
        </div>
    </body>       
</html>
