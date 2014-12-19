package coursework.mymerryxmas;

import java.io.Serializable;

/**
 * Created by stephen on 04/12/2014.
 */
public class mxRSSDataItem implements Serializable {

    // *********************************************
    // Declare variables etc.
    // *********************************************

    private String itemTitle;
    private String itemDesc;
    private String itemLink;

    // *********************************************
    // Declare getters and setters etc.
    // *********************************************

    public String getItemTitle()
    {
        return this.itemTitle;
    }

    public void setItemTitle(String sItemTitle)
    {
        this.itemTitle = sItemTitle;
    }

    public String getItemDesc()
    {
        return this.itemDesc;
    }

    public void setItemDesc(String sItemDesc)
    {
        this.itemDesc = sItemDesc;
    }

    public String getItemLink()
    {
        return this.itemLink;
    }

    public void setItemLink(String sItemLink)
    {
        this.itemLink = sItemLink;
    }


    // **************************************************
    // Declare constructor.
    // **************************************************

    public mxRSSDataItem()
    {
        this.itemTitle = "";
        this.itemDesc = "";
        this.itemLink = "";
    }

    @Override
    public String toString() {
        String northPoleNews;
        northPoleNews = "mcRSSDataItem [itemTitle=" + itemTitle;
        northPoleNews = ", itemDesc=" + itemDesc;
        northPoleNews = ", itemLink=" + itemLink +"]";
        return northPoleNews;
    }

}

