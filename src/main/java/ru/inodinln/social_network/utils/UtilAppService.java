package ru.inodinln.social_network.utils;

import org.springframework.beans.BeanUtils;
import ru.inodinln.social_network.utils.interfaces.Convertable;

import java.util.ArrayList;
import java.util.List;

public class UtilAppService {


    /**
     * Don't let anyone instantiate this class.
     */
    private UtilAppService() {}

    /*
     * Returns the quotient of the arguments, throwing an exception if the
     * result overflows an {@code int}.  Such overflow occurs in this method if
     * {@code x} is {@link Integer#MIN_VALUE} and {@code y} is {@code -1}.
     * In contrast, if {@code Integer.MIN_VALUE / -1} were evaluated directly,
     * the result would be {@code Integer.MIN_VALUE} and no exception would be
     * thrown.
     * <p>
     * If {@code y} is zero, an {@code ArithmeticException} is thrown
     * (JLS {@jls 15.17.2}).
     * <p>
     * The built-in remainder operator "{@code %}" is a suitable counterpart
     * both for this method and for the built-in division operator "{@code /}".
     *
     * @param obj1 object to be converted
     * @param className the divisor
     * @return the quotient {@code x / y}
     * @throws ArithmeticException if {@code y} is zero or the quotient
     * @throws InstantiationException if {@code y} is zero or the quotient
     *
     * overflows an int
     */

   /* public static <T, T1 extends Convertable<T>, T2 extends Convertable<T>>
    T2 convertEntityAndDTO(T1 obj1, Class<T2> className) throws InstantiationException, IllegalAccessException {

        try {
            T2 obj2 = className.newInstance();
            BeanUtils.copyProperties(obj1, obj2);
            return obj2;
        }
        catch (IllegalAccessException e) {
            throw new IllegalAccessException("Currently executing method doesn't have access to the definition, " +
                    "constructor or field of " + className.getName());
        }
        catch (InstantiationException e) {
            throw new InstantiationException("Unable to create the object of the class " + className.getName());
        }
    }*/

    public static <T, T1 extends Convertable<T>, T2 extends Convertable<T>>
    Iterable<T2> convertCollectionsEntityAndDTO(Iterable<T1> list, Class<T2> className) {

        List<T2> targetList = new ArrayList<>();

        list.forEach(element -> {

            T2 obj;

            try {
                // obj = new Class<T2>();
                obj = className.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Runtime exception");
            }
            BeanUtils.copyProperties(element, obj);
            targetList.add(obj);
        });
        return targetList;
    }

}
