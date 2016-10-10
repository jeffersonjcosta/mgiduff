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
                &rsaquo;&rsaquo; Inputs                
                <hr/>
            </h2>
                        
            For more information about the files to be sent, please, acess the menu <b><a href='${pageContext.request.contextPath}/examples'>Datasets - Examples</a></b>

            <br /><br /><br />
            
            <form method="post" action="upload" enctype="multipart/form-data">               
                <fieldset>
                    <legend>&nbsp;Program Flowchar&nbsp;</legend>

                    <strong>File 1 - Disciplines and Prerequisite</strong>  
                    <br />
                    <input type="file" name="preRequisitos" /> 

                    <br /><br />

                    <strong>File 2 - Equivalences</strong>                           
                    <br />
                    <input type="file" name="equivalences" /> 

                </fieldset>

                <br /><br />

                <fieldset>
                    <legend>&nbsp;Historical Curriculum&nbsp;</legend>

                    <strong>File 3 - Students's accompaniments</strong>
                    <br />
                    <input type="file" name="accompaniments" />                  

                    <br /><br />

                    <strong>File 4 - Historical</strong>
                    <br />
                    <input type="file" name="historical"/>                                                          
                </fieldset> 

                <br />

                <input type="submit" name="submit" value="Send"/>
                <input type="reset" name="limpar" value="Cancel"/>   

                <c:if test="${extensionError}">                                        
                    <br /><br />
                    <span class='error'>Only TXT or CSV files are accepted!</span>                    
                </c:if>     

                <c:if test="${uploadOK}"> 
                    <br /><br />
                    <fieldset class="aviso">
                        Successfully uploaded files.                      
                    </fieldset>
                </c:if>  
            </form>

        </div>
    </body>       
</html>
