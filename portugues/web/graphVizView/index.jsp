<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Mineração de Grafos | IDUFF</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <script type="text/javascript" src="js/vis.js"></script>
        <script type="text/javascript" src="js/jquery_min.js"></script>
        <link href="css/vis.css" rel="stylesheet" type="text/css" />            

        <style type="text/css">
            p {
              width: 600px;
            }
            html, body, select {
              font: 11pt arial;
            }
            #mygraph {
              width: 100%;
              height: 1000px;
              border: 1px solid lightgray;
            }
        </style>
    </head>
    <body onLoad="loadData()">
        <c:set var="dir" value="data/${param.dotGraphViz}"/>                      

        <div id="mynetwork"></div>         

        <div id="mygraph"></div>
        <script type="text/javascript">
          var container = document.getElementById('mygraph');
          var url = '';
          var network = new vis.Network(container,{},{physics:{barnesHut:{springLength:75,springConstant:0.015}}});

          function loadData () {
            $.ajax({
              type: "GET",
              url: "${dir}"
            }).done(function(data) {
              network.setOptions({
                    stabilize: false
                  });
              network.setData( {
                    dot: data
                    });
                });
            }
            loadData();
        </script>
    </body>
</html>
