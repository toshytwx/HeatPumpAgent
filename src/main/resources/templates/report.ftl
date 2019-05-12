<#import "parts/common.ftl" as c>
<#import "parts/pump.ftl" as p>

<@c.page>
    <div class="my-2 card card-body">
        <p>Report:</p>
        <div class="my-2 card card-body">
            <div class="row">
                <div class="col-8">
                    <@p.pumpParam></@p.pumpParam>
                </div>
                <div class="col-4 text-center">
                    <img src="img/${pump.id}.jpg" class="img-fluid">
                </div>
            </div>
        </div>
        <div id="charts">
            <div class="row my-2">
                <div class="col chart-container" style="position: relative;">
                    <canvas class="bar-chart" id="tempFreqChart"></canvas>
                </div>
            </div>
            <div class="row my-2">
                <div class="col chart-container" style="position: relative;">
                    <canvas class="line-chart" id="tempConditionsChart"></canvas>
                </div>
            </div>
            <div class="row my-2">
                <div class="col chart-container" style="position: relative;">
                    <canvas class="line-chart" id="heatProductivityChart"></canvas>
                </div>
            </div>
            <div class="row my-2">
                <div class="col chart-container" style="position: relative;">
                    <canvas class="line-chart" id="powerConsumptionChart"></canvas>
                </div>
            </div>
            <div class="row my-2">
                <div class="col chart-container" style="position: relative;">
                    <canvas class="line-chart" id="lossToProducedChart"></canvas>
                </div>
            </div>
            <div class="row my-2">
                <div class="col chart-container" style="position: relative;">
                    <canvas class="bar-chart" id="workLoadChart"></canvas>
                </div>
            </div>
        </div>
        <div class="table-responsive my-2">
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
    <script src="js/saveCharts.js"></script>
    <script src="js/printTable.js"></script>
    <script>
        var barDictionary = {
            0: {
                label: {
                    x: "Date",
                    y: "Workload"
                }, data: {
                    x: [
                        <#list report.workDate as t>new Date("${t}".replace('T', ' ')).toLocaleString(), </#list>
                    ],
                    y: [
                        <#list report.workloadRate as w>${w}, </#list>
                    ]
                }
            },
            1: {
                label: {
                    x: "Temperature",
                    y: "Hours"
                }, data: {
                    x: [
                        <#list keys as t>${t}, </#list>
                    ],
                    y: [
                        <#list values as v>${v} * 0.5,
        </#list>
        ]
        }
        }
        }
        ;

        var lineDictionary = {
            0: {
                label: "Temperature", data: [
                    <#list report.tempRate as t>${t}, </#list>
                ]
            },
            1: {
                label: "Heat Productivity Kw", data: [
                    <#list report.normalizedHeatProductivity as h>${h}, </#list>
                ]
            },
            2: {
                label: "Power consumption Kw/h", data: [
                    <#list report.normalizedPowerConsumption as p>${p}, </#list>
                ]
            },
            3: {
                label: "Heat loss kW", data: [
                    <#list report.heatLoss as hl>${hl}, </#list>
                ]
            }
        };

        $(".bar-chart").each(function (index) {
            var ctx = this.getContext('2d');
            var barDictElement = barDictionary[index];
            new Chart(ctx, {
                "type": "bar",
                "data": {
                    "labels": barDictElement.data.x,
                    "datasets": [{
                        "label": barDictElement.label.y,
                        "data": barDictElement.data.y,
                        "fill": false,
                        "backgroundColor": "rgba(54, 162, 235, 0.2)",
                        "borderColor": "rgb(54, 162, 235)",
                        "borderWidth": 1
                    }]
                },
                "options": {
                    "responsive": true,
                    "scales": {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: barDictElement.label.x
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: barDictElement.label.y
                            }
                        }]
                    }
                }
            });
        });


        $(".line-chart").each(function (index) {
            var ctx = this.getContext('2d');
            var lineDictionaryElement = lineDictionary[index];
            new Chart(ctx, {
                "type": "line",
                "data": {
                    "labels": [
                        <#list report.workDate as t>new Date("${t}".replace('T', ' ')).toLocaleString(), </#list>
                    ],
                    "datasets": [{
                        "label": lineDictionaryElement.label,
                        "data": lineDictionaryElement.data,
                        "fill": false,
                        "borderColor": "rgb(75, 192, 192)",
                        "lineTension": 0.1
                    }]
                },
                "options": {
                    "responsive": true,
                    "scales": {
                        xAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: 'Date'
                            }
                        }],
                        yAxes: [{
                            display: true,
                            scaleLabel: {
                                display: true,
                                labelString: lineDictionaryElement.label
                            }
                        }]
                    }
                }
            });
        });
        window.onload = printTable.bind(JSON.parse(${report.toJSON()}));
    </script>
</@c.page>