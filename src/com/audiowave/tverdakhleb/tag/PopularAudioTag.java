package com.audiowave.tverdakhleb.tag;

import com.audiowave.tverdakhleb.entity.Audiotrack;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class PopularAudioTag extends TagSupport {
    private List<Audiotrack> list;

    public void setList(List<Audiotrack> list) {
        this.list = list;
    }

    public int doStartTag() throws JspException {
        if (list != null && !list.isEmpty()) {
            for (Audiotrack audio : list) {
                try {
                    pageContext.getOut().write(
                            "<div class=\"block\">\n" +
                                    "\t\t\t\t<div class=\"image\">\n" +
                                    "\t\t\t\t\t<img class=\"img1\" src=\""+audio.getAlbumCoverURI()+"\"  width=\"300px\" height=\"300px\"" +
                                    " alt=\"" + audio.getSingerName() + "\"></a>\n" +
                                    "\t\t\t\t</div>\n" +
                                    "\t\t\t\t<div class=\"singer\">\n" +
                                    "\t\t\t\t\t<p>"+audio.getSingerName()+"</p>\n" +
                                    "\t\t\t\t</div>\n" +
                                    "\t\t\t\t<div class=\"song\">\n" +
                                    "\t\t\t\t\t<p>"+audio.getName()+"</p>\n" +
                                    "\t\t\t\t</div>\n" +
                                    "\t\t\t\t<div>\n" +
                                    "\t\t\t\t\t<audio src=\"" + audio.getLocation() + "\" controls preload></audio>\n" +
                                    "\t\t\t\t</div>\n" +
                                    "\t\t\t</div>");
                } catch (IOException e) {
                    throw new JspException(e);
                }
            }
        }
        return SKIP_BODY;
    }
}