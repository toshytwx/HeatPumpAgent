<#import "parts/common.ftl" as c>
<#import "parts/pump.ftl" as p>
<@c.page>
    <form action="/data" method="post" class="my-2 card card-body">
        <div class="row">
            <div class="col">
                <label for="start">Start date:</label>
                <input type="date" id="start" name="dateFrom" class="form-control" min="2019-01-01" max="2019-12-31">
            </div>
            <div class="col">
                <label for="end">End date:</label>
                <input type="date" id="end" name="dateTo" class="form-control" min="2019-01-01" max="2019-12-31">
            </div>
        </div>
        <p class="my-2">Building and temperature:</p>
        <div class="row my-2">
            <div class="col">
                <select name="tempMode" class="custom-select">
                    <option selected>Select temperature mode</option>
                    <option value="15">15</option>
                    <option value="20">20</option>
                    <option value="25">25</option>
                </select>
            </div>
            <div class="col">
                <input type="number" name="area" class="form-control" placeholder="Building area (sq.m)" step="0.1"
                       min="0">
            </div>
            <div class="col">
                <input type="number" name="loss" class="form-control" placeholder="Building heat loss (kV/h)"
                       step="0.1" min="0">
            </div>
        </div>
        <@p.pumpParam></@p.pumpParam>
        <input type="hidden" name="pumpId" value="${pump.id}">
        <div class="form-group row">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-primary">Proceed</button>
            </div>
        </div>
    </form>
</@c.page>
