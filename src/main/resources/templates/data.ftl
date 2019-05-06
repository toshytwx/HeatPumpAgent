<#import "parts/common.ftl" as c>

<@c.page>
    <div class="my-2 card card-body">
        <div id="charts">
            <div class="row my-2">
                <div class="col chart-container" style="position: relative;">
                    <canvas id="heatProductivityChart"></canvas>
                </div>
                <div class="col chart-container" style="position: relative;">
                    <canvas id="powerConsumptionChart"></canvas>
                </div>
            </div>
            <div class="row my-2">
                <div class="col chart-container" style="position: relative;">
                    <canvas id="lossToProducedChart"></canvas>
                </div>
                <div class="col chart-container" style="position: relative;">
                    <canvas id="workLoadChart"></canvas>
                </div>
            </div>
        </div>
        <div id="foo" class="my-2">
            <table id="data" class="table table-bordered">
                <thead>
                <tr>
                    <th>Temperature</th>
                    <th>Heat loss</th>
                    <th>Heat productivity normalization coefficient</th>
                    <th>Normalized heat Productivity</th>
                    <th>Power consumption normalization coefficient</th>
                    <th>Normalized power consumption</th>
                    <th>Heat productivity of additional heater</th>
                    <th>Workload coefficient</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="my-2 card card-body">
            <a href="#" id="downloadPdf">Download Report Page as PDF</a>
            <form action="/sendMail" method="post">
                <p>Save your report for further usage?</p>
                <div class="form-group">
                    <label for="exampleInputEmail1">Email address</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroupPrepend">@</span>
                        </div>
                        <input type="email" name="email" class="form-control" id="exampleInputEmail1"
                               aria-describedby="emailHelp"
                               placeholder="Enter email">
                    </div>
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone
                        else.
                    </small>
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" name="notify" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Notify with latest news?</label>
                </div>
                <input type="hidden" name="reportId" value="${report.id}">
                <button type="submit" class="btn btn-primary">Send</button>
            </form>
        </div>
    </div>
    <script>
        var ctx = document.getElementById("heatProductivityChart").getContext('2d');
        var temperatureData = [
            <#list report.tempRate as t>${t}, </#list>
        ];
        var heatProductivityData = [
            <#list report.normalizedHeatProductivity as h>${h}, </#list>
        ];
        var nominalHeatProductivityData = new Array(heatProductivityData.length);
        nominalHeatProductivityData.fill(${pump.heatProductivity});
        var heatProductivityChart = new Chart(ctx, {
            "type": "line",
            "data": {
                "labels": temperatureData,
                "datasets": [{
                    "label": "Normalized heat productivity",
                    "data": heatProductivityData,
                    "fill": false,
                    "borderColor": "rgb(75, 192, 192)",
                    "lineTension": 0.1
                },
                    {
                        "label": "Nominal heat productivity",
                        "data": nominalHeatProductivityData,
                        "fill": false,
                        "borderColor": "rgb(255, 0, 0)",
                        "lineTension": 0.1
                    }]
            },
            "options": {
                "responsive": true
            }
        });
        var ctx2 = document.getElementById("powerConsumptionChart").getContext('2d');
        var powerConsumptionData = [
            <#list report.normalizedPowerConsumption as p>${p}, </#list>
        ];
        var nominalPowerConsumptionData = new Array(powerConsumptionData.length);
        nominalPowerConsumptionData.fill(${pump.powerConsumption});
        var powerConsumptionChart = new Chart(ctx2, {
            "type": "line",
            "data": {
                "labels": temperatureData,
                "datasets": [{
                    "label": "Normalized power consumption",
                    "data": powerConsumptionData,
                    "fill": false,
                    "borderColor": "rgb(75, 192, 192)",
                    "lineTension": 0.1
                },
                    {
                        "label": "Nominal power consumption",
                        "data": nominalPowerConsumptionData,
                        "fill": false,
                        "borderColor": "rgb(255, 0, 0)",
                        "lineTension": 0.1
                    }]
            },
            "options": {
                "responsive": true
            }
        });
        var ctx3 = document.getElementById("lossToProducedChart").getContext('2d');
        var heatLossData = [
            <#list report.heatLoss as hl>${hl}, </#list>
        ];
        var lossToProducedChart = new Chart(ctx3, {
            "type": "line",
            "data": {
                "labels": temperatureData,
                "datasets": [
                    {
                        "label": "Produced heat",
                        "data": heatProductivityData,
                        "fill": true,
                        "backgroundColor": "rgba(75, 192, 192, 0.1)",
                        "borderColor": "rgb(75, 192, 192)",
                        "lineTension": 0.1
                    },
                    {
                        "label": "Consumed heat",
                        "data": heatLossData,
                        "fill": true,
                        "backgroundColor": "rgba(255, 0, 0, 0.1)",
                        "borderColor": "rgb(255, 0, 0)",
                        "lineTension": 0.1
                    }]
            },
            "options": {
                "responsive": true
            }
        });
        var ctx4 = document.getElementById("workLoadChart").getContext('2d');
        var workLoadData = [
            <#list report.workloadRate as w>${w}, </#list>
        ];
        var lossToProducedChart = new Chart(ctx4, {
            "type": "bar",
            "data": {
                "labels": temperatureData,
                "datasets": [
                    {
                        "label": "Workload",
                        "data": workLoadData,
                        "fill": false,
                        "backgroundColor": "rgba(54, 162, 235, 0.2)",
                        "borderColor": "rgb(54, 162, 235)",
                        "borderWidth": 1
                    }
                ]
            },
            "options": {
                "responsive": true
            }
        });
        var records = JSON.parse(${report.toJSON()});
        $('#data').dynatable({
            dataset: {
                records: records
            }
        });

        $('#downloadPdf').click(function (event) {
            // get size of report page
            var reportPageHeight = $('.container').innerHeight();
            var reportPageWidth = $('.container').innerWidth();

            // create a new canvas object that we will populate with all other canvas objects
            var pdfCanvas = $('<canvas />').attr({
                id: "canvaspdf",
                width: reportPageWidth,
                height: reportPageHeight
            });

            // keep track canvas position
            var pdfctx = $(pdfCanvas)[0].getContext('2d');
            var pdfctxX = 0;
            var pdfctxY = 0;
            var buffer = 100;
            pdfctx.beginPath();
            pdfctx.rect(pdfctxX, pdfctxY, reportPageWidth, reportPageHeight);
            pdfctx.fillStyle = "white";
            pdfctx.fill();
            var canvasHeight;
            // for each chart.js chart
            $("canvas").each(function (index) {
                // get the chart height/width
                canvasHeight = $(this).innerHeight();
                var canvasWidth = $(this).innerWidth();

                // draw the chart into the new canvas

                pdfctx.drawImage($(this)[0], pdfctxX, pdfctxY, canvasWidth, canvasHeight);
                pdfctxX += canvasWidth + buffer;

                // our report page is in a grid pattern so replicate that in the new canvas
                if (index % 2 === 1) {
                    pdfctxX = 0;
                    pdfctxY += canvasHeight + buffer;
                }
            });

            // create new pdf and add our new canvas as an image
            var pdf = new jsPDF('l', 'pt', [reportPageWidth, reportPageHeight]);
            pdf.addImage($(pdfCanvas)[0], 'PNG', 0, 0);

            source = document.getElementById('data').outerHTML;
            margins = {
                top: canvasHeight * 2 + 10,
                bottom: 60,
                left: pdfctxX,
                width: reportPageWidth
            };
            pdf.fromHTML(
                source, // HTML string or DOM elem ref.
                margins.left, // x coord
                margins.top, { // y coord
                    'width': margins.width
                },

                function (dispose) {
                    pdf.save('Test.pdf');
                }, margins);
        });
    </script>
</@c.page>