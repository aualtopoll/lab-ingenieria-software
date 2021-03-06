package cl.usach.ingesoft.agendator.business.validator;

import cl.usach.ingesoft.agendator.util.BusinessException;
import cl.usach.ingesoft.agendator.util.FormatHelper;
import org.apache.log4j.Logger;

public class Validator {

    private static final Logger logger = Logger.getLogger(Validator.class);

    public static void shouldNotBeNull(Object o) {
        if (o == null) {
            throw new BusinessException("Null value is not allowed.");
        }
    }

    public static void shouldBeNull(Object o) {
        if (o != null) {
            throw new BusinessException("Value should not be set (null was expected)");
        }
    }

    public static void shouldNotBeNegative(int val) {
        if (val < 0) {
            throw new BusinessException("Value cannot be negative (" + val + ")");
        }
    }

    public static void shouldBeMoney(String param) {
        try {
            if (FormatHelper.parseCurrency(param) < 0) {
                throw new BusinessException("Value is not a correct money amount");
            }
        } catch (BusinessException e1) {
            throw e1;
        } catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
            throw new RuntimeException(e2);
        }
    }

    public static void shouldBeFound(Object o) {
        if (o == null) {
            throw new BusinessException("Data could not be found");
        }
    }

    public static void shouldNotBeFound(Object o) { // same as shouldBeNull
        if (o != null) {
            throw new BusinessException("Data should not be found");
        }
    }

    public static <T extends Comparable<T>> void shouldBeOrdered(T... args) {
        for (int i = 1; i < args.length; i++) {
            T c1 = args[i - 1];
            T c2 = args[i];
            if (c1.compareTo(c2) > 0) {
                throw new BusinessException(c1.toString() + " > " + c2.toString());
            }
        }
    }

    public static void shouldBeEmpty(String value) {
        if (!"".equals(value)) {
            throw new BusinessException("Value should not be set.");
        }
    }
    public static void shouldNotBeEmpty(String username) {
        if (username == null || username.isEmpty()) {
            throw new BusinessException("Value cannot be empty.");
        }
    }

    public static void shouldBeNumeric(String numeric) {
        try {
            Integer.parseInt(String.valueOf(numeric));
        } catch (Exception e) {
            throw new BusinessException("Value should be numeric (integer).");
        }
    }

    public static void shouldBeEquals(Object a, Object b) {
        if (a == null) {
            shouldBeNull(b);
        }
        if (b == null) {
            shouldBeNull(a);
        }
        if (!a.equals(b)) {
            throw new BusinessException("Values should be equal.");
        }
    }

    public static void shouldBeUsername(String username) {
        shouldNotBeNull(username);
        if (!username.matches("[a-zA-Z0-9_]+")) {
            throw new BusinessException("Username can only contain letters lowercase/uppercase, digits and underscore.");
        }
    }

    public static void shouldBeFormattedDate(String birthDate) {
        try {
            FormatHelper.parseDate(birthDate);
        } catch (Exception e) {
            throw new BusinessException("error with date:" + e.getMessage());
        }
    }

    public static void shouldBeBooleanStr(String hired) {
        boolean cond = "true".equals(hired) || "false".equals(hired);
        if (!cond) {
            throw new BusinessException("error, boolean value required (true|false): invalid value \"" + hired + "\"");
        }
        // ok
    }

    public static void shouldBePhoneNumber(String phoneNumStr) {
        shouldNotBeEmpty(phoneNumStr);
        if (!phoneNumStr.matches("\\d{7,12}")) {
            throw new BusinessException("Phone numbers can only contain between 7 and 12 digits.");
        }
    }

    public static void shouldBeEmail(String emailStr) {
        shouldNotBeEmpty(emailStr);
        if (!emailStr.matches("(.+)@(.+)\\.(.+)")) {
            throw new BusinessException("Invalid email ("+ emailStr +")");
        }
    }

    public static void shouldBePassword(String passwordStr) {
        shouldNotBeEmpty(passwordStr);
        if (passwordStr.length() < 4) {
            throw new BusinessException("Password should have 4 characters or more");
        }
        if (passwordStr.length() > 12) {
            throw new BusinessException("Password should have 12 characters or less");
        }
    }

    public static char getDv(String rutS) {
        rutS = rutS.toLowerCase();
        int factor = 2;
        int calculoDV = 0;
        for (int i = rutS.length()-1; i >= 0; i--) {
            char r = rutS.charAt(i);
            if (('0' <= r && r <= '9') || r == 'k') {
                calculoDV += (r - '0') * factor;
                factor++;
                if (factor == 8){
                    factor = 2;
                }
            } else {
                throw new BusinessException("Invalid character for RUN: " + r);
            }

        }
        calculoDV = 11 - (calculoDV % 11);
        return (calculoDV == 10
                ? 'K' : (calculoDV == 11
                ? '0' : String.valueOf(calculoDV).charAt(0)));

    }

    public static int shouldBeValidRun(String runWithDv) {
        runWithDv = runWithDv.replace("\\.", "").replace(" ", "");
        String[] parts = runWithDv.split("-");
        if (parts.length  != 2) {
            throw new BusinessException(String.format("El RUN '%s' es invalido (debe ser de la forma xxxxx-x).", runWithDv));
        }
        if (parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new BusinessException(String.format("El RUN '%s' es invalido (debe ser de la forma xxxxx-x).", runWithDv));
        }

        if (getDv(parts[0]) != parts[1].charAt(0)) {
            throw new BusinessException(String.format("El RUN '%s' es invalido (el digito verificador no coincide).", runWithDv));
        }
        return Integer.parseInt(parts[0]);
    }
}
