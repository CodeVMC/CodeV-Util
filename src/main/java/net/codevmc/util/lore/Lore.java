package net.codevmc.util.lore;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Lore extends Serializable {

    List<String> get(Map<String,? extends Object> map);

}
