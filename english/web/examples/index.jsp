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
                &rsaquo;&rsaquo; Examples
                <hr/>
            </h2>
            
            <ul>
                <li><a href="${pageContext.request.contextPath}/examples/files/prerequisites.csv">Example of CSV file with Disciplines and Prerequisite</a></li>
                <li><a href="${pageContext.request.contextPath}/examples/files/equivalences.csv">Example of CSV file with Disciplines and Equivalents</a></li>
                <li><a href="${pageContext.request.contextPath}/examples/files/accompaniments.csv">Example of CSV file with accompaniment of the students</a></li>
                <li><a href="${pageContext.request.contextPath}/examples/files/historical.csv">Example of CSV file with student's historical</a></li>
            </ul>  

            <br />

            <fieldset>
                <legend>&nbsp;Orientations&nbsp;</legend>
                <ul>
                    <li>The input file must be in TXT or CSV format;</li>
                    <li>The values must be separated by ';' (semicolon);</li>                                       
                    <li>The datasets generated will have a structure based on gSpan (YAN and HAN, 2002) - as can be seen in section <b><a href="${pageContext.request.contextPath}/datasets_ijis/structure">Datasets -> Structure</a></b></li>                                                                            
                    <li>For the generation of graphs of the program flowchar, should be sent a file (CSV or TXT) containing disciplines with their respective prerequisites;</li>                    
                    <li>It is important to note that the disciplines not compulsory and those that do not have prerequisites should not be present at this file</li>
                    <li>In the case of co-requirements, the discipline more theoretical workload should appear as a prerequisite of the discipline with workload practical.</li>                    
                    <li>For the generation of the historical graphs of students, is necessary the sending onf two files: one with the accompaniment of students (semester per semester) and another with historical</li>
                    <li>In the file of accompaniment of students is necessary include the identification of the student, the situation in each semester, the year / semester and your curriculum in that semester;</li>
                    <li>The historical of the student should contain your id, the year and semester, the discipline that he studied this semester, the grade in the discipline, the note of the additional verification (if necessary), the frequency and the situation of the student in the discipline;</li>                    
                </ul>                                         
            </fieldset> 

            <br /><br />            

            <fieldset>
                <legend>&nbsp;Formats&nbsp;</legend>
                <ul>
                    <li>
                        Standard format - Disciplines e Prerequisites: 
                        <br />
                        ID_CURRICULUM;ID_DISCIPLINE;PERIOD_DISCIPLINE;CHT;CHP;ID_PRE_REQUISITE;PERIOD_PRE_REQUISITE                     
                    </li> 
                    <li>
                        Standard format - Equivalences: 
                        <br />
                        ID_CURRICULUM;ID_DISCIPLINE_CURRICULUM;ID_EQUIVALENCE                    
                    </li> 
                    <li>
                        Standard format - Accompaniment: 
                        <br />
                        ID_STUDENT;STATUS;SITUATION;YEAR_SEMESTER;ID_CURRICULUM
                    </li> 
                    <li>
                        Standard format - Historical curriculum: 
                        <br />
                        ID_STUDENT;YEAR_SEMESTER;ID_DISCIPLINE;GRADE;GRADE_SV;FREQUENCY;SITUATION_DISCIPLINE
                    </li> 
                </ul>
            </fieldset>         
        </div>
    </body>       
</html>
