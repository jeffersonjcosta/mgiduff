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
                    &rsaquo;&rsaquo; Heavier edges
                    <hr/>            
            </h2>
            
            <h2 style="margin-top:0;">                                                 
                Ranking of heavier edges - 
                Using 
                <c:choose>
                    <c:when test="${tipoDeDataset eq 'cml'}">
                        Longests Paths
                    </c:when>
                    <c:otherwise>
                        Historical Curriculum
                    </c:otherwise>    
                </c:choose> 
                 
                <br />
                Type of sum: 
                <c:choose>
                    <c:when test="${tipoDeSoma eq 'pesos'}">
                        Sum of the Costs
                    </c:when>
                    <c:otherwise>
                        umber of Edges
                    </c:otherwise>    
                </c:choose> 
            </h2>
            <h3>Curriculum ${idGrafo}</h3>
            <table width="30%" border='1'>
                <tr style="background-color: #e2e2e2; font-weight: bold;">
                    <td width="70%">Edge</td>
                    <td style="text-align: center;">Cost*</td>
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
                            * The cost of edge, in this case, represents the sum of the weights of each edge found in all the longest paths of historical 
                            <br />
                            That is, is the sum of the number of times each student attended the edge.
                        </c:when>                                             
                        <c:otherwise>                            
                            * The weight of the edge, in this case, is the sum of the weights of each edge found in the historical.
                            <br />
                            That is, it is the sum of the number of times each student attended the edge.
                        </c:otherwise>
                    </c:choose>                   
                </c:when>                    
                <c:otherwise>
                    <c:choose>
                        <c:when test="${tipoDeDataset eq 'cml'}">
                            * The weight of the edge in this case is the sum of the number of times that the edge appears at the longest paths of the historical.
                        </c:when>                                             
                        <c:otherwise>
                            * The weight of the edge in this case is the sum of the number of times that the edge appears at the longest paths of the historical.
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </div>
    </body>       
</html>