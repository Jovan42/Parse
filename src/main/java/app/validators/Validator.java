package app.validators;

import app.Main;
import app.anotations.NotEmpty;
import app.anotations.NotNull;
import app.anotations.Valid;
import app.exceptions.BadRequestException;
import app.model.BaseEntity;
import app.model.impls.User;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator {
    public static void validate(Method method, BaseEntity entity) {
        List<String> errors = new ArrayList<>();
        Annotation[][] annotations = method.getParameterAnnotations();
        final Integer[] count = {0};
        Arrays.asList(annotations).forEach((param) -> {
            Arrays.asList(param).forEach((annotation -> {
                forAnnotation(annotation, method, entity, errors, count);
            }));
            count[0]++;
        });
        errors.forEach(System.out::println);
        if(!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }

    private static void validateAnnotations(Annotation fieldAnnotation, Field field, BaseEntity entity, List<String> errors) {
        if(fieldAnnotation.annotationType().equals(NotNull.class)) {
            try {
                String s = (String) entity.getClass().getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(entity);
                if (s == null) errors.add(field.getName() + " can not be null");
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if(fieldAnnotation.annotationType().equals(NotEmpty.class)) {
            try {
                String s = (String) entity.getClass().getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)).invoke(entity);
                if (s == null || s.equalsIgnoreCase("")) errors.add(field.getName() + " can not be empty");
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void forAnnotation(Annotation annotation, Method method, BaseEntity entity, List<String> errors, Integer[] count) {
        if(annotation.annotationType().equals(Valid.class)) {
            Parameter parameters = method.getParameters()[count[0]];
            Arrays.asList(parameters.getType().getDeclaredFields()).forEach((field -> {
                Arrays.asList(field.getDeclaredAnnotations()).forEach((fieldAnnotation->{
                    validateAnnotations(fieldAnnotation, field, entity, errors);
                }));
            }));
        }
    }
}
