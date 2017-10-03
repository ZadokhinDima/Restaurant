package model.tags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class WeightTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(PriceTag.class);

    private int weight;

    public void setWeight(int weight){
        this.weight = weight;
    }

    @Override
    public int doStartTag() throws JspException {
        String formattedWeight = "" + weight;
        formattedWeight += " g";
        try {
            pageContext.getOut().write(formattedWeight);
        } catch (IOException e) {
            LOGGER.error("Error during the output of the formatted weight: ", e);
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }
}
