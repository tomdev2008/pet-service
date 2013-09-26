<?php
//We've included ../Includes/FusionCharts.php and ../Includes/DBConn.php, which contains
//functions to help us easily embed the charts and connect to a database.
include("../Includes/FusionCharts.php");
include("../Includes/DBConn.php");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <link href="../assets/ui/css/style.css" rel="stylesheet" type="text/css" />
        <title>
            FusionCharts - Database + JavaScript Example
        </title>

        <?php
        //In this example, we show a combination of database + JavaScript rendering using FusionCharts.

        //The entire app (page) can be summarized as under. This app shows the break-down
        //of factory wise output generated. In a pie chart, we first show the sum of quantity
        //generated by each factory. These pie slices, when clicked would show detailed date-wise
        //output of that factory.

        //The XML data for the pie chart is fully created in PHP at run-time. PHP interacts
        //with the database and creates the XML for this.
        //Now, for the column chart (date-wise output report), we do not submit request to the server.
        //Instead we store the data for the factories in JavaScript arrays. These JavaScript
        //arrays are rendered by our PHP Code (at run-time). We also have a few defined JavaScript
        //functions which react to the click event of pie slice.

        //We've used an MySQL databasw which contains two databases.

        //Before the page is rendered, we need to connect to the database and get the
        //data, as we'll need to convert this data into JavaScript variables.
        $link = connectToDB();

        //The following string will contain the JS Data and variables.
        //This string will be built in PHP and rendered at run-time as JavaScript.
        $jsVarString = "";

        //Generate the chart element
        $strXML = "<chart caption='Factory Output report' subCaption='By Quantity' pieSliceDepth='30' showBorder='1' formatNumberScale='0' numberSuffix=' Units' >";

        //initialize index
        $indexCount = 0;

        // Fetch all factory records
        $strQuery = "select * from Factory_Master";
        $result = mysql_query($strQuery) or die(mysql_error());

        //Iterate through each factory
        if ($result) {
            while($ors = mysql_fetch_array($result)) {

                $indexCount++;

                //Create JavaScript code to add sub-array to data array
                //data is an array defined in JavaScript (see below)
                //We've added \t and \n to data so that if you View Source of the
                //output HTML, it will appear properly. It helps during debugging
                $jsVarString .= "\t\t data[" . $indexCount . "] = new Array(); \n";

                //Now create second query to get date-wise details for this factory
                $strQuery = "select * from Factory_Output where FactoryId=" . $ors['FactoryId'] . " order by DatePro Asc";
                $result2 = mysql_query($strQuery) or die(mysql_error());

                if ($result2) {
                    while($ors2 = mysql_fetch_array($result2)) {
                        //Put this data into JavaScript as another nested array.
                        //Finally the array would look like data[factoryIndex][i][dataLabel,dataValue]
                        $jsVarString .= "\t\t" . "data[" . $indexCount . "].push(new Array('" . datePart("d",$ors2["DatePro"]) . "/" . datePart("m",$ors2['DatePro']) . "'," . $ors2['Quantity'] . ")); \n";
                    }
                    //free the resultset
                    mysql_free_result($result2);
                }

                //Now create another query to get details for this factory
                $strQuery = "select sum(Quantity) as TotOutput from Factory_Output where FactoryId=" . $ors['FactoryId'];
                $result2 = mysql_query($strQuery) or die(mysql_error());
                $ors2 = mysql_fetch_array($result2);
                //Generate <set label='..' value='..' link='..' />
                //Note that we're setting link as updateChart(factoryIndex) - JS Function
                $strXML .= "<set label='" . $ors['FactoryName'] . "' value='" . $ors2['TotOutput'] . "' link='javaScript:updateChart(" . $indexCount . ")'/>";
            }
        }

        //Finally, close <chart> element
        $strXML .= "</chart>";

        ?>
        <script LANGUAGE="Javascript" SRC="../../FusionCharts/FusionCharts.js">
        </script>

        <script LANGUAGE="JavaScript">
            //Here, we use a mix of server side script (PHP) and JavaScript to
            //render our data for factory chart in JavaScript variables. We'll later
            //utilize this data to dynamically plot charts.

            //All our data is stored in the data array. From PHP, we iterate through
            //each recordset of data and then store it as nested arrays in this data array.
            var data = new Array();

<?php
//Write the data as JavaScript variables here
echo $jsVarString;
//The data is now present as arrays in JavaScript. Local JavaScript functions
//can access it and make use of it. We'll see how to make use of it.
?>

                    /**
                     * updateChart method is invoked when the user clicks on a pie slice.
                     * In this method, we get the index of the factory, build the XML data
                     * for that that factory, using data stored in data array, and finally
                     * update the Column Chart.
                     *	@param	factoryIndex	Sequential Index of the factory.
                     */
                    function updateChart(factoryIndex){
                        //Storage for XML data document
                        var strXML = "<chart palette='2' caption='Factory " + factoryIndex  + " Output ' subcaption='(In Units)' xAxisName='Date' showValues='1'>";

                        //Add <set> elements
                        var i=0;
                        for (i=0; i<data[factoryIndex].length; i++){
                            strXML = strXML + "<set label='" + data[factoryIndex][i][0] + "' value='" + data[factoryIndex][i][1] + "' />";
                        }

                        //Closing Chart Element
                        strXML = strXML + "</chart>";

                        //Get reference to chart object using Dom ID "FactoryDetailed"
                        var chartObj = FusionCharts("FactoryDetailed");
                        //Update it's XML
                        chartObj.setXMLData(strXML);
                    }
        </script>

        <!--[if IE 6]>
        <script type="text/javascript" src="../assets/ui/js/DD_belatedPNG_0.0.8a-min.js"></script>

<script>
          /* select the element name, css selector, background etc */
          DD_belatedPNG.fix('img');

          /* string argument can be any CSS selector */
        </script>
        <![endif]-->

        <style type="text/css">
            h2.headline {
                font: normal 110%/137.5% "Trebuchet MS", Arial, Helvetica, sans-serif;
                padding: 0;
                margin: 25px 0 25px 0;
                color: #7d7c8b;
                text-align: center;
            }
            p.small {
                font: normal 68.75%/150% Verdana, Geneva, sans-serif;
                color: #919191;
                padding: 0;
                margin: 0 auto;
                width: 664px;
                text-align: center;
            }
        </style>

    </head>
    <body>

        <div id="wrapper">

            <div id="header">
                

               <div class="logo"><a class="imagelink"  href="http://www.fusioncharts.com/" target="_blank"><img src="../assets/ui/images/fusionchartsv3.2-logo.png" width="131" height="75" alt="FusionCharts XT logo" /></a></div>
                <h1 class="brand-name">FusionCharts XT</h1>
                <h1 class="logo-text">FusionCharts Database + JavaScript Example</h1>
            </div>

            <div class="content-area">
                <div id="content-area-inner-main">
                    <h2 class="headline">Inter-connected charts - Click on any pie slice to see detailed chart below</h2>

                    <div class="gen-chart-render">

 <center>
            <?php
            //Initialize the Pie chart with sum of production for each of the factories
            //$strXML will be used to store the entire XML document generated

            //Create the chart - Pie 3D Chart with data from strXML
            echo renderChart("../../FusionCharts/Pie3D.swf", "", $strXML, "FactorySum", 500, 250, false, false);
            ?>
            <br>
            <?php
            //Column 2D Chart with changed "No data to display" message
            //We initialize the chart with <chart></chart>
            echo renderChart("../../FusionCharts/Column2D.swf?ChartNoDataText=Please select a factory from pie chart above to view detailed data.", "", "<chart></chart>", "FactoryDetailed", 600, 250, false, true);
            ?>
        </center>
                    </div>
                    <div class="clear"></div>
                    <p>&nbsp;</p>
                    <p class="small"> The charts in this page have been dynamically generated using data contained in a database. We've NOT hard-coded the data in JavaScript</p>

                    <div class="underline-dull"></div>
                </div>
            </div>

            <div id="footer">
                <ul>
                    <li><a href="../index.html"><span>&laquo; Back to list of examples</span></a></li>
                    <li class="pipe">|</li>
                    <li><a href="../NoChart.html"><span>Unable to see the chart above?</span></a></li>
                </ul>
            </div>
        </div>
    </body>
</html>
