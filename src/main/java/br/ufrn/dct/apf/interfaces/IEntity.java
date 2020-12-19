package br.ufrn.dct.apf.interfaces;

import java.io.Serializable;

public interface IEntity extends Serializable {
    public IDTO convertToDTO();
}
