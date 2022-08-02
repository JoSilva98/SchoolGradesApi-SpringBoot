package school.schoolGrades.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MainConverter implements MainConverterI {

    private final ModelMapper modelMapper;

    public MainConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public ModelMapper getMapper() {
        return this.modelMapper;
    }
}
