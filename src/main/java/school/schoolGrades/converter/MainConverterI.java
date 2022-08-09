package school.schoolGrades.converter;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public interface MainConverterI {
    ModelMapper getMapper();

    default <T, D> D converter(T inClass, Class<D> outClass) {
        return getMapper().map(inClass, outClass);
    }

    default <D, T> List<D> listConverter(List<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> getMapper().map(entity, outCLass))
                .collect(Collectors.toList());
    }

    default <T, D> D updateConverter(T update, D result) {
        getMapper().map(update, result);
        return result;
    }
}
