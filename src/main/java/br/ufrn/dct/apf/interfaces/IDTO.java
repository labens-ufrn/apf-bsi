package br.ufrn.dct.apf.interfaces;

import java.io.Serializable;

public interface IDTO extends Serializable {
    IEntity convertToEntity();
}
