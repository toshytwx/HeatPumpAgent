<#macro pumpParam>
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
</#macro>