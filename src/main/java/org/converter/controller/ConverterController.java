package org.converter.controller;

import org.converter.controller.form.ConverterForm;
import org.converter.domain.UserCredentials;
import org.converter.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class ConverterController {

    private CurrencyConversionService currencyConversionService;

    @Autowired
    public ConverterController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping("/converter")
    public String getConverter(Model model) throws Exception {
        UserCredentials user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        setModelAttributes(model, user.getId());
        model.addAttribute("converterForm", new ConverterForm());

        return "converter";
    }

    @PostMapping("/convert")
    public String convert(ConverterForm converterForm, Model model) throws Exception {
        UserCredentials user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        setModelAttributes(model, user.getId());
        String result = currencyConversionService.calculate(
                user.getId(),
                converterForm.getCurrencyFrom(),
                converterForm.getCurrencyTo(),
                converterForm.getAmount(),
                Optional.ofNullable(converterForm.getDate()));
        model.addAttribute("converterForm", converterForm);
        model.addAttribute("result", result);

        return "converter";
    }

    private void setModelAttributes(Model model, Long userId) throws Exception {
        model.addAttribute("history", currencyConversionService.getUserHistory(userId));
        model.addAttribute("currencies", currencyConversionService.getCurrencies());
    }

}
