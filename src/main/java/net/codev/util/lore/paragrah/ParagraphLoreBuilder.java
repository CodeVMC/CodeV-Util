package net.codev.util.lore.paragrah;

import net.codev.util.lore.Lore;
import net.codev.util.lore.paragrah.line.ListParagraphLine;
import net.codev.util.lore.paragrah.line.ParagraphLine;
import net.codev.util.lore.paragrah.paragraph.Paragraph;
import net.codev.util.lore.paragrah.paragraph.TextParagraph;

public class ParagraphLoreBuilder {
    private Lore lore = new ParagraphListLore();

    public LineBuilder editLore(){
        return new LineBuilder(this);
    }

    private void addLine(ParagraphLine line){
        lore.addLine(line);
    }

    public Lore build(){
        return lore;
    }

    public class LineBuilder{

        private ParagraphLoreBuilder loreBuilder;
        private ParagraphLine line = new ListParagraphLine();

        public LineBuilder(ParagraphLoreBuilder loreBuilder){
            this.loreBuilder = loreBuilder;
        }

        public LineBuilder nextLine(){
            loreBuilder.addLine(line);
            return new LineBuilder(loreBuilder);
        }

        public Lore build(){
            loreBuilder.addLine(line);
            return loreBuilder.build();
        }

        public LineBuilder appendParagraph(Paragraph paragraph){
            line.addParagraph(paragraph);
            return this;
        }

        public LineBuilder appendString(String string){
            this.appendParagraph(new TextParagraph(string));
            return this;
        }

    }
}
