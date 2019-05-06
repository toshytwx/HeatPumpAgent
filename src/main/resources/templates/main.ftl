<#import "parts/common.ftl" as c>

<@c.page>
    <div class="card-columns">
        <#list pumps as pump>
            <div class="card m-2">
                <img src="img/${pump.id}.jpg" class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title">${pump.name}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${pump.company}</h6>
                    <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
                        incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation
                        ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">COP: ${pump.COP}</li>
                    <li class="list-group-item">Phases: ${pump.phase}</li>
                    <li class="list-group-item">Energy efficiency class: ${pump.getEnergyEfficiencyAsString()}</li>
                    <li class="list-group-item">Power consumption: ${pump.powerConsumption}</li>
                    <li class="list-group-item">Heat productivity: ${pump.heatProductivity}</li>
                </ul>
                <div class="card-body">
                    <form action="/proceed" method="get">
                        <button type="submit" class="btn btn-outline-secondary">Proceed info</button>
                        <input type="hidden" name="pumpId" value="${pump.id}">
                        <#--<input type="hidden" name="_csrf" value="${_csrf}"/>-->
                    </form>
                    <a href="#" class="card-link">Compare</a>
                </div>
            </div>
        </#list>
    </div>
</@c.page>