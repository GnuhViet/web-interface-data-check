package com.example;

import com.example.entity.User;
import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.intuit.fuzzymatcher.domain.ElementType.*;

@Controller
public class HomeController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private UserDAO userDAO;

    @RequestMapping({"/"})
    public String showHome(Model model) {
        List<User> users = userDAO.getUsers();

        model.addAttribute("users", users);
        model.addAttribute("user", new User());

        return "home";
    }

    @PostMapping("/process")
    public String processData(@ModelAttribute("user") User user, Model model) {
        List<User> users = userDAO.getUsers();
        List<Integer> matchIds = checkMatch(user, users);

        model.addAttribute("users", users);
        model.addAttribute("user", user);
        model.addAttribute("matchIds", matchIds);

        return "home";
    }

    public List<Integer> checkMatch(User input, List<User> list) {
        List<Integer> res = new ArrayList<>();

        input.setId(999);
        list.add(input);

        List<Document> documentList = new ArrayList<>();
        for(User u : list) {
            Document.Builder builder = new Document.Builder(String.valueOf(u.getId()));
            builder.addElement(new Element.Builder<String>().setValue(u.getName()).setType(NAME).createElement());
            builder.addElement(new Element.Builder<Integer>().setValue(u.getAge()).setType(AGE).createElement());
            builder.addElement(new Element.Builder<String>().setValue(u.getAddress()).setType(ADDRESS).createElement());
            builder.addElement(new Element.Builder<String>().setValue(u.getEmail()).setType(EMAIL).createElement());
            builder.addElement(new Element.Builder<String>().setValue(u.getPhone()).setType(PHONE).createElement());
            documentList.add(builder.createDocument());
        }

        MatchService matchService = new MatchService();
        Map<String, List<Match<Document>>> result = matchService.applyMatchByDocId(documentList);

        result.entrySet().forEach(entry -> {
            entry.getValue().forEach(match -> {
                res.add(Integer.valueOf(match.getMatchedWith().getKey()));
                logger.info("\nLogger: Data: " + match.getData() + " Matched With: " + match.getMatchedWith() + " Score: " + match.getScore().getResult());
            });
        });

        list.remove(input);

        return res;
    }
}
