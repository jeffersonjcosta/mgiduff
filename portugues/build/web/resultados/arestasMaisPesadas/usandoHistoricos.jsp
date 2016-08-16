<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Graph Mining | IDUFF</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/public/css/grafos.css" cgharset="utf-8" />   
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/public/css/menu.css" cgharset="utf-8" />           
    </head>
    <body>
        <div id="menu_superior">
            <ul class="menu">
                <li class="current-item"><a href="${pageContext.request.contextPath}">Inputs</a></li>

                <li><a href="#">Datasets <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="${pageContext.request.contextPath}/datasets/estrutura">Structure</a></li>
                        <li><a href="${pageContext.request.contextPath}/datasets/download.jsp">Download</a></li>                                       
                        <li><a href='${pageContext.request.contextPath}/exemplos'>Examples</a></li>
                    </ul>
                </li>

                <li><a href="#">Graphs <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=gradeCurricular">Program Flowchart Graph</a></li>
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=historicos">Historical Curriculum</a></li>                                       
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=grafoMedio">Mean Graph</a></li>       
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=grafoMediano">Median Graph</a></li>       
                        <li><a href="${pageContext.request.contextPath}/grafos?listarIds=true&menu=grafos&item=grafoMaximo">Maximum Graph</a></li>       
                    </ul>
                </li>

                <li><a href="#">Results <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="#">Longests Paths &rsaquo;</a>                        
                            <ul class="submenu-2">                                                     
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=gradeCurricular'>Program Flowchar Graph</a></li>                                                       
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=historicos'>Historical Curriculum</a></li>  
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=grafoMedio'>Mean Graph</a></li>
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=grafoMediano'>Mean Graph</a></li>
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=grafoMaximo'>Median Graph</a></li>
                                <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=caminhosMaisLongos&subitem=frequentes'>Frequent Critical Paths</a></li>                                                                                                                 
                            </ul>
                        </li>                                                            
                        <li><a href="#">Heavier Edges &rsaquo;</a>
                            <ul class="submenu-2">
                                <li><a href="#">Using Longests Paths &rsaquo;</a>
                                    <ul class="submenu-3">
                                        <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=arestasMaisPesadas&subitem=cml&soma=pesos'>Sum of the Costs</a></li>
                                        <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=arestasMaisPesadas&subitem=cml&soma=qtd'>Number of Edges</a></li>                                          
                                    </ul>
                                </li>                                                         
                                <li><a href="#">Using Historical Curriculum &rsaquo;</a>
                                    <ul class="submenu-3">
                                        <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=arestasMaisPesadas&subitem=historicos&soma=pesos'>Sum of the Costs</a></li>
                                        <li><a href='${pageContext.request.contextPath}/grafos?listarIds=true&menu=resultados&item=arestasMaisPesadas&subitem=historicos&soma=qtd'>Number of Edges</a></li>                                          
                                    </ul>
                                </li>                                                                                     
                            </ul>
                        </li>
                    </ul>
                </li>
               
                <li><a href='${pageContext.request.contextPath}/sobre.jsp'>About</a></li>
                <li><a href='${pageContext.request.contextPath}/contato.jsp'>Contact</a></li>             
            </ul>
        </div>

        <div class="conteudo"> 
            
            <h2 style="line-height: 5px;">
                &rsaquo;&rsaquo; Heavier Edges - Using Historical Curriculum
                <hr/>            
            </h2>
            
            Choose a curriculum to see the heaviest edges.
            <br />
            Total of generated graphs: <b>${idsCurriculosSize}</b>          
            <br /><br />
            <table width="80%" border='1'>               
                <tr style="background-color: #e2e2e2;">                    
                    <td><b>Curriculum</b></td>
                    <td>
                        <b>Heavier edges - 
                            <c:choose>
                                <c:when test="${tipoDeSoma eq 'pesos'}">                                
                                    <c:choose>
                                        <c:when test="${tipoDeDataset eq 'cml'}">                                                                            
                                            Sum of the costs of the edges presents in the longest paths of historial
                                        </c:when>                                                           
                                        <c:otherwise>                                            
                                            Sum of the costs of the edges presents just in the historical
                                        </c:otherwise>                            
                                    </c:choose>                                    
                                </c:when>                                                           
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${tipoDeDataset eq 'cml'}">                                                                            
                                            Number of the edges presents in the longest paths of historical
                                        </c:when>                                                           
                                        <c:otherwise>                                            
                                            Number of edge in the historical
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
                            <a href="exibirGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&gephi=true" title="View with Gephi Gephi JS (without the edges's costs)">View with Gephi</a>                          
                            |
                            <a href="exibirGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&graphViz=true" title="View with GraphViz (with the edges's costs)">View with GraphViz</a>                           
                            |                                                    
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&gexf=true" title="Download GEXF file - Gephi's extension">Download GEXF</a>
                            |
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&dot=true" title="Download DOT file">Download DOT/GV</a>
                            |
                            <a href="baixarGrafo?idGrafo=${idCurr}&tipo=arestasMaisPesadas&txt=true" title="Download the graph with the same structure of the datasets">Download TXT</a>
                            |
                            <a href="resultados?idGrafo=${idCurr}&tipo=rankingArestasMaisPesadas&dataset=historicos&soma=${tipoDeSoma}" title="List the heavier edges (highest to lowest)">Ranking</a>                          
                        </td>
                    </tr>
                </c:forEach> 
            </table>                      
        </div>
    </body>       
</html>
