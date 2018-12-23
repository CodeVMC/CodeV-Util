package net.codevmc.util.lore.paragraph.paragraph;

import java.util.List;
import java.util.Map;

public class TickParagraph implements Paragraph{

    private List<? extends Paragraph> loopList;
    private int index;
    private long lastUseTime = System.currentTimeMillis();

    public TickParagraph(List<? extends Paragraph> list){
        this.loopList = list;
    }

    @Override
    public String getParagraph(Map<String,? extends Object> map) {
        computeIndex();
        setLastUseTime();
        return loopList.get(index).getParagraph(map);
    }

    private void computeIndex(){
        int passTick = passTick();
        index+=passTick;
        index%=loopList.size();
    }

    private int passTick(){
        long now = System.currentTimeMillis()-lastUseTime;
        return (int) (now/50);
    }

    private void setLastUseTime(){
        lastUseTime = System.currentTimeMillis();
    }

}
