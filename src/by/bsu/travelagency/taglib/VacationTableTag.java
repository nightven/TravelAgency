package by.bsu.travelagency.taglib;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Михаил on 9/10/2016.
 */
public class VacationTableTag extends TagSupport {

    /**
     * Do start tag.
     *
     * @return the int
     * @throws JspTagException the jsp tag exception
     */
    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<div class=\"table-responsive\">");
            out.write("<table class=\"table table-condensed table-hover\">");
            out.write("<thead class=\"admin-table-list\">\n");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    /**
     * Do end tag.
     *
     * @return the int
     * @throws JspTagException the jsp tag exception
     */
    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</tbody>\n </table>\n </div>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }

}
