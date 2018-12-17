package net.codev.util.lore.util;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTList;
import me.dpohvar.powernbt.api.NBTManager;
import me.dpohvar.powernbt.nbt.NBTTagByteArray;
import org.bukkit.inventory.ItemStack;

import java.io.*;

public class NBTHelper {

    private static final NBTManager nbtManager;

    static {
        nbtManager = NBTManager.getInstance();
    }

    public static NBTCompound getNBT(ItemStack stack){
        NBTCompound nbtCompound = nbtManager.read(stack);
        if(nbtCompound==null)
            nbtCompound= new NBTCompound();
        return nbtCompound;
    }

    public static NBTTagByteArray objectToNBTTagByteArray(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(bos);
        stream.writeObject(o);
        stream.flush();
        return new NBTTagByteArray(bos.toByteArray());
    }

    public static <T> T nbtTagByteArrayToObject(NBTTagByteArray bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes.get());
        ObjectInputStream stream = new ObjectInputStream(bis);
        T t = (T) stream.readObject();
        stream.close();
        return t;
    }

    public static NBTTagByteArray getNBTTagByteArray(NBTCompound nbtCompound,String path){
        NBTList list = nbtCompound.getList(path);
        byte[] bytes = new byte[list.size()];
        for(int i=0;i<list.size();i++)
            bytes[i]= (byte) list.get(i);
        return new NBTTagByteArray(bytes);
    }

}
