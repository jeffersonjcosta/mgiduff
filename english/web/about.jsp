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
                &rsaquo;&rsaquo; About the project
                <hr/>
            </h2>

            <span>An increase in undergraduate registered students in universities largely grown last years. 
                However, the number of graduates remains low. The main cause of this issue is the evasion and / or retention in higher education courses. 
                Techniques and methods that assist the identification of causes and / or patterns are important in this area.
                The purpose of this paper is to propose methods based on graph mining to identify bottlenecks retention in undergraduate programs.
                The methods can be used both on graphs representing undergraduate programs and on graphs representing students grade reports.
                To evaluate the proposed methods, real data from a Brazilian federal higher education institution were used.
            </span>
        </div>
    </body>       
</html>
