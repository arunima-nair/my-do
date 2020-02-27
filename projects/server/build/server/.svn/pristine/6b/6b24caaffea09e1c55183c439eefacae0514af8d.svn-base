package ae.dt.common.validator;

import ae.dt.common.controller.GlobalControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<Enum, String>
{
    private Enum annotation;
    private static Logger logger = LoggerFactory.getLogger(EnumValueValidator.class);

    @Override
    public void initialize(Enum annotation)
    {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext)
    {
        boolean result = false;
        try {
            Object[] enumValues = this.annotation.enumClass().getEnumConstants();

            if(enumValues != null)
            {
                for(Object enumValue:enumValues)
                {
                    if(valueForValidation.equals(enumValue.toString())
                            || (this.annotation.ignoreCase() && valueForValidation.equalsIgnoreCase(enumValue.toString())))
                    {
                        result = true;
                        break;
                    }
                }
            }
        } catch (NullPointerException e) {
            logger.error("isValid Exception caught");
        }

        return result;
    }
}
