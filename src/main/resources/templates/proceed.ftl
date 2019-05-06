<#import "parts/common.ftl" as c>
<@c.page>
    <form action="/data" method="post" class="my-2 card card-body">
        <p class="my-2">Building and temperature:</p>
        <div class="row mb-2">
            <div class="col">
                <input type="number" name="minT" class="form-control" placeholder="Min temperature (C)">
            </div>
            <div class="col">
                <input type="number" name="maxT" class="form-control" placeholder="Max temperature (C)">
            </div>
        </div>
        <div class="row">
            <div class="col">
                <select name="tempMode" class="custom-select">
                    <option selected>Select temperature mode</option>
                    <option value="15">15</option>
                    <option value="20">20</option>
                    <option value="25">25</option>
                </select>
            </div>
            <div class="col">
                <input type="number" name="area" class="form-control" placeholder="Building area (sq.m)">
            </div>
        </div>
        <p class="my-2">Pump parameters:</p>
        <div class="row">
            <div class="col">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <div class="input-group-text">Nominal heat productivity</div>
                    </div>
                    <input type="text" class="form-control inlineFormInputGroup" value="${pump.heatProductivity}"
                           readonly>
                </div>
            </div>
            <div class="col">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <div class="input-group-text">Nominal power consumption</div>
                    </div>
                    <input type="text" class="form-control inlineFormInputGroup" value="${pump.powerConsumption}"
                           readonly>
                </div>
            </div>
            <div class="col">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <div class="input-group-text">Nominal COP</div>
                    </div>
                    <input type="text" class="form-control inlineFormInputGroup" value="${pump.COP}" readonly>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <div class="input-group-text">Energy efficiency</div>
                    </div>
                    <input type="text" class="form-control inlineFormInputGroup"
                           value="${pump.energyEfficiencyAsString}" readonly>
                </div>
            </div>
            <div class="col">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <div class="input-group-text">Model</div>
                    </div>
                    <input type="text" class="form-control inlineFormInputGroup"
                           value="${pump.name}" readonly>
                </div>
            </div>
            <div class="col">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <div class="input-group-text">Company</div>
                    </div>
                    <input type="text" class="form-control inlineFormInputGroup"
                           value="${pump.company}" readonly>
                </div>
            </div>
        </div>
        <input type="hidden" name="pumpId" value="${pump.id}">
        <div class="form-group row">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-primary">Proceed</button>
            </div>
        </div>
    </form>
</@c.page>
