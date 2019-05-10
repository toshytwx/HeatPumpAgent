package diploma.controller;

import diploma.domain.entities.FormattedDate;
import diploma.domain.entities.Pump;
import diploma.domain.entities.Report;
import diploma.domain.util.TemperatureData;
import diploma.repos.PumpRepo;
import diploma.repos.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class ReportController {
    @Autowired
    private PumpRepo pumpRepo;

    @Autowired
    private ReportRepo reportRepo;

    @GetMapping("/proceed")
    public String proceedPump(Model model,
                              @RequestParam String pumpId) {
        Optional<Pump> pumpOption = pumpRepo.findById(Long.parseLong(pumpId));
        pumpOption.ifPresent(pump -> model.addAttribute("pump", pump));
        return "proceed";
    }

    @PostMapping("/data")
    public String getData(Model model,
                          @RequestParam String area,
                          @RequestParam String loss,
                          @RequestParam String tempMode,
                          @RequestParam String pumpId,
                          @RequestParam String dateFrom,
                          @RequestParam String dateTo) {
        Optional<Pump> pumpOption = pumpRepo.findById(Long.parseLong(pumpId));
        Map<FormattedDate, Integer> temperatureForPeriod = TemperatureData.getTemperatureForPeriod(dateFrom + "T00:00", dateTo + "T00:00");
        if (pumpOption.isPresent()) {
            Pump pump = pumpOption.get();
            Double heatLoss = Double.parseDouble(area) * Double.parseDouble(loss);
            Report report = new Report(temperatureForPeriod, Integer.parseInt(tempMode), pump, heatLoss);
            reportRepo.save(report);
            model.addAttribute("report", report);
            model.addAttribute("pump", pump);
        }
        return "data";
    }

    @GetMapping("/loadData")
    public String loadData(Model model,
                           @RequestParam String reportId,
                           @RequestParam String pumpId) {
        Optional<Pump> pumpOption = pumpRepo.findById(Long.parseLong(pumpId));
        Optional<Report> reportOption = reportRepo.findById(Long.parseLong(reportId));
        if (pumpOption.isPresent() && reportOption.isPresent()) {
            Pump pump = pumpOption.get();
            Report report = reportOption.get();
            model.addAttribute("report", report);
            model.addAttribute("pump", pump);
        }
        return "data";
    }
}
