package diploma.controller;

import diploma.domain.entities.Customer;
import diploma.domain.entities.Pump;
import diploma.domain.entities.Report;
import diploma.repos.CustomerRepo;
import diploma.repos.PumpRepo;
import diploma.repos.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private PumpRepo pumpRepo;

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "proceed";
    }

    @GetMapping("/")
    public String main(Model model) {
        Iterable<Pump> allPumps = pumpRepo.findAll();
        model.addAttribute("pumps", allPumps);
        return "main";
    }

    @GetMapping("/filter")
    public String filter(Model model, @RequestParam String filter) {
        Customer customer = customerRepo.findByEmail(filter);
        if (customer != null) {
            List<Report> reports = reportRepo.findByOwner(customer);
            reports = reports == null ? new ArrayList<>() : reports;
            model.addAttribute("reports", reports);
            return "filter";
        } else {
            Iterable<Pump> allPumps = pumpRepo.findAll();
            model.addAttribute("pumps", allPumps);
            return "main";
        }
    }
}
