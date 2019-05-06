<#import "parts/common.ftl" as c>

<@c.page>
    <table class="table table-bordered my-2">
        <thead>
        <tr>
            <th>#</th>
            <th>Pump model</th>
            <th>Temperature mode</th>
            <th>Get data</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <#assign counter = 1>

            <#list reports as report>
                <td>${counter}</td>
                <td>${report.pump.name}</td>
                <td>${report.temperatureMode.temperature}</td>
                <td>
                    <form action="/loadData" method="get" class="form-inline my-2 my-lg-0">
                        <input type="hidden" id="pumpId" name="pumpId" value="${report.pump.id}">
                        <input type="hidden" id="reportId" name="reportId" value="${report.id}">
                        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Overview</button>
                    </form>
                </td>
            </#list>
        </tr>
        </tbody>
    </table>
</@c.page>