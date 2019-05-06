package diploma.controller;

import diploma.domain.entities.Customer;
import diploma.domain.entities.Report;
import diploma.domain.handler.EmailHandler;
import diploma.repos.CustomerRepo;
import diploma.repos.ReportRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@Controller
public class MailController {

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @PostMapping("/sendMail")
    public ModelAndView send(@RequestParam String email,
                             @RequestParam String reportId) throws IOException, MessagingException {
        long id = Long.parseLong(reportId);
        Optional<Report> optionalReport = reportRepo.findById(id);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            Customer customer;
            if (!email.isEmpty()) {
                customer = customerRepo.findByEmail(email);
                if (customer == null) {
                    customer = createAndSaveNewCustomer(email, true);
                }
            } else {
                customer = createAndSaveNewCustomer(RandomStringUtils.randomAlphabetic(10) + "@gmail.com",
                        true);
            }
            report.setOwner(customer);
            reportRepo.save(report);
            EmailHandler.sendEmail(customer, report);
        }
        return new ModelAndView("redirect:/");
    }

    private Customer createAndSaveNewCustomer(@RequestParam String email, @RequestParam Boolean notify) {
        Customer customer = new Customer(email);
        customer.setShareNews(notify);
        customerRepo.save(customer);
        return customer;
    }
}
