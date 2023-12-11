package com.example.ultraspringmvc.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.BindingResult;
@Getter
@Setter
public class BadRequestException extends RuntimeException{
    private String function = null;
    private BindingResult bindingResult;
    private Object object = null;
    private String name = null;

    public BadRequestException(String function, BindingResult bindingResult) {
        this.function = function;
        this.bindingResult = bindingResult;
    }

    public BadRequestException(String function, BindingResult bindingResult, Object object,String name) {
        this.function = function;
        this.bindingResult = bindingResult;
        this.object = object;
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}
