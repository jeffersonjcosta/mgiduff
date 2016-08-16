<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>      
        <title>Gephi: JavaScript GEXF Viewer</title>
        <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/gexfView/styles/gexfjs.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/gexfView/styles/jquery-ui.css" />
        <script type="text/javascript" src="${pageContext.request.contextPath}/gexfView/js/jquery2.js"></script>     
        <script type="text/javascript" src="${pageContext.request.contextPath}/gexfView/js/jquerymousewheel.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/gexfView/js/jquery-ui.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/gexfView/js/gexf-js.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/gexfView/config.js"></script>
    </head>
    <body>        
        <div id="zonecentre" class="gradient">
            <canvas id="carte" width="0" height="0"></canvas>
            <ul id="ctlzoom">
                <li>
                    <a href="#" id="zoomPlusButton" title="S'approcher"> </a>
                </li>
                <li id="zoomSliderzone">
                    <div id="zoomSlider"></div>
                </li>
                <li>
                    <a href="#" id="zoomMinusButton" title="S'éloigner"> </a>
                </li>
                <li>
                    <a href="#" id="lensButton"> </a>
                </li>
                <li>
                    <a href="#" id="edgesButton"> </a>
                </li>
            </ul>
        </div>
        <div id="overviewzone" class="gradient">
            <canvas id="overview" width="0" height="0"></canvas>
        </div>
        <div id="leftcolumn">
            <div id="unfold">
                <a href="#" id="aUnfold" class="rightarrow"> </a>
            </div>
            <div id="leftcontent"></div>
        </div>
        <div id="titlebar">
            <div id="maintitle">
                <h1><a href="http://gephi.org/" target="_blank" title="Made with Gephi">Gephi: JavaScript GEXF Viewer</a></h1>
            </div>
            <form id="recherche">
                <input id="searchinput" class="grey" autocomplete="off" />
                <input id="searchsubmit" type="submit" />
            </form>
        </div>
        <ul id="autocomplete">
        </ul>        
    </body>
</html>