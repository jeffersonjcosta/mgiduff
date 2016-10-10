<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <title>Graph Mining | IDUFF</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/public/css/graphs.css" cgharset="utf-8" />   
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/public/css/menu.css" cgharset="utf-8" />                 
    </head>
    <body>
        <div id="menu_superior">
            <ul class="menu">
                <li class="current-item"><a href="${pageContext.request.contextPath}">Inputs</a></li>

                <li><a href="#">Datasets <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="${pageContext.request.contextPath}/datasets_ijis/structure">Structure</a></li>
                        <li><a href="${pageContext.request.contextPath}/datasets_ijis/download.jsp">Download</a></li>                                       
                        <li><a href='${pageContext.request.contextPath}/examples'>Examples</a></li>
                    </ul>
                </li>

                <li><a href="#">Graphs <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="${pageContext.request.contextPath}/graphs?listIds=true&menu=graphs&item=flowchart">Program Flowchart Graph</a></li>
                        <li><a href="${pageContext.request.contextPath}/graphs?listIds=true&menu=graphs&item=historical">Historical Curriculum</a></li>                                       
                        <li><a href="${pageContext.request.contextPath}/graphs?listIds=true&menu=graphs&item=meanGraph">Mean Graph</a></li>       
                        <li><a href="${pageContext.request.contextPath}/graphs?listIds=true&menu=graphs&item=medianGraph">Median Graph</a></li>       
                        <li><a href="${pageContext.request.contextPath}/graphs?listIds=true&menu=graphs&item=maximumGraph">Maximum Graph</a></li>       
                    </ul>
                </li>

                <li><a href="#">Results <span class="arrow">&#9660;</span></a>
                    <ul class="submenu-1"> 
                        <li><a href="#">Longests Paths &rsaquo;</a>                        
                            <ul class="submenu-2">                                                     
                                <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=longestsPaths&subitem=flowchart'>Program Flowchar Graph</a></li>                                                       
                                <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=longestsPaths&subitem=historical'>Historical Curriculum</a></li>  
                                <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=longestsPaths&subitem=meanGraph'>Mean Graph</a></li>
                                <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=longestsPaths&subitem=medianGraph'>Median Graph</a></li>
                                <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=longestsPaths&subitem=maximumGraph'>Maximum Graph</a></li>
                                <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=longestsPaths&subitem=frequents'>Frequent Critical Paths</a></li>                                                                                                                 
                            </ul>
                        </li>                                                            
                        <li><a href="#">Heavier Edges &rsaquo;</a>
                            <ul class="submenu-2">
                                <li><a href="#">Using Longests Paths &rsaquo;</a>
                                    <ul class="submenu-3">
                                        <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=heaviestsEdges&subitem=lp&sum=weights'>Sum of the Costs</a></li>
                                        <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=heaviestsEdges&subitem=lp&sum=qtd'>Number of Edges</a></li>                                          
                                    </ul>
                                </li>                                                         
                                <li><a href="#">Using Historical Curriculum &rsaquo;</a>
                                    <ul class="submenu-3">
                                        <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=heaviestsEdges&subitem=historical&sum=weights'>Sum of the Costs</a></li>
                                        <li><a href='${pageContext.request.contextPath}/graphs?listIds=true&menu=results&item=heaviestsEdges&subitem=historical&sum=qtd'>Number of Edges</a></li>                                          
                                    </ul>
                                </li>                                                                                     
                            </ul>
                        </li>
                    </ul>
                </li>
               
                <li><a href='${pageContext.request.contextPath}/about.jsp'>About</a></li>
                <li><a href='${pageContext.request.contextPath}/contact.jsp'>Contact</a></li>             
            </ul>
        </div>
            
        <div class="conteudo">   
            
            <h2 style="line-height: 5px;">
                &rsaquo;&rsaquo; Graphs - Mean Graph
                <hr/>
            </h2>
            
            Choose the curriculum that you want to view the graph.            
            <br />
            The mean graph has the same structure (vertices and edges) of the program flowchart graph, but the weight of its edges is achieved with the average number of students who attended each edge.
            <br />
            Total of generated graphs: <b>${idsStudentsSize}</b>          
            <br /><br />            
            <table width="65%" border='1'>               
                <tr style="background-color: #e2e2e2;">                    
                    <td><b>Curriculum</b></td>
                    <td><b>Mean Graph</b></td>
                </tr>
                <c:forEach var="idCurr" items="${idsCurriculum}">                                                                         
                    <tr>
                        <td width="20%">${idCurr}</td>                      
                        <td>
                            <a href="viewGraph?idGraph=${idCurr}&type=meanGraph&gephi=true" title="View with Gephi JS (without the edges's costs)">View with Gephi</a>                           
                            |
                            <a href="viewGraph?idGraph=${idCurr}&type=meanGraph&graphViz=true" title="View with GraphViz (with the edges's costs)">View with GraphViz</a>                           
                            |
                            <a href="downloadGraph?idGraph=${idCurr}&type=meanGraph&gexf=true" title="Download GEXF file - Gephi's extension">Download GEXF</a>
                            |
                            <a href="downloadGraph?idGraph=${idCurr}&type=meanGraph&dot=true" title="Download DOT file">Download DOT/GV</a>
                            |
                            <a href="downloadGraph?idGraph=${idCurr}&type=meanGraph&txt=true" title="Download the graph with the same structure of the datasets">Download TXT</a>                                                       
                        </td>
                    </tr>
                </c:forEach> 
            </table>                      
        </div>
    </body>       
</html>
