package br.ufrn.dct.apf.utils;

import br.ufrn.dct.apf.interfaces.IDTO;
import br.ufrn.dct.apf.interfaces.IEntity;
import org.modelmapper.ModelMapper;

public class Mapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T> T DTOtoEntity(IDTO obj, Class<T> entity) {
        return modelMapper.map(obj, entity);
    }

    public static <T> T EntityToDTO(IEntity obj, Class<T> dtoClass) {
        return modelMapper.map(obj, dtoClass);
    }
}
