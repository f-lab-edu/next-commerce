package org.example.nextcommerce.validator;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@SuppressWarnings("unchecked")
public abstract class AbstractValidator<T> implements Validator {
    @Override
    public boolean supports(Class<?> clazz){
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        try{
            doValidate((T) target, errors);
        }
        catch (IllegalStateException e){
            log.error("Duplicate validate error",e);
            throw e;
        }
    }

    protected abstract void doValidate(final T dto, final Errors errors);
}
