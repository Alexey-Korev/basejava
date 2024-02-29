package ru.basejava;

import ru.basejava.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        field.get(r);
        System.out.println(r);
        field.set(r, "new_uuid");
        System.out.println(r);

        Method method = r.getClass().getMethod("toString", r.getClass());
        System.out.println(method);
    }
}
