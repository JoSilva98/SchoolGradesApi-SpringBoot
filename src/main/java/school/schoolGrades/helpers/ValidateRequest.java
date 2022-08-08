package school.schoolGrades.helpers;

import school.schoolGrades.exception.ValueNotAllowedException;

public class ValidateRequest {
    public static void validatePages(int page, int pageSize) {
        if (page <= 0)
            throw new ValueNotAllowedException("Only pages above 0");

        if (pageSize < 1 || pageSize > 100)
            throw new ValueNotAllowedException("Only between 1 and 100 people per page");
    }
}
