//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.20 at 07:20:42 PM MESZ 
//


package org.matsim.jaxb.lightsignalsystemsconfig10;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for planbasedlightSignalSystemControlInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="planbasedlightSignalSystemControlInfoType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.matsim.org/files/dtd}lightSignalSystemControlInfoType">
 *       &lt;sequence>
 *         &lt;element name="lightSignalSystemPlan" type="{http://www.matsim.org/files/dtd}lightSignalSystemPlanType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "planbasedlightSignalSystemControlInfoType", propOrder = {
    "lightSignalSystemPlan"
})
public class XMLPlanbasedlightSignalSystemControlInfoType
    extends XMLLightSignalSystemControlInfoType
{

    @XmlElement(required = true)
    protected List<XMLLightSignalSystemPlanType> lightSignalSystemPlan;

    /**
     * Gets the value of the lightSignalSystemPlan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lightSignalSystemPlan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLightSignalSystemPlan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XMLLightSignalSystemPlanType }
     * 
     * 
     */
    public List<XMLLightSignalSystemPlanType> getLightSignalSystemPlan() {
        if (lightSignalSystemPlan == null) {
            lightSignalSystemPlan = new ArrayList<XMLLightSignalSystemPlanType>();
        }
        return this.lightSignalSystemPlan;
    }

}
