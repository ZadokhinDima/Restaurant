package model.tags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PriceTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(PriceTag.class);

    private int price;

    public void setPrice(int price){
        this.price = price;
    }

    @Override
    public int doStartTag() throws JspException {
        String formattedPrice = String.format("%.2f", price/100.0);
        formattedPrice += " \u20B4";
        try {
            pageContext.getOut().write(formattedPrice);
        } catch (IOException e) {
            LOGGER.error("Error during the output of the formatted price: ", e);
            throw new RuntimeException(e);
        }
        return SKIP_BODY;
    }
}
